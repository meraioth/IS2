package cl.udec.ingsoftware.proyecto_is.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cl.udec.ingsoftware.proyecto_is.R;
import cl.udec.ingsoftware.proyecto_is.databinding.BusquedaFragmentBinding;
import cl.udec.ingsoftware.proyecto_is.view_model.SucursalerViewModel;


public class BusquedaFragment extends Fragment {

    private RecyclerView recyclerView;
    private SucursalRecyclerViewAdapter adapter;
    private SucursalerViewModel mViewModel;
    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        BusquedaFragmentBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.busqueda_fragment, container, false);
        View view = binding.getRoot();

        binding.setView(this);
        binding.setViewmodel(mViewModel);

        button = binding.button;
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        adapter = new SucursalRecyclerViewAdapter(mViewModel.sucursales);

        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mViewModel.start();
    }


    public void addModel(SucursalerViewModel mViewModel) {
        this.mViewModel = mViewModel;
    }
}

