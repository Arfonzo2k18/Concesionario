package com.example.alfon.concesionario.BaseDeDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.alfon.concesionario.Clases.Coche;
import com.example.alfon.concesionario.Clases.Extra;

import java.sql.Blob;
import java.util.ArrayList;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Leer las provincias de la base de datos.
     *
     * @return a List of quotes
     */
    /*Devuelve todas las provincias

     */
    //metodo que devuelve un cursor con todos los libros de la tabla libros
    public ArrayList<Coche> todos_los_coches_nuevos()
    {
        Cursor c;
        //Array donde se devuelven todos los libros
        ArrayList<Coche> arrayCochesNuevos = new ArrayList<Coche>();

        c = database.rawQuery("SELECT id_coche, marca, modelo, imagen, precio, descripcion, nuevo FROM coches WHERE nuevo = 1", null);

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {

                arrayCochesNuevos.add(new Coche(c.getInt(0),c.getString(1),c.getString(2),c.getBlob(3),c.getDouble(4),c.getString(5),c.getInt(6)));

            } while(c.moveToNext());
        }
        //cerramos el cursor
        c.close();

        //devolvemos el array
        return arrayCochesNuevos;
    }

    public ArrayList<Coche> todos_los_coches_usados()
    {
        Cursor c;
        //Array donde se devuelven todos los libros
        ArrayList<Coche> arrayCochesUsados = new ArrayList<Coche>();

        c = database.rawQuery(" SELECT id_coche, marca, modelo, imagen, precio, descripcion, nuevo FROM coches WHERE nuevo = 0", null);

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {

                arrayCochesUsados.add(new Coche(c.getInt(0),c.getString(1),c.getString(2),c.getBlob(3),c.getDouble(4),c.getString(5),c.getInt(6)));

            } while(c.moveToNext());
        }
        //cerramos el cursor
        c.close();

        //devolvemos el array
        return arrayCochesUsados;
    }

    public ArrayList<Extra> todos_los_extras(){
        Cursor c;
        //Array donde se devuelven todos los libros
        ArrayList<Extra> arrayExtras = new ArrayList<Extra>();

        c = database.rawQuery("SELECT * FROM extras", null);

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {

                arrayExtras.add(new Extra(c.getInt(0),c.getString(1),c.getDouble(2), c.getString(3)));

            } while(c.moveToNext());
        }
        //cerramos el cursor
        c.close();

        //devolvemos el array
        return arrayExtras;
    }

    public ArrayList<Coche> crear_nuevo_coche(Coche nuevo_coche){
        ArrayList<Coche> arrayCoches = new ArrayList<>();

        if(database != null){
            ContentValues valores = new ContentValues();
            valores.put("marca", nuevo_coche.getMarca_coche());
            valores.put("modelo", nuevo_coche.getModelo_coche());
            valores.put("imagen", nuevo_coche.getImagen_coche());
            valores.put("precio", nuevo_coche.getPrecio_coche());
            valores.put("descripcion", nuevo_coche.getDescripcion_coche());
            valores.put("nuevo", nuevo_coche.getCoche_usado());
            //insertamos en la base de datos en la tabla libros
            database.insert("coches", null, valores);
            database.close();
        }

        return arrayCoches;
    }

    public ArrayList<Extra> crear_nuevo_extra(Extra nuevo_extra){
        ArrayList<Extra> arrayExtras = new ArrayList<>();

        if(database != null){
            ContentValues valores = new ContentValues();
            valores.put("nombre", nuevo_extra.getNombre_extra());
            valores.put("precio", nuevo_extra.getPrecio_extra());
            valores.put("descripcion", nuevo_extra.getDescripcion_extra());
            //insertamos en la base de datos en la tabla libros
            database.insert("extras", null, valores);
            database.close();
        }

        return arrayExtras;
    }

    public Coche busqueda_coche(int codigo_coche){
        Cursor c;
        //Array donde se devuelven todos los libros
        Coche coche = new Coche();

        c = database.rawQuery("SELECT marca, modelo, imagen, precio, descripcion, nuevo FROM coches WHERE id_coche = " + codigo_coche, null);

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {

                coche = (new Coche(c.getString(0),c.getString(1),c.getBlob(2),c.getDouble(3),c.getString(4),c.getInt(5)));

            } while(c.moveToNext());
        }
        //cerramos el cursor
        c.close();

        //devolvemos el array
        return coche;
    }

    public void borrar_extra(int codigo_extra){
        String[] args = new String[]{String.valueOf(codigo_extra)};
        database.execSQL("DELETE FROM extras WHERE id_extra=?", args);
    }

    public void borrar_coche(int codigo_coche){
        String[] args = new String[]{String.valueOf(codigo_coche)};
        database.execSQL("DELETE FROM coches WHERE id_coche=?", args);
    }

    public Extra busqueda_extra(int codigo_extra){
        Cursor c;
        //Array donde se devuelven todos los libros
        Extra extra = new Extra();

        c = database.rawQuery("SELECT * FROM extras WHERE id_extra = " + codigo_extra, null);

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {

                extra = (new Extra(c.getInt(0),c.getString(1),c.getDouble(2),c.getString(3)));

            } while(c.moveToNext());
        }
        //cerramos el cursor
        c.close();

        //devolvemos el array
        return extra;
    }

    public void modificar_coche(int codigo_coche, String marca_coche, String modelo_coche, double precio_coche, String descripcion_coche){
        if(database != null) {
            ContentValues valores = new ContentValues();
            valores.put("marca", marca_coche);
            valores.put("modelo", modelo_coche);
            valores.put("precio", precio_coche);
            valores.put("descripcion", descripcion_coche);
            database.update("coches", valores, "id_coche = " + codigo_coche, null);

        }
    }

    public void modificar_extra(int codigo_extra, String nombre_extra, double precio_extra, String descripcion_extra){
        if(database != null) {
            ContentValues valores = new ContentValues();
            valores.put("nombre", nombre_extra);
            valores.put("precio", precio_extra);
            valores.put("descripcion", descripcion_extra);
            database.update("extras", valores, "id_extra = " + codigo_extra, null);

        }
    }

}

