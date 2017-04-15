package cl.udec.ingsoftware.proyecto_is;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by meraioth on 12-04-17.
 */

public class DBlocal extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Persona.TABLE_NAME + " (" +
                    Persona._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Persona.COL1 + TEXT_TYPE + COMMA_SEP+
                    Persona.COL2 + TEXT_TYPE +
                  " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Persona.TABLE_NAME;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Local.sqlite";


    public DBlocal(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Método para crear la Tabla que recibe la consulta Transact-SQL
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    //Método que elimina la tabla y vuelve a llamar al método que la crea
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }



    public static abstract class Persona implements BaseColumns
    {
        public static final String TABLE_NAME = "Persona";
        public static final String COL1 = "Nombre";
        public static final String COL2 = "RUT";


    }
}
