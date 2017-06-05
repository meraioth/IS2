package cl.udec.ingsoftware.proyecto_is.Fragmentos;

import android.content.Context;
import android.net.Uri;
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
import java.util.List;

import cl.udec.ingsoftware.proyecto_is.Actividades.MapaBusquedaItinerarioActivity;
import cl.udec.ingsoftware.proyecto_is.AuxiliarVista.VerticalRVAdapter;
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
    private OnFragmentInteractionListener mListener;



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
    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_itinerarios, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_vertical);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(llm);

        VerticalRVAdapter cr= new VerticalRVAdapter(getContext(),catalogo);
        mRecyclerView.setAdapter(cr);


        return view;
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnBusuqedaAvanzadaInteractionListener");
//        }
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
