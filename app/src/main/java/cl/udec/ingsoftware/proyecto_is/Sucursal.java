package cl.udec.ingsoftware.proyecto_is;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by matisin on 28-12-16.
 */

public class Sucursal extends PuntoDeInteres{

    private boolean sello;
    private String nombre;
    private ArrayList<Servicio> servicios;

    public Sucursal(String nombre, String id, String direccion,int longitud, int latitud, boolean sello){
        super(id,direccion,longitud,latitud);
        this.nombre = nombre;
        this.sello = sello;
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
