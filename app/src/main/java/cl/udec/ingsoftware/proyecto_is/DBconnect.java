package cl.udec.ingsoftware.proyecto_is;

/**
 * Created by meraioth on 28-12-16.
 */
import java.sql.DriverManager ;
import java.sql.Connection ;
import java.sql.SQLException ;
import java.sql.Statement ;
import java.sql.ResultSet ;
public class DBconnect {

    Connection conn;
    String consulta;
    ResultSet result;
    public DBconnect() {

    }


    public ResultSet query(String aux)  {
        consulta=aux;
        sqlThread.start();
        return result;
    }

    public boolean query_user(String user, String pass) {


        return false;
    }

    public boolean query_empresario(String s, String s1) {
        return true;
    }

    Thread sqlThread = new Thread() {
        public void run() {
            try {
                Class.forName("org.postgresql.Driver");
                // "jdbc:postgresql://IP:PUERTO/DB", "USER", "PASSWORD");
                // Si estás utilizando el emulador de android y tenes el PostgreSQL en tu misma PC no utilizar 127.0.0.1 o localhost como IP, utilizar 10.0.2.2
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://plop.inf.udec.cl/Matias?currentSchema=is", "matiasmedina", "Psmlgipxfq1");
                System.out.println("entro");
                //En el stsql se puede agregar cualquier consulta SQL deseada
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(consulta);
                result= rs;
                conn.close();
            } catch (SQLException se) {
                System.out.println("oops! No se puede conectar. Error: " + se.toString());
            } catch (ClassNotFoundException e) {
                System.out.println("oops! No se encuentra la clase. Error: " + e.getMessage());
            }
        }
    };
}