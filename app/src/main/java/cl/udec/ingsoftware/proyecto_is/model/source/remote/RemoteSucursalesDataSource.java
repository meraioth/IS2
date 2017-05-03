package cl.udec.ingsoftware.proyecto_is.model.source.remote;

import android.support.annotation.NonNull;

import cl.udec.ingsoftware.proyecto_is.model.Categoria;
import cl.udec.ingsoftware.proyecto_is.model.Sucursal;
import cl.udec.ingsoftware.proyecto_is.model.source.SucursalesDataSource;
import cl.udec.ingsoftware.proyecto_is.model.source.local.PersistenceContract;
import cl.udec.ingsoftware.proyecto_is.model.source.local.PersistenceContract.SucursalEntry;
import cl.udec.ingsoftware.proyecto_is.model.source.local.PersistenceContract.ServicioEntry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by matisin on 21-04-17.
 */

public class RemoteSucursalesDataSource implements SucursalesDataSource {

    private static RemoteSucursalesDataSource INSTANCE;

    private DBconnect dbconnect;

    public static RemoteSucursalesDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteSucursalesDataSource();
        }
        return INSTANCE;
    }

    // Constructor
    private RemoteSucursalesDataSource() {}


    /**
     * Note: {@link SucursalesDataSource.LoadSucursalCallback#onDataNotAvailable()} se llama cuando el
     * servidor no puede ser contactado o el servidor retorna error.
     */
    @Override
    public void getSucursales(final @NonNull SucursalesDataSource.LoadSucursalCallback callback) {
        List<Sucursal> sucursales = new ArrayList<>();
        dbconnect = new DBconnect();

        dbconnect.query("SELECT * FROM "+SucursalEntry.TABLE_NAME) ;
        ResultSet rs = dbconnect.getResult();

        try {
            if(rs!= null)
                while (rs.next()){
                    int id = rs.getInt(SucursalEntry.COLUMN_NAME_ENTRY_ID);
                    String nombre = rs.getString(SucursalEntry.COLUMN_NAME_NOMBRE);
                    int sello = rs.getInt(SucursalEntry.COLUMN_NAME_SELLO);
                    String rut_empresa = rs.getString(SucursalEntry.COLUMN_NAME_EMPRESA);
                    String comuna = rs.getString(SucursalEntry.COLUMN_NAME_COMUNA);
                    double latitud = rs.getDouble(SucursalEntry.COLUMN_NAME_LAT);
                    double longitud = rs.getDouble(SucursalEntry.COLUMN_NAME_LONG);
                    Sucursal sucursal = new Sucursal(id,nombre,sello,rut_empresa,comuna,latitud,longitud);
                    sucursales.add(sucursal);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (sucursales.isEmpty()) {
            // Cuando es nuevo o está vacio
            callback.onDataNotAvailable();
        } else {
            callback.onSucursalLoaded(sucursales);
        }
    }

    @Override
    public void getSucursal(int sucursalId, @NonNull GetSucursalCallback callback) {
        Sucursal sucursal = null;
        dbconnect = new DBconnect();

        String query = "select * from"+SucursalEntry.TABLE_NAME+ "where"+SucursalEntry.COLUMN_NAME_ENTRY_ID+ "="+ sucursalId;
        dbconnect.query(query);
        ResultSet rs = dbconnect.getResult();

        try {
            if(rs!= null) {
                int id = rs.getInt(SucursalEntry.COLUMN_NAME_ENTRY_ID);
                String nombre = rs.getString(SucursalEntry.COLUMN_NAME_NOMBRE);
                int sello = rs.getInt(SucursalEntry.COLUMN_NAME_SELLO);
                String rut_empresa = rs.getString(SucursalEntry.COLUMN_NAME_EMPRESA);
                String comuna = rs.getString(SucursalEntry.COLUMN_NAME_COMUNA);
                double latitud = rs.getDouble(SucursalEntry.COLUMN_NAME_LAT);
                double longitud = rs.getDouble(SucursalEntry.COLUMN_NAME_LONG);
                sucursal = new Sucursal(id, nombre, sello, rut_empresa, comuna, latitud, longitud);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (sucursal == null) {
            // Cuando es nuevo o está vacio
            callback.onDataNotAvailable();
        } else {
            callback.onSucursalLoaded(sucursal);
        }
    }

    //TODO: implementar filtrar sucursales en base a categorias
    @Override
    public void filtrarSucursales(@NonNull LoadSucursalFilterCallback callback, @NonNull List<Categoria> categorias) {

    }
    //Metodo para obtener sucursales dada una o más palabras claves, se obtienen las sucursales que en su nombre o en el nombre
    //del servicio tengan alguna de las palabras claves
   @Override
   public void getSucursalesKeyword(@NonNull LoadSucursalCallback callback, @NonNull String str) {
       List<Sucursal> sucursales = new ArrayList<>();
       String[] parsing = str.split(" +,-");
       dbconnect = new DBconnect();

       String query="select * from"+SucursalEntry.TABLE_NAME+","+ ServicioEntry.TABLE_NAME +", sucursal_servicio where sucursal.id=sucursal_servicio.id_sucursal and servicio.id=sucursal_servicio.id_servicio and ( ";
       for (int i=0;i<parsing.length;i++) {
           query += " ("+SucursalEntry.TABLE_NAME+"."+
                   SucursalEntry.COLUMN_NAME_NOMBRE +" like '%"+parsing[i]+"%' or "+
                   ServicioEntry.TABLE_NAME+"."+ServicioEntry.COLUMN_NAME_NOMBRE + "like '%"+parsing[i]+"%' )";
           if(i!=parsing.length-1) query+=" or ";
       }
       query+=");";
       dbconnect.query(query) ;
       ResultSet rs = dbconnect.getResult();

       try {
           if(rs!= null)
               while (rs.next()){
                   int id = rs.getInt(SucursalEntry.COLUMN_NAME_ENTRY_ID);
                   String nombre = rs.getString(SucursalEntry.COLUMN_NAME_NOMBRE);
                   int sello = rs.getInt(SucursalEntry.COLUMN_NAME_SELLO);
                   String rut_empresa = rs.getString(SucursalEntry.COLUMN_NAME_EMPRESA);
                   String comuna = rs.getString(SucursalEntry.COLUMN_NAME_COMUNA);
                   double latitud = rs.getDouble(SucursalEntry.COLUMN_NAME_LAT);
                   double longitud = rs.getDouble(SucursalEntry.COLUMN_NAME_LONG);
                   Sucursal sucursal = new Sucursal(id,nombre,sello,rut_empresa,comuna,latitud,longitud);
                   sucursales.add(sucursal);
               }
       } catch (SQLException e) {
           e.printStackTrace();
       }
       if (sucursales.isEmpty()) {
           // Cuando es nuevo o está vacio
           callback.onDataNotAvailable();
       } else {
           callback.onSucursalLoaded(sucursales);
       }
   }


    @Override
    public void saveSucursal(@NonNull Sucursal sucursal) {
        //TODO: implementar si se necesita
    }



    @Override
    public void deleteAllSucursales() {
        //TODO: implementar si se necesita
    }

    @Override
    public void refreshSucursales() {

    }
}

