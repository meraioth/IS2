package cl.udec.ingsoftware.proyecto_is.Fragmentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.HashMap;

import cl.udec.ingsoftware.proyecto_is.Actividades.MapaBusquedaItinerarioActivity;
import cl.udec.ingsoftware.proyecto_is.AuxiliarVista.ListSucursalesAgregarItinerarioAdapter;
import cl.udec.ingsoftware.proyecto_is.AuxiliarVista.MisItinerariosAdapter;
import cl.udec.ingsoftware.proyecto_is.Presentador.Catalogo;
import cl.udec.ingsoftware.proyecto_is.Presentador.PresentadorItinerario;
import cl.udec.ingsoftware.proyecto_is.R;

/**
 * Created by matisin on 13-07-17.
 */

public class MisItinerariosFragment extends Fragment {
    private Catalogo mPresentador;
    private ListView mItinerarios;
    private HashMap<String,Integer> mis_itinerarios;
    private MisItinerariosAdapter mAdapterSucursales;

    public MisItinerariosFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MisItinerariosFragment newInstance() {
        MisItinerariosFragment fragment = new MisItinerariosFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mis_itinerarios = new HashMap<>();
        mPresentador = MapaBusquedaItinerarioActivity.catalogo;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_mis_itinerario, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mis_itinerarios, container, false);
        MapaBusquedaItinerarioActivity activity = (MapaBusquedaItinerarioActivity) this.getActivity();
        mis_itinerarios = mPresentador.getItinerariosUsuario(activity.getUsuarioSP().getId());
        mItinerarios = (ListView) view.findViewById(R.id.mis_itinerarios_lista);
        ArrayList itinerarios = new ArrayList();
        for(String key : mis_itinerarios.keySet() ){
            itinerarios.add(key);
        }
        FrameLayout fl = (FrameLayout) view.findViewById(R.id.no_hay_sucursales_frame);
        if(itinerarios.isEmpty()){

            fl.setVisibility(View.VISIBLE);
        }else{
            fl.setVisibility(View.INVISIBLE);
        }
        mAdapterSucursales = new MisItinerariosAdapter(this.getActivity(), itinerarios,mis_itinerarios);
        mItinerarios.setAdapter(mAdapterSucursales);
        return view;
    }

    public void DataChange(String itinerario) {
        mAdapterSucursales.addData(itinerario);
    }
}
