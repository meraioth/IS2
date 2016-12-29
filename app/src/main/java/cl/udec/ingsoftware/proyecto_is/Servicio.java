package cl.udec.ingsoftware.proyecto_is;

/**
 * Created by matisin on 28-12-16.
 */

public class Servicio {

    private String nombre;
    private String descripcion;


    public  Servicio(String nombre, String descripcion){
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public boolean isServicio(String valor){
        return valor.equals(nombre);
    }


}
