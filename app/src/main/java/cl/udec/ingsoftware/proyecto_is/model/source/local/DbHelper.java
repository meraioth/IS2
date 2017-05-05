/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cl.udec.ingsoftware.proyecto_is.model.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "sucursal.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String BOOLEAN_TYPE = " INTEGER";

    private static final String INTEGER_TYPE = " INTEGER";

    private static final String VARCHAR_TYPE = " VARCHAR";

    private static final String DOUBLE_PRECISION_TYPE = " DOUBLE PRECISION";

    private static final String COMMA_SEP = ",";

    //TABLA DE SUCURSAL
    private static final String SQL_CREATE_ENTRIES_SUCURSAL =
            "CREATE TABLE " + PersistenceContract.SucursalEntry.TABLE_NAME + " (" +
                    PersistenceContract.SucursalEntry._ID + INTEGER_TYPE + " PRIMARY KEY," +
                    PersistenceContract.SucursalEntry.COLUMN_NAME_ENTRY_ID + INTEGER_TYPE + COMMA_SEP +
                    PersistenceContract.SucursalEntry.COLUMN_NAME_NOMBRE + VARCHAR_TYPE + "(50)" + COMMA_SEP +
                    PersistenceContract.SucursalEntry.COLUMN_NAME_SELLO + INTEGER_TYPE + COMMA_SEP +
                    PersistenceContract.SucursalEntry.COLUMN_NAME_EMPRESA + VARCHAR_TYPE + "(20)" + COMMA_SEP +
                    PersistenceContract.SucursalEntry.COLUMN_NAME_COMUNA + VARCHAR_TYPE + "(20)" + COMMA_SEP +
                    PersistenceContract.SucursalEntry.COLUMN_NAME_LAT + DOUBLE_PRECISION_TYPE + COMMA_SEP +
                    PersistenceContract.SucursalEntry.COLUMN_NAME_LONG + DOUBLE_PRECISION_TYPE +
            " )";


    //TODO: agregar más tablas como SQL_CREATE_ENTRIES acá.

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        //TODO: cuando existan más tablas, agregarlas con db.execSQL
        db.execSQL(SQL_CREATE_ENTRIES_SUCURSAL);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }
}
