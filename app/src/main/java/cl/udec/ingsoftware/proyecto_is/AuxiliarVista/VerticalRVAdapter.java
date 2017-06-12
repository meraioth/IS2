package cl.udec.ingsoftware.proyecto_is.AuxiliarVista;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

import cl.udec.ingsoftware.proyecto_is.Actividades.MapaBusquedaItinerarioActivity;
import cl.udec.ingsoftware.proyecto_is.Fragmentos.ItinerarioFragment;
import cl.udec.ingsoftware.proyecto_is.Modelo.Categoria;
import cl.udec.ingsoftware.proyecto_is.Modelo.Itinerario;
import cl.udec.ingsoftware.proyecto_is.Modelo.Servicio;
import cl.udec.ingsoftware.proyecto_is.Modelo.Sucursal;
import cl.udec.ingsoftware.proyecto_is.Presentador.Catalogo;
import cl.udec.ingsoftware.proyecto_is.Presentador.Formateador;
import cl.udec.ingsoftware.proyecto_is.Presentador.PresentadorItinerario;
import cl.udec.ingsoftware.proyecto_is.Presentador.PresentadorSucursal;
import cl.udec.ingsoftware.proyecto_is.R;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created by meraioth on 04-06-17.
 */

public class VerticalRVAdapter extends RecyclerView.Adapter<VerticalRVAdapter.ItemViewHolder>{

    private final Context mContext;
    private Catalogo catalogo;
    private PresentadorItinerario itinerario;
    private static RecyclerView horizontalList;
    Button boton;
    private OnItemClickListener mListener;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private ItinerarioFragment itinerario_fragment = ItinerarioFragment.newInstance();
    private int identificador_itinerario;


    public void setNewData(String aux) {
        //modificar con itinerarios filtrados
        /*
        *   public void setNewData(ArrayList<Tripleta> sucursales) {
        this.sucursales.clear();
        this.sucursales.addAll(sucursales);
        notifyDataSetChanged();
    }
*/
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder{
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
/*
    FragmentManager fm = getSupportFragmentManager();
    YourFragment fragment = new YourFragment();
 fm.beginTransaction().add(R.id.main_contenier,fragment).commit();
  */

    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(mContext).inflate(R.layout.itinerario_item, parent, false);
        boton = (Button)itemView.findViewById(R.id.buton_ver_itinerario);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = ((MapaBusquedaItinerarioActivity)mContext).getSupportFragmentManager();

                //INGRESAR IDENTIFICADOR DEL ITINERARIO QUE SE QUIERE MOSTRAR !!!!
                identificador_itinerario = 2;
                itinerario_fragment.set_id_itinerario(identificador_itinerario);

                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_mapa_busqueda_itinerario, itinerario_fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                Toast.makeText(mContext, "hola click", Toast.LENGTH_SHORT).show();
            }
        });

        ItemViewHolder holder = new ItemViewHolder(itemView);
        return holder;
    }

    public interface OnItemClickListener{
        void onItemClick(int id);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }




    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Log.d("final int pos", String.valueOf(position));
        holder.horizontalAdapter.setData(itinerario.getNombreSucursales(position+1),itinerario.getFotoSucursales(position+1),itinerario.getIdSucursales(position+1)); // List of Strings
        holder.horizontalAdapter.setRowIndex(position);
        holder.titulo.setText(itinerario.getNombreItinerario(position+1));


        for(String cat: itinerario.getItinerarioServices(position+1)){
            if (cat.contentEquals("Alojamiento Turístico")){holder.alojamiento.setVisibility(VISIBLE);}
            if (cat.contentEquals("Restaurantes y Similares")){holder.comida.setVisibility(VISIBLE);}
            if (cat.contentEquals("Agencias de Viaje y Tour Operador")){holder.tour.setVisibility(VISIBLE);}
            if (cat.contentEquals("Transporte de Pasajeros por Carretera Interurbana")){holder.transporte.setVisibility(VISIBLE);}
            if (cat.contentEquals("Turismo Aventura")){holder.tour.setVisibility(VISIBLE);}
            if (cat.contentEquals("Servicios de Esparcimiento")){holder.esparcimiento.setVisibility(VISIBLE);}
            if (cat.contentEquals("Artesanía")){holder.artesania.setVisibility(VISIBLE);}
            if (cat.contentEquals("Guías de Turismo")){holder.tour.setVisibility(VISIBLE);}
            if (cat.contentEquals("Servicios Deportivos")){holder.deporte.setVisibility(VISIBLE);}
        }


    }

    @Override
    public int getItemCount() {
        return catalogo.getItinerarios().size();
    }



}
