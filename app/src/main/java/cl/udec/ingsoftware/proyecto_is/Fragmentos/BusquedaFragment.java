package cl.udec.ingsoftware.proyecto_is.Fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

import cl.udec.ingsoftware.proyecto_is.Actividades.MapaBusquedaItinerarioActivity;
import cl.udec.ingsoftware.proyecto_is.AuxiliarVista.SucursalAdapter;
import cl.udec.ingsoftware.proyecto_is.Modelo.Tripleta;
import cl.udec.ingsoftware.proyecto_is.Presentador.Catalogo;
import cl.udec.ingsoftware.proyecto_is.R;


public class BusquedaFragment extends Fragment implements View.OnClickListener, SearchView.OnQueryTextListener, SucursalAdapter.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PRESENTADOR = "presentador";

    private SearchView mBusqueda;

    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;

    private SucursalAdapter adapter;
    private OnSucursalSelectedListener mSucursalListener;
    private Catalogo mPresentador;

    public BusquedaFragment() {
        // constructor vacio requerido

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BusquedaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BusquedaFragment newInstance() {

        BusquedaFragment fragment = new BusquedaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresentador = MapaBusquedaItinerarioActivity.catalogo;
        if(adapter == null){
            try {
                adapter = new SucursalAdapter(mPresentador.getTripletasOfSucursales());
                adapter.setOnItemClickListener(this);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_busqueda, menu);
        MenuItem searchItem = menu.findItem(R.id.busqueda_sucursal);
        mBusqueda = (SearchView) MenuItemCompat.getActionView(searchItem);
        mBusqueda.setOnQueryTextListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_busqueda, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_sucursales);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setAdapter(adapter);
        return view;
    }

    public void onClickBusqueda(){
        //TODO: implementar busqueda
    }

    // TODO: hook method into UI event
    public void onSucursalPressed(int position) {
        if (mSucursalListener != null) {
            mSucursalListener.OnSucursalSelected(position);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSucursalSelectedListener) {
            mSucursalListener = (OnSucursalSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBusuqedaAvanzadaInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mSucursalListener= null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        String aux = toTitleCase(query);
        adapter.setNewData(mPresentador.getBuscarKeyword(aux));
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
        mSucursalListener.OnSucursalSelected(id);
    }

    public void onSearchAdvanced(String str_comuna, String str_categoria, String str_servicio) {
        ArrayList<Tripleta> aux = mPresentador.getTripletasOfSucursales(str_comuna, str_categoria, str_servicio);
        adapter.setNewData(aux);
    }

    public interface OnSucursalSelectedListener {
        void OnSucursalSelected(int posicion);
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
