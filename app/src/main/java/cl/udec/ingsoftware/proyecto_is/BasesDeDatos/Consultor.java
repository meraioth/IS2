package cl.udec.ingsoftware.proyecto_is.BasesDeDatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by meraioth on 08-05-17.
 */

public class Consultor {
    private DBremoto remoto;
    private DBlocal local;
    private Context cont;

    public Consultor(Context cont){
        this.cont=cont;
        remoto = new DBremoto();
        local = new DBlocal(cont);
    }


    //TODO:Instanciar BDS, Solo metodos vacios, SQL va aquí
    public Cursor getSucursalesLocal(){
        SQLiteDatabase db = local.getReadableDatabase();
        Cursor c = db.rawQuery("select * " +
                "from sucursal, servicio, sucursal_servicio, servicio_categoria " +
                "where sucursal.id = sucursal_servicio.id_sucursal " +
                "and sucursal_servicio.id_servicio = servicio.id " +
                "and servicio.id = servicio_categoria.id_servicio;",null);
        return c;
    };

    public void getItinerariosLocal(){};

    public void getCategoriasLocal(){};

    public void getServiciosLocal(){};

    public ResultSet getSucursalesRemoto(){
        remoto.query("select * " +
                "from sucursal, servicio, sucursal_servicio, servicio_categoria " +
                "where sucursal.id = sucursal_servicio.id_sucursal " +
                "and sucursal_servicio.id_servicio = servicio.id " +
                "and servicio.id = servicio_categoria.id_servicio;") ;
        return remoto.getResult();
    };

    public void getItinerariosRemoto(){};

    public void getCategoriasRemoto(){};

    public void getServiciosRemoto(){};

    public int getVersionRemoto(){
        remoto.query("select max(update) from log;");
        ResultSet rs = remoto.getResult();
        try {
            if(rs!=null){
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }





    //query1,query2
}
