package cl.udec.ingsoftware.proyecto_is.Presentador;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import cl.udec.ingsoftware.proyecto_is.BasesDeDatos.DBremoto;
import cl.udec.ingsoftware.proyecto_is.BasesDeDatos.DBlocal;
import cl.udec.ingsoftware.proyecto_is.Modelo.Categoria;
import cl.udec.ingsoftware.proyecto_is.Modelo.Itinerario;
import cl.udec.ingsoftware.proyecto_is.Modelo.Servicio;
import cl.udec.ingsoftware.proyecto_is.Modelo.Sucursal;

/**
 * Created by matisin on 28-12-16.
 */

public class Catalogo implements Serializable {
    //TODO:Filtros serán aplicados aca. retornando solo strings a activites

    private ArrayList<Itinerario> itinerarios;
    private ArrayList<Sucursal> sucursales;
    private Formateador formateador;

    public Catalogo(Context cont) {

        itinerarios = new ArrayList<Itinerario>();
        sucursales = new ArrayList<Sucursal>();
        formateador = new Formateador(cont);
    }


    public ArrayList getSucursales(){
        ArrayList<Sucursal> suc = null;
        try {
            suc = formateador.getSucursales();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList salida = new ArrayList();
        for (Sucursal sucursal:suc
             ) {
            salida.add(sucursal.getNombre());
        }
        return suc;
    }
}
