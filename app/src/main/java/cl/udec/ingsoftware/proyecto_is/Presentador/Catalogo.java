package cl.udec.ingsoftware.proyecto_is.Presentador;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.util.Pair;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cl.udec.ingsoftware.proyecto_is.BasesDeDatos.DBremoto;
import cl.udec.ingsoftware.proyecto_is.BasesDeDatos.DBlocal;
import cl.udec.ingsoftware.proyecto_is.Modelo.Categoria;
import cl.udec.ingsoftware.proyecto_is.Modelo.Itinerario;
import cl.udec.ingsoftware.proyecto_is.Modelo.Servicio;
import cl.udec.ingsoftware.proyecto_is.Modelo.Sucursal;
import cl.udec.ingsoftware.proyecto_is.Modelo.Tripleta;

/**
 * Created by matisin on 28-12-16.
 */

public class Catalogo implements Serializable {

    //TODO:Filtros serán aplicados aca. retornando solo strings a activites

    private ArrayList<Itinerario> itinerarios;
    private ArrayList<Sucursal> sucursales;
    private Formateador formateador;

    public Catalogo(Context cont) throws SQLException {
        formateador = new Formateador(cont);
        itinerarios = new ArrayList<Itinerario>();
        sucursales = formateador.getSucursales();
    }


     public ArrayList getTripletasOfSucursales()throws SQLException {
        ArrayList<Tripleta> info = new ArrayList<Tripleta>();
        ArrayList<Integer> ides = new ArrayList<Integer>();
        int id;
        //Log.e("id", String.valueOf(sucursales.size()));
        for (Sucursal suc: sucursales) {
            id = suc.getId();
            if (!ides.contains(id)){
                ides.add(id);
                Tripleta tri = new Tripleta(suc.getId(),suc.getNombre(),suc.getImagen());
                info.add(tri);

            }
        }

        return info;
    }

    public ArrayList getBuscarKeyword(String arg) {
        ArrayList<Sucursal> Sucursales = new ArrayList<>();
        ArrayList<String> Resp = new ArrayList<String>();

        for (Sucursal sucursal:Sucursales) {
            if (sucursal.getNombre() == arg){
                Resp.add(sucursal.getNombre());
            }
        }
        return Resp;
    }


    public  ArrayList getCategorias(){
        Set<String> cat = new HashSet<String>();
        ArrayList<String> categoria= new ArrayList<String>();
        categoria.add("Todas");
        for (Sucursal suc: sucursales
             ) {
            for (Servicio serv:suc.getServicios()
                 ) {
                cat.add(serv.getCategoria().getNombre());
            }
        }
        for (String str:cat
             ) {
            categoria.add(str);
        }
        return categoria;
    }


    public  ArrayList getServicios(){
        Set<String> ser = new HashSet<String>();
        ArrayList<String> servicios= new ArrayList<String>();
        for (Sucursal suc: sucursales
                ) {
            for (Servicio serv:suc.getServicios()
                    ) {
                ser.add(serv.getNombre());
            }
        }
        for (String str:ser
                ) {
            servicios.add(str);
        }
        return servicios;
    }

    public  ArrayList getServiciosBusqueda(String categoria){
        Set<String> ser = new HashSet<String>();
        ArrayList<String> servicios= new ArrayList<String>();
        for (Sucursal suc: sucursales
                ) {
            for (Servicio serv:suc.getServicios()
                    ) {
                if(serv.getCategoria().getNombre()==categoria){
                    ser.add(serv.getNombre());
                }

            }
        }
        for (String str:ser
                ) {
            servicios.add(str);
        }
        return servicios;
    }




}
