package cl.udec.ingsoftware.proyecto_is.Modelo;

import java.util.ArrayList;

/**
 * Created by matisin on 28-12-16.
 */

public class Usuario {
    String name,email;
    int rol,id;
    public Usuario(String name,String email,int rol,int id){
        this.name=name;
        this.email=email;
        this.rol=rol;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public int getRol() {
        return rol;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
