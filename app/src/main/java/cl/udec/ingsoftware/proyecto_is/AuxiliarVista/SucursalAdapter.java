package cl.udec.ingsoftware.proyecto_is.AuxiliarVista;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cl.udec.ingsoftware.proyecto_is.Modelo.Tripleta;
import cl.udec.ingsoftware.proyecto_is.R;
import cl.udec.ingsoftware.proyecto_is.util.AsyncTaskLoadImage;


public class SucursalAdapter extends RecyclerView.Adapter<SucursalAdapter.SucursalViewHolder>{

    private List<Tripleta> sucursales;
    private Context context;
    private OnItemClickListener mListener;

    public SucursalAdapter(List<Tripleta> sucursales){
        this.sucursales = sucursales;
    }

    @Override
    public SucursalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View statusContainer = inflater.inflate(R.layout.cardview_sucursal, parent, false);

        return new  SucursalViewHolder(statusContainer);
    }

    @Override
    public void onBindViewHolder(SucursalViewHolder holder, int position) {
        holder.mTitulo.setText(sucursales.get(position).get_name());
        holder.mId = sucursales.get(position).get_id();
        String url = sucursales.get(position).get_image();
        new AsyncTaskLoadImage(holder.mFoto).execute(url);
        holder.setOnItemClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return sucursales.size();
    }

    public interface OnItemClickListener{
        void onItemClick(int id);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }


    public static class SucursalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public CardView mCardView;
        public TextView mTitulo;
        public ImageView mFoto;
        public ImageView mSello;
        public int mId;
        private OnItemClickListener mListener;

        public SucursalViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView)itemView.findViewById(R.id.card_view_sucursal);
            mTitulo = (TextView)itemView.findViewById(R.id.titulo_sucursal);
            mFoto = (ImageView)itemView.findViewById(R.id.imagen_sucursal);
            mSello = (ImageView) itemView.findViewById(R.id.sello_verde_card);
            mFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            final Context context = itemView.getContext();
            mCardView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                mListener.onItemClick(mId);
            }
        }

        public void setOnItemClickListener(OnItemClickListener mListener) {
            this.mListener = mListener;
        }
    }

}