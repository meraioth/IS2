package cl.udec.ingsoftware.proyecto_is.Actividades;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cl.udec.ingsoftware.proyecto_is.AuxiliarVista.SucursalAdapter;
import cl.udec.ingsoftware.proyecto_is.Modelo.Sucursal;
import cl.udec.ingsoftware.proyecto_is.Modelo.Usuario;
import cl.udec.ingsoftware.proyecto_is.Presentador.Catalogo;
import cl.udec.ingsoftware.proyecto_is.Presentador.PresentadorSucursal;
import cl.udec.ingsoftware.proyecto_is.R;

public class Vista_empresario extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    TextView bienvenido, name, bienvenido2;
    ListView mis_sucursales;
    Usuario usuario;
    ArrayAdapter<String> adaptador;
    Catalogo catalogo;
    Button agregar_sucursal_boton;
    int id_suc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_empresario);
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
        usuario = getUsuarioSP();
        bienvenido = (TextView) findViewById(R.id.welcometext);
        name = (TextView)findViewById(R.id.name_empresario);
        name.setText(usuario.getName());
        bienvenido2 = (TextView) findViewById(R.id.welcome2);
        List<String> lista_sucursales = catalogo.getSucursalesById(usuario.getId());
        mis_sucursales = (ListView)findViewById(R.id.sucursalesfull);
        adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, lista_sucursales);
        mis_sucursales.setAdapter(adaptador);

        mis_sucursales.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Context context = getApplicationContext();
                Intent intent = new Intent(context, ActualizarSucursal.class);
                //intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);

            }
        });
        mis_sucursales.setLongClickable(true);

        mis_sucursales.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Vista_empresario.this);
                builder.setCancelable(true);
                builder.setTitle("Desea eliminar esta sucursal?");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        String sucursal = (String) mis_sucursales.getItemAtPosition(position);
                        try {
                            catalogo.eliminarSucursal(sucursal);
                            adaptador.clear();
                            adaptador.addAll(catalogo.getSucursalesById(usuario.getId()));
                            adaptador.notifyDataSetChanged();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }

                });
                builder.setNegativeButton("Cancelar",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                );
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }
            });


        agregar_sucursal_boton = (Button) findViewById(R.id.agregar_sucursal);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
        getMenuInflater().inflate(R.menu.vista_empresario, menu);
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

        if (id == R.id.principal) {
            gotoPrincipal();
        } else if (id == R.id.actualizar_sucursal) {
            gotoActualizarSucursal();
        } else if (id == R.id.cerrar_sesion) {
            cerrar_sesion();
        } else if (id == R.id.mis_sucursales){
            gotoVerSucursales();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void gotoActualizarSucursal() {
        startActivity(new Intent(this,ActualizarSucursal.class));

    }

    private void gotoVerSucursales() {
        startActivity(new Intent(this,Vista_empresario.class));

    }

    private void gotoPrincipal() {
        startActivity(new Intent(this,MapaBusquedaItinerarioActivity.class));
    }
    private void cerrar_sesion() {
        SharedPreferences sp = this.getSharedPreferences("usuario",0);
        sp.edit().putInt("rol",0).commit();
        Intent intent = new Intent(this, MapaBusquedaItinerarioActivity.class);

        startActivity(intent);
    }

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
}
