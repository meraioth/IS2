package cl.udec.ingsoftware.proyecto_is.Fragmentos;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
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
import cl.udec.ingsoftware.proyecto_is.AuxiliarVista.ListSucursalesAgregarItinerarioAdapter;
import cl.udec.ingsoftware.proyecto_is.Modelo.Usuario;
import cl.udec.ingsoftware.proyecto_is.Presentador.Catalogo;
import cl.udec.ingsoftware.proyecto_is.Presentador.PresentadorItinerario;
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
    private PresentadorItinerario mPresentador;
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
    private ListSucursalesAgregarItinerarioAdapter mAdapterSucursales;

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
        mPresentador = PresentadorItinerario.getInstance(this.getContext());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_crear_itinerario, menu);
    }

    public boolean onSave(){
        String nombre = mTituloItinerario.getText().toString();
        Usuario user = getUsuarioSP();
        String estacion;

        if(mVerano.isChecked()){
            estacion = "Verano";
        }else if(mOtoño.isChecked()){
            estacion = "Otoño";
        }else if(mInvierno.isChecked()){
            estacion = "Invierno";
        }else if(mPrimavera.isChecked()){
            estacion = "Primavera";
        }else{
            estacion = "No";
        }
        int[] idSucursales = new int[sucursales.size()];
        ArrayList duraciones = mAdapterSucursales.getDuraciones();
        for(int i = 0 ; i<sucursales.size(); i++){
            idSucursales[i] = sucursalesIds.get(sucursales.get(i));
        }
        int rol = user.getRol();

        System.out.println(user.getName());
       // Toast.makeText(this.getContext(),rol,Toast.LENGTH_SHORT).show();

        //mPresentador.crearItinerario();


        return true;
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
        mAdapterSucursales = new ListSucursalesAgregarItinerarioAdapter(this.getActivity(),sucursales);
        mSucursalesAgregadas.setAdapter(mAdapterSucursales);
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
    public boolean saveData(){
        ArrayList duraciones = mAdapterSucursales.getDuraciones();
        String message ="";
        for(int i= 0; i < sucursales.size() ; i ++){
            message = message  +" "+ sucursales.get(i).toString();
            message = message + " " + duraciones.get(i)+ " \n";
        }
        Toast.makeText(this.getContext(),message,Toast.LENGTH_SHORT).show();
        return true;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String sucursal = mAdapter.getItem(position).toString();
        if (sucursales.contains(sucursal)){
            Toast.makeText(this.getActivity(),"El lugar ya existe en el itinerario",Toast.LENGTH_LONG).show();
        }else{
            sucursales.add(sucursal);
            mAdapterSucursales.notifyDataSetChanged();
        }
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

    private Usuario getUsuarioSP() {
        SharedPreferences sp = this.getActivity().getSharedPreferences("usuario",0);
        String name= sp.getString("name","");
        String email = sp.getString("email","");
        int rol = sp.getInt("rol",0);
        Log.d("rol",""+rol);
        int id = sp.getInt("id",0);
        Log.d("int id ",id+"");
        return new Usuario(name,email,rol,id);
    }


}
