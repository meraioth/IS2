package cl.udec.ingsoftware.proyecto_is.Presentador;

import android.content.Context;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

import cl.udec.ingsoftware.proyecto_is.Modelo.Sucursal;
import cl.udec.ingsoftware.proyecto_is.Modelo.Itinerario;

/**
 * Created by koskovi on 15-05-17.
 */

public class OnlyOneSucursal implements Serializable {

    private ArrayList<Sucursal> sucursales;
    private Formateador formateador;
    private Sucursal sucursal;
    int id;

    public OnlyOneSucursal(Context cont, int id) {
        this.id = id;
        sucursales = null;
        formateador = new Formateador(cont);
        sucursal = null;

        //Extraemos todas las sucursales
        try {
            sucursales = formateador.getSucursales();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Buscamos la sucursal y la guardamos en la variable privada "sucursal"
        for (Sucursal su:sucursales){
            if (su.getId() == id ){
                sucursal = su;
            }
        }

    }

    public String get_name(){
        return sucursal.getNombre();
    }

    public int get_sello(){
        return sucursal.getSello();
    }

    public double get_latitud(){
        return sucursal.getLatitud();
    }

    public double get_longitud(){
        return sucursal.getLongitud();
    }

    public ArrayList get_servicios(){
        ArrayList servicios = new ArrayList();
        servicios = sucursal.getServicios();
        return servicios;
    }


}
