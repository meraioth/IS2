package cl.udec.ingsoftware.proyecto_is.old;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import cl.udec.ingsoftware.proyecto_is.R;

public class MapaTematico extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Sucursal> sucursales;
    private Catalogo cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cat= MainActivity.catalogo;
        //cat = (Catalogo) getIntent().getSerializableExtra("catalogo");
        cat.connect();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_tematico);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);





        mapFragment.getMapAsync(this);


    }





    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        System.out.println("tipo de mapa :"+mMap.getMapType());
        ArrayList<Sucursal> suc= cat.getSucursales();
        LatLng aux=new LatLng(0,0);
        float zoom = (float)8.5;
        for (Sucursal x: suc) {

            if (x.getLatitud() != (double) -1) {
                aux = new LatLng(x.getLatitud(), x.getLongitud());
                mMap.addMarker(new MarkerOptions().position(aux).title(x.getNombre()));
                System.out.println("nombre: " + x.getNombre() + " lat:" + x.getLatitud() + " long:" + x.getLongitud());
            }
        }

       // LatLng sydney = new LatLng(-34, 151);
       // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-37.726562, -73.353960),zoom));
    }
}
