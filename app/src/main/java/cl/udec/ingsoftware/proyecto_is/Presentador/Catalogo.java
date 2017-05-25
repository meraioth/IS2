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
        sucursales = formateador.getSucursales();
        itinerarios = formateador.getItinerarios();

    }

    public ArrayList getSucursales(){
        ArrayList<String> sucursaless = new ArrayList();
        for (Sucursal suc:sucursales
             ) {
            sucursaless.add(suc.getNombre());
        }
        return sucursaless;
    }


    public ArrayList getIds(){
        ArrayList<Integer> ids = new ArrayList<>();
        for (Sucursal suc:sucursales
                ) {
            ids.add(suc.getId());

        }
        return ids;

    }

    public ArrayList getSucursales(String categoria){
        ArrayList<String> sucursaless = new ArrayList();
        for (Sucursal suc:sucursales
                ) {
            for(Servicio serv : suc.getServicios()){
                if(serv.getCategoria().getNombre().compareTo(categoria) == 0){
                    sucursaless.add(suc.getNombre());
                }
            }
        }
        return sucursaless;
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
        ArrayList<Tripleta> Resp = new ArrayList<>();
        ArrayList<Integer> ides = new ArrayList<Integer>();
        int id;
        for (Sucursal sucursal: sucursales) {
            for(Servicio serv: sucursal.getServicios()){
                if (sucursal.getNombre().contains(arg)|| serv.getNombre().contains(arg)||serv.getCategoria().getNombre().contains(arg)||sucursal.getComuna().contains(arg)){
                    id = sucursal.getId();
                    if (!ides.contains(id)){
                        ides.add(id);
                        Resp.add(new Tripleta(sucursal.getId(),sucursal.getNombre(),sucursal.getImagen()));
                    }
                }
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

    public ArrayList getLongitudes(){
        ArrayList<Double> longitudes = new ArrayList<>();
        for (Sucursal suc:sucursales
                ) {
            longitudes.add(suc.getLongitud());

        }
        return longitudes;
    }

    public ArrayList getLatitudes(){
        ArrayList<Double> latitudes = new ArrayList<>();
        for (Sucursal suc:sucursales
                ) {
            latitudes.add(suc.getLatitud());

        }
        return latitudes;

    }

    public ArrayList getLongitudes(String categoria){
        ArrayList<Double> longitudes = new ArrayList<>();
        for (Sucursal suc:sucursales
                ) {
            for(Servicio serv : suc.getServicios()){

                if(serv.getCategoria().getNombre().compareTo(categoria) == 0){
                    longitudes.add(suc.getLongitud());

                }
            }
        }
        return longitudes;
    }

    public ArrayList getLatitudes(String categoria){
        ArrayList<Double> latitudes = new ArrayList<>();
        for (Sucursal suc:sucursales
                ) {
            for (Servicio serv : suc.getServicios()) {
//                System.out.println("sucursal no filtrada "+suc.getNombre()+" "+serv.getCategoria().getNombre() +" categoria:"+categoria +" equals: "+String.valueOf(serv.getCategoria().getNombre().compareTo(categoria) == 0 ));

                if (serv.getCategoria().getNombre().compareTo(categoria) == 0 ){
                    System.out.println("sucursal filtrada "+suc.getNombre()+" "+serv.getCategoria().getNombre());

                    latitudes.add(suc.getLatitud());

                }
            }
        }
        return latitudes;

    }

    public ArrayList<String> getItinerarios (){
        ArrayList<String> nombreItinerarios = new ArrayList<>();
        for (Itinerario itinerario : itinerarios){
            nombreItinerarios.add(itinerario.getNombre());
        }
        return nombreItinerarios;
    }



    public ArrayList<String> getComunas() {
        Set<String> com = new HashSet<String>();
        ArrayList<String> comunas= new ArrayList<String>();
        //comunas.add("Todas");
        for (Sucursal suc: sucursales
                ) {

                com.add(suc.getComuna());

        }
        for (String str:com
                ) {
            comunas.add(str);
        }
        return comunas;
    }

    /**
     * Metodo para buscar las categorías que esten presentes en la comuna "comuna"
     * @param comuna
     * @return Arreglo de String con categorias presentes en la comuna "Comuna"
     */
    public ArrayList getCategorias(String comuna) {

        Set<String> cat = new HashSet<String>();
        ArrayList<String> categoria= new ArrayList<String>();
        categoria.add("Todas");
        for (Sucursal suc: sucursales
                ) {
            for (Servicio serv:suc.getServicios()
                    ) {
                if(suc.getComuna().compareTo(comuna)==0)
                cat.add(serv.getCategoria().getNombre());
            }
        }
        for (String str:cat
                ) {
            categoria.add(str);
        }
        return categoria;

    }

    /**
     * Metodo para filtrar las sucursales por comuna, categoria y servicio
     * @param str_comuna
     * @param str_categoria
     * @param str_servicio
     * @return Arreglo de tripletas
     */
    public ArrayList getTripletasOfSucursales(String str_comuna, String str_categoria, String str_servicio) {
        ArrayList<Tripleta> info = new ArrayList<Tripleta>();
        ArrayList<Integer> ides = new ArrayList<Integer>();
        int id;
        //Log.e("id", String.valueOf(sucursales.size()));
        for (Sucursal suc: sucursales) {
            id = suc.getId();
            if (!ides.contains(id)){
                ides.add(id);
                for (Servicio serv:suc.getServicios()
                     ) {
                    if(suc.getComuna().compareTo(str_comuna)==0 && serv.getNombre().compareTo(str_servicio)==0 && serv.getCategoria().getNombre().compareTo(str_categoria)==0) {
                        Tripleta tri = new Tripleta(suc.getId(), suc.getNombre(), suc.getImagen());
                        info.add(tri);
                    }
                }
            }
        }

        return info;
    }

    public ArrayList<String> getAllCategorias() {
        ArrayList<String> categoria= new ArrayList<String>();
        categoria.add("Todas");
        for (Sucursal suc: sucursales
                ) {
            for (Servicio serv:suc.getServicios()
                    ) {
                    categoria.add(serv.getCategoria().getNombre());
            }
        }

        return categoria;
    }
}
