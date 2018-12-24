package com.example.alfon.concesionario.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.alfon.concesionario.BaseDeDatos.DatabaseAccess;
import com.example.alfon.concesionario.Clases.Extra;
import com.example.alfon.concesionario.R;

public class Modificar_Extras extends AppCompatActivity implements View.OnClickListener{

    EditText edtNombre, edtPrecio, edtDescripcion;
    Button btnGuardar;

    Extra extra;

   int posicion_lista = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_extras);

        EditText edtNombre = (EditText)findViewById(R.id.edtNombre);
        EditText edtPrecio = (EditText)findViewById(R.id.edtPrecio);
        EditText edtDescripcion = (EditText)findViewById(R.id.edtDescripcion);
        Button btnGuardar = (Button)findViewById(R.id.btnGuardar);

        posicion_lista = getIntent().getIntExtra("idextra", 0);


        btnGuardar.setEnabled(false);
        edtNombre.setEnabled(false);
        edtPrecio.setEnabled(false);
        edtDescripcion.setEnabled(false);

        btnGuardar.setOnClickListener(this);

        //CREAMOS UN OBJETO DE LA CLASE TOOLBAR LLAMADO TOOLBAR.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //LE DAMOS A DICHO OBJETO UN TÍTULO.
        toolbar.setTitle("Modificar Extra");

        //PONEMOS LA TOOLBAR EN EL SITIO DE LA TOOLBAR POR DEFECTO.
        setSupportActionBar(toolbar);

        rellenar_datos();
    }

    //MÉTODO PARA CARGAR EL MENÚ EN LA ACTIVIDAD.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_modificar_extra, menu);
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
                eliminar_extra();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //MÉTODO PARA ACTIVAR LOS EDITTEXT Y EL BOTÓN DE GUARDAR CAMBIOS.
    public void activar_campos(){
        Button btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setEnabled(true);
        EditText edtNombre = findViewById(R.id.edtNombre);
        edtNombre.setEnabled(true);
        EditText edtPrecio = findViewById(R.id.edtPrecio);
        edtPrecio.setEnabled(true);
        EditText edtDescripcion = findViewById(R.id.edtDescripcion);
        edtDescripcion.setEnabled(true);
    }

    //MÉTODO QUE RELLENA LOS CAMPOS CON LOS DATOS DEL EXTRA SELECCIONADO.
    public void rellenar_datos(){
        EditText edtNombre = (EditText)findViewById(R.id.edtNombre);
        EditText edtPrecio = (EditText)findViewById(R.id.edtPrecio);
        EditText edtDescripcion = (EditText)findViewById(R.id.edtDescripcion);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        extra = databaseAccess.busqueda_extra(posicion_lista);

        edtNombre.setText(extra.getNombre_extra());
        edtPrecio.setText(String.valueOf(extra.getPrecio_extra()));
        edtDescripcion.setText(extra.getDescripcion_extra());
        databaseAccess.close();
    }

    //MÉTODO QUE ELIMINA EL EXTRA SELECCIONADO.
    public void eliminar_extra(){
        final int posicion_lista = getIntent().getIntExtra("idextra", 0);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        databaseAccess.borrar_extra(posicion_lista);
        databaseAccess.close();
        Intent vuelta = new Intent(getApplicationContext(), Principal.class);
        vuelta.putExtra("actividad", 2);
        startActivityForResult(vuelta, 2);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==findViewById(R.id.btnGuardar).getId()){
            modificar_datos();
        }
    }

    //MÉTODO PARA MODIFICAR LOS DATOS EN LA BDD.
    public void modificar_datos(){
        EditText edtNombre = (EditText)findViewById(R.id.edtNombre);
        EditText edtPrecio = (EditText)findViewById(R.id.edtPrecio);
        EditText edtDescripcion = (EditText)findViewById(R.id.edtDescripcion);

        String nombre = edtNombre.getText().toString();
        Double precio = Double.parseDouble(edtPrecio.getText().toString());
        String descripcion = edtDescripcion.getText().toString();

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        databaseAccess.modificar_extra(posicion_lista, nombre, precio, descripcion);
        databaseAccess.close();
        Intent vuelta = new Intent(getApplicationContext(), Principal.class);
        vuelta.putExtra("actividad", 2);
        startActivityForResult(vuelta, 2);
    }
}