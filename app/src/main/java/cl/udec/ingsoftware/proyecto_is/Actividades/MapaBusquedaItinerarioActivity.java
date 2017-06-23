package cl.udec.ingsoftware.proyecto_is.Actividades;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.sql.SQLException;

import cl.udec.ingsoftware.proyecto_is.Fragmentos.BusquedaAvanzadaFragment;
import cl.udec.ingsoftware.proyecto_is.Fragmentos.BusquedaFragment;
import cl.udec.ingsoftware.proyecto_is.Fragmentos.BusquedaItinerario;
import cl.udec.ingsoftware.proyecto_is.Fragmentos.ItinerarioFragment;
import cl.udec.ingsoftware.proyecto_is.Fragmentos.ItinerariosFragment;
import cl.udec.ingsoftware.proyecto_is.Fragmentos.MapaFragment;
import cl.udec.ingsoftware.proyecto_is.Presentador.Catalogo;
import cl.udec.ingsoftware.proyecto_is.Presentador.PresentadorSucursal;
import cl.udec.ingsoftware.proyecto_is.R;

public class MapaBusquedaItinerarioActivity extends AppCompatActivity implements BusquedaFragment.OnSucursalSelectedListener, ItinerarioFragment.OnFragmentInteractionListener, BusquedaAvanzadaFragment.OnBusuqedaAvanzadaInteractionListener, SearchView.OnQueryTextListener, NavigationView.OnNavigationItemSelectedListener, BusquedaItinerario.OnBusuqedaAvanzadaItinerarioInteractionListener {

    private BusquedaFragment busquedaFragment;
    private MapaFragment mapaFragment;
    private ItinerarioFragment itinerarioFragment;
    private ItinerariosFragment itinerariosFragment;
    private BusquedaAvanzadaFragment busquedaAvanzadaFragment;
    private BusquedaItinerario busquedaItinerarioFragment;

    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    public static Catalogo catalogo;
    private PresentadorSucursal mPresentadorSucursal;

    private SearchView mBusqueda;
    private Toolbar mToolbar;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_mapa:
                    fragmentTransaction.replace(R.id.content_mapa_busqueda_itinerario, mapaFragment);
                    fragmentTransaction.commit();

                    return true;
                case R.id.navigation_busqueda:
                    fragmentTransaction.replace(R.id.content_mapa_busqueda_itinerario, busquedaFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_itinerario:
                    fragmentTransaction.replace(R.id.content_mapa_busqueda_itinerario, itinerariosFragment);
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        }

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_busqueda_itinerario);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(1).setChecked(true);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_busqueda);
        setSupportActionBar(mToolbar);
        try {
            catalogo = new Catalogo(this.getApplicationContext());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //mPresentadorSucursal = new PresentadorSucursal(this.getApplicationContext());
        /*mapaFragment = MapaFragment.newInstance((Serializable) catalogo);
        busquedaFragment = BusquedaFragment.newInstance((Serializable) catalogo);
        itinerarioFragment = ItinerarioFragment.newInstance((Serializable) catalogo);*/
        mapaFragment = MapaFragment.newInstance();
        busquedaFragment = BusquedaFragment.newInstance();
        //itinerarioFragment = ItinerarioFragment.newInstance();
        itinerariosFragment = ItinerariosFragment.newInstance();

        busquedaAvanzadaFragment = BusquedaAvanzadaFragment.newInstance();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_mapa_busqueda_itinerario, busquedaFragment);
        fragmentTransaction.commit();


    }

    @Override
    public void OnSucursalSelected(int id) {
        Intent intent = new Intent(this,VisualizacionSucursal.class);
        intent.putExtra(VisualizacionSucursal.ARG_PRESENTADOR,id);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        fragmentManager = getSupportFragmentManager();
        switch (item.getItemId()) {
            case R.id.busqueda_avanzada:
                busquedaAvanzadaFragment = BusquedaAvanzadaFragment.newInstance();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_mapa_busqueda_itinerario, busquedaAvanzadaFragment);
                fragmentTransaction.commit();
                return true;
            case R.id.cerrar_avanzada:
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_mapa_busqueda_itinerario, busquedaFragment);
                fragmentTransaction.commit();
            case R.id.busqueda_itinerario_avanzada:
                busquedaItinerarioFragment= BusquedaItinerario.newInstance();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_mapa_busqueda_itinerario, busquedaItinerarioFragment);
                fragmentTransaction.commit();
                return true;
            case R.id.cerrar_itinerario_avanzada:
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_mapa_busqueda_itinerario, itinerariosFragment);
                fragmentTransaction.commit();



            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onCancelAdvancedSearch() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_mapa_busqueda_itinerario, busquedaFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onSearchAdvanced(String str_duracion, String str_estacion) {
        itinerariosFragment.onSearchAdvanced(str_duracion,str_estacion);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_mapa_busqueda_itinerario,itinerariosFragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onSearchAdvanced(String str_comuna, String str_categoria, String str_servicio) {
        busquedaFragment.onSearchAdvanced(str_comuna,str_categoria,str_servicio);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_mapa_busqueda_itinerario, busquedaFragment);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gallery) {
            // Handle the camera action
        } else if (id == R.id.login) {
            vista_login();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
    private void vista_login(){
        Intent intent = new Intent(this, Login.class);

        startActivity(intent);
    }
}
