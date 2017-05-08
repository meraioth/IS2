package cl.udec.ingsoftware.proyecto_is.BasesDeDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;


import java.io.Serializable;

/**
 * Created by meraioth on 12-04-17.
 */

public class DBlocal extends SQLiteOpenHelper implements Serializable{
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "sucursal.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String BOOLEAN_TYPE = " INTEGER";

    private static final String INTEGER_TYPE = " INTEGER";

    private static final String VARCHAR_TYPE = " VARCHAR";

    private static final String DOUBLE_PRECISION_TYPE = " DOUBLE PRECISION";

    private static final String COMMA_SEP = ",";

    //TABLA DE SUCURSAL
    //TODO:Colocar restricciones foreignkey
    private static final String SQL_CREATE_ENTRIES_SUCURSAL =
            "CREATE TABLE " + PersistenceContract.SucursalEntry.TABLE_NAME + " (" +
                    PersistenceContract.SucursalEntry.COLUMN_NAME_ID + INTEGER_TYPE + " PRIMARY KEY," +
                    PersistenceContract.SucursalEntry.COLUMN_NAME_NOMBRE + VARCHAR_TYPE + "(50)" + COMMA_SEP +
                    PersistenceContract.SucursalEntry.COLUMN_NAME_SELLO + INTEGER_TYPE + COMMA_SEP +
                    PersistenceContract.SucursalEntry.COLUMN_NAME_EMPRESA + VARCHAR_TYPE + "(20)" + COMMA_SEP +
                    PersistenceContract.SucursalEntry.COLUMN_NAME_COMUNA + VARCHAR_TYPE + "(20)" + COMMA_SEP +
                    PersistenceContract.SucursalEntry.COLUMN_NAME_LAT + DOUBLE_PRECISION_TYPE + COMMA_SEP +
                    PersistenceContract.SucursalEntry.COLUMN_NAME_LONG + DOUBLE_PRECISION_TYPE +
                    " )";
    //TODO:FALTA AGREGAR DESCRIPCION Y FOTO
    private static final String SQL_CREATE_ENTRIES_SERVICIO =
            "CREATE TABLE " + PersistenceContract.ServicioEntry.TABLE_NAME + " (" +
                    PersistenceContract.ServicioEntry.COLUMN_NAME_ID + INTEGER_TYPE + " PRIMARY KEY," +
                    PersistenceContract.ServicioEntry.COLUMN_NAME_NOMBRE + VARCHAR_TYPE + "(50)" +
                     " )";
    private static final String SQL_CREATE_ENTRIES_SUCURSAL_SERVICIO =
            "CREATE TABLE " + PersistenceContract.Sucursal_ServicioEntry.TABLE_NAME + " (" +
                    PersistenceContract.Sucursal_ServicioEntry.COLUMN_NAME_ENTRY_ID_SUCURSAL + INTEGER_TYPE + COMMA_SEP +
                    PersistenceContract.Sucursal_ServicioEntry.COLUMN_NAME_ENTRY_ID_SERVICIO + INTEGER_TYPE +
                    " )";

//
//    private static final String SQL_CREATE_ENTRIES_SUCURSAL_SERVICIO =
//            "CREATE TABLE " + PersistenceContract.SucursalEntry.TABLE_NAME + " (" +
//                    PersistenceContract.SucursalEntry._ID + INTEGER_TYPE + " PRIMARY KEY," +
//                    PersistenceContract.SucursalEntry.COLUMN_NAME_ENTRY_ID + INTEGER_TYPE + COMMA_SEP +
//                    PersistenceContract.SucursalEntry.COLUMN_NAME_NOMBRE + VARCHAR_TYPE + "(50)" + COMMA_SEP +
//                    PersistenceContract.SucursalEntry.COLUMN_NAME_SELLO + INTEGER_TYPE + COMMA_SEP +
//                    PersistenceContract.SucursalEntry.COLUMN_NAME_EMPRESA + VARCHAR_TYPE + "(20)" + COMMA_SEP +
//                    PersistenceContract.SucursalEntry.COLUMN_NAME_COMUNA + VARCHAR_TYPE + "(20)" + COMMA_SEP +
//                    PersistenceContract.SucursalEntry.COLUMN_NAME_LAT + DOUBLE_PRECISION_TYPE + COMMA_SEP +
//                    PersistenceContract.SucursalEntry.COLUMN_NAME_LONG + DOUBLE_PRECISION_TYPE +
//                    " )";
//
    private static final String SQL_CREATE_ENTRIES_SERVICIO_CATEGORIA =
            "CREATE TABLE " + PersistenceContract.Servicio_CategoriaEntry.TABLE_NAME + " (" +
                    PersistenceContract.Servicio_CategoriaEntry.COLUMN_NAME_ENTRY_ID_SERVICIO + INTEGER_TYPE + COMMA_SEP +
                    PersistenceContract.Servicio_CategoriaEntry.COLUMN_NAME_NOMBRE_CATEGORIA + VARCHAR_TYPE + "(50)" +
                    " )";
//    private static final String SQL_CREATE_ENTRIES_CATEGORIA =
//            "CREATE TABLE " + PersistenceContract.SucursalEntry.TABLE_NAME + " (" +
//                    PersistenceContract.SucursalEntry._ID + INTEGER_TYPE + " PRIMARY KEY," +
//                    PersistenceContract.SucursalEntry.COLUMN_NAME_ENTRY_ID + INTEGER_TYPE + COMMA_SEP +
//                    PersistenceContract.SucursalEntry.COLUMN_NAME_NOMBRE + VARCHAR_TYPE + "(50)" + COMMA_SEP +
//                    PersistenceContract.SucursalEntry.COLUMN_NAME_SELLO + INTEGER_TYPE + COMMA_SEP +
//                    PersistenceContract.SucursalEntry.COLUMN_NAME_EMPRESA + VARCHAR_TYPE + "(20)" + COMMA_SEP +
//                    PersistenceContract.SucursalEntry.COLUMN_NAME_COMUNA + VARCHAR_TYPE + "(20)" + COMMA_SEP +
//                    PersistenceContract.SucursalEntry.COLUMN_NAME_LAT + DOUBLE_PRECISION_TYPE + COMMA_SEP +
//                    PersistenceContract.SucursalEntry.COLUMN_NAME_LONG + DOUBLE_PRECISION_TYPE +
//                    " )";
//
//


    //TODO: agregar más tablas como SQL_CREATE_ENTRIES acá.

    public DBlocal(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        //TODO: cuando existan más tablas, agregarlas con db.execSQL
        db.execSQL(SQL_CREATE_ENTRIES_SUCURSAL);
        db.execSQL(SQL_CREATE_ENTRIES_SERVICIO);
        db.execSQL(SQL_CREATE_ENTRIES_SUCURSAL_SERVICIO);
        db.execSQL(SQL_CREATE_ENTRIES_SERVICIO_CATEGORIA);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }

}
