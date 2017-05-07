package cl.udec.ingsoftware.proyecto_is;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import org.mapsforge.core.model.LatLong;
import org.mapsforge.map.android.graphics.AndroidGraphicFactory;
import org.mapsforge.map.android.util.AndroidUtil;
import org.mapsforge.map.android.view.MapView;
import org.mapsforge.map.datastore.MapDataStore;
import org.mapsforge.map.layer.cache.TileCache;
import org.mapsforge.map.layer.renderer.TileRendererLayer;
import org.mapsforge.map.reader.MapFile;
import org.mapsforge.map.rendertheme.InternalRenderTheme;

import java.io.File;

public class mapsforge extends Activity {



        // name of the map file in the external storage
        private static final String MAP_FILE = "raw/germany.map";

        private MapView mapView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            AndroidGraphicFactory.createInstance(this.getApplication());

            this.mapView = new MapView(this);
            setContentView(this.mapView);

            this.mapView.setClickable(true);
            this.mapView.getMapScaleBar().setVisible(true);
            this.mapView.setBuiltInZoomControls(true);
            this.mapView.setZoomLevelMin((byte) 10);
            this.mapView.setZoomLevelMax((byte) 20);

            // create a tile cache of suitable size
            TileCache tileCache = AndroidUtil.createTileCache(this, "mapcache",
                    mapView.getModel().displayModel.getTileSize(), 1f,
                    this.mapView.getModel().frameBufferModel.getOverdrawFactor());
            Uri url = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.germany);

            // tile renderer layer using internal render theme
            //MapDataStore mapDataStore = new MapFile(new File(Environment.getExternalStorageDirectory(), MAP_FILE));
             File mapita=new File(url.toString());

            MapDataStore mapDataStore = new MapFile(mapita);
            TileRendererLayer tileRendererLayer = new TileRendererLayer(tileCache, mapDataStore,
                    this.mapView.getModel().mapViewPosition, AndroidGraphicFactory.INSTANCE);
            tileRendererLayer.setXmlRenderTheme(InternalRenderTheme.DEFAULT);

            // only once a layer is associated with a mapView the rendering starts
            this.mapView.getLayerManager().getLayers().add(tileRendererLayer);

            this.mapView.setCenter(new LatLong(52.517037, 13.38886));
            this.mapView.setZoomLevel((byte) 12);

        }

        @Override
        protected void onDestroy() {
            this.mapView.destroyAll();
            AndroidGraphicFactory.clearResourceMemoryCache();
            super.onDestroy();
        }
    }

