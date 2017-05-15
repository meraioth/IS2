package cl.udec.ingsoftware.proyecto_is.Presentador;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;

import cl.udec.ingsoftware.proyecto_is.Modelo.Itinerario;

/**
 * Created by meraioth on 12-05-17.
 */

public class PresentadorItinerario {
    Context context;
    ArrayList<Itinerario> itinerarios;
    Formateador formater;
    public PresentadorItinerario(Context cont) throws SQLException {
        this.context=cont;
        formater= new Formateador(context);
        itinerarios=formater.getItinerarios();
    }


}
