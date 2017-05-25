package cl.udec.ingsoftware.proyecto_is.Fragmentos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.udec.ingsoftware.proyecto_is.Actividades.MapaBusquedaItinerarioActivity;
import cl.udec.ingsoftware.proyecto_is.Presentador.Catalogo;
import cl.udec.ingsoftware.proyecto_is.R;

/**
 * Created by meraioth on 16-05-17.
 */

public class MapaFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private static final String ARG_PRESENTADOR = "presentador";
    private Catalogo mPresentador;
    Map<String,Integer> iconos = new HashMap<String,Integer>();

    private HashMap<String, Integer> mHashMap = new HashMap<>();

    //es la activity
    private BusquedaFragment.OnSucursalSelectedListener mSucursalListener;
    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPresentador= MapaBusquedaItinerarioActivity.catalogo;
        }

        setHasOptionsMenu(true);

    }
    MapView mMapView;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mPresentador= MapaBusquedaItinerarioActivity.catalogo;

        View rootView = inflater.inflate(R.layout.location_fragment, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        // Spinner element
        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner_categoria);
        spinner.setOnItemSelectedListener(this);

        final ArrayList<String> categories = mPresentador.getCategorias();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setVisibility(View.VISIBLE);



        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                ArrayList latitudes = mPresentador.getLatitudes();
                ArrayList longitudes = mPresentador.getLongitudes();
                ArrayList nombre = mPresentador.getSucursales();
                ArrayList categorias = mPresentador.getAllCategorias();
                ArrayList ids = mPresentador.getIds();

                for(int i = 0;i<latitudes.size();i++){
                    if((Double)latitudes.get(i)!=-1){
                        Log.d("mapa :",nombre.get(i)+" "+latitudes.get(i)+longitudes.get(i));
                        googleMap.addMarker(new MarkerOptions().position(new LatLng((Double)latitudes.get(i),(Double)longitudes.get(i))).title((String)nombre.get(i)).icon(getIcon((String) categorias.get(i))));
                        mHashMap.put((String) nombre.get(i),(Integer) ids.get(i));
                    }
                }

//                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//                    @Override
//                    public boolean onMarkerClick(Marker marker) {
//                        int id = mHashMap.get(marker.getTitle());
//                        mSucursalListener.OnSucursalSelected(id);
//                        return false;
//                    }
//                });
                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        int id = mHashMap.get(marker.getTitle());
                        mSucursalListener.OnSucursalSelected(id);
                    }
                });

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-37.726562, -73.353960),(float)8.5));

            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    public static MapaFragment newInstance() {
        MapaFragment fragment = new MapaFragment();
        return fragment;
    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

            filtrar(item);
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    private void filtrar(String item) {

        googleMap.clear();
        if(item != "Todas"){
        ArrayList latitudes = mPresentador.getLatitudes(item);
        ArrayList longitudes = mPresentador.getLongitudes(item);
        ArrayList nombre = mPresentador.getSucursales(item);

        Log.d("Cantidad", String.valueOf(latitudes.size()));

        for(int i = 0;i<latitudes.size();i++){
            if((Double)latitudes.get(i)!=-1){

//                Drawable circleDrawable = getResources().getDrawable( R.drawable.esparcimiento);
//                BitmapDescriptor markerIcon = getMarkerIconFromDrawable(circleDrawable);

                LatLng aux = new LatLng((double)latitudes.get(i),(double)longitudes.get(i));
                googleMap.addMarker(new MarkerOptions().position(aux).title((String)nombre.get(i)).icon(getIcon(item)));
            }
        }
        }else {
            ArrayList latitudes = mPresentador.getLatitudes();
            ArrayList longitudes = mPresentador.getLongitudes();
            ArrayList nombre = mPresentador.getSucursales();
            ArrayList categories =mPresentador.getAllCategorias();
            Log.d("Cantidad", String.valueOf(latitudes.size()));

            for(int i = 0;i<latitudes.size();i++){
                if((Double)latitudes.get(i)!=-1){
//                    Drawable circleDrawable = getResources().getDrawable(iconos.get(categories.get(i)));
//                    BitmapDescriptor markerIcon = getMarkerIconFromDrawable(circleDrawable);
                    LatLng aux = new LatLng((double)latitudes.get(i),(double)longitudes.get(i));
                    googleMap.addMarker(new MarkerOptions().position(aux).title((String)nombre.get(i)).icon(getIcon((String) categories.get(i))));
                }
            }
        }

    }

    private BitmapDescriptor getIcon(String item) {
        Drawable aux;
        switch (item) {
            case "Alojamiento Turístico":
             aux = getResources().getDrawable(R.drawable.alojamiento);
                break;
            case "Restaurantes y Similares":
                aux = getResources().getDrawable(R.drawable.restaurant);
                break;
            case "Agencias de Viaje y Tour Operador":
                aux = getResources().getDrawable(R.drawable.restaurant);
                break;
            case "Transporte de Pasajeros por Carretera Interurbana":
                aux = getResources().getDrawable(R.drawable.transporte);
                break;
            case "Turismo Aventura":
                aux = getResources().getDrawable(R.drawable.turismo);
                break;
            case "Servicios de Esparcimiento":
                aux = getResources().getDrawable(R.drawable.esparcimiento);
                break;
            case "Artesanía":
                aux = getResources().getDrawable(R.drawable.artesania);
                break;
            case "Guías de Turismo":
                aux = getResources().getDrawable(R.drawable.turismo);
                break;
            case "Servicios Deportivos":
                aux = getResources().getDrawable(R.drawable.deporte);
                break;
            default:
                aux = getResources().getDrawable(R.drawable.arauco);
                break;


        }

        BitmapDescriptor markerIcon = getMarkerIconFromDrawable(aux);
        return markerIcon;

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //se checkea que la activity implemente la interfaz
        if (context instanceof BusquedaFragment.OnSucursalSelectedListener) {
            mSucursalListener = (BusquedaFragment.OnSucursalSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBusuqedaAvanzadaInteractionListener");
        }
    }


    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


}
