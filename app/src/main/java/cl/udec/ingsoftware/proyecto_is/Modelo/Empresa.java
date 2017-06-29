package cl.udec.ingsoftware.proyecto_is.Modelo;

import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by matisin on 28-12-16.
 */

public class Empresa {

    private int id;
    private String nombre;
    private String rut_empresa;
    private int id_empresario;

    public Empresa(int id, String nombre, String rut_empresa, int id_empresario) {
        this.id = id;
        this.nombre = nombre;
        this.rut_empresa = rut_empresa;
        this.id_empresario = id_empresario;
    }

    public int getId(){return id;}
    public String getNombre(){return nombre;}
    public String getRutEmpresa(){return rut_empresa;}
    public int getIdEmpresario(){return id_empresario;}
}
