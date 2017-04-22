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

package cl.udec.ingsoftware.proyecto_is.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import cl.udec.ingsoftware.proyecto_is.data.Sucursal;
import cl.udec.ingsoftware.proyecto_is.data.source.SucursalesDataSource;
import cl.udec.ingsoftware.proyecto_is.data.Categoria;
import cl.udec.ingsoftware.proyecto_is.data.source.local.PersistenceContract.SucursalEntry;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Concrete implementation of a data source as a db.
 */
public class LocalSucursalesDataSource implements SucursalesDataSource {

    private static LocalSucursalesDataSource INSTANCE;

    private DbHelper mDbHelper;

    // Prevent direct instantiation.
    private LocalSucursalesDataSource(@NonNull Context context) {
        checkNotNull(context);
        mDbHelper = new DbHelper(context);
    }

    public static LocalSucursalesDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LocalSucursalesDataSource(context);
        }
        return INSTANCE;
    }

    /**
     * Nota: {@link LoadSucursalCallback#onDataNotAvailable()} es llamado cuando la base de datos no existe
     * o la tabla esta vacia
     */
    @Override
    public void getSucursales(@NonNull LoadSucursalCallback callback) {
        List<Sucursal> sucursales = new ArrayList<Sucursal>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                SucursalEntry.COLUMN_NAME_ENTRY_ID,
                SucursalEntry.COLUMN_NAME_NOMBRE,
                SucursalEntry.COLUMN_NAME_SELLO,
                SucursalEntry.COLUMN_NAME_EMPRESA,
                SucursalEntry.COLUMN_NAME_COMUNA,
                SucursalEntry.COLUMN_NAME_LAT,
                SucursalEntry.COLUMN_NAME_LONG
        };

        Cursor c = db.query(
                SucursalEntry.TABLE_NAME, projection, null, null, null, null, null);
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                int id = Integer.parseInt(
                        c.getString(c.getColumnIndexOrThrow(SucursalEntry.COLUMN_NAME_ENTRY_ID)));
                String nombre = c.getString(c.getColumnIndexOrThrow(SucursalEntry.COLUMN_NAME_NOMBRE));
                int sello = Integer.parseInt(
                        c.getString(c.getColumnIndexOrThrow(SucursalEntry.COLUMN_NAME_SELLO))
                );
                String rut_empresa = c.getString(c.getColumnIndexOrThrow(SucursalEntry.COLUMN_NAME_EMPRESA));
                String comuna = c.getString(c.getColumnIndexOrThrow(SucursalEntry.COLUMN_NAME_COMUNA));
                double latitud = Double.parseDouble(
                        c.getString(c.getColumnIndexOrThrow(SucursalEntry.COLUMN_NAME_LAT))
                );
                double longitud = Double.parseDouble(
                        c.getString(c.getColumnIndexOrThrow(SucursalEntry.COLUMN_NAME_LONG))
                );
                Sucursal sucursal = new Sucursal(id,nombre,sello,rut_empresa,comuna,latitud,longitud);
                sucursales.add(sucursal);
            }
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (sucursales.isEmpty()) {
            // Cuando es nuevo o está vacio
            callback.onDataNotAvailable();
        } else {
            callback.onSucursalLoaded(sucursales);
        }
    }

    /**
     * Nota: {@link GetSucursalCallback#onDataNotAvailable()} es llamado cuando la {@link Sucursal} no se
     * encuentra.
     */
    @Override
    public void getSucursal(@NonNull int sucursalId, @NonNull GetSucursalCallback callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                SucursalEntry.COLUMN_NAME_ENTRY_ID,
                SucursalEntry.COLUMN_NAME_NOMBRE,
                SucursalEntry.COLUMN_NAME_SELLO,
                SucursalEntry.COLUMN_NAME_EMPRESA,
                SucursalEntry.COLUMN_NAME_COMUNA,
                SucursalEntry.COLUMN_NAME_LAT,
                SucursalEntry.COLUMN_NAME_LONG
        };

        String selection = SucursalEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = { Integer.toString(sucursalId) };

        Cursor c = db.query(
                SucursalEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        Sucursal sucursal = null;

        if (c != null && c.getCount() > 0) {
            int id = Integer.parseInt(
                    c.getString(c.getColumnIndexOrThrow(SucursalEntry.COLUMN_NAME_ENTRY_ID)));
            String nombre = c.getString(c.getColumnIndexOrThrow(SucursalEntry.COLUMN_NAME_NOMBRE));
            int sello = Integer.parseInt(
                    c.getString(c.getColumnIndexOrThrow(SucursalEntry.COLUMN_NAME_SELLO))
            );
            String rut_empresa = c.getString(c.getColumnIndexOrThrow(SucursalEntry.COLUMN_NAME_EMPRESA));
            String comuna = c.getString(c.getColumnIndexOrThrow(SucursalEntry.COLUMN_NAME_COMUNA));
            double latitud = Double.parseDouble(
                    c.getString(c.getColumnIndexOrThrow(SucursalEntry.COLUMN_NAME_LAT))
            );
            double longitud = Double.parseDouble(
                    c.getString(c.getColumnIndexOrThrow(SucursalEntry.COLUMN_NAME_LONG))
            );
            sucursal = new Sucursal(id,nombre,sello,rut_empresa,comuna,latitud,longitud);
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (sucursal != null) {
            callback.onSucursalLoaded(sucursal);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void saveSucursal(@NonNull Sucursal sucursal) {
        checkNotNull(sucursal);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SucursalEntry.COLUMN_NAME_ENTRY_ID,
                sucursal.getId());
        values.put(SucursalEntry.COLUMN_NAME_NOMBRE,
                sucursal.getNombre());
        values.put(SucursalEntry.COLUMN_NAME_SELLO,
                sucursal.getSello());
        values.put(SucursalEntry.COLUMN_NAME_EMPRESA,
                sucursal.getRutEmpresa());
        values.put(SucursalEntry.COLUMN_NAME_COMUNA,
                sucursal.getComuna());
        values.put(SucursalEntry.COLUMN_NAME_LAT,
                sucursal.getLatitud());
        values.put(SucursalEntry.COLUMN_NAME_LONG,
                sucursal.getLongitud());

        db.insert(SucursalEntry.TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void deleteAllSucursales() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        db.delete(SucursalEntry.TABLE_NAME, null, null);

        db.close();
    }

    //TODO: implementar filtrar sucursales en base a categorias
    @Override
    public void filtrarSucursales(@NonNull LoadSucursalFilterCallback callback, @NonNull List<Categoria> categorias) {

    }
}
