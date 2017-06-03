package cl.udec.ingsoftware.proyecto_is.Fragmentos;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.SQLException;
import java.util.ArrayList;

import cl.udec.ingsoftware.proyecto_is.Actividades.MapaBusquedaItinerarioActivity;
import cl.udec.ingsoftware.proyecto_is.AuxiliarVista.ItinerariosAdapter;
import cl.udec.ingsoftware.proyecto_is.AuxiliarVista.SucursalItinerarioAdapter;
import cl.udec.ingsoftware.proyecto_is.Presentador.Catalogo;
import cl.udec.ingsoftware.proyecto_is.Presentador.PresentadorItinerario;
import cl.udec.ingsoftware.proyecto_is.R;

/**
 * Created by cridonoso on 03-06-17.
 */

public class ItinerariosFragment extends Fragment {

    PresentadorItinerario mPresentador;
    Catalogo catalogo;
    SucursalItinerarioAdapter adapter;
    ItinerariosAdapter adapter_lista;
    RecyclerView mRecyclerView, mRecyclerView2;


    public ItinerariosFragment() {}

    public static ItinerariosFragment newInstance() {
        ItinerariosFragment fragment = new ItinerariosFragment();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
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
        inflater.inflate(R.menu.menu_busqueda_avanzada, menu);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_itinerarios, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_itinerariosucursales);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(llm);

        ArrayList<Integer> arr = new ArrayList<>();
        arr = catalogo.getAllIdOfItinerarios();

        adapter_lista = new ItinerariosAdapter(this.getContext(), arr, mPresentador);

        mRecyclerView.setAdapter(adapter_lista);


        return view;
    }
}
