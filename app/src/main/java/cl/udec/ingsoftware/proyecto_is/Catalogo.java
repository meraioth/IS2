package cl.udec.ingsoftware.proyecto_is;

import org.postgresql.ssl.DbKeyStoreSocketFactory;

import java.sql.Array;
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

    public Catalogo() {

        itinerarios = new ArrayList<Itinerario>();
        sucursales = new ArrayList<Sucursal>();
        dBconnect = new DBconnect();
    }

    public void connect(){
        dBconnect = new DBconnect();
        dBconnect.query("SELECT * FROM sucursal");
        ResultSet rs = dBconnect.getResult();
        try {
            while (rs.next()){
                //System.out.println("asd"+rs.getString("nombre"));
                Sucursal sucursal = new Sucursal(rs.getString("nombre"),rs.getInt("id"),
                        rs.getInt("sello_de_turismo"));
                sucursales.add(sucursal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        dBconnect = new DBconnect();
        dBconnect.query("SELECT * FROM itinerario");
        rs = dBconnect.getResult();
        try {
            while (rs.next()){
                //System.out.println("asd"+rs.getString("nombre"));
                Itinerario it = new Itinerario(rs.getInt("id"),rs.getString("nombre"),rs.getString("duracion"));
                itinerarios.add(it);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public ArrayList servicios_to_array() {
        ArrayList It = new ArrayList();
        Iterator<Sucursal> iterator = sucursales.iterator();
        Sucursal S;
        boolean res;
        while (iterator.hasNext()) {
            S = iterator.next();
                It.add(S.getNombre());

        }
        return It;
    }
    public ArrayList itinerarios_to_array() {
        ArrayList It = new ArrayList();
        Iterator<Itinerario> iterator = itinerarios.iterator();
        Itinerario S;
        boolean res;
        while (iterator.hasNext()) {
            S = iterator.next();
            It.add(S.getNombre());

        }
        return It;
    }
}
