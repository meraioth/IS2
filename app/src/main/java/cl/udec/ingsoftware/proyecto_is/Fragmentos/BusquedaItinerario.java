package cl.udec.ingsoftware.proyecto_is.Fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;

import cl.udec.ingsoftware.proyecto_is.Actividades.MapaBusquedaItinerarioActivity;
import cl.udec.ingsoftware.proyecto_is.Presentador.Catalogo;
import cl.udec.ingsoftware.proyecto_is.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BusquedaItinerario.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BusquedaItinerario#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BusquedaItinerario extends Fragment {
    private Catalogo mPresentador;
    private Spinner duracion,estacion;
    private String str_duracion,str_estacion;
    private Button buscar;


//    private ItinerariosFragment.OnItinerarioSelectedListener mListener;

    public BusquedaItinerario() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static BusquedaItinerario newInstance() {
        BusquedaItinerario fragment = new BusquedaItinerario();
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
        View view = inflater.inflate(R.layout.fragment_busqueda_itinerario, container, false);
        buscar = (Button) view.findViewById(R.id.button_busqueda_avanzada_itinerario);
        duracion = (Spinner) view.findViewById(R.id.duracion_spinner);
        estacion = (Spinner)view.findViewById(R.id.estacion_spinner);
        estacion.setAdapter(new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_spinner_item,new String[]{"Otoño","Invierno","Primavera","Verano"}));
        final ArrayList[] dura = new ArrayList[1];
        final String[][] duraciones = new String[1][1];
        estacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_estacion=parent.getItemAtPosition(position).toString();

                duracion.setAdapter(new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item, mPresentador.getDuraciones(str_estacion)));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        duracion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_duracion=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(str_estacion==null || str_duracion == null ){
                    Toast.makeText(view.getContext(),"Debe rellenar todos los campos",Toast.LENGTH_SHORT).show();
                }else{
//                    mListener.onSearchAdvanced(str_duracion,str_estacion);
                }
            }
        });



        setHasOptionsMenu(true);


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof BusquedaItinerario.OnBusuqedaAvanzadaItinerarioInteractionListener) {
////            mListener = (BusquedaItinerario.OnBusuqedaAvanzadaItinerarioInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnBusuqedaAvanzadaInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
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
    public interface OnBusuqedaAvanzadaItinerarioInteractionListener {
        void onCancelAdvancedSearch();
        void onSearchAdvanced(String str_duracion, String str_estacion);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_busqueda_itinerario, menu);
    }
}
