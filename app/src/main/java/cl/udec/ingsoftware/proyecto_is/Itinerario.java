package cl.udec.ingsoftware.proyecto_is;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by matisin on 28-12-16.
 */

public class Itinerario {
    private String nombre;
    private ArrayList<Servicio> servicios;

    public Itinerario(String nombre){
        this.nombre = nombre;
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
