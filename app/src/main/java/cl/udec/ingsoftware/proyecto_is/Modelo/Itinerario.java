package cl.udec.ingsoftware.proyecto_is.Modelo;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by matisin on 28-12-16.
 */

public class Itinerario {
    private int id;
    private String nombre;
    private int id_usuario;
    private String estacion;
    private ArrayList<Pair> sucursales_duracion;

    public Itinerario(int id,String nombre, int id_usuario, String estacion){
        this.id = id;
        this.nombre = nombre;
        this.id_usuario = id_usuario;
        this.estacion = estacion;
    }
    public void inicializarSucursales(ArrayList<Pair> sucursales){
        this.sucursales_duracion = sucursales;
    }

    public int getId(){
        return this.id;
    }
    public String getNombre(){
        return this.nombre;
    }

    public ArrayList<Pair> getSucursales(){
        return this.sucursales_duracion;
    }
    public String getEstacion(){
        return this.estacion;
    }
    public int getId_usuario(){
        return this.id_usuario;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
}
