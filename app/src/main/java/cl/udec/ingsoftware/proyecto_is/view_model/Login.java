package cl.udec.ingsoftware.proyecto_is.view_model;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;

import cl.udec.ingsoftware.proyecto_is.R;

public class Login extends AppCompatActivity {
    Button b1,b2;
    EditText ed1,ed2;
    DBconnect db,db1;
    public final static String EXTRA_MESSAGE = "cl.udec.ingsoftware.proyecto_is.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        b1 = (Button)findViewById(R.id.button_login);
        ed1 = (EditText)findViewById(R.id.mail);
        ed2 = (EditText)findViewById(R.id.pass);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    db = new DBconnect();
                    db1=new DBconnect();
                    String cons = "SELECT * FROM usuario where usuario.email like '"+
                            ed1.getText().toString()+"' and usuario.password like '"+ed2.getText().toString()+"';";
                    db.query(cons);
                    ResultSet rs = db.getResult();
                try {
                    if (rs.next()){
                        String id =rs.getString("id");
                        System.out.println(id);
                        String consu =  "SELECT * FROM empresario where id ="+id+";";
                        System.out.println(consu);
                        db1.query(consu);
                        ResultSet rs1 = db1.getResult();
                        if(rs1.next()){

                            Toast.makeText(getApplicationContext(),
                             "Redirecting...",Toast.LENGTH_SHORT).show();
                            vista_empresario(id);
                        }else
                        {
                            vista_turista(id);
                           Toast.makeText(getApplicationContext(),
                                "Redirecting...",Toast.LENGTH_SHORT).show();


                        }

                    }else Toast.makeText(getApplicationContext(),
                    "Fallo verificación...",Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    e.printStackTrace();
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
