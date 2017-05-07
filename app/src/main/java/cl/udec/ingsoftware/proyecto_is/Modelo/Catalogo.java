package cl.udec.ingsoftware.proyecto_is.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import cl.udec.ingsoftware.proyecto_is.BasesDeDatos.DBconnect;
import cl.udec.ingsoftware.proyecto_is.BasesDeDatos.DBlocal;
import cl.udec.ingsoftware.proyecto_is.Modelo.Itinerario;
import cl.udec.ingsoftware.proyecto_is.Modelo.Sucursal;

/**
 * Created by matisin on 28-12-16.
 */

public class Catalogo {
    private ArrayList<Itinerario> itinerarios;
    private ArrayList<Sucursal> sucursales;
    private DBconnect dBconnect;
    private DBlocal local;
    private Context cont;
    private ResultSet rs;
    boolean actualizar;
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
        dBconnect = new DBconnect();
        local = new DBlocal(cont);
        this.cont=cont;
    }
    //Carga Sucursales e itinerarios enteros.
    public void connect(){
        SharedPreferences sp = cont.getSharedPreferences("config_inicial",0);
//        System.out.println("bd local creada? :"+sp.getBoolean("creacion_bd",false));
        if(!isNetworkAvailable())
            offline();
       else {
            sp = cont.getSharedPreferences("update",0);
            int update = getLastUpdate();
            System.out.println("Ultima Version BD:"+update);
            actualizar=false;
            //Si el ultimo entero guardado es distinto del ultimo guardado en la bd remota, hay q actualizar la información
            if(sp.getInt("ultimo",1)!= update){
                sp.edit().putInt("ultimo",update).commit();
                actualizar=true;
                online();
            }else offline(); //si no hay actualización en bd ,trabaja en local y ahorra consumo de datos.

        }
    }
    //Cuando catalogo sabe que hay internet va a la base de datos, trae las sucursales,los servicios y otro que se necesite y lo guarda local (verificar ultimo id de tabla log)
    private void online() {
        System.out.println("Estoy online!!!!");
        RemoteSucursales();
        RemoteItinerarios();
    }

    private int getLastUpdate() {
        dBconnect.query("select max(update) from log;");
        rs = dBconnect.getResult();
        try {
            if(rs!=null)
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //TODO: guardar itinerarios locales;
    private void RemoteItinerarios() {
        dBconnect = new DBconnect();
        dBconnect.query("SELECT * FROM itinerario");
        rs = dBconnect.getResult();
        try {
            if(rs!=null)
                while (rs.next()){
                    //System.out.println("asd"+rs.getString("nombre"));
                    Itinerario it = new Itinerario(rs.getInt("id"),rs.getString("nombre"),rs.getString("duracion"));
                    itinerarios.add(it);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void RemoteSucursales() {
        dBconnect = new DBconnect();
        dBconnect.query("select * " +
                "from sucursal, servicio, sucursal_servicio, servicio_categoria " +
                "where sucursal.id = sucursal_servicio.id_sucursal " +
                "and sucursal_servicio.id_servicio = servicio.id " +
                "and servicio.id = servicio_categoria.id_servicio;") ;
        ResultSet rs = dBconnect.getResult();
        try {
            if(rs!= null)
                while (rs.next()){
                    boolean existe_sucursal=false;
                    //Crear Categoria
                    Categoria cat=new Categoria(rs.getString(16));
                    //Crear Servicio
                    Servicio serv= new Servicio(rs.getString(10),rs.getString(12),cat);
                    //Buscaar si existe la sucursal asociada al a tupla
                    Sucursal sucursal;
                    for (Sucursal suc:sucursales
                         ) {
                        if(rs.getString(1)==String.valueOf(suc.getId())){

                            //Si existe la sucursal, añadimos el servicio
                            suc.addServicio(serv);
                            existe_sucursal=true;
                        }
                    }
                    if(!existe_sucursal){
                        //Si no existe la sucursal, se crea y se añade servicio
                        sucursal = new Sucursal(rs.getString(2),rs.getInt(1),
                                rs.getString("sello_de_turismo"),rs.getDouble("latitud"),rs.getDouble("longitud"));
                        sucursal.addServicio(serv);
                        sucursales.add(sucursal);

                    }

                    if(actualizar){
                        //System.out.println("asd"+rs.getString("nombre"));
                        ContentValues contentValues = new ContentValues();
                        //contentValues.put("_id",rs.getInt("id"));
                        contentValues.put(local.getFieldName(),rs.getString("nombre"));
                        contentValues.put(local.getFieldSeal(),rs.getString("sello_de_turismo"));
                        contentValues.put(local.getFieldLat(),rs.getString("latitud"));
                        contentValues.put(local.getFieldLng(),rs.getString("longitud"));

                        local.insert(contentValues);

                    }
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void offline() {
        Cursor c = local.getAllLocations();
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                int id = c.getInt(0);
                float latitud = c.getFloat(3);
                float longitud = c.getFloat(4);
                String nombre = c.getString(1);
                String sello = c.getString(2);
                Sucursal suc = new Sucursal(nombre,id,sello,latitud,longitud);
                sucursales.add(suc);
                System.out.println("AGREGO LA WEA");
            } while(c.moveToNext());
        }
    }

    public ArrayList<Sucursal> busqueda_sucursal(String valor) {
        ArrayList<Sucursal> Suc = new ArrayList<Sucursal>();
        Iterator<Sucursal> iterator = sucursales.iterator();
        boolean res;
        while (iterator.hasNext()) {
            Sucursal S = iterator.next();
            res = S.isServicio(valor);
            if (res) {
                Suc.add(S);
            }

        }
        return Suc;
    }

    public ArrayList<Itinerario> busqueda_itinerario(String valor) {
        ArrayList<Itinerario> It = new ArrayList<Itinerario>();
        Iterator<Itinerario> iterator = itinerarios.iterator();
        Itinerario S;
        boolean res;
        while (iterator.hasNext()) {
            S = iterator.next();
            res = S.isServicio(valor);
            if (res) {
                It.add(S);
            }
        }
        return It;
    }

    public ArrayList servicios_to_array() {
        ArrayList It = new ArrayList();
        Iterator<Sucursal> iterator = sucursales.iterator();
        Sucursal S;
        boolean res;
        while (iterator.hasNext()) {
            S = iterator.next();
                It.add(S.getNombre());

        }
        return It;
    }
    public ArrayList itinerarios_to_array() {
        ArrayList It = new ArrayList();
        Iterator<Itinerario> iterator = itinerarios.iterator();
        Itinerario S;
        boolean res;
        while (iterator.hasNext()) {
            S = iterator.next();
            It.add(S.getNombre());

        }
        return It;
    }

    public ArrayList<Sucursal> getSucursales(){
        return sucursales;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) cont.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
