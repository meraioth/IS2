package cl.udec.ingsoftware.proyecto_is.Modelo;

/**
 * Created by matisin on 28-12-16.
 */

public class Servicio {
    private int id;
    private String nombre;
    private String descripcion;
    private Categoria cat;

    public  Servicio(int id,String nombre, String descripcion,Categoria categoria){
        this.id=id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cat=categoria;
    }

    public boolean isServicio(String valor){
        return valor.equals(nombre);
    }
    public Categoria getCategoria(){return cat;}
    public void setCategoria(Categoria cat){this.cat=cat;}
    public int getId(){
        return id;
    }
    public String getNombre(){
        return nombre;

    }
    public String getDescripcion(){
        return descripcion;
    }
}
