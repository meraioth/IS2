package cl.udec.ingsoftware.proyecto_is.Modelo;

/**
 * Created by matisin on 28-12-16.
 */

public class Categoria {
    private String name;
    private String descripcion;
    public Categoria(String name,String descripcion){
        this.name = name;
        this.descripcion = descripcion;
    }
    public String getNombre(){
        return name;
    }

}
