package cl.udec.ingsoftware.proyecto_is.Presentador;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

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
    private boolean primera_carga[]=new boolean[2];
    private int version_local[]=new int[2];

    public Formateador(Context cont){
        this.cont=cont;
        consultor=new Consultor(cont);
        primera_carga[0] = cont.getSharedPreferences("init",0).getBoolean("sucursal",true);
        primera_carga[1] = cont.getSharedPreferences("init",0).getBoolean("itinerario",true);
        version_local[0] = cont.getSharedPreferences("update",0).getInt("sucursal",0);
        version_local[1] = cont.getSharedPreferences("update",0).getInt("itinerario",0);

    }

    public ArrayList<Sucursal> getSucursales() throws SQLException {
        ArrayList<Sucursal> sucursales = new ArrayList<>();
        ResultSet resultSet;
        Log.d("Primera Carga", String.valueOf(primera_carga[0]));
        //System.out.println("PRIMERA CARGA:"+primera_carga);
        System.out.println("version local sucursal:"+version_local[0]);
        //System.out.println("version remoto db:"+consultor.getVersionRemoto());


        if(primera_carga[0]){
            Toast.makeText(cont,"Primera Carga",Toast.LENGTH_SHORT).show();
            setVersion_local(consultor.getVersionRemoto(),0);
            setPrimera_carga(0);
            resultSet = consultor.getSucursalesRemoto();
            agregarSucursales(resultSet,sucursales);
//            imprimir_resultado(resultSet);
            consultor.respaldar_sucursales(resultSet);

        }else if(isNetworkAvailable() && version_local[0]!=consultor.getVersionRemoto() ){
            Toast.makeText(cont,"Base de Dato Desactualizada",Toast.LENGTH_SHORT).show();
            setVersion_local(consultor.getVersionRemoto(),0);
            consultor.reset_local();
            resultSet = consultor.getSucursalesRemoto();
            agregarSucursales(resultSet, sucursales);
            consultor.respaldar_sucursales(resultSet);
        }else{
            Toast.makeText(cont,"Cargando Local",Toast.LENGTH_SHORT).show();
            Cursor cursor= consultor.getSucursalesLocal();
            agregarSucursales(cursor,sucursales);
        }

        return sucursales;
    }

    private void imprimir_resultado(ResultSet resultSet) throws SQLException {
            if(resultSet!=null) resultSet.first();
            while (resultSet.next()) {
                for(int i=1;i<5;i++){
                    System.out.print(resultSet.getString(i));
                }
                System.out.println("");

            }
    }


    void agregarSucursales(ResultSet aux, ArrayList<Sucursal> sucursales) throws SQLException {
        while(aux.next()) {
            boolean existe_sucursal = false;
            //Crear Categoria
            Categoria cat = new Categoria(aux.getString(18),aux.getString(19));
            //Crear Servicio
            Servicio serv = new Servicio(aux.getInt(12), aux.getString(13), aux.getString(15), cat);
            //Buscaar si existe la sucursal asociada al a tupla
            Sucursal sucursal;
            for (Sucursal suc : sucursales) {
                if (aux.getString(1) == String.valueOf(suc.getId())) {
                    //Si existe la sucursal, añadimos el servicio
                    suc.addServicio(serv);
                    existe_sucursal = true;
                }
            }
            if (!existe_sucursal) {
                //Si no existe la sucursal, se crea y se añade servicio
                sucursal = new Sucursal(aux.getString("nombre"),aux.getInt("id"),aux.getInt("sello_de_turismo"),
                        aux.getDouble("latitud"), aux.getDouble("longitud"), aux.getString("foto"), aux.getString("descripcion"));
                sucursal.addServicio(serv);
                sucursales.add(sucursal);
            }
        }
    }

    void agregarSucursales(Cursor aux, ArrayList<Sucursal> sucursales) throws SQLException {
        while(aux.moveToNext()){
            boolean existe_sucursal = false;
            //Crear Categoria
            Categoria cat = new Categoria(aux.getString(17),aux.getString(18));
            //Crear Servicio
            Servicio serv = new Servicio(aux.getInt(11), aux.getString(12), aux.getString(14), cat);
            //Buscaar si existe la sucursal asociada al a tupla
            Sucursal sucursal;
            for (Sucursal suc : sucursales) {
                if (aux.getString(0) == String.valueOf(suc.getId())) {
                    //Si existe la sucursal, añadimos el servicio
                    suc.addServicio(serv);
                    existe_sucursal = true;
                }
            }
            if (!existe_sucursal) {
                //Si no existe la sucursal, se crea y se añade servicio
                sucursal = new Sucursal(aux.getString(1),aux.getInt(0),aux.getInt(2),
                        aux.getDouble(5), aux.getDouble(6), aux.getString(8), aux.getString(7));
                sucursal.addServicio(serv);
                sucursales.add(sucursal);
            }
        }
    }

    //TODO: Recibir Cursor (Local) y ResultSet (Remota) y formatea los datos , entregando conjunto de objetos. (Respalda información en SQLite)
    private void setVersion_local(int version_remoto,int caso) {
        switch (caso){

            case 0: cont.getSharedPreferences("update",0).edit().putInt("sucursal",version_remoto).commit();
                break;
            case 1: cont.getSharedPreferences("update",0).edit().putInt("itinerario",version_remoto).commit();
                break;
        }


    }

    private void setPrimera_carga(int caso) {
        switch (caso){

            case 0: cont.getSharedPreferences("init",0).edit().putBoolean("sucursal",false).commit();
            break;
            case 1: cont.getSharedPreferences("init",0).edit().putBoolean("itinerario",false).commit();
            break;
        }


    }



    public ArrayList<Itinerario> getItinerarios() throws SQLException {
        ArrayList<Itinerario> itinerarios = new ArrayList<>();
        ResultSet resultSet;
        Log.d("Primera Carga", String.valueOf(primera_carga[1]));
        //System.out.println("PRIMERA CARGA:"+primera_carga);
        System.out.println("version local sucursal:"+version_local[1]);
        //System.out.println("version remoto db:"+consultor.getVersionRemoto());


        if(primera_carga[1]){
            Toast.makeText(cont,"Primera Carga",Toast.LENGTH_SHORT).show();
            setVersion_local(consultor.getVersionRemoto(),1);
            setPrimera_carga(1);
            resultSet = consultor.getSucursalesRemoto();
            agregarItinerarios(resultSet,itinerarios);
//            imprimir_resultado(resultSet);
            //consultor.respaldar_Itinerario(resultSet);

        }else if(isNetworkAvailable() && version_local[1]!=consultor.getVersionRemoto() ){
            Toast.makeText(cont,"Base de Dato Desactualizada",Toast.LENGTH_SHORT).show();
            setVersion_local(consultor.getVersionRemoto(),1);
            consultor.reset_local();
            resultSet = consultor.getItinerariosRemoto();
            agregarItinerarios(resultSet, itinerarios);
            //consultor.respaldar_Itinerario(resultSet);
        }else{
            Toast.makeText(cont,"Cargando Local",Toast.LENGTH_SHORT).show();
            Cursor cursor= consultor.getItinerariosLocal();
            agregarItinerarios(cursor,itinerarios);
        }
        return itinerarios;
    }

    private void agregarItinerarios(ResultSet aux, ArrayList<Itinerario> itinerario) {
    }

    private void agregarItinerarios(Cursor aux, ArrayList<Itinerario> itinerario) {
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) cont.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
