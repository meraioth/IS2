package cl.udec.ingsoftware.proyecto_is.Actividades;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.io.Serializable;

import cl.udec.ingsoftware.proyecto_is.Fragmentos.BusquedaFragment;
import cl.udec.ingsoftware.proyecto_is.Presentador.Catalogo;
import cl.udec.ingsoftware.proyecto_is.R;

public class MapaBusquedaItinerarioActivity extends AppCompatActivity implements BusquedaFragment.OnSucursalSelectedListener {

    private BusquedaFragment busquedaFragment;

    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private static Catalogo catalogo;


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

        catalogo = new Catalogo(this.getApplicationContext());
        busquedaFragment = BusquedaFragment.newInstance((Serializable) catalogo);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_mapa_busqueda_itinerario, busquedaFragment);
        fragmentTransaction.commit();


    }

    @Override
    public void OnSucursalSelected(int posicion) {
        //TODO: rellenar método que es llamado desde el fragment
    }
}
