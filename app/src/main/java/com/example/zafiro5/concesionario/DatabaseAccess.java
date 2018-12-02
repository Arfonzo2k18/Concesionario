package com.example.zafiro5.concesionario;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
}

