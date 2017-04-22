package cl.udec.ingsoftware.proyecto_is.data.source.remote;

import android.support.annotation.NonNull;

import cl.udec.ingsoftware.proyecto_is.data.Categoria;
import cl.udec.ingsoftware.proyecto_is.data.Sucursal;
import cl.udec.ingsoftware.proyecto_is.data.source.SucursalesDataSource;
import cl.udec.ingsoftware.proyecto_is.data.source.local.PersistenceContract.SucursalEntry;

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

        dbconnect.query("SELECT * FROM sucursal") ;
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

        String query = "select * from sucursal where id = " + sucursalId;
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

    @Override
    public void saveSucursal(@NonNull Sucursal sucursal) {
        //TODO: implementar si se necesita
    }

    @Override
    public void deleteAllSucursales() {
        //TODO: implementar si se necesita
    }
}

