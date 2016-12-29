package cl.udec.ingsoftware.proyecto_is;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by matisin on 28-12-16.
 */

public class Sucursal{
    private int sello;
    private String nombre;
    private int id;
    private ArrayList<Servicio> servicios;

    public Sucursal(int id,String nombre, int sello){
        this.nombre = nombre;
        this.sello = sello;
        this.id = id;
        servicios = new ArrayList<Servicio>();
    }

    public int getId(){
        return id;
    }

    public void setServicio(ArrayList<Servicio> servicios){
        this.servicios = (ArrayList<Servicio>) servicios.clone();
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
        return nombre;
    }

    public ArrayList get_info(){
        ArrayList info = new ArrayList();
        info.add(nombre);
        info.add(sello);
        info.add(servicios);
        return info;
    }

    public void addServicio(Servicio ser) {
        servicios.add(ser);
    }
}
