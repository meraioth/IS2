package cl.udec.ingsoftware.proyecto_is;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by matisin on 28-12-16.
 */

//public class Sucursal extends PuntoDeInteres{
public class Sucursal {

    private int id;
    private String sello;
    private String nombre;
    private  double latitud,longitud;
    private ArrayList<Servicio> servicios;

    //public Sucursal(String nombre, String id, String direccion,int longitud, int latitud, int sello){
    public Sucursal(String nombre, int id, String sello,double latitud,double longitud){
        //super(id,direccion,longitud,latitud);
        this.nombre = nombre;
        this.sello = sello;
        this.id=id;
        this.latitud=latitud;
        this.longitud=longitud;
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
    public String getNombre(){
        return this.nombre;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }
}
