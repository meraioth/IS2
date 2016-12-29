package cl.udec.ingsoftware.proyecto_is;

import org.postgresql.ssl.DbKeyStoreSocketFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by matisin on 28-12-16.
 */

public class Catalogo {
    private ArrayList<Itinerario> itinerarios;
    private ArrayList<Sucursal> sucursales;
    private DBconnect dBconnect;

    public Itinerario getItinerario(Itinerario it){
        Itinerario r = null;
        for (int i = 0; i < itinerarios.size(); i++){
            if(itinerarios.get(i) == it){
                r = itinerarios.get(i);
            }
        }
        return r;
    }

    public Catalogo() throws SQLException {
        itinerarios = new ArrayList<Itinerario>();
        sucursales = new ArrayList<Sucursal>();
        dBconnect = new DBconnect();
        dBconnect.start();
        ResultSet rs = dBconnect.query("SELECT * FROM SUCURSAL;");
        rs.next();
        System.out.println(rs.getArray(0));
        dBconnect.end();

    }

    public ArrayList<Sucursal> busqueda_sucursal(String valor) {
        ArrayList<Sucursal> Suc = new ArrayList<Sucursal>();
        Iterator<Sucursal> iterator = sucursales.iterator();
        boolean res;
        while (iterator.hasNext()) {
            Sucursal S = iterator.next();
            res = S.isServicio(valor);
            if (res) {
                Suc.add(S);
            }

        }
        return Suc;
    }

    public ArrayList<Itinerario> busqueda_itinerario(String valor) {
        ArrayList<Itinerario> It = new ArrayList<Itinerario>();
        Iterator<Itinerario> iterator = itinerarios.iterator();
        Itinerario S;
        boolean res;
        while (iterator.hasNext()) {
            S = iterator.next();
            res = S.isServicio(valor);
            if (res) {
                It.add(S);
            }
        }
        return It;
    }
}
