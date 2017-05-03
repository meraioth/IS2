package cl.udec.ingsoftware.proyecto_is.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cl.udec.ingsoftware.proyecto_is.R;
import cl.udec.ingsoftware.proyecto_is.databinding.BusquedaFragmentBinding;


public class BusquedaFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        BusquedaFragmentBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.busqueda_fragment, container, false);
        View view = binding.getRoot();
        return view;
    }
}
