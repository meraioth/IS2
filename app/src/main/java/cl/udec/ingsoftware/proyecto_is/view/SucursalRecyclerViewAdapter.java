package cl.udec.ingsoftware.proyecto_is.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cl.udec.ingsoftware.proyecto_is.R;
import cl.udec.ingsoftware.proyecto_is.model.Sucursal;

/**
 * Created by matisin on 03-05-17.
 */

public class SucursalRecyclerViewAdapter extends RecyclerView.Adapter<SucursalViewHolder> {

    private List<Sucursal> listaSucursales;

    public SucursalRecyclerViewAdapter(List<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    @Override
    public SucursalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View statusContainer = inflater.inflate(R.layout.sucursal_card_layout, parent, false);

        return new  SucursalViewHolder(statusContainer);
    }

    @Override
    public void onBindViewHolder(SucursalViewHolder holder, int position) {
        Sucursal status = listaSucursales.get(position);
        holder.bind(status);
    }

    @Override
    public int getItemCount() {
        return listaSucursales.size();
    }


}
