package cl.udec.ingsoftware.proyecto_is.Actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import cl.udec.ingsoftware.proyecto_is.Presentador.OnlyOneSucursal;
import cl.udec.ingsoftware.proyecto_is.R;

public class VisualizacionSucursal extends AppCompatActivity {
    public static final String ARG_PRESENTADOR = "presentador";
    private int mId;
    private OnlyOneSucursal mPresentador;
    private String mTitulo;
    private ImageView mSelloTurismo;
    private TextView mDescripcion;
    private RatingBar mRating;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizacion_sucursal);
        Intent intent = getIntent();
        this.mId = intent.getIntExtra(ARG_PRESENTADOR,0);
        mPresentador = new OnlyOneSucursal(getApplicationContext(),mId);
        mDescripcion = (TextView) findViewById(R.id.descripcion_sucursal);
        mDescripcion.setText(mPresentador.get_descripcion());
    }
}
