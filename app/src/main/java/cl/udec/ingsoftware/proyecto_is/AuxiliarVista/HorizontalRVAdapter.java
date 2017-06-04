package cl.udec.ingsoftware.proyecto_is.AuxiliarVista;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cl.udec.ingsoftware.proyecto_is.R;
import cl.udec.ingsoftware.proyecto_is.util.AsyncTaskLoadImage;

/**
 * Created by meraioth on 04-06-17.
 */

public class HorizontalRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<String> nombresucursales,fotosucursales;
    private ArrayList<Integer> idsucursales;
    private List<String> mDataList;
    private int mRowIndex = -1;

    public HorizontalRVAdapter() {


    }

    public void setData(ArrayList nombresucursales,ArrayList fotosucursales, ArrayList idsucursales) {

            this.nombresucursales = nombresucursales;
            this.idsucursales = idsucursales;
            this.fotosucursales=fotosucursales;
            notifyDataSetChanged();

    }

    public void setRowIndex(int index) {
        mRowIndex = index;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView text;
        public CardView mCardView;
        public TextView mTitulo;
        public ImageView mFoto;
        public ImageView mSello;
        public int mId;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView)itemView.findViewById(R.id.card_view_sucursal);
            mTitulo = (TextView)itemView.findViewById(R.id.titulo_sucursal);
            mFoto = (ImageView)itemView.findViewById(R.id.imagen_sucursal);
            mSello = (ImageView) itemView.findViewById(R.id.sello_verde_card);
            mFoto.setScaleType(ImageView.ScaleType.FIT_XY);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.cardview_sucursal, parent, false);
        ItemViewHolder holder = new ItemViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder rawHolder, int position) {
        ItemViewHolder holder = (ItemViewHolder) rawHolder;
        holder.mTitulo.setText(nombresucursales.get(position));
        String url = fotosucursales.get(position);
        new AsyncTaskLoadImage(holder.mFoto).execute(url);

    }

    @Override
    public int getItemCount() {
        return nombresucursales.size();
    }

}