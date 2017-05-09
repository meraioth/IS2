package cl.udec.ingsoftware.proyecto_is.Presentador;

import android.content.Context;
import android.database.Cursor;

import java.sql.ResultSet;
import java.util.ArrayList;

import cl.udec.ingsoftware.proyecto_is.BasesDeDatos.Consultor;
import cl.udec.ingsoftware.proyecto_is.Modelo.Categoria;
import cl.udec.ingsoftware.proyecto_is.Modelo.Itinerario;
import cl.udec.ingsoftware.proyecto_is.Modelo.Servicio;
import cl.udec.ingsoftware.proyecto_is.Modelo.Sucursal;

/**
 * Created by meraioth on 08-05-17.
 */

public class Formateador {
    private ArrayList<Itinerario> itinerarios;
    private ArrayList<Sucursal> sucursales;
    private ArrayList<Categoria> categorias;
    private ArrayList<Servicio> Servicios;
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

    public ArrayList<Sucursal> getSucursales(){
        if(primera_carga){
            setPrimera_carga();
            ResultSet aux = consultor.getSucursalesRemoto();

        }else if(version_local!=consultor.getVersionRemoto()){
            setVersion_local();
            reset_local();
            ResultSet aux = consultor.getSucursalesRemoto();

        }else{
            Cursor cursor= consultor.getSucursalesLocal();
            //TODO:Hacer proceso de formato
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

    //TODO: Recibir Cursor (Local) y ResultSet (Remota) y formatea los datos , entregando conjunto de objetos. (Respalda información en SQLite)

}
