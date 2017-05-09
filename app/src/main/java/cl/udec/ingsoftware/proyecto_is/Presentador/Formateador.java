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
        primera_carga = cont.getSharedPreferences("init",0).getBoolean("bd",true);
        version_local = cont.getSharedPreferences("update",0).getInt("ultimo",0);
    }

    public ArrayList<Sucursal> getSucursales() throws SQLException {
        ArrayList<Sucursal> sucursales = new ArrayList<>();
        ResultSet resultSet;
        System.out.println("PRIMERA CARGA:"+primera_carga);
        System.out.println("version local bd:"+version_local);
        System.out.println("version remoto db"+consultor.getVersionRemoto());


        if(primera_carga){
            setPrimera_carga();
            resultSet = consultor.getSucursalesRemoto();
            agregarSucursales(resultSet,sucursales);
            consultor.respaldar_sucursales(resultSet);

        }else if(version_local!=consultor.getVersionRemoto()){
            setVersion_local(consultor.getVersionRemoto());
            consultor.reset_local();
            resultSet = consultor.getSucursalesRemoto();
            agregarSucursales(resultSet, sucursales);
            consultor.respaldar_sucursales(resultSet);
        }else{
            Cursor cursor= consultor.getSucursalesLocal();
            agregarSucursales(cursor,sucursales);
        }
        return sucursales;
    }






    private void setVersion_local(int version_remoto) {
        cont.getSharedPreferences("update",0).edit().putInt("ultimo",version_remoto).commit();

    }

    private void setPrimera_carga() {
        cont.getSharedPreferences("init",0).edit().putBoolean("bd",false).commit();

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
