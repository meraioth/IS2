package cl.udec.ingsoftware.proyecto_is.AuxiliarVista;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import cl.udec.ingsoftware.proyecto_is.R;

/**
 * Created by diego on 29-12-16.
 */

public class ListSucursalesAgregarItinerarioAdapter extends ArrayAdapter {
    private Activity context;
    private ArrayList sucursales;
    private ArrayList horas_fijas;
    private ArrayList horas_elegidas;
    private ArrayList<Spinner> spinners;

    public ListSucursalesAgregarItinerarioAdapter(Activity context, ArrayList sucursales){
        super(context, R.layout.layout_fila,sucursales);
        this.sucursales = sucursales;
        horas_elegidas = new ArrayList();
        this.context=context;
        horas_fijas = new ArrayList();
        for(int i = 0; i < 24 ; i++){
            horas_fijas.add(i+1);
        }
    }

    public ArrayList getDuraciones(){
        return horas_elegidas;

    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.layout_sucursales_itinerario, null, true);
        TextView titulo = (TextView) rowView.findViewById(R.id.titulo_sucursal_id);
        Spinner horas = (Spinner) rowView.findViewById(R.id.spinner_sucursal);
        horas.setAdapter(new ArrayAdapter(this.getContext(),android.R.layout.simple_list_item_1,horas_fijas));
        ImageButton cancelar = (ImageButton) rowView.findViewById(R.id.borrar_sucursal);
        cancelar.setTag(position);
        horas_elegidas.add(horas.getSelectedItem());
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer index = (Integer) v.getTag();
                sucursales.remove(index.intValue());
                horas_elegidas.remove(index.intValue());
                notifyDataSetChanged();
            }
        });
        horas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position_spinner, long id) {
                horas_elegidas.set(position,horas_fijas.get(position_spinner));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        titulo.setText(sucursales.get(position).toString());
        return rowView;
    }

}
