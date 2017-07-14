package cl.udec.ingsoftware.proyecto_is.AuxiliarVista;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import cl.udec.ingsoftware.proyecto_is.Fragmentos.BusquedaFragment;
import cl.udec.ingsoftware.proyecto_is.R;

/**
 * Created by matisin on 13-07-17.
 */

public class MisItinerariosAdapter extends ArrayAdapter {
    private ArrayList itinerarios;
    private HashMap<String, Integer> itinerarios_id;
    private Activity context;
    private OnInteractionListener mListener;
    private ImageButton mas;

    public MisItinerariosAdapter(Activity context, ArrayList itinerarios, HashMap<String, Integer> itinerarios_id){
        super(context, R.layout.lista_mis_itinerarios,itinerarios);
        this.itinerarios = itinerarios;
        this.itinerarios_id = itinerarios_id;
        this.context=context;
        if (context instanceof OnInteractionListener) {
            mListener = (OnInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBusuqedaAvanzadaInteractionListener");
        }
    }
    public void addData(String itinerario){
        itinerarios.remove(itinerario);
        notifyDataSetChanged();
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.lista_mis_itinerarios, null, true);
        TextView titulo = (TextView) rowView.findViewById(R.id.titulo_mis_itinerarios);
        titulo.setText((String) itinerarios.get(position));
        mas = (ImageButton) rowView.findViewById(R.id.acciones);
        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(v.getContext(),mas);
                popup.getMenuInflater().inflate(R.menu.popup_menu_mis_itinerarios,popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id_it = itinerarios_id.get(itinerarios.get(position));
                        switch (item.getItemId()){
                            case R.id.modificar_itinerario_popup:
                                mListener.modificarItinerario(id_it);
                                return true;
                            case R.id.borrar_itinerario:
                                mListener.eliminarItinerario(id_it, (String) itinerarios.get(position));
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();
            }
        });
        return rowView;
    }
    public interface OnInteractionListener{
        void modificarItinerario(int id_itinerario);
        void eliminarItinerario(int id_itinerario, String nombre);
    }

}