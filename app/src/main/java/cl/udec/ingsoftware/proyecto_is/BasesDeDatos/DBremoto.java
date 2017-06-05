package cl.udec.ingsoftware.proyecto_is.BasesDeDatos;

/**
 * Created by meraioth on 28-12-16.
 */
import android.content.AsyncTaskLoader;
import android.util.Log;

import java.sql.Array;
import java.sql.DriverManager ;
import java.sql.Connection ;
import java.sql.SQLException ;
import java.sql.Statement ;
import java.sql.ResultSet ;
import java.util.ArrayList;

import static org.postgresql.util.JdbcBlackHole.close;

public class DBremoto {

    private Connection conn;
    private String consulta;
    private ResultSet result;

    public DBremoto(){}

    public void query(String aux){
        consulta=aux;

        sqlThread.start();
            try {
                sqlThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    public ResultSet getResult(){
        return result;
    }

    Thread sqlThread = new Thread() {
        public void run() {
            try {
                Class.forName("org.postgresql.Driver");
                // "jdbc:postgresql://IP:PUERTO/DB", "USER", "PASSWORD");
                // Si estás utilizando el emulador de android y tenes el PostgreSQL en tu misma PC no utilizar 127.0.0.1 o localhost como IP, utilizar 10.0.2.2
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://plop.inf.udec.cl/Matias?currentSchema=turismo", "matiasmedina", "Psmlgipxfq1");
                System.out.println("entro");
                //En el stsql se puede agregar cualquier consulta SQL deseada
                Statement st = conn.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(consulta);


                result = rs;

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {

                close(conn);
            }
        }
    };
}