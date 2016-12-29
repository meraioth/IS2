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

    public DBconnect() {

    }

    public void start() {
        try {
            Class.forName("org.postgresql.Driver");
            // "jdbc:postgresql://IP:PUERTO/DB", "USER", "PASSWORD");
            // Si estás utilizando el emulador de android y tenes el PostgreSQL en tu misma PC no utilizar 127.0.0.1 o localhost como IP, utilizar 10.0.2.2
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://plop.inf.udec.cl/Matias","matiasmedina", "Psmlgipxfq1");
            System.out.println("entro");
            //En el stsql se puede agregar cualquier consulta SQL deseada.
            String stsql = "Select version()";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(stsql);
            rs.next();
            System.out.println( rs.getString(1) );
           // conn.close();
        } catch (SQLException se) {
            System.out.println("oops! No se puede conectar. Error: " + se.toString());
        }
        catch (ClassNotFoundException e) {
            System.out.println("oops! No se encuentra la clase. Error: " + e.getMessage());
        }
    }
    public  void end(){
        try {
            conn.close();
        }catch (SQLException se){
            System.out.println("oops! No se puede cerrar. Error: " + se.toString());
        }
    }

    public String query() throws SQLException {
        String query = "SELECT *  FROM is.usuario ";
        Statement stmt = conn.createStatement( ) ;
        ResultSet rs = stmt.executeQuery(query) ;
        return rs.getString( "nombre" );
     }
}
