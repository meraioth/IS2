package cl.udec.ingsoftware.proyecto_is.BasesDeDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorJoiner;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
        local = new DBlocal(this.cont);
    }


    //TODO:Instanciar BDS, Solo metodos vacios, SQL va aquí
    public Cursor getSucursalesLocal(){
        SQLiteDatabase db = local.getReadableDatabase();
        Cursor c = db.rawQuery("select *\n" +
                " from sucursal,sucursal_servicio,servicio ,servicio_categoria,categoria\n" +
                "where sucursal.id=sucursal_servicio.id_sucursal and \n" +
                "servicio.id=sucursal_servicio.id_servicio \n" +
                "and servicio.id=servicio_categoria.id_servicio\n" +
                "and servicio_categoria.nombre_categoria=categoria.nombre_categoria;",null);
        return c;
    };

    public Cursor getItinerariosLocal(){
        return null;
    };

    public Cursor getCategoriasLocal(){
        SQLiteDatabase db = local.getReadableDatabase();
        Cursor c = db.rawQuery("select * " +
                "from categoria;",null);
        return c;
    };


    public Cursor getServiciosLocal(){
        SQLiteDatabase db = local.getReadableDatabase();
        Cursor c = db.rawQuery("select * " +
                "from servicio;",null);
        return c;
    };


    public ResultSet getSucursalesRemoto(){
        remoto = new DBremoto();
        remoto.query("select *\n" +
                " from sucursal,sucursal_servicio,servicio ,servicio_categoria,categoria\n" +
                "where sucursal.id=sucursal_servicio.id_sucursal and \n" +
                "servicio.id=sucursal_servicio.id_servicio \n" +
                "and servicio.id=servicio_categoria.id_servicio\n" +
                "and servicio_categoria.nombre_categoria=categoria.nombre_categoria;") ;
        return remoto.getResult();
    };
//// TODO: 22-05-17 Agregar tabla usuario en la consulta para luego instanciar los objetos de usuario 
    public ResultSet getItinerariosRemoto(){
        remoto = new DBremoto();
        remoto.query("select * from itinerarios_y_sucursales") ;
        return remoto.getResult();
    };

    public ResultSet getEmpresas(){
        remoto = new DBremoto();
        remoto.query("select *\n" +
                "from empresa;");
        return remoto.getResult();
    };

    public ResultSet getCategoriasRemoto(){
        remoto = new DBremoto();
        remoto.query("select * " +
                "from categorias; ") ;
        return remoto.getResult();
    };

    public ResultSet getServiciosRemoto(){
        remoto = new DBremoto();
        remoto.query("select * " +
                "from servicio; ") ;
        return remoto.getResult();
    };

    public int getVersionRemoto(){
        remoto = new DBremoto();
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
        Log.d("RESEANDO LOCAL","TRUE");
        local.getWritableDatabase().delete("sucursal",null,null);
        local.getWritableDatabase().delete("servicio",null,null);
        local.getWritableDatabase().delete("sucursal_servicio",null,null);
        local.getWritableDatabase().delete("servicio_categoria",null,null);
        local.getWritableDatabase().delete("categoria",null,null);
//        local.getWritableDatabase().delete("itinerario",null,null);
//        local.getWritableDatabase().delete("orden",null,null);
        local.getWritableDatabase().delete("usuario",null,null);
        local.getWritableDatabase().close();

    }

    public void respaldar_sucursales(ResultSet resultSet) throws SQLException {
        System.out.println("GUARDANDO REGISTROS ################");

        if(resultSet!= null){
            resultSet.first();
            while (resultSet.next()) {

                SQLiteDatabase db = local.getReadableDatabase();
                ContentValues contentValues = new ContentValues();
                if(db.rawQuery("select * from categoria where nombre_categoria = ?",new String[]{resultSet.getString(18)}).getCount()==0){
                    contentValues = new ContentValues();
                    contentValues.put("nombre_categoria",resultSet.getString(18));
                    contentValues.put("descripcion",resultSet.getString(19));
                    local.getWritableDatabase().insert("categoria", null, contentValues);


                }


                if(db.rawQuery("select * from servicio where id = ?",new String[]{String.valueOf(resultSet.getInt(12))}).getCount()==0) {
                    contentValues = new ContentValues();
                    contentValues.put("id", resultSet.getInt(12));
                    contentValues.put("nombre_servicio", resultSet.getString(13));
                    contentValues.put("descripcion", resultSet.getString(15));
                    local.getWritableDatabase().insert("servicio", null, contentValues);

                    contentValues = new ContentValues();
                    contentValues.put("id_servicio",resultSet.getInt(16));
                    contentValues.put("nombre_categoria",resultSet.getString(17));
                    local.getWritableDatabase().insert("servicio_categoria", null, contentValues);


                }

                if(db.rawQuery("select * from sucursal where sucursal.id = ?",new String[]{String.valueOf(resultSet.getInt(1))}).getCount()==0) {
                    Log.d("Registro no existe", "sucursal no existe,agregando...");
                    contentValues = new ContentValues();
                    contentValues.put("id", resultSet.getInt(1));
                    contentValues.put("nombre", resultSet.getString(2));
                    contentValues.put("sello_de_turismo", resultSet.getString(3));
                    contentValues.put("comuna", resultSet.getString(5));
                    contentValues.put("latitud", resultSet.getDouble(6));
                    contentValues.put("longitud", resultSet.getDouble(7));
                    contentValues.put("descripcion", resultSet.getString(8));
                    contentValues.put("foto", resultSet.getString(9));
                    System.out.println("Tupla---->> id :" + resultSet.getInt(1) + " nombre:" + resultSet.getString(2) + " comuna:" + resultSet.getString(5));
                    local.getWritableDatabase().insert("sucursal", null, contentValues);

                    contentValues = new ContentValues();
                    contentValues.put("id_sucursal", resultSet.getInt(10));
                    contentValues.put("id_servicio", resultSet.getInt(11));
                    local.getWritableDatabase().insert("sucursal_servicio", null, contentValues);

                }

            }
        }

        local.getWritableDatabase().close();
    }


    public boolean crearItinerario(int id, String nombre, int idUsuario, String estacion,int[]idsSucursales, int[]duraciones) throws SQLException {
        boolean itinerarioCreado = false;
        boolean ordenCreado = false;
        remoto = new DBremoto();
        remoto.query("insert into itinerario " +
                "values("+ id +"," + nombre + "," + idUsuario + "," + estacion + ");");
        remoto.query("select * from itinerario where nombre = " + nombre + ");");
        if (remoto.getResult().next()){
            itinerarioCreado = true;
        }
        if(itinerarioCreado){
            //PARA CADA ID DE SUCURSAL, INSERTAR EN LA BD EL ORDEN CORRESPONDIENTE
            for(int i = 0; i < idsSucursales.length; i++){
                remoto.query("insert into orden" +
                        "values(" +
                        id + "," + idsSucursales[i] + "," + i + "," + duraciones[i]);
            }
            remoto.query("select * from orden where id_itinerario = " + id + ";");
            if(remoto.getResult().next()){
                ordenCreado = true;
            }
        }
        return ordenCreado;
    }
}
