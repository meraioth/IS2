package cl.udec.ingsoftware.proyecto_is;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;


public class OSM extends AppCompatActivity {

    private MapView myOpenMapView;
    private MapController myMapController;
    private Catalogo cat;


    ArrayList<OverlayItem> anotherOverlayItemArray;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        cat= MainActivity.catalogo;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_osm);

        myOpenMapView = (MapView)findViewById(R.id.openmapview);
        myOpenMapView.setBuiltInZoomControls(true);
        myMapController = (MapController) myOpenMapView.getController();
        myMapController.setZoom(8);
        myMapController.setCenter(new GeoPoint(-37.726562, -73.353960));

        //--- Create Another Overlay for multi marker


        ItemizedIconOverlay<OverlayItem> anotherItemizedIconOverlay
                = new ItemizedIconOverlay<OverlayItem>(
                this, getarray(), null);
        myOpenMapView.getOverlays().add(anotherItemizedIconOverlay);
        //---

        //Add Scale Bar
        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(this);
        myOpenMapView.getOverlays().add(myScaleBarOverlay);
    }

    private List<OverlayItem> getarray() {

        ArrayList<Sucursal> suc= cat.getSucursales();



        anotherOverlayItemArray = new ArrayList<OverlayItem>();


        GeoPoint aux=new GeoPoint(0,0);
        float zoom = (float)8.5;
        for (Sucursal x: suc) {

            if (x.getLatitud() != (double) -1) {
                aux = new GeoPoint(x.getLatitud(), x.getLongitud());
                anotherOverlayItemArray.add(new OverlayItem(x.getNombre(),x.getNombre(),aux));

            }
        }
        return anotherOverlayItemArray;
    }

}
