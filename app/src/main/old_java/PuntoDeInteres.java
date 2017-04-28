package cl.udec.ingsoftware.proyecto_is.old;

/**
 * Created by matisin on 28-12-16.
 */

public class PuntoDeInteres {
    private String id;
    private String direccion;
    private int longitud,latitud;

    public PuntoDeInteres(String id, String direccion,int longitud, int latitud){
        this.id = id;
        this.direccion = direccion;
        this.longitud = longitud;
        this.latitud = latitud;
    }
}
