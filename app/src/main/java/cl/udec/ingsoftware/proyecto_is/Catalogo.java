package cl.udec.ingsoftware.proyecto_is;

import java.util.ArrayList;

/**
 * Created by matisin on 28-12-16.
 */

public class Catalogo {
    private ArrayList<Itinerario> itinerarios;

    public Itinerario getItinerario(Itinerario it){
        Itinerario r = null;
        for (int i = 0; i < itinerarios.size(); i++){
            if(itinerarios.get(i) == it){
                r = itinerarios.get(i);
            }
        }
        return r;
    }
}
