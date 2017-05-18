package cl.udec.ingsoftware.proyecto_is.Fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import cl.udec.ingsoftware.proyecto_is.Presentador.PresentadorSucursal;
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

    public BusquedaAvanzadaFragment() {
        // Required empty public constructor
    }

    public static BusquedaAvanzadaFragment newInstance() {
        BusquedaAvanzadaFragment fragment = new BusquedaAvanzadaFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_busqueda_avanzada, container, false);
        //mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
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
            mListener.onSearchAdvanced();
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
        void onSearchAdvanced();
    }
}
