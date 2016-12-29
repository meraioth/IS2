package cl.udec.ingsoftware.proyecto_is;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by diego on 29-12-16.
 */

public class ListAdapter extends ArrayAdapter {
    private Activity context;
    private ArrayList sucursales;
    public ListAdapter(Activity context, ArrayList sucursales){
        super(context, R.layout.layout_fila,sucursales);
        this.sucursales = sucursales;
        this.context=context;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.layout_fila, null, true);
        TextView titulo = (TextView) rowView.findViewById(R.id.id_titulo);
      //  Sucursal a = sucursales.get(position);
        titulo.setText((CharSequence) sucursales.get(position));
        return rowView;
    }
}
