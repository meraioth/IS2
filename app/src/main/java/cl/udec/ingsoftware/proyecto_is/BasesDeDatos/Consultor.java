package cl.udec.ingsoftware.proyecto_is.BasesDeDatos;

import android.content.ContentValues;
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
                "from sucursal;") ;
        return remoto.getResult();
    };

    public ResultSet getItinerariosRemoto(){
        remoto.query("select * from itinerario,orden,sucursal\n" +
                "where itinerario.id=orden.id_itinerario\n" +
                "and sucursal.id=orden.id_sucursal; ") ;
        return remoto.getResult();
    };

    public ResultSet getCategoriasRemoto(){
        remoto.query("select * " +
                "from categorias; ") ;
        return remoto.getResult();
    };

    public ResultSet getServiciosRemoto(){
        remoto.query("select * " +
                "from servicio; ") ;
        return remoto.getResult();
    };

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
    public void reset_local(){
        local.getWritableDatabase().delete("sucursal",null,null);
        local.getWritableDatabase().delete("servicio",null,null);
        local.getWritableDatabase().delete("sucursal_servicio",null,null);
        local.getWritableDatabase().delete("servicio_categoria",null,null);
        local.getWritableDatabase().close();

    }

    public void respaldar_sucursales(ResultSet resultSet) throws SQLException {

        if(resultSet!= null)
            while (resultSet.next()) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", resultSet.getInt(1));
                contentValues.put("nombre", resultSet.getString(2));
                contentValues.put("sello_de_turismo", resultSet.getString(3));
                contentValues.put("comuna", resultSet.getString(5));
                contentValues.put("latitud", resultSet.getDouble(6));
                contentValues.put("longitud", resultSet.getDouble(7));
                contentValues.put("descripcion", resultSet.getString(8));
                contentValues.put("foto", resultSet.getString(9));


                local.getWritableDatabase().insert("sucursal", null, contentValues);
            }
//        contentValues = new ContentValues();
//        contentValues.put("id",serv.getId());
//        contentValues.put("nombre_servicio",serv.getNombre());
//        //contentValues.put("descripcion",serv.getDescripcion());
//        local.getWritableDatabase().insert("servicio", null, contentValues);
//
//
//        contentValues = new ContentValues();
//        contentValues.put("id_sucursal",sucursal.getId());
//        contentValues.put("id_servicio",serv.getId());
//        local.getWritableDatabase().insert("sucursal_servicio", null, contentValues);
//
//        contentValues = new ContentValues();
//        contentValues.put("id_servicio",serv.getId());
//        contentValues.put("nombre_categoria",cat.getNombre());
//        local.getWritableDatabase().insert("servicio_categoria", null, contentValues);
        local.getWritableDatabase().close();
    }


    //query1,query2
}
