package cl.udec.ingsoftware.proyecto_is.Modelo;

import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by Cecilia on 04-06-2017.
 */

public class TripletaItinerario {
    private int id;
    private String nombre;
    private ArrayList<Sucursal> sucursales;

    public TripletaItinerario(int id, String nombre, ArrayList<Sucursal> sucursales) {
        this.id = id;
        this.nombre = nombre;
        sucursales = new ArrayList<>();
    }

    public void change_id(int id){this.id = id;}
    public void change_nombre(String nombre){this.nombre = nombre;}
    public void change_sucursales(ArrayList<Sucursal> sucursales){this.sucursales = sucursales;}

    public int get_id(){return id;}
    public String get_nombre(){return nombre;}
    public ArrayList<Sucursal> get_sucursales(){return sucursales;}
}
