package com.example.alfon.concesionario.Actividades;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.alfon.concesionario.Adaptadores.AdaptadorExtras;
import com.example.alfon.concesionario.BaseDeDatos.DatabaseAccess;
import com.example.alfon.concesionario.Clases.Coche;
import com.example.alfon.concesionario.Clases.Extra;
import com.example.alfon.concesionario.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class Modificar_Coches extends AppCompatActivity implements View.OnClickListener {

    ImageView imvImagen;
    EditText edtMarca;
    EditText edtModelo;
    EditText edtPrecio;
    EditText edtDescripcion;
    Button btnGuardar;

    Coche coche;

    ArrayList<Extra> arrayExtras = new ArrayList<>();

    private int codigocoche;
    private int posicion_lista;

    AdaptadorExtras adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_coches);

        Button btnGuardar = (Button)findViewById(R.id.btnGuardar);
        ImageView imvImagen = (ImageView)findViewById(R.id.imvImagen);
        EditText edtMarca = (EditText)findViewById(R.id.edtMarca);
        EditText edtModelo = (EditText)findViewById(R.id.edtModelo);
        EditText edtPrecio = (EditText)findViewById(R.id.edtPrecio);
        EditText edtDescripcion = (EditText)findViewById(R.id.edtDescripcion);

        btnGuardar.setOnClickListener(this);

        rellenar_datos();

        codigocoche = getIntent().getIntExtra("esnuevo", 5);

        btnGuardar.setEnabled(false);
        edtMarca.setEnabled(false);
        edtModelo.setEnabled(false);
        edtPrecio.setEnabled(false);
        edtDescripcion.setEnabled(false);

        //CREAMOS UN OBJETO DE LA CLASE TOOLBAR LLAMADO TOOLBAR.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //LE DAMOS A DICHO OBJETO UN TÍTULO.
        toolbar.setTitle("Modificar Vehículo");

        //PONEMOS LA TOOLBAR EN EL SITIO DE LA TOOLBAR POR DEFECTO.
        setSupportActionBar(toolbar);

    }

    //MÉTODO PARA CARGAR EL MENÚ EN LA ACTIVIDAD.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_modificar, menu);
        return true;
    }

    //MÉTODO PARA SABER QUE SE PULSA EN EL MENÚ.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.menu_modificar_activar:
                Log.i("Modificar...", "Se han activado los campos de texto.");
                activar_campos();
                break;
            case R.id.menu_modificar_eliminar:
                Log.i("Eliminar", "Has pulsado eliminar.");
                eliminar_coche();
                break;
            case R.id.menu_modificar_presupuesto:
                Log.i("Crear Presupuesto", "Has pulsado Crear presupuesto");

                if(codigocoche == 0) {
                    Intent selecExtras = new Intent(getApplicationContext(), SeleccionExtras.class);
                    selecExtras.putExtra("idcoche", posicion_lista);
                    startActivityForResult(selecExtras, 1);
                } else {
                    Intent generarInforme = new Intent(getApplicationContext(), GenerarInforme.class);
                    generarInforme.putExtra("idcoche", posicion_lista);

                    DatabaseAccess databaseAcessExtra = DatabaseAccess.getInstance(this);
                    databaseAcessExtra.open();
                    arrayExtras = databaseAcessExtra.todos_los_extras();
                    adapter = new AdaptadorExtras(this, arrayExtras);
                    int tamanio = adapter.getCount();
                    boolean[] arraybooleanos = new boolean[tamanio];
                    for(int i = 0; i < tamanio; i++)
                        arraybooleanos[i] = false;
                    databaseAcessExtra.close();
                    generarInforme.putExtra("arrayextras", arraybooleanos);
                    startActivityForResult(generarInforme, 1);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //MÉTODO PARA ACTIVAR LOS EDITTEXT Y EL BOTÓN DE GUARDADO.
    public void activar_campos(){
        Button btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setEnabled(true);
        EditText edtMarca = findViewById(R.id.edtMarca);
        edtMarca.setEnabled(true);
        EditText edtModelo = findViewById(R.id.edtModelo);
        edtModelo.setEnabled(true);
        EditText edtPrecio = findViewById(R.id.edtPrecio);
        edtPrecio.setEnabled(true);
        EditText edtDescripcion = findViewById(R.id.edtDescripcion);
        edtDescripcion.setEnabled(true);
    }

    //MÉTODO QUE RELLENA LOS CAMPOS CON LOS DATOS DEL COCHE SELECCIONADO.
    public void rellenar_datos(){
        ImageView imvImagen = (ImageView)findViewById(R.id.imvImagen);
        EditText edtMarca = (EditText)findViewById(R.id.edtMarca);
        EditText edtModelo = (EditText)findViewById(R.id.edtModelo);
        EditText edtPrecio = (EditText)findViewById(R.id.edtPrecio);
        EditText edtDescripcion = (EditText)findViewById(R.id.edtDescripcion);

        posicion_lista = getIntent().getIntExtra("idcoche", 0);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        coche = databaseAccess.busqueda_coche(posicion_lista);

        edtMarca.setText(coche.getMarca_coche());
        edtModelo.setText(coche.getModelo_coche());

        ByteArrayInputStream imageStream = new ByteArrayInputStream(coche.getImagen_coche());
        Bitmap imagen= BitmapFactory.decodeStream(imageStream);
        imvImagen.setImageBitmap(imagen);

        edtPrecio.setText(String.valueOf(coche.getPrecio_coche()));
        edtDescripcion.setText(coche.getDescripcion_coche());
        databaseAccess.close();
    }

    //MÉTODO PARA ELIMINAR EL COCHE SELECCIONADO.
    public void eliminar_coche(){
        posicion_lista = getIntent().getIntExtra("idcoche", 0);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        databaseAccess.borrar_coche(posicion_lista);
        databaseAccess.close();
        Intent vuelta = new Intent(getApplicationContext(), Principal.class);
        startActivityForResult(vuelta, 3);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==findViewById(R.id.btnGuardar).getId()){
            modificar_datos();
        }
    }

    //MÉTODO QUE GUARDA LOS DATOS UNA VEZ HAN SIDO MODIFICADOS.
    public void modificar_datos(){
        EditText edtMarca = (EditText)findViewById(R.id.edtMarca);
        EditText edtModelo = (EditText)findViewById(R.id.edtModelo);
        EditText edtPrecio = (EditText)findViewById(R.id.edtPrecio);
        EditText edtDescripcion = (EditText)findViewById(R.id.edtDescripcion);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        String marca = edtMarca.getText().toString();
        String modelo = edtModelo.getText().toString();
        Double precio = Double.parseDouble(edtPrecio.getText().toString());
        String descripcion = edtDescripcion.getText().toString();

        databaseAccess.open();
        databaseAccess.modificar_coche(posicion_lista, marca, modelo, precio, descripcion);

        Intent vuelta = new Intent(getApplicationContext(), Principal.class);
        if(databaseAccess.busqueda_coche(posicion_lista).getCoche_usado() == 1) {
            vuelta.putExtra("actividad", 1);
            databaseAccess.close();
            startActivityForResult(vuelta, 4);
        } else {
            vuelta.putExtra("actividad", 0);
            databaseAccess.close();
            startActivityForResult(vuelta, 3);
        }

    }
}
