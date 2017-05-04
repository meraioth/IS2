package cl.udec.ingsoftware.proyecto_is.model.source.local;

import android.provider.BaseColumns;

/**
 * Created by matisin on 21-04-17.
 */

public final class PersistenceContract {

    // Constructor vacio para evitar que se instancie accidentalmente.
    private PersistenceContract() {}

    /* Inner class that defines the table contents */
    public static abstract class SucursalEntry implements BaseColumns {
        public static final String TABLE_NAME = "sucursal";
        public static final String COLUMN_NAME_ENTRY_ID = "id";
        public static final String COLUMN_NAME_NOMBRE = "nombre";
        public static final String COLUMN_NAME_SELLO = "sello_de_turismo";
        public static final String COLUMN_NAME_EMPRESA = "rut_empresa";
        public static final String COLUMN_NAME_COMUNA = "comuna";
        public static final String COLUMN_NAME_LAT = "latitud";
        public static final String COLUMN_NAME_LONG = "longitud";
    }

    public static abstract class ServicioEntry implements BaseColumns {
        public static final String TABLE_NAME = "servicio";
        public static final String COLUMN_NAME_ENTRY_ID = "id";
        public static final String COLUMN_NAME_NOMBRE = "nombre_servicio";
        public static final String COLUMN_NAME_FOTO = "foto";
        public static final String COLUMN_NAME_DESCRIPCION = "descripcion";

    }

    //TODO: Agregar más definiciones de las tablas acá.

}
