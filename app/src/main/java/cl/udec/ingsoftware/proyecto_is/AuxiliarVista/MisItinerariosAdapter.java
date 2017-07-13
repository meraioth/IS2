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

import cl.udec.ingsoftware.proyecto_is.R;

/**
 * Created by matisin on 13-07-17.
 */

public class MisItinerariosAdapter extends ArrayAdapter {
    private ArrayList itinerarios;
    private HashMap<String, Integer> itinerarios_id;
    private Activity context;
    private ImageButton mas;

    public MisItinerariosAdapter(Activity context, ArrayList itinerarios, HashMap<String, Integer> itinerarios_id){
        super(context, R.layout.lista_mis_itinerarios,itinerarios);
        this.itinerarios = itinerarios;
        this.itinerarios_id = itinerarios_id;
        this.context=context;
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
                        switch (item.getItemId()){
                            case R.id.modificar_itinerario_popup:
                                Toast.makeText(context,"moasdddificar",Toast.LENGTH_SHORT).show();
                                return true;
                                //TODO: modificar
                            case R.id.borrar_itinerario:
                                Toast.makeText(context,"borrdsaar",Toast.LENGTH_SHORT).show();
                                return true;
                                //TODO: borrar
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

}