package cl.udec.ingsoftware.proyecto_is.BasesDeDatos;

import android.provider.BaseColumns;

/**
 * Created by meraioth on 07-05-17.
 */

public class Contrato {
        // Constructor vacio para evitar que se instancie accidentalmente.
        private Contrato() {}

        /* Inner class that defines the table contents */
        public static abstract class SucursalEntry implements BaseColumns {
            public static final String TABLE_NAME = "sucursal";
            public static final String COLUMN_NAME_ID = "id";
            public static final String COLUMN_NAME_NOMBRE = "nombre";
            public static final String COLUMN_NAME_SELLO = "sello_de_turismo";
            public static final String COLUMN_NAME_EMPRESA = "rut_empresa";
            public static final String COLUMN_NAME_COMUNA = "comuna";
            public static final String COLUMN_NAME_LAT = "latitud";
            public static final String COLUMN_NAME_LONG = "longitud";
        }

        public static abstract class ServicioEntry implements BaseColumns {
            public static final String TABLE_NAME = "servicio";
            public static final String COLUMN_NAME_ID = "id";
            public static final String COLUMN_NAME_NOMBRE = "nombre_servicio";
            public static final String COLUMN_NAME_FOTO = "foto";
            public static final String COLUMN_NAME_DESCRIPCION = "descripcion";

        }

        public static abstract class Sucursal_ServicioEntry implements BaseColumns {
            public static final String TABLE_NAME = "sucursal_servicio";
            public static final String COLUMN_NAME_ENTRY_ID_SUCURSAL = "id_sucursal";
            public static final String COLUMN_NAME_ENTRY_ID_SERVICIO = "id_servicio";

        }
        public static abstract class Servicio_CategoriaEntry implements BaseColumns {
            public static final String TABLE_NAME = "servicio_categoria";
            public static final String COLUMN_NAME_ENTRY_ID_SERVICIO = "id_servicio";
            public static final String COLUMN_NAME_NOMBRE_CATEGORIA = "nombre_categoria";

        }
        public static abstract class CategoriaEntry implements BaseColumns {
            public static final String TABLE_NAME = "servicio";
            public static final String COLUMN_NAME_ENTRY_ID = "id";
            public static final String COLUMN_NAME_NOMBRE = "nombre_servicio";
            public static final String COLUMN_NAME_FOTO = "foto";
            public static final String COLUMN_NAME_DESCRIPCION = "descripcion";

        }
        public static abstract class ItinerarioEntry implements BaseColumns {
            public static final String TABLE_NAME = "servicio";
            public static final String COLUMN_NAME_ENTRY_ID = "id";
            public static final String COLUMN_NAME_NOMBRE = "nombre_servicio";
            public static final String COLUMN_NAME_FOTO = "foto";
            public static final String COLUMN_NAME_DESCRIPCION = "descripcion";

        }
        public static abstract class OrdenEntry implements BaseColumns {
            public static final String TABLE_NAME = "servicio";
            public static final String COLUMN_NAME_ENTRY_ID = "id";
            public static final String COLUMN_NAME_NOMBRE = "nombre_servicio";
            public static final String COLUMN_NAME_FOTO = "foto";
            public static final String COLUMN_NAME_DESCRIPCION = "descripcion";

        }

        //TODO: Agregar más definiciones de las tablas acá.

    }


