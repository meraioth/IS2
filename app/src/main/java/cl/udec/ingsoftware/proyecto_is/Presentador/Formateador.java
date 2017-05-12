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
        Log.d("Primera Carga", String.valueOf(primera_carga));
        //System.out.println("PRIMERA CARGA:"+primera_carga);
        System.out.println("version local bd:"+version_local);
        //System.out.println("version remoto db:"+consultor.getVersionRemoto());


        if(primera_carga){
            Toast.makeText(cont,"Primera Carga",Toast.LENGTH_SHORT).show();
            setVersion_local(consultor.getVersionRemoto());
            setPrimera_carga();
            resultSet = consultor.getSucursalesRemoto();
            agregarSucursales(resultSet,sucursales);
//            imprimir_resultado(resultSet);
            consultor.respaldar_sucursales(resultSet);

        }else if(isNetworkAvailable() && version_local!=consultor.getVersionRemoto() ){
            Toast.makeText(cont,"Base de Dato Desactualizada",Toast.LENGTH_SHORT).show();
            setVersion_local(consultor.getVersionRemoto());
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
            Sucursal sucursal = new Sucursal(null, -1, null, -1, -1);
            for (Sucursal suc : sucursales) {
                if (aux.getString(1) == String.valueOf(suc.getId())) {
                    //Si existe la sucursal, añadimos el servicio
                    sucursal = suc;
                    suc.addServicio(serv);
                    existe_sucursal = true;
                }
            }
            if (!existe_sucursal) {
                //Si no existe la sucursal, se crea y se añade servicio
                sucursal = new Sucursal(aux.getString("nombre"),aux.getInt("id"),aux.getString("sello_de_turismo"),
                        aux.getDouble("latitud"), aux.getDouble("longitud"));
                sucursal.addServicio(serv);
                sucursales.add(sucursal);

            }
        }
    }

    void agregarSucursales(Cursor aux, ArrayList<Sucursal> sucursales) throws SQLException {
        while(aux.moveToNext()){
            Sucursal sucursal = new Sucursal(aux.getString(1),aux.getInt(0),aux.getString(2),
                    aux.getDouble(5), aux.getDouble(6));
            System.out.println("Tupla---->> id :" + aux.getInt(0) + " nombre:" + aux.getString(1) + " comuna:" + aux.getString(4));

            sucursales.add(sucursal);
        }
    }

    //TODO: Recibir Cursor (Local) y ResultSet (Remota) y formatea los datos , entregando conjunto de objetos. (Respalda información en SQLite)
    private void setVersion_local(int version_remoto) {
        cont.getSharedPreferences("update",0).edit().putInt("ultimo",version_remoto).commit();

    }

    private void setPrimera_carga() {
        cont.getSharedPreferences("init",0).edit().putBoolean("bd",false).commit();

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) cont.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
