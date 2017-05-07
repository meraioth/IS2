package cl.udec.ingsoftware.proyecto_is.Actividades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import cl.udec.ingsoftware.proyecto_is.R;

public class VisualizacionSucursal extends AppCompatActivity {
    private TextView titulo;
    private TextView descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizacion_sucursal);
        titulo = (TextView) findViewById(R.id.textView3);
        titulo.setText(getIntent().getStringExtra("Titulo").toString());
    }
}
