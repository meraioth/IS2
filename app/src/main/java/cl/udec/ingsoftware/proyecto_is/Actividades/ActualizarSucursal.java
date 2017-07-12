package cl.udec.ingsoftware.proyecto_is.Actividades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cl.udec.ingsoftware.proyecto_is.Modelo.Tripleta;
import cl.udec.ingsoftware.proyecto_is.Modelo.Usuario;
import cl.udec.ingsoftware.proyecto_is.Presentador.Catalogo;
import cl.udec.ingsoftware.proyecto_is.Presentador.PresentadorSucursal;
import cl.udec.ingsoftware.proyecto_is.R;

public class ActualizarSucursal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {
    EditText nombre,rut,descripcion;
    int id_suc;
    Button actualizar,subir_foto;
    Spinner mis_sucursales;
    Catalogo catalogo;
    PresentadorSucursal msucursal;
    Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_sucursal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        try {
            catalogo=new Catalogo(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        actualizar = (Button) findViewById(R.id.actualizar);
        nombre = (EditText) findViewById(R.id.edit_nombre);
        rut = (EditText) findViewById(R.id.edit_rut);
        descripcion = (EditText) findViewById(R.id.edit_descripcion);
        usuario = getUsuarioSP();
        mis_sucursales = (Spinner) findViewById(R.id.sucursales);
        //TODO: esto no está entregando nada por favor revisar el metodo getSucursalesbyID
        Log.d("id usuario", String.valueOf(usuario.getId()));
        List<String> sucursales = catalogo.getSucursalesById(usuario.getId());
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sucursales);
        mis_sucursales.setAdapter(dataAdapter);
        mis_sucursales.setOnItemSelectedListener(this);
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"actualizando...",Toast.LENGTH_SHORT).show();
                Log.d("mensaje:",nombre.toString()+descripcion.toString());
                actualizar(nombre.getText().toString(),descripcion.getText().toString());
            }
        });
    }


    private void actualizar(String name ,String Descripcion){
        msucursal.setNombre(name);
        msucursal.setDescripcion(Descripcion);

    }
    //TODO: sacar este modelo de aca
    private Usuario getUsuarioSP() {
        SharedPreferences sp = this.getSharedPreferences("usuario",0);
        String name= sp.getString("name","");
        String email = sp.getString("email","");
        int rol = sp.getInt("rol",0);
        Log.d("rol",""+rol);
        int id = sp.getInt("id",0);
        Log.d("int id ",id+"");
        return new Usuario(name,email,rol,id);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actualizar_sucursal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            gotoHome();
        } else if (id == R.id.mis_sucursales) {
            gotovista_empresario();
        } else if (id == R.id.cerrar_sesion) {
        cerrar_sesion();
        }else if ( id== R.id.actualizar_sucursal){
            refresh();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void refresh() {
        startActivity(new Intent(this,ActualizarSucursal.class));
    }

    private void gotovista_empresario() {
        Intent intent = new Intent(this, Vista_empresario.class);
        startActivity(intent);
    }

    private void gotoHome() {
        Intent intent = new Intent(this, MapaBusquedaItinerarioActivity.class);
        startActivity(intent);
    }

    private void cerrar_sesion() {
        SharedPreferences sp = this.getSharedPreferences("usuario",0);
        sp.edit().putInt("rol",0).commit();
        Intent intent = new Intent(this, MapaBusquedaItinerarioActivity.class);

        startActivity(intent);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ArrayList<Tripleta> suc = catalogo.getBuscarKeyword(parent.getItemAtPosition(position).toString());
        Tripleta aux = suc.get(0);
        this.id_suc = aux.get_id();
        msucursal = new PresentadorSucursal(view.getContext(),aux.get_id());
        if(msucursal!=null){
            nombre.setText(msucursal.get_name());
            descripcion.setText(msucursal.get_descripcion());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
