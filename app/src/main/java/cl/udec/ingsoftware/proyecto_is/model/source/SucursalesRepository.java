package cl.udec.ingsoftware.proyecto_is.model.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cl.udec.ingsoftware.proyecto_is.model.Categoria;
import cl.udec.ingsoftware.proyecto_is.model.Sucursal;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementación para cargar sucursales desde las fuentes de datos al cache.
 * <p>
 * TODO: profundizar más
 * For simplicity, this implements a dumb synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source only if the local database doesn't
 * exist or is empty.
 */

public class SucursalesRepository implements SucursalesDataSource {

    private static SucursalesRepository INSTANCE = null;

    private final SucursalesDataSource mSucursalRemoteDataSource;

    private final SucursalesDataSource mSucursalLocalDataSource;

    /**
     * Variable con visibilidad local para los test
     */
    Map<Integer, Sucursal> mCachedSucursales;

    /**
     * Marca el cache como invalido para forzar una actualizacion la proxima vez que se requieran los datos
     */
    boolean mCacheIsDirty = false;

    private SucursalesRepository(@NonNull SucursalesDataSource sucursalRemoteSucursalesDataSource,
                                 @NonNull SucursalesDataSource sucursalLocalSucursalesDataSource){
        mSucursalRemoteDataSource = checkNotNull(sucursalRemoteSucursalesDataSource);
        mSucursalLocalDataSource = checkNotNull(sucursalLocalSucursalesDataSource);
    }

    /**
     * Retorna una instancia de la clase, creandola de ser necesario.
     *
     * @param sucursalRemoteSucursalesDataSource la fuente de datos remota
     * @param sucursalLocalSucursalesDataSource  la fuente de datos del dispositivo
     * @return the {@link SucursalesRepository} instance
     */
    public static SucursalesRepository getInstance(SucursalesDataSource sucursalRemoteSucursalesDataSource,
                                                   SucursalesDataSource sucursalLocalSucursalesDataSource){
        if(INSTANCE == null){
            INSTANCE = new SucursalesRepository(sucursalRemoteSucursalesDataSource, sucursalLocalSucursalesDataSource);
        }
        return INSTANCE;
    }

    /**
     * Se usa para forzar a {@link #getInstance(SucursalesDataSource, SucursalesDataSource)}para crear una nueva
     * instancia la proxima vez que sea llamado.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    //TODO: implementar las funciones de la interfaz


    /**
     * Obtiene los sucursales del cache, datos locales (SQLite) o de la base de datos remota, la que este
     * disponible primero
     * <p>
     * Note: {@link SucursalesDataSource.LoadSucursalCallback#onDataNotAvailable()} es llamado cuando todas las fuentes
     * de datos fallan en buscar los datos.
     */

    @Override
    public void getSucursales(@NonNull final LoadSucursalCallback callback) {
        checkNotNull(callback);

        // Se responde con la caché si está disponible
        if (mCachedSucursales != null && !mCacheIsDirty) {
            callback.onSucursalLoaded(new ArrayList<>(mCachedSucursales.values()));
            return;
        }

        if (mCacheIsDirty) {
            // Si la cache está sucia se buscan los datos de internet
            getSucursalesFromRemoteDataSource(callback);
        } else {
            // Si esta disponible, se traen los datos desde la bd local, si no, de internet
            mSucursalLocalDataSource.getSucursales(new LoadSucursalCallback(){
                @Override
                public void onSucursalLoaded(List<Sucursal> sucursales) {
                    refreshCache(sucursales);
                    callback.onSucursalLoaded(new ArrayList<>(mCachedSucursales.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    getSucursalesFromRemoteDataSource(callback);
                }
            });
        }
    }

    @Override
    public void getSucursal(@NonNull final int sucursalId, @NonNull final GetSucursalCallback callback) {
        checkNotNull(sucursalId);
        checkNotNull(callback);

        Sucursal cachedSucursal = getSucursalWithId(sucursalId);

        // Responder con cache si está disponible
        if (cachedSucursal != null) {
            callback.onSucursalLoaded(cachedSucursal);
            return;
        }

        // Load from server/persisted if needed.

        // Is the task in the local data source? If not, query the network.
        mSucursalLocalDataSource.getSucursal(sucursalId, new GetSucursalCallback() {
            @Override
            public void onSucursalLoaded(Sucursal sucursal) {
                // Do in memory cache update to keep the app UI up to date
                if (mCachedSucursales == null) {
                    mCachedSucursales = new LinkedHashMap<>();
                }
                mCachedSucursales.put(sucursal.getId(), sucursal);
                callback.onSucursalLoaded(sucursal);
            }

            @Override
            public void onDataNotAvailable() {
                mSucursalRemoteDataSource.getSucursal(sucursalId, new GetSucursalCallback() {
                    @Override
                    public void onSucursalLoaded(Sucursal sucursal) {
                        // Do in memory cache update to keep the app UI up to date
                        if (mCachedSucursales == null) {
                            mCachedSucursales = new LinkedHashMap<>();
                        }
                        mCachedSucursales.put(sucursal.getId(), sucursal);
                        callback.onSucursalLoaded(sucursal);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }

    @Override
    public void filtrarSucursales(@NonNull LoadSucursalFilterCallback callback, @NonNull List<Categoria> categorias) {

    }

    @Override
    public void saveSucursal(@NonNull Sucursal sucursal) {
        //TODO: implementar
    }

    @Override
    public void deleteAllSucursales() {
        //TODO: implementar
    }

    @Override
    public void refreshSucursales() {

    }

    private void getSucursalesFromRemoteDataSource(@NonNull final LoadSucursalCallback callback) {
        mSucursalRemoteDataSource.getSucursales(new LoadSucursalCallback() {
            @Override
            public void onSucursalLoaded(List<Sucursal> sucursales) {
                refreshCache(sucursales);
                refreshLocalDataSource(sucursales);
                callback.onSucursalLoaded(new ArrayList<>(mCachedSucursales.values()));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });

    }

    private void refreshCache(List<Sucursal> sucursales) {
        if (mCachedSucursales == null) {
            mCachedSucursales = new LinkedHashMap<>();
        }
        mCachedSucursales.clear();
        for (Sucursal sucursal : sucursales) {
            mCachedSucursales.put(sucursal.getId(), sucursal);
        }
        mCacheIsDirty = false;
    }

    private void refreshLocalDataSource(List<Sucursal> sucursales) {

        mSucursalLocalDataSource.deleteAllSucursales();
        for (Sucursal sucursal : sucursales) {
            mSucursalLocalDataSource.saveSucursal(sucursal);
        }
    }

    @Nullable
    private Sucursal getSucursalWithId(@NonNull int id) {
        checkNotNull(id);
        if (mCachedSucursales == null || mCachedSucursales.isEmpty()) {
            return null;
        } else {
            return mCachedSucursales.get(id);
        }
    }
}