package cl.udec.ingsoftware.proyecto_is;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class Vista_turista extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
Catalogo catalogo;
    boolean crear=false;
    String seleccionado="NULL";
    Button b1,b2;
    EditText ed1,ed2;
    Turista turista;
    Itinerario it;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_turista);
        catalogo= new Catalogo();
        catalogo.connect();
        Spinner spinner = (Spinner) findViewById(R.id.spinner_servicio);
        Intent intent = getIntent();
        int id = intent.getIntExtra(Login.EXTRA_MESSAGE,0);
        turista = new Turista(id);
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = catalogo.servicios_to_array();


        // Creating adapter for spinner
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        b1 = (Button)findViewById(R.id.crear_itinerario);
        b2 = (Button)findViewById(R.id.agregar_itinerario);
        ed1 = (EditText)findViewById(R.id.nombre_itinerario);
        ed2 = (EditText)findViewById(R.id.duracion_itinerario);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crear= !crear;
                if(crear){
                    b1.setText("Guardar Itinerario");
                    String nombre=ed1.getText().toString();
                    String duracion =ed2.getText().toString();
                    turista.crearItinerario(nombre,duracion);

                    Toast.makeText(getApplicationContext(), "Selected: " + nombre+duracion, Toast.LENGTH_LONG).show();

                }else{
                    b1.setText("Crear Itinerario");

                    turista.guardarItinerario();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seleccionado.compareTo("NULL")!=0)
                turista.agregarServicioaItinerario(seleccionado);
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        seleccionado=item;

    }
    public void onNothingSelected(AdapterView<?> arg0) {
        seleccionado="NULL";
    }
}
