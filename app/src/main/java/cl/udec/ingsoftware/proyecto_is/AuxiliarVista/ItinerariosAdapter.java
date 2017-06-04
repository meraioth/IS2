package cl.udec.ingsoftware.proyecto_is.AuxiliarVista;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cl.udec.ingsoftware.proyecto_is.Modelo.Itinerario;
import cl.udec.ingsoftware.proyecto_is.Modelo.Tripleta;
import cl.udec.ingsoftware.proyecto_is.Presentador.PresentadorItinerario;
import cl.udec.ingsoftware.proyecto_is.R;
import cl.udec.ingsoftware.proyecto_is.util.AsyncTaskLoadImage;

/**
 * Created by cridonoso on 03-06-17.
 */

public class ItinerariosAdapter extends RecyclerView.Adapter<ItinerariosAdapter.ItinerarioViewHolder>{
    private Context context;
    private ArrayList<Integer> id_sucursales;
    private PresentadorItinerario mPresentador;

    public ItinerariosAdapter(Context context, ArrayList<Integer> ids, PresentadorItinerario mPresentador) {
        this.context = context;
        id_sucursales = ids;
        this.mPresentador = mPresentador;

    }

    @Override
    public ItinerarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View statusContainer = inflater.inflate(R.layout.lista_itinerarios, parent, false);

        return new  ItinerarioViewHolder(statusContainer,mPresentador);
    }

    @Override
    public void onBindViewHolder(ItinerarioViewHolder holder, int position) {

        holder.recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recycler.setAdapter(new SucursalItinerarioAdapter(
                mPresentador.getNombreSucursales(3),
                mPresentador.getFotoSucursales(3),
                mPresentador.getIdSucursales(3)));
        holder.recycler.setNestedScrollingEnabled(false);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ItinerarioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        RecyclerView recycler;
        private SucursalAdapter.OnItemClickListener mListener;

        public ItinerarioViewHolder(View itemView, PresentadorItinerario mPresentador) {
            super(itemView);

            recycler = (RecyclerView) itemView.findViewById(R.id.lista_itinerarios);
            LinearLayoutManager llm = new LinearLayoutManager(itemView.getContext(),LinearLayoutManager.HORIZONTAL,false);
            recycler.setLayoutManager(llm);
            recycler.setNestedScrollingEnabled(false);
            recycler.setAdapter(null);
        }

        @Override
        public void onClick(View v) {

        }

        public void setOnItemClickListener(SucursalAdapter.OnItemClickListener mListener) {
            this.mListener = mListener;
        }
    }
}
