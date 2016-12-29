package cl.udec.ingsoftware.proyecto_is;

import java.util.ArrayList;

/**
 * Created by matisin on 28-12-16.
 */

public class Empresario extends Usuario {
    private ArrayList<Empresa> empresas;
    int id;
    public Empresario(int id){
        this.id=id;
    }
}
