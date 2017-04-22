package cl.udec.ingsoftware.proyecto_is.data;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by matisin on 28-12-16.
 */

//public class Sucursal extends PuntoDeInteres{
public final class Sucursal{

    private int id;
    private int sello;
    private String nombre;
    private double latitud,longitud;
    private String comuna;
    private String rut_empresa;
    private ArrayList<Servicio> servicios;

    //public Sucursal(String nombre, String id, String direccion,int longitud, int latitud, int sello){
    public Sucursal(int id, String nombre, int sello, String rut_empresa, String comuna , double latitud, double longitud){
        //super(id,direccion,longitud,latitud);
        this.nombre = nombre;
        this.sello = sello;
        this.id=id;
        this.comuna = comuna;
        this.rut_empresa = rut_empresa;
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
    public int getId(){ return this.id; }

    public String getNombre(){
        return this.nombre;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public int getSello() {
        return sello;
    }

    public String getRutEmpresa() {
        return rut_empresa;
    }

    public String getComuna() {
        return comuna;
    }
}
