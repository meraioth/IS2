package cl.udec.ingsoftware.proyecto_is;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by matisin on 28-12-16.
 */

public class Itinerario {
    private String nombre;
    private ArrayList<Servicio> servicios;
    private int id;
    private String duracion;


    public Itinerario(String nombre, String duracion){
        servicios = new ArrayList<Servicio>();
        this.nombre=nombre;
        this.duracion=duracion;
    }

    public Itinerario(String nombre,int id){
        servicios = new ArrayList<Servicio>();
        this.nombre = nombre;
        this.id = id;
        this.duracion = duracion;
        DBconnect db = new DBconnect();
        String q = "select itinerario.id, servicio.descripcion, orden.orden ,servicio.id , servicio.nombre_servicio " +
                "from itinerario , orden , servicio " +
                "where itinerario.id=orden.id_itinerario and servicio.id=orden.id_servicio and itinerario.id =" +id+
                " order by itinerario.id, orden.orden;";
        db.query(q);
        ResultSet rs = db.getResult();
        try {
            while (rs.next()){
                //System.out.println("asd"+rs.getString("nombre"));
                Servicio servicio = new Servicio(rs.getString("nombre_servicio"),rs.getString("descripcion"));
                servicios.add(servicio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getId(){
        return id;
    }

    public String getNombre(){
        return this.nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public boolean isServicio(String valor){
        boolean res = false;
        Iterator<Servicio> iterator = servicios.iterator();
        while(iterator.hasNext()){
            Servicio Ser = iterator.next();
            res = Ser.isServicio(valor);
            if(res){
                return res;
            }
        }
        return res;

    }

    public ArrayList get_info(){
        ArrayList info = new ArrayList();
        info.add(nombre);
        info.add(servicios);
        return info;
    }
    public boolean addServicio(String name){
        DBconnect db = new DBconnect();
        String q = "select  servicio.descripcion,servicio.id , servicio.nombre_servicio " +
                "from servicio " +
                "where servicio.nombre_servicio like '" +name+
                "';";
        System.out.println(q);
        db.query(q);
        ResultSet rs = db.getResult();
        try {
            while (rs.next()){
                //System.out.println("asd"+rs.getString("nombre"));
                Servicio servicio = new Servicio(rs.getString("nombre_servicio"),rs.getString("descripcion"));
                servicios.add(servicio);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean writeDB(int id) {
        DBconnect db = new DBconnect();
        String q = "insert into itinerario (nombre,duracion,id_usuario) values('" +this.nombre+"','"+duracion+"',"+id+");";
        System.out.println(q);

        db.query(q);
        ResultSet rs = db.getResult();

//        return false;
        return false;
    }
}
