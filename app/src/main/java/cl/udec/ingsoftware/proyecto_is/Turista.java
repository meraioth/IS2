package cl.udec.ingsoftware.proyecto_is;

import java.util.ArrayList;

/**
 * Created by matisin on 28-12-16.
 */

public class Turista extends Usuario {
    private ArrayList<Itinerario> itinerarios;
    Itinerario it;
    int id;
    public Turista(int id){
        itinerarios=new ArrayList<Itinerario>();
        this.id=id;
    }

//    public void crearItinerario(String nombreItinerario){
//        if(itinerarios.isEmpty()){
//            this.itinerarios = new ArrayList<Itinerario>();
//        }
//        Itinerario it = new Itinerario(nombreItinerario);
//        if(!existeNombre(nombreItinerario)){
//            itinerarios.add(it);
//        }
//    }

    boolean existeNombre(String nombreItinerario){
        boolean b = false;
        for(int i = 0; i < itinerarios.size();i++){
            if (itinerarios.get(i).getNombre().equals(nombreItinerario)){
                b = true;
            }
        }
        return b;
    }

    public void crearItinerario(String nombre, String duracion) {
        it = new Itinerario(nombre,duracion);
    }

    public void guardarItinerario() {
        itinerarios.add(it);
        it.writeDB(id);
        //GUARDAR EN BD;
    }

    public void agregarServicioaItinerario(String seleccionado) {
        it.addServicio(seleccionado);

    }
}
