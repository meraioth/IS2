package cl.udec.ingsoftware.proyecto_is.Actividades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import cl.udec.ingsoftware.proyecto_is.R;

public class VisualizacionSucursal extends AppCompatActivity {
    private String mTitulo;
    private int mId;
    private ImageView mSelloTurismo;
    private TextView mDescripcion;
    private RatingBar mRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizacion_sucursal);
    }
}
