package cl.udec.ingsoftware.proyecto_is.model.source;

import android.support.annotation.NonNull;

import java.util.List;

import cl.udec.ingsoftware.proyecto_is.model.Categoria;
import cl.udec.ingsoftware.proyecto_is.model.Servicio;

/**
 * Created by meraioth on 03-05-17.
 */

public interface ServiciosDataSource {

    interface LoadServicioCallback {

        void onServicioLoaded(List<Servicio> servicio);

        void onDataNotAvailable();
    }

    interface GetServicioCallback {

        void onServicioLoaded(Servicio servicio);

        void onDataNotAvailable();

    }
    interface LoadServicioFilterCallback {

        void onServicioFilter(List<Servicio> servicio);

        void onDataNotAvailable();
    }

    void getServicio(@NonNull ServiciosDataSource.LoadServicioCallback callback);

    void getServicio(@NonNull int servicioId, @NonNull ServiciosDataSource.GetServicioCallback callback);

    void filtrarServicio(@NonNull ServiciosDataSource.LoadServicioFilterCallback callback, @NonNull List<Categoria> categorias);

    void saveServicio(@NonNull Servicio servicio);

    void getServicioesKeyword(final @NonNull ServiciosDataSource.LoadServicioCallback callback, @NonNull String str);

    void deleteAllServicio();

    void refreshServicioes();
}
