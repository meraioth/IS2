package cl.udec.ingsoftware.proyecto_is;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by matisin on 28-12-16.
 */

public class Itinerario {
    private String nombre;
    private ArrayList<Servicio> servicios;
    private int id;

    public Itinerario(String nombre){
        servicios = new ArrayList<Servicio>();
        this.nombre = nombre;
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
}
