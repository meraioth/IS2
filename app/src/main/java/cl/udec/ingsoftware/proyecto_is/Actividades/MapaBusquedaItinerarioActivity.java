package cl.udec.ingsoftware.proyecto_is.Actividades;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.sql.SQLException;

import cl.udec.ingsoftware.proyecto_is.Fragmentos.BusquedaAvanzadaFragment;
import cl.udec.ingsoftware.proyecto_is.Fragmentos.BusquedaFragment;
import cl.udec.ingsoftware.proyecto_is.Fragmentos.ItinerarioFragment;
import cl.udec.ingsoftware.proyecto_is.Fragmentos.MapaFragment;
import cl.udec.ingsoftware.proyecto_is.Presentador.Catalogo;
import cl.udec.ingsoftware.proyecto_is.Presentador.PresentadorSucursal;
import cl.udec.ingsoftware.proyecto_is.R;

public class MapaBusquedaItinerarioActivity extends AppCompatActivity implements BusquedaFragment.OnSucursalSelectedListener, ItinerarioFragment.OnFragmentInteractionListener, BusquedaAvanzadaFragment.OnBusuqedaAvanzadaInteractionListener, SearchView.OnQueryTextListener {

    private BusquedaFragment busquedaFragment;
    private MapaFragment mapaFragment;
    private ItinerarioFragment itinerarioFragment;
    private BusquedaAvanzadaFragment busquedaAvanzadaFragment;

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
                    fragmentTransaction.replace(R.id.content_mapa_busqueda_itinerario, itinerarioFragment);
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
        //mPresentadorSucursal = new PresentadorSucursal(this.getApplicationContext());
        /*mapaFragment = MapaFragment.newInstance((Serializable) catalogo);
        busquedaFragment = BusquedaFragment.newInstance((Serializable) catalogo);
        itinerarioFragment = ItinerarioFragment.newInstance((Serializable) catalogo);*/
        mapaFragment = MapaFragment.newInstance();
        busquedaFragment = BusquedaFragment.newInstance();
        itinerarioFragment = ItinerarioFragment.newInstance();
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
}
