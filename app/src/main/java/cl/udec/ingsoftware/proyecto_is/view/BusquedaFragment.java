package cl.udec.ingsoftware.proyecto_is.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cl.udec.ingsoftware.proyecto_is.R;
import cl.udec.ingsoftware.proyecto_is.databinding.BusquedaFragmentBinding;
import cl.udec.ingsoftware.proyecto_is.model.source.SucursalesRepository;
import cl.udec.ingsoftware.proyecto_is.view_model.SucursaleViewModel;


public class BusquedaFragment extends Fragment {

    private RecyclerView recyclerView;
    private SucursalRecyclerViewAdapter adapter;
    private SucursaleViewModel mViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        BusquedaFragmentBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.busqueda_fragment, container, false);
        View view = binding.getRoot();
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        adapter = new SucursalRecyclerViewAdapter(mViewModel.sucursales);
        recyclerView.setAdapter(adapter);

        return view;
    }


    public void addModel(SucursaleViewModel mViewModel) {
        this.mViewModel = mViewModel;
    }
}

