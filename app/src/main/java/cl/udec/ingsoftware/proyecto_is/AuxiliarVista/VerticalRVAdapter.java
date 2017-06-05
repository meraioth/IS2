package cl.udec.ingsoftware.proyecto_is.AuxiliarVista;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.SQLException;

import cl.udec.ingsoftware.proyecto_is.Presentador.Catalogo;
import cl.udec.ingsoftware.proyecto_is.Presentador.PresentadorItinerario;
import cl.udec.ingsoftware.proyecto_is.R;

/**
 * Created by meraioth on 04-06-17.
 */

public class VerticalRVAdapter extends RecyclerView.Adapter<VerticalRVAdapter.SimpleViewHolder> {

    private final Context mContext;
    private Catalogo catalogo;
    private PresentadorItinerario itinerario;
    private static RecyclerView horizontalList;

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        private HorizontalRVAdapter horizontalAdapter;

        public SimpleViewHolder(View view) {
            super(view);
            Context context = itemView.getContext();
            horizontalList = (RecyclerView) itemView.findViewById(R.id.horizontal_list);
            horizontalList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            horizontalAdapter = new HorizontalRVAdapter();
            horizontalList.setAdapter(horizontalAdapter);
        }
    }

    public VerticalRVAdapter(Context context, Catalogo catalogo) {
        mContext = context;
        this.catalogo = catalogo;
        try {
            this.itinerario=new PresentadorItinerario(mContext);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.itinerario_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        Log.d("final int pos", String.valueOf(position));
        holder.horizontalAdapter.setData(itinerario.getNombreSucursales(position+1),itinerario.getFotoSucursales(position+1),itinerario.getIdSucursales(position+1)); // List of Strings
        holder.horizontalAdapter.setRowIndex(position);
    }

    @Override
    public int getItemCount() {
        return catalogo.getItinerarios().size();
    }

}
