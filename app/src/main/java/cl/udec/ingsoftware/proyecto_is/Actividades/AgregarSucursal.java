package cl.udec.ingsoftware.proyecto_is.Actividades;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import cl.udec.ingsoftware.proyecto_is.AuxiliarVista.ListAdapter;
import cl.udec.ingsoftware.proyecto_is.Modelo.Tripleta;
import cl.udec.ingsoftware.proyecto_is.Modelo.Usuario;
import cl.udec.ingsoftware.proyecto_is.Presentador.Catalogo;
import cl.udec.ingsoftware.proyecto_is.Presentador.PresentadorSucursal;
import cl.udec.ingsoftware.proyecto_is.R;

public class AgregarSucursal extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener{

    EditText nombre;
    EditText rut;
    Spinner comuna;
    ListView servicios;
    EditText descripcion;
    Button guardar;
    Catalogo catalogo;
    PresentadorSucursal msucursal;
    int id_suc;

    private ArrayList<String> misServicios;

    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_sucursal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        try {
            catalogo = new Catalogo(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        nombre = (EditText) findViewById(R.id.nombre_sucursal);
        rut = (EditText) findViewById(R.id.rut_empresa);
        comuna = (Spinner) findViewById(R.id.listado_comunas);
        servicios = (ListView) findViewById(R.id.listado_servicios);
        servicios.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        descripcion = (EditText) findViewById(R.id.descripcion_sucursal);
        guardar = (Button) findViewById(R.id.guardar_sucursal);

        comuna.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, catalogo.getComunas()));
        servicios.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        servicios.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, catalogo.getServicios()));

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), comuna.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                try {
                    guardar(nombre.getText().toString(),rut.getText().toString(),descripcion.getText().toString(), comuna.getSelectedItem().toString());
                    startActivity(new Intent(getApplicationContext(),Vista_empresario.class));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void guardar(String nombre, String rut, String descripcion, String comuna) throws SQLException {
        int idSucursalAgregada = catalogo.setDatosSucursal(nombre, rut, descripcion, comuna);
        if(idSucursalAgregada!=0){
            SparseBooleanArray array = servicios.getCheckedItemPositions();
            ArrayList<Integer> servArray = new ArrayList<>();
            for (int i = 0; i < servicios.getAdapter().getCount();i++){
                if(array.get(i)){
                    servArray.add((Integer) catalogo.getIdsServicios().get(i));
                }
            }
            catalogo.setServicioSucursal(servArray,idSucursalAgregada);
        }
    }

/*    private void guardarServicio(String servicios) {
        msucursal.setServicioSucursal(servicios);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.agregar_sucursal, menu);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            gotoHome();
        } else if (id == R.id.mis_sucursales) {
            gotovista_empresario();
        } else if (id == R.id.cerrar_sesion) {
            cerrar_sesion();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void gotoHome() {
        Intent intent = new Intent(this, MapaBusquedaItinerarioActivity.class);
    }

    private void gotovista_empresario() {
        Intent intent = new Intent(this, Vista_empresario.class);
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
