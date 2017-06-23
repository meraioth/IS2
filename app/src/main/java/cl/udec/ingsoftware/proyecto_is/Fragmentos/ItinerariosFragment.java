package cl.udec.ingsoftware.proyecto_is.Fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cl.udec.ingsoftware.proyecto_is.Actividades.MapaBusquedaItinerarioActivity;
import cl.udec.ingsoftware.proyecto_is.AuxiliarVista.VerticalRVAdapter;
import cl.udec.ingsoftware.proyecto_is.AuxiliarVista.ItinerariosAdapter;
import cl.udec.ingsoftware.proyecto_is.AuxiliarVista.SucursalItinerarioAdapter;
import cl.udec.ingsoftware.proyecto_is.Modelo.TripletaItinerario;
import cl.udec.ingsoftware.proyecto_is.Presentador.Catalogo;
import cl.udec.ingsoftware.proyecto_is.Presentador.PresentadorItinerario;
import cl.udec.ingsoftware.proyecto_is.R;

/**
 * Created by cridonoso on 03-06-17.
 */

public class ItinerariosFragment extends Fragment implements SearchView.OnQueryTextListener,View.OnClickListener ,VerticalRVAdapter.OnItemClickListener {

    PresentadorItinerario mPresentador;
    private SearchView mBusqueda;
    Catalogo catalogo;
    RecyclerView mRecyclerView;
    private OnItinerarioSelectedListener mItinerarioListener;
    private VerticalRVAdapter verticalRVAdapter;


    public ItinerariosFragment() {}

    public static ItinerariosFragment newInstance() {
        ItinerariosFragment fragment = new ItinerariosFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            catalogo = MapaBusquedaItinerarioActivity.catalogo;
            mPresentador = new PresentadorItinerario(this.getContext());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_itinerario, menu);
        MenuItem searchItem = menu.findItem(R.id.busqueda_itinerario);
        mBusqueda = (SearchView) MenuItemCompat.getActionView(searchItem);
        mBusqueda.setOnQueryTextListener(this);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_itinerarios, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_vertical);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(llm);
        verticalRVAdapter= new VerticalRVAdapter(getContext(),catalogo.getTripletasOfItinerario());
        mRecyclerView.setAdapter(verticalRVAdapter);
        return view;
    }

    public void onSucursalPressed(int position) {
        if (mItinerarioListener != null) {
            mItinerarioListener.OnItinerarioSelected(position);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItinerarioSelectedListener) {
            mItinerarioListener = (OnItinerarioSelectedListener) context;
        }
//        else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnBusuqedaAvanzadaInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mItinerarioListener = null;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        String aux = toTitleCase(query);
        verticalRVAdapter.setNewData(catalogo.getBuscarItinerariosKeyword(aux));
        mBusqueda.clearFocus();
        mBusqueda.onActionViewCollapsed();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onItemClick(int id) {
        mItinerarioListener.OnItinerarioSelected(id);
    }

    public void onSearchAdvanced(String duracion, String estacion) {
        ArrayList<TripletaItinerario> aux = catalogo.getBuscarItinerarios(estacion,Integer.valueOf(duracion));
        verticalRVAdapter.setNewData(aux);
    }

    public interface OnItinerarioSelectedListener {
        void OnItinerarioSelected(int position);
    }

    public static String toTitleCase(String str) {

        if (str == null) {
            return null;
        }

        boolean space = true;
        StringBuilder builder = new StringBuilder(str);
        final int len = builder.length();

        for (int i = 0; i < len; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }
}
