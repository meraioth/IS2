package cl.udec.ingsoftware.proyecto_is.Presentador;

import android.content.Context;
import android.util.Log;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

import cl.udec.ingsoftware.proyecto_is.Modelo.Sucursal;
import cl.udec.ingsoftware.proyecto_is.Modelo.Itinerario;

/**
 * Created by koskovi on 15-05-17.
 */

public class PresentadorSucursal implements Serializable {

    private ArrayList<Sucursal> sucursales;
    private Formateador formateador;
    private Sucursal sucursal;
    int id;

    public PresentadorSucursal(Context cont,int id) {
        this.id = id;
        sucursales = null;
        formateador = new Formateador(cont);
        sucursal = null;
        getData();
    }

    public void getData(){
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

    public String get_descripcion() {
        return sucursal.getDescripcion();
    }

    public String get_image(){
        return sucursal.getImagen();
    }

    public ArrayList get_servicios(){
        ArrayList servicios = new ArrayList();
        servicios = sucursal.getServicios();
        return servicios;
    }


    public String getComuna() {
        return sucursal.getComuna();
    }

    public void setDescripcion(String descripcion) {
        formateador.updateDescripcionSucursal(id,descripcion);
    }

    public void setNombre(String name) {
        Log.d("presentador_sucursal_update_name",name);
        formateador.updateNombreSucursal(id,name);

    }
}
