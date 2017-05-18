package cl.udec.ingsoftware.proyecto_is.Actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

import cl.udec.ingsoftware.proyecto_is.Presentador.PresentadorSucursal;
import cl.udec.ingsoftware.proyecto_is.R;
import cl.udec.ingsoftware.proyecto_is.util.AsyncTaskLoadImage;

public class VisualizacionSucursal extends AppCompatActivity {
    public static final String ARG_PRESENTADOR = "presentador";
    private int mId;
    private PresentadorSucursal mPresentador;
    private String mTitulo;
    private ImageView mSelloTurismo;
    private ImageView mImagenSucursal;
    private TextView mDescripcion;
    private TextView mCalificacion;
    private RatingBar mRating;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizacion_sucursal);
        Intent intent = getIntent();
        this.mId = intent.getIntExtra(ARG_PRESENTADOR,0);
        mPresentador = new PresentadorSucursal(getApplicationContext(),mId);
        mDescripcion = (TextView) findViewById(R.id.descripcion_sucursal);
        mDescripcion.setText(mPresentador.get_descripcion());

        mImagenSucursal = (ImageView) findViewById(R.id.imagen_sucursal_visualizacion);
        mImagenSucursal.setScaleType(ImageView.ScaleType.FIT_XY);
        String url = mPresentador.get_image();
        new AsyncTaskLoadImage(mImagenSucursal).execute(url);

        mSelloTurismo = (ImageView) findViewById(R.id.sello_verde);
        if(mPresentador.get_sello() == 0) {
            mSelloTurismo.setVisibility(View.INVISIBLE);
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar_sucursal);
        mToolbar.setTitle(mPresentador.get_name());
        setSupportActionBar(mToolbar);

        mCalificacion = (TextView) findViewById(R.id.texto_calificacion);
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(50);
        mCalificacion.setText("("+Integer.toString(randomInt) + " VOTOS)");
        mRating = (RatingBar) findViewById(R.id.ratingBar);
        float rating = randomGenerator.nextInt(5);
        mRating.setRating(rating);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sucursal, menu);
        return true;
    }




}
