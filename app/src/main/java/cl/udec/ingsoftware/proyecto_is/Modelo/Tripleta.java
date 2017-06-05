package cl.udec.ingsoftware.proyecto_is.Modelo;

import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by koskovi on 16-05-17.
 */

public class Tripleta{
    private int id;
    private String name;
    private String imagen;

    public Tripleta(int d, String n, String i) {
        this.id = d;
        this.name = n;
        this.imagen = i;
    }
    public void change_id(int i){this.id = i;}
    public void change_name(String n){this.name = n;}
    public void change_image(String i){this.imagen = i;}

    public int get_id(){return id;}
    public String get_name(){return name;}
    public String get_image(){return imagen;}
}