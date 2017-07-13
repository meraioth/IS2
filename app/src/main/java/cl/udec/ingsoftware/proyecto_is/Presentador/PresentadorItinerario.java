package cl.udec.ingsoftware.proyecto_is.Presentador;

import android.content.Context;
import android.util.Pair;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import cl.udec.ingsoftware.proyecto_is.Modelo.Categoria;
import cl.udec.ingsoftware.proyecto_is.Modelo.Itinerario;
import cl.udec.ingsoftware.proyecto_is.Modelo.Servicio;
import cl.udec.ingsoftware.proyecto_is.Modelo.Sucursal;

/**
 * Created by meraioth on 12-05-17.
 */

public class PresentadorItinerario {
    Context context;
    ArrayList<Itinerario> itinerarios = new ArrayList<>();
    Formateador formateador;
    public static PresentadorItinerario instance;

    public static PresentadorItinerario getInstance(Context cont){
        if (instance == null){
            try{
                new PresentadorItinerario(cont);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }


    public PresentadorItinerario(Context cont) throws SQLException {
        this.context=cont;
        formateador= new Formateador(context);
        itinerarios=formateador.getItinerarios();
        instance = this;
    }
    public String getNombreItinerario(int idItinerario){
        return findItinerarioById(idItinerario).getNombre();
    }

    public int getIdUsuario(int idItinerario){
        return findItinerarioById(idItinerario).getId_usuario();
    }

    public String getEstacionItinerario(int idItinerario){
        return  findItinerarioById(idItinerario).getEstacion();
    }

    public int getDuracionItinerario(int id){
        ArrayList<Pair> sucursales_duracion = findItinerarioById(id).getSucursales();
        int suma = 0;
        for (Pair pair : sucursales_duracion){
            suma += (int)pair.second;
        }
        return suma;
    }
    public int getDuracionSucursalItinerario(int idItinerario, int id_sucursal){
        ArrayList<Pair> sucursales_duracion = findItinerarioById(idItinerario).getSucursales();
        for (Pair pair : sucursales_duracion){
            Sucursal suc = (Sucursal) pair.first;
            if( suc.getId() == id_sucursal){
                return (int) pair.second;
            }
        }
        return 0;
    }

    public ArrayList<String> getNombreSucursales(int id){
        ArrayList<Pair> sucursales_duracion = new ArrayList<Pair>();
        sucursales_duracion = findItinerarioById(id).getSucursales();
        ArrayList<String> nombresSucursales = new ArrayList<>();
        for (Pair pair : sucursales_duracion){
            Sucursal suc = (Sucursal) pair.first;
            nombresSucursales.add(suc.getNombre());
        }
        return nombresSucursales;
    }

    public ArrayList<String> getItinerarioServices(int id){
        ArrayList<Pair> sucursales_duracion = new ArrayList<>();
        sucursales_duracion = findItinerarioById(id).getSucursales();
        ArrayList<String> servicios = new ArrayList<>();
        for (Pair pair:  sucursales_duracion){
            Sucursal suc = (Sucursal) pair.first;
            ArrayList<Servicio> sese = suc.getServicios();
            for (Servicio ss : suc.getServicios()){
                String categoria = ss.getCategoria().getNombre();
                if (categoria.contentEquals("Alojamiento Turístico")){servicios.add(categoria);}
                if (categoria.contentEquals("Agencias de Viaje y Tour Operador")){servicios.add(categoria);}
                if (categoria.contentEquals("Restaurantes y Similares")){servicios.add(categoria);}
                if (categoria.contentEquals("Transporte de Pasajeros por Carretera Interurbana")){servicios.add(categoria);}
                if (categoria.contentEquals("Turismo Aventura")){servicios.add(categoria);}
                if (categoria.contentEquals("Servicios de Esparcimiento")){servicios.add(categoria);}
                if (categoria.contentEquals("Artesanía")){servicios.add(categoria);}
                if (categoria.contentEquals("Guías de Turismo")){servicios.add(categoria);}
                if (categoria.contentEquals("Servicios Deportivos")){servicios.add(categoria);}
            }
        }
        return servicios;
    }

    public ArrayList<String> getFotoSucursales(int id){
        ArrayList<Pair> sucursales_duracion = findItinerarioById(id).getSucursales();
        ArrayList<String> fotosSucursales = new ArrayList<>();
        for (Pair pair : sucursales_duracion){
            Sucursal suc = (Sucursal) pair.first;
            fotosSucursales.add(suc.getImagen());
        }
        return fotosSucursales;
    }

    private Itinerario findItinerarioById(int idItinerario){
        for (Itinerario itinerario: itinerarios){
            if(itinerario.getId() == idItinerario){
                return itinerario;
            }
        }
        return null;
    }


    public ArrayList<Integer> getIdSucursales(int id) {
        ArrayList<Pair> sucursales_duracion = findItinerarioById(id).getSucursales();
        ArrayList<Integer> fotosSucursales = new ArrayList<>();
        for (Pair pair : sucursales_duracion){
            Sucursal suc = (Sucursal) pair.first;
            fotosSucursales.add(suc.getId());
        }
        return fotosSucursales;
    }

    public void crearItinerario(int id,String nombre, int idUsuario, String estacion, int[] idsSucursales, int[] duraciones) throws SQLException {
        Itinerario itinerario = formateador.crearItinerario(id, nombre,idUsuario,estacion,idsSucursales,duraciones);
        if (itinerario != null){
            itinerarios.add(itinerario);
        }
    }
}
