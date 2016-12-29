package cl.udec.ingsoftware.proyecto_is;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.sql.*;

public class MainActivity extends AppCompatActivity {
DBconnect bd;
Catalogo catalogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);











        sqlThread.start();
        catalogo = new Catalogo();
//        bd = new DBconnect();
//        bd.start();
//
//        bd.end();
    }


    Thread sqlThread = new Thread() {
        public void run() {
            try {
                Class.forName("org.postgresql.Driver");
                // "jdbc:postgresql://IP:PUERTO/DB", "USER", "PASSWORD");
                // Si estás utilizando el emulador de android y tenes el PostgreSQL en tu misma PC no utilizar 127.0.0.1 o localhost como IP, utilizar 10.0.2.2
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://plop.inf.udec.cl/Matias?currentSchema=is","matiasmedina", "Psmlgipxfq1");
                System.out.println("entro");
                //En el stsql se puede agregar cualquier consulta SQL deseada.
                String stsql = "Select * from servicio;";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(stsql);
                rs.next();
                System.out.println(rs.getString("nombre_servicio"));
                //System.out.println( rs.getString(1) );
                conn.close();
            } catch (SQLException se) {
                System.out.println("oops! No se puede conectar. Error: " + se.toString());
            }
            catch (ClassNotFoundException e) {
                System.out.println("oops! No se encuentra la clase. Error: " + e.getMessage());
            }
        }
    };
}
