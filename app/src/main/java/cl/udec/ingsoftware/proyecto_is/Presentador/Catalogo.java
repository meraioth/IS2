package cl.udec.ingsoftware.proyecto_is.Presentador;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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

public class Catalogo {
    //TODO:Filtros serán aplicados aca. retornando solo strings a activites

    private ArrayList<Itinerario> itinerarios;
    private ArrayList<Sucursal> sucursales;
    private Formateador formateador;
//TODO: sacar y colocar en modelo itinerario
//    public Itinerario getItinerario(Itinerario it){
//        Itinerario r = null;
//        for (int i = 0; i < itinerarios.size(); i++){
//            if(itinerarios.get(i) == it){
//                r = itinerarios.get(i);
//            }
//        }
//        return r;
//    }

    public Catalogo(Context cont) {

        itinerarios = new ArrayList<Itinerario>();
        sucursales = new ArrayList<Sucursal>();
        formateador = new Formateador(cont);
    }


    public void getSucursales() throws SQLException {
        Iterator<Sucursal> it = formateador.getSucursales().iterator();
        while (it.hasNext()){
            sucursales.add(it.next());
        }
    }
}
