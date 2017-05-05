package cl.udec.ingsoftware.proyecto_is.model;

/**
 * Created by matisin on 28-12-16.
 */

public class Categoria {

    private String nombre;

    public  Categoria(String nombre_categoria){
        this.nombre = nombre_categoria;
    }

    public boolean isCategoria(String valor){
        return valor.equals(nombre);
    }

    public String getNombre(){
        return this.nombre;
}
