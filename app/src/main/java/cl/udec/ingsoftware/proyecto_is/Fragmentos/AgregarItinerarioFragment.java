package cl.udec.ingsoftware.proyecto_is.Fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import cl.udec.ingsoftware.proyecto_is.Actividades.MapaBusquedaItinerarioActivity;
import cl.udec.ingsoftware.proyecto_is.AuxiliarVista.ListAdapter;
import cl.udec.ingsoftware.proyecto_is.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AgregarItinerarioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AgregarItinerarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgregarItinerarioFragment extends Fragment implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private EditText mTituloItinerario;
    private RadioButton mVerano,mOtoño,mInvierno,mPrimavera;
    private ListView mSucursalesAgregadas;
    private ArrayList<String> sucursales;
    private ArrayList<Integer> ids;
    private ArrayList sucursalesTodos;
    private HashMap<String, Integer> sucursalesIds;
    private ArrayList idsTodos;
    private AutoCompleteTextView mBusquedaSucursal;

    private OnFragmentInteractionListener mListener;
    private ArrayAdapter mAdapter;

    public AgregarItinerarioFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AgregarItinerarioFragment newInstance() {
        AgregarItinerarioFragment fragment = new AgregarItinerarioFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_crear_itinerario, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_agregar_itinerario, container, false);
        mTituloItinerario = (EditText) view.findViewById(R.id.titulo_itinerario);
        mVerano = (RadioButton) view.findViewById(R.id.verano);
        mOtoño = (RadioButton) view.findViewById(R.id.otoño);
        mInvierno = (RadioButton) view.findViewById(R.id.invierno);
        mPrimavera = (RadioButton) view.findViewById(R.id.primavera);
        mBusquedaSucursal = (AutoCompleteTextView) view.findViewById(R.id.AutoCompleteSucursales);
        sucursalesTodos = MapaBusquedaItinerarioActivity.catalogo.getSucursales();
        idsTodos = MapaBusquedaItinerarioActivity.catalogo.getIds();
        mAdapter = new ArrayAdapter(this.getActivity(),android.R.layout.simple_list_item_1,sucursalesTodos);
        mBusquedaSucursal.setAdapter(mAdapter);
        sucursales = new ArrayList();
        ids = new ArrayList();
        sucursalesIds = new HashMap<>();
        for(int i = 0 ; i < sucursalesTodos.size() ; i++){
            sucursalesIds.put(sucursalesTodos.get(i).toString(),(Integer) idsTodos.get(i));
        }
        mSucursalesAgregadas = (ListView) view.findViewById(R.id.lista_agregados);
        mSucursalesAgregadas.setAdapter(new ListAdapter(this.getActivity(),sucursales));
        mBusquedaSucursal.setOnItemClickListener(this);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
  /*  public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
*/
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int identificador = sucursalesIds.get( mAdapter.getItem(position));
        sucursales.add(mAdapter.getItem(position).toString());
        mSucursalesAgregadas.setAdapter(new ListAdapter(this.getActivity(),sucursales));
        mBusquedaSucursal.setText("");
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
