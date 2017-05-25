package cl.udec.ingsoftware.proyecto_is.Fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import cl.udec.ingsoftware.proyecto_is.Actividades.MapaBusquedaItinerarioActivity;
import cl.udec.ingsoftware.proyecto_is.Presentador.Catalogo;
import cl.udec.ingsoftware.proyecto_is.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnBusuqedaAvanzadaInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BusquedaAvanzadaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BusquedaAvanzadaFragment extends Fragment {


    private OnBusuqedaAvanzadaInteractionListener mListener;
    private Toolbar mToolbar;
    private Catalogo mPresentador;
    private Spinner categoria;
    private Spinner servicios;
    private Spinner comuna;
    private Button buscar;
    private String str_comuna,str_categoria,str_servicio;
    private RecyclerView mRecyclerView;
    private BusquedaFragment.OnSucursalSelectedListener mSucursalListener;

    public BusquedaAvanzadaFragment() {
        // Required empty public constructor
    }

    public static BusquedaAvanzadaFragment newInstance() {
        BusquedaAvanzadaFragment fragment = new BusquedaAvanzadaFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            mPresentador = MapaBusquedaItinerarioActivity.catalogo;

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_busqueda_avanzada, container, false);
        //mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        comuna = (Spinner)view.findViewById(R.id.spinner_comuna);
        buscar = (Button) view.findViewById(R.id.button_busqueda_avanzada);
        comuna.setAdapter(new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_spinner_item,mPresentador.getComunas()));
        categoria = (Spinner)view.findViewById(R.id.spinner_categoria_busqueda);
        servicios = (Spinner)view.findViewById(R.id.spinner_servicio);
        comuna.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                str_comuna=adapterView.getItemAtPosition(i).toString();
                categoria.setAdapter(new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item,mPresentador.getCategorias(adapterView.getItemAtPosition(i).toString())));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                str_categoria=adapterView.getItemAtPosition(i).toString();
                servicios.setAdapter(new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item,mPresentador.getServiciosBusqueda(adapterView.getItemAtPosition(i).toString())));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        servicios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                str_servicio = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(str_servicio==null || str_categoria == null || str_comuna==null){
                    Toast.makeText(view.getContext(),"Debe rellenar todos los campos",Toast.LENGTH_SHORT).show();
                }else{

                    Toast.makeText(view.getContext(),str_comuna+" "+str_categoria+" "+str_servicio,Toast.LENGTH_SHORT).show();
                    mListener.onSearchAdvanced(str_comuna,str_categoria,str_servicio);


                }
            }
        });
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_busqueda_avanzada, menu);
    }

    public void onCancelPressed() {
        if (mListener != null) {
            mListener.onCancelAdvancedSearch();
        }
    }

    public void onBusquedaAdvancedPressed() {
        if (mListener != null) {
            mListener.onSearchAdvanced(str_comuna, str_categoria, str_servicio);
        }
    }

    public void onRefreshPressed() {
        if (mListener != null) {
            mListener.onCancelAdvancedSearch();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBusuqedaAvanzadaInteractionListener) {
            mListener = (OnBusuqedaAvanzadaInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBusuqedaAvanzadaInteractionListener");
        }
    }
    public void onItemClick(int id) {
        mSucursalListener.OnSucursalSelected(id);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnBusuqedaAvanzadaInteractionListener {
        void onCancelAdvancedSearch();
        void onSearchAdvanced(String str_comuna, String str_categoria, String str_servicio);
    }
}
