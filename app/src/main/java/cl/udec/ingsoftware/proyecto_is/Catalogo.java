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
                Sucursal sucursal = new Sucursal(rs.getInt("id"),rs.getString("nombre"),
                        rs.getInt("sello_de_turismo"));
                sucursales.add(sucursal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Iterator<Sucursal> ite = sucursales.iterator();
        while(ite.hasNext()){
            Sucursal s = ite.next();
            dBconnect = new DBconnect();
            dBconnect.query("select * from sucursal, servicio where sucursal.id = servicio.id_sucursal and sucursal.id="+
            Integer.toString(s.getId())+";");
            rs = dBconnect.getResult();
            try {
                while (rs.next()){
                    //System.out.println("asd"+rs.getString("nombre"));
                    Servicio ser = new Servicio(rs.getString("nombre_servicio"),rs.getString("descripcion"));
                    s.addServicio(ser);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        dBconnect = new DBconnect();
        dBconnect.query("SELECT * FROM itinerario");
        rs = dBconnect.getResult();
        try {
            while (rs.next()){
                //System.out.println("asd"+rs.getString("nombre"));
                Itinerario it = new Itinerario(rs.getString("nombre"),rs.getInt("id"),rs.getString("duracion"));
                itinerarios.add(it);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList get_itin_info(int id){
        Iterator<Itinerario> iterator = itinerarios.iterator();
        Itinerario It = null;
        ArrayList info = new ArrayList();
        while(iterator.hasNext()){
            It = iterator.next();
            if(It.getId() == id){
                break;
            }
        }
        if(It != null){
            info = It.get_info();
        }
        return info;
    }


    public ArrayList get_suc_info(int id){
        Iterator<Sucursal> iterator = sucursales.iterator();
        Sucursal S = null;
        ArrayList info = new ArrayList();
        while(iterator.hasNext()){
            S = iterator.next();
            if(S.getId() == id){
                break;
            }
        }
        if(S != null){
            info = S.get_info();
        }
        return info;
    }

    public ArrayList busqueda_itinerario(String valor) {
        ArrayList It = new ArrayList();
        Iterator<Itinerario> iterator = itinerarios.iterator();
        Itinerario S;
        boolean res;
        while (iterator.hasNext()) {
            S = iterator.next();
            res = S.isServicio(valor);
            if (res) {
                It.add(S.getNombre());
            }
        }
        return It;
    }

    public ArrayList busqueda_sucursal(String valor) {
        ArrayList Suc = new ArrayList();
        Iterator<Sucursal> iterator = sucursales.iterator();
        boolean res;
        while (iterator.hasNext()) {
            Sucursal S = iterator.next();
            res = S.isServicio(valor);
            if (res) {
                Suc.add(S.getNombre());
            }

        }
        return Suc;
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

    public ArrayList<Sucursal> getSucursales() {
        return sucursales;
    }
}
