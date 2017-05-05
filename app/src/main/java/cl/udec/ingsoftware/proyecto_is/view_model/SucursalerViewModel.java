package cl.udec.ingsoftware.proyecto_is.view_model;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cl.udec.ingsoftware.proyecto_is.BR;
import cl.udec.ingsoftware.proyecto_is.model.Sucursal;
import cl.udec.ingsoftware.proyecto_is.model.source.SucursalesDataSource;
import cl.udec.ingsoftware.proyecto_is.model.source.SucursalesRepository;

/**
 * Created by matisin on 24-04-17.
 */
public class SucursalerViewModel extends BaseObservable {

    public final ObservableList<Sucursal> sucursales = new ObservableArrayList<>();

    public final ObservableField<String> texto_busqueda = new ObservableField<>("");

    public final ObservableBoolean dataLoading = new ObservableBoolean(false);

    private final SucursalesRepository mRepositorioSucursales;

    private final ObservableBoolean mIsDataLoadingError = new ObservableBoolean(false);

    private Context mContext; // To avoid leaks, this must be an Application Context.

    public SucursalerViewModel(
            SucursalesRepository repository,
            Context context) {
        mContext = context.getApplicationContext(); // Force use of Application Context.
        mRepositorioSucursales = repository;
    }

    public void start() {
        loadTasks(false);
    }

    public void loadTasks(boolean forceUpdate) {
        loadTasks(forceUpdate, true);
    }

    private void loadTasks(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            dataLoading.set(true);
        }
        if (forceUpdate) {

            mRepositorioSucursales.refreshSucursales();
        }

        mRepositorioSucursales.getSucursales(new SucursalesDataSource.LoadSucursalCallback() {
            @Override
            public void onSucursalLoaded(List<Sucursal> sucursals) {
                List<Sucursal> sucursalesToShow = new ArrayList<Sucursal>();

                // We filter the tasks based on the requestType
                for (Sucursal sucursal : sucursals) {
                    sucursalesToShow.add(sucursal);
                }
                if (showLoadingUI) {
                    dataLoading.set(false);
                }
                mIsDataLoadingError.set(false);

                sucursales.clear();
                sucursales.addAll(sucursalesToShow);
                notifyPropertyChanged(BR.sucursal); // It's a @Bindable so update manually
            }

            @Override
            public void onDataNotAvailable() {
                mIsDataLoadingError.set(true);
            }
        });
    }


    public void buscarSucursales(View view){
        Toast toast = Toast.makeText(mContext, texto_busqueda.get(), Toast.LENGTH_LONG);
        toast.show();
    }




}
