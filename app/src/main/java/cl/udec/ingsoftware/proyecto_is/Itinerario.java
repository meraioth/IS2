package cl.udec.ingsoftware.proyecto_is;

/**
 * Created by matisin on 28-12-16.
 */

public class Itinerario {
    private String nombre;
    public Itinerario(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return this.nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
}
