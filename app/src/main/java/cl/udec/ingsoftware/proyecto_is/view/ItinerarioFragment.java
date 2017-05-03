package cl.udec.ingsoftware.proyecto_is.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cl.udec.ingsoftware.proyecto_is.R;

/**
 * Created by matisin on 02-05-17.
 */

public class ItinerarioFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */
        return inflater.inflate(R.layout.itinerario, container, false);
    }

}
