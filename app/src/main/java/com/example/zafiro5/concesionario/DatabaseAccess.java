package com.example.zafiro5.concesionario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    ArrayList<Coche> todos_los_coches_nuevos()
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

    ArrayList<Coche> todos_los_coches_usados()
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

    ArrayList<Extra> todos_los_extras(){
        Cursor c;
        //Array donde se devuelven todos los libros
        ArrayList<Extra> arrayExtras = new ArrayList<Extra>();

        c = database.rawQuery("SELECT * FROM extras", null);

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {

                arrayExtras.add(new Extra(c.getInt(0),c.getString(1),c.getDouble(2)));

            } while(c.moveToNext());
        }
        //cerramos el cursor
        c.close();

        //devolvemos el array
        return arrayExtras;
    }

    ArrayList<Coche> crear_nuevo_coche(Coche nuevo_coche){
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

    Coche busqueda_coche(int codigo_coche){
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

    void borrar_coche(int codigo_coche){
        String[] args = new String[]{String.valueOf(codigo_coche)};
        database.execSQL("DELETE FROM coches WHERE id_coche=?", args);
    }

    Extra busqueda_extra(int codigo_extra){
        Cursor c;
        //Array donde se devuelven todos los libros
        Extra extra = new Extra();

        c = database.rawQuery("SELECT * FROM extras WHERE id_extra = " + codigo_extra, null);

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {

                extra = (new Extra(c.getInt(0),c.getString(1),c.getInt(2)));

            } while(c.moveToNext());
        }
        //cerramos el cursor
        c.close();

        //devolvemos el array
        return extra;
    }

    int total_extras() {
        Cursor c;
        int numero_extras = 0;
        //definimos la sentencia sql en una cadena
        String[] valores_recuperar = {"id_extra", "nombre", "precio"};
        //Ejecutamos la cadena
        c = database.query("extras", valores_recuperar, null, null, null, null, null, null);

        if (c != null) {
            numero_extras = c.getCount();
        }

        //cerramos el cursor y el SQLiteDatabase
        c.close();
        database.close();

        return numero_extras;
    }

    void insertar_extras(int cod_coche, int cod_extra){
        Cursor c;

        if(database != null){
            ContentValues valores = new ContentValues();
            valores.put("fk_id_coche", cod_coche);
            valores.put("fk_id_extra", cod_extra);
            //insertamos en la base de datos en la tabla libros
            database.insert("extras_en_coches", null, valores);
            database.close();
        }

    }

    void modificar_coche(int codigo_coche, String marca_coche, String modelo_coche, double precio_coche, String descripcion_coche){
        if(database != null) {
            ContentValues valores = new ContentValues();
            valores.put("marca", marca_coche);
            valores.put("modelo", modelo_coche);
            valores.put("precio", precio_coche);
            valores.put("descripcion", descripcion_coche);
            database.update("coches", valores, "id_coche = " + codigo_coche, null);

        }
    }

    ArrayList<Extra> generar_informe(int codigo_coche){

        ArrayList<Extra> arrayExtras = new ArrayList<Extra>();

        Cursor c;

        c = database.rawQuery("SELECT id_extra, nombre FROM extras\n" +
                "        INNER JOIN extras_en_coches ON fk_id_extra = id_extra\n" +
                "        INNER JOIN coches ON id_coche = fk_id_coche\n" +
                "        where id_coche = " + codigo_coche, null);

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {

                arrayExtras.add(new Extra(c.getInt(0),c.getString(1),c.getDouble(2)));

            } while(c.moveToNext());
        }
        //cerramos el cursor
        c.close();

        return arrayExtras;
    }

}

