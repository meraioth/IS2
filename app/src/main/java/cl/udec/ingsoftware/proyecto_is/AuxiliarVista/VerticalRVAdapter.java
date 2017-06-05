package cl.udec.ingsoftware.proyecto_is.AuxiliarVista;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.SQLException;

import cl.udec.ingsoftware.proyecto_is.Presentador.Catalogo;
import cl.udec.ingsoftware.proyecto_is.Presentador.PresentadorItinerario;
import cl.udec.ingsoftware.proyecto_is.R;

/**
 * Created by meraioth on 04-06-17.
 */

public class VerticalRVAdapter extends RecyclerView.Adapter<VerticalRVAdapter.ItemViewHolder> {

    private final Context mContext;
    private Catalogo catalogo;
    private PresentadorItinerario itinerario;
    private static RecyclerView horizontalList;


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private HorizontalRVAdapter horizontalAdapter;
        private TextView titulo;
        private ImageView transporte, alojamiento, comida, deporte, esparcimiento, artesania, tour;
        public ItemViewHolder(View view) {
            super(view);
            Context context = itemView.getContext();
            horizontalList = (RecyclerView) itemView.findViewById(R.id.horizontal_list);
            horizontalList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            horizontalAdapter = new HorizontalRVAdapter();
            horizontalList.setAdapter(horizontalAdapter);

            titulo = (TextView)itemView.findViewById(R.id.titulo_itinerario);
            transporte = (ImageView)itemView.findViewById(R.id.ico_transporte);
            alojamiento = (ImageView)itemView.findViewById(R.id.ico_alojamiento);
            comida = (ImageView)itemView.findViewById(R.id.ico_comida);
            deporte = (ImageView)itemView.findViewById(R.id.ico_deporte);
            esparcimiento = (ImageView)itemView.findViewById(R.id.ico_esparcimiento);
            artesania = (ImageView)itemView.findViewById(R.id.ico_artesania);
            tour = (ImageView)itemView.findViewById(R.id.ico_tour);
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



    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mContext).inflate(R.layout.itinerario_item, parent, false);

        ItemViewHolder holder = new ItemViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Log.d("final int pos", String.valueOf(position));
        holder.horizontalAdapter.setData(itinerario.getNombreSucursales(position+1),itinerario.getFotoSucursales(position+1),itinerario.getIdSucursales(position+1)); // List of Strings
        holder.horizontalAdapter.setRowIndex(position);
        holder.titulo.setText(itinerario.getNombreItinerario(position+1));
    }


    @Override
    public int getItemCount() {
        return catalogo.getItinerarios().size();
    }

}
