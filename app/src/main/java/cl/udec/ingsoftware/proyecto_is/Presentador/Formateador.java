package cl.udec.ingsoftware.proyecto_is.Presentador;

import android.content.Context;
import android.database.Cursor;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import cl.udec.ingsoftware.proyecto_is.BasesDeDatos.Consultor;
import cl.udec.ingsoftware.proyecto_is.Modelo.Categoria;
import cl.udec.ingsoftware.proyecto_is.Modelo.Itinerario;
import cl.udec.ingsoftware.proyecto_is.Modelo.Servicio;
import cl.udec.ingsoftware.proyecto_is.Modelo.Sucursal;

/**
 * Created by meraioth on 08-05-17.
 */

public class Formateador {
    private Consultor consultor;
    private Context cont;
    private boolean primera_carga;
    private int version_local;

    public Formateador(Context cont){
        this.cont=cont;
        consultor=new Consultor(cont);
        primera_carga = cont.getSharedPreferences("init",0).getBoolean("bd",false);
        version_local = cont.getSharedPreferences("update",0).getInt("ultimo",1);
    }

    public ArrayList<Sucursal> getSucursales() throws SQLException {
        ArrayList<Sucursal> sucursales = new ArrayList<>();
        ResultSet aux;
        if(primera_carga){
            setPrimera_carga();
            aux = consultor.getSucursalesRemoto();
            agregarSucursales(aux,sucursales);

        }else if(version_local!=consultor.getVersionRemoto()){
            setVersion_local();
            reset_local();
            aux = consultor.getSucursalesRemoto();
            agregarSucursales(aux, sucursales);
        }else{
            Cursor cursor= consultor.getSucursalesLocal();
            agregarSucursales(cursor,sucursales);
        }
        return sucursales;
    }



    //Borrar la base de datos local, para actualización
    private void reset_local() {
    }

    //TODO:Hacer este metodo asincrono
    private void setVersion_local() {
        cont.getSharedPreferences("init",0).edit().putBoolean("bd",true).commit();

    }

    //TODO:Hacer este metodo asincrono
    private void setPrimera_carga() {
        cont.getSharedPreferences("update",0).edit().putInt("ultimo",consultor.getVersionRemoto()).commit();
    }

    void agregarSucursales(ResultSet aux, ArrayList<Sucursal> sucursales) throws SQLException {
        while(aux.next()){
            Sucursal sucursal = new Sucursal(aux.getString("nombre"),aux.getInt("id"),aux.getString("sello_de_turismo"),
                    aux.getDouble("latitud"), aux.getDouble("longitud"));
            sucursales.add(sucursal);
        }
    }
    void agregarSucursales(Cursor aux, ArrayList<Sucursal> sucursales) throws SQLException {
        while(aux.moveToNext()){
            Sucursal sucursal = new Sucursal(aux.getString(0),aux.getInt(1),aux.getString(2),
                    aux.getDouble(3), aux.getDouble(4));
            sucursales.add(sucursal);
        }
    }

    //TODO: Recibir Cursor (Local) y ResultSet (Remota) y formatea los datos , entregando conjunto de objetos. (Respalda información en SQLite)

}
