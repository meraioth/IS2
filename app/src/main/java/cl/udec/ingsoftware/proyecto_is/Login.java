package cl.udec.ingsoftware.proyecto_is;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.R.id.message;

public class Login extends AppCompatActivity {
    Button b1,b2;
    EditText ed1,ed2;
    DBconnect db;
    public final static String EXTRA_MESSAGE = "cl.udec.ingsoftware.proyecto_is.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        b1 = (Button)findViewById(R.id.button_login);
        ed1 = (EditText)findViewById(R.id.mail);
        ed2 = (EditText)findViewById(R.id.pass);
        db = new DBconnect();
        db.start();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean login= db.query_user(ed1.getText().toString(),ed2.getText().toString());
                if(login){
                    if(db.query_empresario(ed1.getText().toString(),ed2.getText().toString())){
                        vista_empresario(ed1.getText().toString());
                        Toast.makeText(getApplicationContext(),
                                "Redirecting...",Toast.LENGTH_SHORT).show();
                    }else{
                        vista_turista(ed1.getText().toString());
                        Toast.makeText(getApplicationContext(),
                                "Redirecting...",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(),
                            "Fallo verificación...",Toast.LENGTH_SHORT).show();

                }

                }
            });
    }

    private void vista_empresario(String message){
        Intent intent = new Intent(this, Vista_empresario.class);
        //EditText editText = (EditText) findViewById(R.id.edit_message);

        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    private void vista_turista(String message){
        Intent intent = new Intent(this, Vista_turista.class);
        //EditText editText = (EditText) findViewById(R.id.edit_message);

        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
