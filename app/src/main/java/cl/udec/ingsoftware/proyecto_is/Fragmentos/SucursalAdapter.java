package cl.udec.ingsoftware.proyecto_is.Fragmentos;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cl.udec.ingsoftware.proyecto_is.Modelo.Sucursal;
import cl.udec.ingsoftware.proyecto_is.R;


public class SucursalAdapter extends RecyclerView.Adapter<SucursalAdapter.SucursalViewHolder>{

    List<Sucursal> sucursales;

    SucursalAdapter(List<Sucursal> sucursales){
        this.sucursales = sucursales;
    }

    @Override
    public SucursalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View statusContainer = inflater.inflate(R.layout.cardview_sucursal, parent, false);

        return new  SucursalViewHolder(statusContainer);
    }

    @Override
    public void onBindViewHolder(SucursalViewHolder holder, int position) {
        holder.mTitulo.setText(sucursales.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return sucursales.size();
    }

    public static class SucursalViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mTitulo;
        ImageView mFoto;

        public SucursalViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView)itemView.findViewById(R.id.card_view_sucursal);
            mTitulo = (TextView)itemView.findViewById(R.id.titulo_sucursal);
            mFoto = (ImageView)itemView.findViewById(R.id.imagen_sucursal);
        }
    }

}