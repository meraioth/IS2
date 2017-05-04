package cl.udec.ingsoftware.proyecto_is.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cl.udec.ingsoftware.proyecto_is.databinding.SucursalCardLayoutBinding;
import cl.udec.ingsoftware.proyecto_is.model.Sucursal;

public class SucursalViewHolder extends RecyclerView.ViewHolder {
    private SucursalCardLayoutBinding binding;

    public SucursalViewHolder(View view){
        super(view);
        binding = DataBindingUtil.bind(view);
    }

    public void bind(Sucursal sucursal){
        binding.setSucursal(sucursal);
    }
}
