package cl.udec.ingsoftware.proyecto_is.Actividades;

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
import android.widget.Toast;

import java.io.Serializable;

import cl.udec.ingsoftware.proyecto_is.Fragmentos.BusquedaAvanzadaFragment;
import cl.udec.ingsoftware.proyecto_is.Fragmentos.BusquedaFragment;
import cl.udec.ingsoftware.proyecto_is.Fragmentos.ItinerarioFragment;
import cl.udec.ingsoftware.proyecto_is.Modelo.Itinerario;
import cl.udec.ingsoftware.proyecto_is.Presentador.Catalogo;
import cl.udec.ingsoftware.proyecto_is.R;

public class MapaBusquedaItinerarioActivity extends AppCompatActivity implements BusquedaFragment.OnSucursalSelectedListener, ItinerarioFragment.OnFragmentInteractionListener, BusquedaAvanzadaFragment.OnBusuqedaAvanzadaInteractionListener, SearchView.OnQueryTextListener {

    private BusquedaFragment busquedaFragment;
    private ItinerarioFragment itinerarioFragment;
    private BusquedaAvanzadaFragment busquedaAvanzadaFragment;

    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private static Catalogo catalogo;

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

        catalogo = new Catalogo(this.getApplicationContext());

        //inicialización de fragments
        busquedaFragment = BusquedaFragment.newInstance((Serializable) catalogo);
        itinerarioFragment = ItinerarioFragment.newInstance((Serializable) catalogo);
        busquedaAvanzadaFragment = BusquedaAvanzadaFragment.newInstance();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_mapa_busqueda_itinerario, busquedaFragment);
        fragmentTransaction.commit();


    }

    @Override
    public void OnSucursalSelected(int posicion) {
        System.out.println(posicion);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        fragmentManager = getSupportFragmentManager();
        switch (item.getItemId()) {
            case R.id.busqueda_avanzada:
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
    public void onSearchAdvanced() {

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
