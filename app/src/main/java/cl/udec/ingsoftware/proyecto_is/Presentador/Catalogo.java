package cl.udec.ingsoftware.proyecto_is.Presentador;

import android.content.Context;
import android.util.Log;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import cl.udec.ingsoftware.proyecto_is.Modelo.Categoria;
import cl.udec.ingsoftware.proyecto_is.Modelo.Empresa;
import cl.udec.ingsoftware.proyecto_is.Modelo.Itinerario;
import cl.udec.ingsoftware.proyecto_is.Modelo.Servicio;
import cl.udec.ingsoftware.proyecto_is.Modelo.Sucursal;
import cl.udec.ingsoftware.proyecto_is.Modelo.Tripleta;
import cl.udec.ingsoftware.proyecto_is.Modelo.TripletaItinerario;

/**
 * Created by matisin on 28-12-16.
 */

public class Catalogo implements Serializable {

    //TODO:Filtros serán aplicados aca. retornando solo strings a activites

    private ArrayList<Itinerario> itinerarios;
    private ArrayList<Sucursal> sucursales;
    private ArrayList<Empresa> empresas;
    private Formateador formateador;
    private HashMap<String, Integer> sellos;

    public Catalogo(Context cont) throws SQLException {
        formateador = new Formateador(cont);
        sellos = new HashMap<>();
        sucursales = formateador.getSucursales();
        for(Sucursal sucursal: sucursales){
            sellos.put(sucursal.getNombre(),sucursal.getSello());
        }
        itinerarios = formateador.getItinerarios();
        empresas = formateador.getEmpresas();
    }

    public int getSello(String sucursal){
        return sellos.get(sucursal);
    }

    public ArrayList getSucursales(){
        ArrayList<String> sucursaless = new ArrayList();
        for (Sucursal suc:sucursales
             ) {
            sucursaless.add(suc.getNombre());
        }
        return sucursaless;
    }


    public ArrayList getIds(){
        ArrayList<Integer> ids = new ArrayList<>();
        for (Sucursal suc:sucursales
                ) {
            ids.add(suc.getId());

        }
        return ids;

    }

    public ArrayList getSucursales(String categoria){
        ArrayList<String> sucursaless = new ArrayList();
        for (Sucursal suc:sucursales
                ) {
            for(Servicio serv : suc.getServicios()){
                if(serv.getCategoria().getNombre().compareTo(categoria) == 0){
                    sucursaless.add(suc.getNombre());
                }
            }
        }
        return sucursaless;
    }

     public ArrayList getTripletasOfSucursales()throws SQLException {
        ArrayList<Tripleta> info = new ArrayList<Tripleta>();
        ArrayList<Integer> ides = new ArrayList<Integer>();
        int id;
        for (Sucursal suc: sucursales) {
            id = suc.getId();
            if (!ides.contains(id)){
                ides.add(id);
                Tripleta tri = new Tripleta(suc.getId(),suc.getNombre(),suc.getImagen());
                info.add(tri);
            }
        }

        return info;
    }

    public ArrayList getBuscarKeyword(String arg) {
        ArrayList<Tripleta> Resp = new ArrayList<>();
        ArrayList<Integer> ides = new ArrayList<Integer>();
        int id;
        for (Sucursal sucursal: sucursales) {
            for(Servicio serv: sucursal.getServicios()){
                if (sucursal.getNombre().contains(arg)|| serv.getNombre().contains(arg)||serv.getCategoria().getNombre().contains(arg)||sucursal.getComuna().contains(arg)){
                    id = sucursal.getId();
                    if (!ides.contains(id)){
                        ides.add(id);
                        Resp.add(new Tripleta(sucursal.getId(),sucursal.getNombre(),sucursal.getImagen()));
                    }
                }
            }

        }
        return Resp;
    }


    public  ArrayList getCategorias(){
        Set<String> cat = new HashSet<String>();
        ArrayList<String> categoria= new ArrayList<String>();
        categoria.add("Todas");
        for (Sucursal suc: sucursales
             ) {
            for (Servicio serv:suc.getServicios()
                 ) {
                cat.add(serv.getCategoria().getNombre());
            }
        }
        for (String str:cat) {
            categoria.add(str);
        }
        return categoria;
    }


    public  ArrayList getServicios(){
        Set<String> ser = new HashSet<String>();
        ArrayList<String> servicios= new ArrayList<String>();
        for (Sucursal suc: sucursales
                ) {
            for (Servicio serv:suc.getServicios()
                    ) {
                ser.add(serv.getNombre());
            }
        }
        for (String str:ser
                ) {
            servicios.add(str);
        }
        return servicios;
    }

    public  ArrayList getIdsServicios(){
        Set<Integer> ser = new HashSet<Integer>();
        ArrayList<Integer> idsServicios = new ArrayList<Integer>();
        for (Sucursal suc: sucursales
                ) {
            for (Servicio serv:suc.getServicios()
                    ) {
                ser.add(serv.getId());
            }
        }
        for (Integer str:ser
                ) {
            idsServicios.add(str);
        }
        return idsServicios;
    }

    public  ArrayList getServiciosBusqueda(String categoria){
        Set<String> ser = new HashSet<String>();
        ArrayList<String> servicios= new ArrayList<String>();
        for (Sucursal suc: sucursales
                ) {
            for (Servicio serv:suc.getServicios()
                    ) {
                if(serv.getCategoria().getNombre()==categoria){
                    ser.add(serv.getNombre());
                }

            }
        }
        for (String str:ser
                ) {
            servicios.add(str);
        }
        return servicios;
    }

    public ArrayList getLongitudes(){
        ArrayList<Double> longitudes = new ArrayList<>();
        for (Sucursal suc:sucursales
                ) {
            longitudes.add(suc.getLongitud());

        }
        return longitudes;
    }

    public ArrayList getLatitudes(){
        ArrayList<Double> latitudes = new ArrayList<>();
        for (Sucursal suc:sucursales
                ) {
            latitudes.add(suc.getLatitud());

        }
        return latitudes;

    }

    public ArrayList<Categoria> getCategoria(String s){
        ArrayList<Categoria> cate = new ArrayList();
        for (Sucursal suc:sucursales) {
            if (suc.getNombre().contentEquals(s)){
                for(Servicio serv : suc.getServicios()){
                    cate.add(serv.getCategoria());
                }
            }

        }
        return cate;
    }

    public ArrayList getLongitudes(String categoria){
        ArrayList<Double> longitudes = new ArrayList<>();
        for (Sucursal suc:sucursales) {
            for(Servicio serv : suc.getServicios()){

                if(serv.getCategoria().getNombre().compareTo(categoria) == 0){
                    longitudes.add(suc.getLongitud());

                }
            }
        }
        return longitudes;
    }

    public ArrayList getLatitudes(String categoria){
        ArrayList<Double> latitudes = new ArrayList<>();
        for (Sucursal suc:sucursales
                ) {
            for (Servicio serv : suc.getServicios()) {
//                System.out.println("sucursal no filtrada "+suc.getNombre()+" "+serv.getCategoria().getNombre() +" categoria:"+categoria +" equals: "+String.valueOf(serv.getCategoria().getNombre().compareTo(categoria) == 0 ));

                if (serv.getCategoria().getNombre().compareTo(categoria) == 0 ){
                    System.out.println("sucursal filtrada "+suc.getNombre()+" "+serv.getCategoria().getNombre());

                    latitudes.add(suc.getLatitud());

                }
            }
        }
        return latitudes;

    }

    public ArrayList<String> getItinerarios (){
        ArrayList<String> nombreItinerarios = new ArrayList<>();
        for (Itinerario itinerario : itinerarios){
            nombreItinerarios.add(itinerario.getNombre());
        }
        return nombreItinerarios;
    }

    public ArrayList<String> getComunas() {
        Set<String> com = new HashSet<String>();
        ArrayList<String> comunas= new ArrayList<String>();
        //comunas.add("Todas");
        for (Sucursal suc: sucursales
                ) {

                com.add(suc.getComuna());
        }
        for (String str:com
                ) {
            comunas.add(str);
        }
        return comunas;
    }

    /**
     * Metodo para buscar las categorías que esten presentes en la comuna "comuna"
     * @param comuna
     * @return Arreglo de String con categorias presentes en la comuna "Comuna"
     */
    public ArrayList getCategorias(String comuna) {

        Set<String> cat = new HashSet<String>();
        ArrayList<String> categoria= new ArrayList<String>();
        categoria.add("Todas");
        for (Sucursal suc: sucursales
                ) {
            for (Servicio serv:suc.getServicios()
                    ) {
                if(suc.getComuna().compareTo(comuna)==0)
                cat.add(serv.getCategoria().getNombre());
            }
        }
        for (String str:cat){
            categoria.add(str);
        }
        return categoria;

    }

    /**
     * Metodo para filtrar las sucursales por comuna, categoria y servicio
     * @param str_comuna
     * @param str_categoria
     * @param str_servicio
     * @return Arreglo de tripletas
     */
    public ArrayList getTripletasOfSucursales(String str_comuna, String str_categoria, String str_servicio) {
        ArrayList<Tripleta> info = new ArrayList<Tripleta>();
        ArrayList<Integer> ides = new ArrayList<Integer>();
        int id;
        //Log.e("id", String.valueOf(sucursales.size()));
        for (Sucursal suc: sucursales) {
            id = suc.getId();
            if (!ides.contains(id)){
                ides.add(id);
                for (Servicio serv:suc.getServicios()
                     ) {
                    if(suc.getComuna().compareTo(str_comuna)==0 && serv.getNombre().compareTo(str_servicio)==0 && serv.getCategoria().getNombre().compareTo(str_categoria)==0) {
                        Tripleta tri = new Tripleta(suc.getId(), suc.getNombre(), suc.getImagen());
                        info.add(tri);
                    }
                }
            }
        }

        return info;
    }

    public ArrayList<String> getAllCategorias() {
        ArrayList<String> categoria= new ArrayList<String>();
        categoria.add("Todas");
        for (Sucursal suc: sucursales
                ) {
            for (Servicio serv:suc.getServicios()
                    ) {
                    categoria.add(serv.getCategoria().getNombre());
            }
        }

        return categoria;
    }

    public ArrayList<Integer> getAllIdOfItinerarios(){
        ArrayList<Integer> ids = new ArrayList<>();
        for (Itinerario it: itinerarios){
            ids.add(it.getId());
        }
        return ids;
    }

    public int[] getDuraciones_Itinerarios() {
    return new int[]{5,10,15};
    }

    /**
     * Obtiene las duraciones de itinerarios para la estacion 'estacion'
     * @param estacion
     * @return Arreglo de Enteros con las duraciones para la estacion
     */
    public ArrayList getDuraciones(String estacion) {
        Set<Integer> dur = new HashSet<Integer>();
        ArrayList<Integer> duracion = new ArrayList<Integer>();
        //duracion.add("Todas");
        for (Itinerario itinerario: itinerarios){
            if(itinerario.getEstacion().compareTo(estacion)==0) {
                dur.add(itinerario.getDuracion());
            }
        }
        for (Integer str:dur){
            duracion.add(str);
        }
        if(duracion.size()==0)
            duracion.add(0);
        return duracion;
    }

    /**
     * Metodo que retorna el nombre de los itinerarios junto con sus id de un usuario
     * @param idUsuario id de un usuario
     * @return Tabla hash con itinerarios y ids
     */
    public HashMap<String,Integer> getItinerariosUsuario(int idUsuario){
        HashMap<String,Integer> mis_itinerarios = new HashMap<>();
        for(Itinerario itinerario: itinerarios){
            if(itinerario.getId_usuario() == idUsuario){
                mis_itinerarios.put(itinerario.getNombre(),itinerario.getId());
            }
        }
        return mis_itinerarios;
    }

    /**
     * Filtrar itinerarios por estación y duración
     * @param str_estacion
     * @param str_duracion
     * @return Arreglo de tripletas
     */
    public ArrayList getBuscarItinerarios(String str_estacion, int str_duracion) {
        ArrayList<TripletaItinerario> info = new ArrayList<TripletaItinerario>();
        ArrayList<Integer> ides = new ArrayList<Integer>();
        int id;
        for (Itinerario itinerario: itinerarios) {
            id = itinerario.getId();
            if (!ides.contains(id)){
                ides.add(id);
                    if(itinerario.getEstacion().compareTo(str_estacion)==0 && Integer.valueOf(itinerario.getDuracion()).compareTo(Integer.valueOf(str_duracion))==0) {
                        TripletaItinerario tri = new TripletaItinerario(itinerario.getId(), itinerario.getNombre(), itinerario.getItinerarioSucursarles());
                        info.add(tri);
                    }
            }
        }
        return info;
    }

    /**
     * Búsqueda de itinerarios por keyword
     * @param arg
     * @return Arreglo de tripletas
     */
    public ArrayList getBuscarItinerariosKeyword(String arg) {
        ArrayList<TripletaItinerario> info = new ArrayList<>();
        ArrayList<Integer> ides = new ArrayList<Integer>();
        int id;
        for (Itinerario itinerario: itinerarios) {
            if(arg.contains(itinerario.getNombre())){
                if(!ides.contains(itinerario.getId())) {
                    ides.add(itinerario.getId());
                    info.add(new TripletaItinerario(itinerario.getId(), itinerario.getNombre(), itinerario.getItinerarioSucursarles()));
                }
            }
            for(Sucursal suc: itinerario.getItinerarioSucursarles()) {
                for (Servicio serv : suc.getServicios()){
                    if (itinerario.getNombre().contains(arg) || suc.getNombre().contains(arg) || serv.getNombre().contains(arg) || serv.getCategoria().getNombre().contains(arg) || suc.getComuna().contains(arg))
                {
                    id = itinerario.getId();
                    if (!ides.contains(id)) {
                        ides.add(id);
                        info.add(new TripletaItinerario(itinerario.getId(), itinerario.getNombre(), itinerario.getItinerarioSucursarles()));
                    }
                }
                }
            }
        }
        return info;
    }

    public ArrayList<TripletaItinerario> getTripletasOfItinerario() {
        ArrayList<TripletaItinerario> info = new ArrayList<TripletaItinerario>();
        ArrayList<Integer> ides = new ArrayList<Integer>();
        for (Itinerario itinerario: itinerarios) {
            Log.d("tamaño sucursales", String.valueOf(itinerario.getItinerarioSucursarles().size()));
            TripletaItinerario tri = new TripletaItinerario(itinerario.getId(), itinerario.getNombre(), itinerario.getItinerarioSucursarles());
            info.add(tri);
        }
        return info;
    }

    //Metodo que retorna los String asociado a los nombres de las sucursales de un empresario (por su id)
    public ArrayList<String> getSucursalesById(int id){
        ArrayList<String> sucursaless = new ArrayList();
        for (Empresa firma: empresas){
            System.out.println("id: "+id+" == "+firma.getIdEmpresario());
            if (id == firma.getIdEmpresario()){
                for (Sucursal suc:this.sucursales){
                    if (String.valueOf(suc.getRutEmpresa()).equals(String.valueOf(firma.getRutEmpresa()))){
                        sucursaless.add(suc.getNombre());
                    }
                }
            }
        }
        return sucursaless;
    }

    public void eliminarSucursal(String sucursal) throws SQLException {
        int pos= 0;
        for(Sucursal suc:this.sucursales){
            if(suc.getNombre().equals(sucursal)){
                break;
            }
            pos++;
        }
        boolean eliminado = formateador.eliminarSucursal(sucursal);
        if(eliminado){
            this.sucursales.remove(pos);
        }
    }

    public boolean eliminarItinerario(int id_itinerario) {
        boolean eliminado = formateador.eliminarItinerario(id_itinerario);
        if(eliminado){
            for(Itinerario itinerario: itinerarios){
                if(itinerario.getId() == id_itinerario){
                    itinerarios.remove(itinerario);
                    break;
                }
            }
        }
        return eliminado;
    }
    public int setDatosSucursal(String nombre, String rut, String descripcion, String comuna) throws SQLException {
        return formateador.guardarDatosSucursal(nombre, rut, descripcion, comuna);
    }

    public void setServicioSucursal(ArrayList<Integer> servicios, int idSucursalAgregada){
        formateador.guardarServicioSucursal(servicios,idSucursalAgregada);
    }
}