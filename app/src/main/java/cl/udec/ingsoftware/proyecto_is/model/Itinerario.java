package cl.udec.ingsoftware.proyecto_is.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by matisin on 28-12-16.
 */

public class Itinerario {
    private String nombre;
    private ArrayList<Servicio> servicios;
    private String duracion;

    public Itinerario(int id, String nombre, String duracion, ArrayList<Servicio> servicios){
        this.servicios = servicios;
        this.nombre = nombre;
        this.duracion = duracion;
        // TODO: SACAR DE ACA EL DB CONNECT y ponerlo como debería ser en data
        /*
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
        }*/
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
}