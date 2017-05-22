package cl.udec.ingsoftware.proyecto_is.Modelo;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by matisin on 28-12-16.
 */

public class Itinerario {
    private int id;
    private String nombre;
    private int id_usuario;
    private String duracion;
    private ArrayList<Sucursal> sucursales;

    public Itinerario(int id,String nombre,String duracion, int id_usuario){
        this.id = id;
        this.nombre = nombre;
        this.duracion = duracion;
        this.id_usuario = id_usuario;
    }
    public void inicializarSucursales(ArrayList<Sucursal> sucursales){
        this.sucursales = sucursales;
    }

    public int getId(){
        return this.id;
    }
    public String getNombre(){
        return this.nombre;
    }

    public ArrayList<Sucursal> getSucursales(){
        return this.sucursales;
    }
    public String getDuracion(){
        return this.duracion;
    }
    public int getId_usuario(){
        return this.id_usuario;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
}
