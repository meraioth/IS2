package cl.udec.ingsoftware.proyecto_is;

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
    /** Database name */
    private static String DBNAME = "BDLugares";

    /** Version number of the database */
    private static int VERSION = 1;

    /** Field 1 of the table locations, which is the primary key */
    public static final String FIELD_ROW_ID = "_id";

    /** Field 2 of the table locations, stores the latitude */
    public static final String FIELD_LAT = "lat";

    /** Field 3 of the table locations, stores the longitude*/
    public static final String FIELD_LNG = "lng";

    /** Field 4 of the table locations, stores the name*/
    public static final String FIELD_NAME = "nombre";

    /** Field 5 of the table locations, stores the seal*/
    public static final String FIELD_SEAL = "sello_turismo";
    /** Field 5 of the table locations, stores the zoom level of map*/
    //public static final String FIELD_ZOOM = "zoom";

    /** A constant, stores the the table name */
    private static final String DATABASE_TABLE = "lugares";

    /** An instance variable for SQLiteDatabase */
    private SQLiteDatabase mDB;

    /** Constructor */
    public DBlocal(Context context) {
        super(context, DBNAME, null, VERSION);
        this.mDB = getWritableDatabase();
    }

    /** This is a callback method, invoked when the method getReadableDatabase() / getWritableDatabase() is called
     * provided the database does not exists
     * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql =     "create table if not exists " + DATABASE_TABLE + " ( " +
                FIELD_ROW_ID + " integer primary key autoincrement, " +
                FIELD_NAME + " text ," +
                FIELD_SEAL + " text ," +
                FIELD_LNG + " double , " +
                FIELD_LAT + " double " +
                " ) ";
        db.execSQL(sql);
    }

    /** Inserts a new location to the table locations */
    public long insert(ContentValues contentValues){
        long rowID = mDB.insert(DATABASE_TABLE, null, contentValues);
        return rowID;
    }

    /** Deletes all locations from the table */
    public int del(){
        int cnt = mDB.delete(DATABASE_TABLE, null , null);
        return cnt;
    }

    /** Returns all the locations from the table */
    public Cursor getAllLocations(){
        return mDB.query(DATABASE_TABLE, null , null, null, null, null, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public String getFieldLat(){
        return FIELD_LAT;
    }
    public String getFieldLng(){
        return FIELD_LNG;
    }
    public String getFieldName(){
        return FIELD_NAME;
    }
    public String getFieldSeal(){
        return FIELD_SEAL;
    }
    /*public String getFieldZoom(){
        return FIELD_ZOOM;
    }*/
}
