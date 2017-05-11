package cl.udec.ingsoftware.proyecto_is.Fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

import cl.udec.ingsoftware.proyecto_is.Presentador.Catalogo;
import cl.udec.ingsoftware.proyecto_is.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BusquedaFragment.OnSucursalSelectedListener} interface
 * to handle interaction events.
 * Use the {@link BusquedaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BusquedaFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PRESENTADOR = "presentador";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SearchView mBusqueda;

    private RecyclerView mRecyclerView;

    private OnSucursalSelectedListener mSucursalListener;
    private Catalogo mPresentador;

    public BusquedaFragment() {
        // constructor vacio requerido

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param presentador Parameter 1.
     * @return A new instance of fragment BusquedaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BusquedaFragment newInstance(Serializable presentador) {
        BusquedaFragment fragment = new BusquedaFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRESENTADOR,presentador);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPresentador = (Catalogo) getArguments().getSerializable(ARG_PRESENTADOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_busqueda, container, false);


        //mBusqueda = (SearchView) view.findViewById(R.id.busqueda);
        //mBusqueda.setOnClickListener(this);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_sucursales);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(llm);

        SucursalAdapter adapter = new SucursalAdapter(mPresentador.getSucursales());
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
                    + " must implement OnFragmentInteractionListener");
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
    public interface OnSucursalSelectedListener {
        void OnSucursalSelected(int posicion);
    }


}
