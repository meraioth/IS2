package cl.udec.ingsoftware.proyecto_is.AuxiliarVista;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
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
import cl.udec.ingsoftware.proyecto_is.Modelo.Tripleta;
import cl.udec.ingsoftware.proyecto_is.Modelo.TripletaItinerario;
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
    private PresentadorItinerario itinerario;
    private ArrayList<TripletaItinerario> itinerariosTripleta;
    private static RecyclerView horizontalList;
    Button boton;
    private OnItemClickListener mListener;


    public void setNewData(ArrayList<TripletaItinerario> trip) {
        this.itinerariosTripleta.clear();
        this.itinerariosTripleta = trip;
        notifyDataSetChanged();
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

    public VerticalRVAdapter(Context context, ArrayList<TripletaItinerario> tripleta) {
        mContext = context;
        this.itinerariosTripleta = tripleta;
        Log.d("tamañoMeraioth", String.valueOf(tripleta.size()));
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
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.itinerario_item, parent, false);
        boton = (Button)itemView.findViewById(R.id.buton_ver_itinerario);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.Fragment iti = new android.app.Fragment();
                FragmentTransaction transaction = ((MapaBusquedaItinerarioActivity)mContext).getFragmentManager().beginTransaction();
                transaction.replace(R.id.recycler_view_itinerariosucursales, iti);
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
        Log.d("meraioth en", String.valueOf(itinerariosTripleta.size()));
        //holder.horizontalAdapter.setData(itinerario.getNombreSucursales(position+1),itinerario.getFotoSucursales(position+1),itinerario.getIdSucursales(position+1)); // List of Strings
        ArrayList<String> nombreSucursales = new ArrayList<String>();
        ArrayList<String> fotoSucursales = new ArrayList<String>();
        ArrayList<Integer> idSucursales = new ArrayList();
        for (TripletaItinerario aux:itinerariosTripleta) {
            if(aux.get_id()==position+1){
                //ARREGLAR TRIPLETA ITINERARIOS.GET SUCURSALES AHI ESTA EL PROBLEMA
                Log.d("aux.getSucursales:", String.valueOf(aux.get_sucursales()));
                for(Sucursal s : aux.get_sucursales()) {
                    nombreSucursales.add(s.getNombre());
                    fotoSucursales.add(s.getImagen());
                    idSucursales.add(s.getId());
                }
            }
        }
        holder.horizontalAdapter.setData(nombreSucursales,fotoSucursales,idSucursales);
        holder.horizontalAdapter.setRowIndex(position+1);
        //holder.titulo.setText(itinerario.getNombreItinerario(position));
        holder.titulo.setText(itinerario.getNombreItinerario(itinerariosTripleta.get(position).get_id()));
        for(String cat: itinerario.getItinerarioServices(itinerariosTripleta.get(position).get_id())){
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
        return itinerariosTripleta.size();
    }



}
