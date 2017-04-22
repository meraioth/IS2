package cl.udec.ingsoftware.proyecto_is.data.source;

import android.support.annotation.NonNull;



import java.util.List;

import cl.udec.ingsoftware.proyecto_is.data.Categoria;
import cl.udec.ingsoftware.proyecto_is.data.Sucursal;

/**
 * Principal punto de entrada para obtener datos de sucursales
 */


public interface SucursalesDataSource {

    interface LoadSucursalCallback {

        void onSucursalLoaded(List<Sucursal> sucursales);

        void onDataNotAvailable();
    }

    interface GetSucursalCallback {

        void onSucursalLoaded(Sucursal sucursal);

        void onDataNotAvailable();

    }
    interface LoadSucursalFilterCallback {

        void onSucursalFilter(List<Sucursal> sucursales);

        void onDataNotAvailable();
    }

    void getSucursales(@NonNull LoadSucursalCallback callback);

    void getSucursal(@NonNull int sucursalId, @NonNull GetSucursalCallback callback);

    void filtrarSucursales(@NonNull LoadSucursalFilterCallback callback, @NonNull List<Categoria> categorias);

    void saveSucursal(@NonNull Sucursal sucursal);

    void deleteAllSucursales();
}