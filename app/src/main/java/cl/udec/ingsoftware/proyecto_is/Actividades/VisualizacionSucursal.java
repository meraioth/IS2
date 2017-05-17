package cl.udec.ingsoftware.proyecto_is.Actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import android.widget.RatingBar;
import android.widget.TextView;

import cl.udec.ingsoftware.proyecto_is.Presentador.PresentadorSucursal;
import cl.udec.ingsoftware.proyecto_is.R;

public class VisualizacionSucursal extends AppCompatActivity {
    public static final String ARG_PRESENTADOR = "presentador";
    private PresentadorSucursal mPresentador;
    private String mTitulo;
    private ImageView mSelloTurismo;
    private TextView mDescripcion;
    private RatingBar mRating;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizacion_sucursal);
        Intent intent = getIntent();
        //mPresentador = (PresentadorSucursal) intent.getSerializableExtra(ARG_PRESENTADOR);

        mDescripcion = (TextView) findViewById(R.id.descripcion_sucursal);
      //  mDescripcion.setText(mPresentador.get_descripcion());
    }
}
