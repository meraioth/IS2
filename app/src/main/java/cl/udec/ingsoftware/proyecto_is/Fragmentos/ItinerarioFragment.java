package cl.udec.ingsoftware.proyecto_is.Fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Random;

import cl.udec.ingsoftware.proyecto_is.AuxiliarVista.ListAdapter;
import cl.udec.ingsoftware.proyecto_is.AuxiliarVista.SucursalAdapter;
import cl.udec.ingsoftware.proyecto_is.AuxiliarVista.SucursalItinerarioAdapter;
import cl.udec.ingsoftware.proyecto_is.Presentador.Catalogo;
import cl.udec.ingsoftware.proyecto_is.Presentador.PresentadorItinerario;
import cl.udec.ingsoftware.proyecto_is.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ItinerarioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ItinerarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItinerarioFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PRESENTADOR = "presentador";

    // TODO: Rename and change types of parameters
    private Catalogo presentador;

    private OnFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private PresentadorItinerario mPresentador;
    private int PRUEBA=2;
    private RatingBar mRating;
    private ListView comentarios;

    public ItinerarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ItinerarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ItinerarioFragment newInstance() {
        ItinerarioFragment fragment = new ItinerarioFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_itinerario, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_itinerariosucursales);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(llm);

        Random randomGenerator = new Random();
        mRating = (RatingBar) view.findViewById(R.id.eval_itinerario);
        mRating.setNumStars(5);
        float rating = randomGenerator.nextInt(5);
        mRating.setRating(rating);

        comentarios = (ListView) view.findViewById(R.id.comentarios_itinerario);
        ArrayAdapter<String> comments = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_2,android.R.id.text1,new String[]{"Usuario : Fernando Galvez\n" +
                "Excelente experiencia tomando este viaje, totalmente recomendado, nos demoramos aproximadamente dos dias y alcanzamos a recorrer varias comunas","Usuario : Eduardo Gómez\n" +
                "Una excelente alternativa a los clásicos viajes, hermosa vista por las ciudades puerto de la provincia de Arauco"});
        comentarios.setAdapter(comments);

        SucursalItinerarioAdapter adapter = null;
        adapter = new SucursalItinerarioAdapter(mPresentador.getNombreSucursales(PRUEBA),mPresentador.getFotoSucursales(PRUEBA),mPresentador.getIdSucursales(PRUEBA));
        //adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
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
                    + " must implement OnBusuqedaAvanzadaInteractionListener");
        }
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
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
