package cl.udec.ingsoftware.proyecto_is;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TabHost tabs;
    private ListView lista,lista1;

    DBlocal db_local;
    static Catalogo catalogo;
    private GoogleApiClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openfirst_time();
        catalogo = new Catalogo(this.getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //tabhost
        tabs = (TabHost) findViewById(R.id.tabhost);
        tabs.setup();
        //tab1
        TabHost.TabSpec spec = tabs.newTabSpec("Lugares");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Lugares");
        tabs.addTab(spec);
        //tab2
        spec = tabs.newTabSpec("Itinerarios");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Itinerario");
        tabs.addTab(spec);
        savebdstatus();
        catalogo.connect();

        lista = (ListView) findViewById(R.id.id_lista1);
        lista1 = (ListView) findViewById(R.id.id_lista2);
        ArrayList servicios = catalogo.servicios_to_array();
        ArrayList itinerarios = catalogo.itinerarios_to_array();
        lista.setAdapter(new ListAdapter(this,servicios));
        lista1.setAdapter(new ListAdapter(this,itinerarios));
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.id_titulo);;
                String strText = textView.getText().toString();
                ver_sucursal(strText);

            }
        });
        lista1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.id_titulo);;
                String strText = textView.getText().toString();
                ver_itinerario(strText);

            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Este boton crea un itinerario", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        SharedPreferences sp = getSharedPreferences("config_inicial",0);
        if(sp.getBoolean("internet",true)){

        }

       // System.out.print(db_local.getDatabaseName()+ "   "+db_local.getReadableDatabase().query(DBlocal.Persona.TABLE_NAME,columnas,null,null,null,null,null,null).getColumnName(0));

    }

    private void openfirst_time() {

        SharedPreferences sp = getSharedPreferences("init",0);

        if(sp.getBoolean("first",true)){ // segundo parametro es valor por defecto si es que no existe value "first"

            db_local = new DBlocal(this.getApplicationContext());
            sp.edit().putBoolean("first",false).commit();
        }

    }

    private void ver_itinerario(String strText) {
        Intent intent = new Intent(MainActivity.this,VisualizacionSucursal.class);
        intent.putExtra("Titulo",strText);
        startActivity(intent);
    }

    private void ver_sucursal(String strText) {
        Intent intent = new Intent(MainActivity.this,VisualizacionSucursal.class);
        intent.putExtra("Titulo",strText);
        startActivity(intent);
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
        getMenuInflater().inflate(R.menu.main, menu);
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

        if (id == R.id.nav_gallery) {
            // Handle the camera action
        } else if (id == R.id.login) {
            vista_login();
        } else if (id == R.id.mapa_tematico) {
            vista_mapa_tematico();

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            vista_osm();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void vista_osm() {
        Intent intent = new Intent(this, OSM.class);
        //intent.putExtra("catalogo", catalogo);
        startActivity(intent);
    }

    private void vista_mapa_tematico() {
        Intent intent = new Intent(this, MapaTematico.class);
        //intent.putExtra("catalogo", catalogo);
        startActivity(intent);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        //catalogo.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
    private void vista_login(){
        Intent intent = new Intent(this, Login.class);

        startActivity(intent);
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void savebdstatus(){
        SharedPreferences sp = getSharedPreferences("config_inicial",0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("creacion_bd",true);

        editor.commit();

    }
}
