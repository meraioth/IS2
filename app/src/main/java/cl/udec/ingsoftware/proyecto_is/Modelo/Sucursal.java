package cl.udec.ingsoftware.proyecto_is.Modelo;

import java.util.ArrayList;
import java.util.Iterator;

import cl.udec.ingsoftware.proyecto_is.Modelo.Servicio;

/**
 * Created by matisin on 28-12-16.
 */

//public class Sucursal extends PuntoDeInteres{
public class Sucursal {

    private int id;
    private int sello;
    private String nombre;
    private  double latitud,longitud;
    private ArrayList<Servicio> servicios;

    //public Sucursal(String nombre, String id, String direccion,int longitud, int latitud, int sello){
    public Sucursal(String nombre, int id, int sello,double latitud,double longitud){
        //super(id,direccion,longitud,latitud);
        this.nombre = nombre;
        this.sello = sello;
        this.id=id;
        this.latitud=latitud;
        this.longitud=longitud;
        servicios=new ArrayList<>();
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

    public int getSello(){ return this.sello; }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public ArrayList<Servicio> getServicios(){
        return servicios;
    }
    public void addServicio(Servicio serv){
        servicios.add(serv);

    }
    public int getId(){return id;}
}
