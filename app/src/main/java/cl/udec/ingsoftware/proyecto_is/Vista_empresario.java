package cl.udec.ingsoftware.proyecto_is;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Vista_empresario extends AppCompatActivity {
Empresario empresario;
    Button b1,b2,b3;
    EditText ed1,ed2,ed3,ed4,ed5,ed6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_empresario);
        Intent intent = getIntent();
        int id = intent.getIntExtra(Login.EXTRA_MESSAGE,0);
        empresario = new Empresario(id);
        b1 = (Button)findViewById(R.id.crear_empresa);
        b2 = (Button)findViewById(R.id.crear_sucursal);
        b3 = (Button)findViewById(R.id.crear_servicio);
        ed1 = (EditText)findViewById(R.id.nombre_empresa);
        ed2 = (EditText)findViewById(R.id.rut_empresa);
    }
}
