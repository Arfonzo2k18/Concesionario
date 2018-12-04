package com.example.zafiro5.concesionario;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;

public class Modificar_Coches extends AppCompatActivity {

    ImageView imvImagen;
    EditText edtMarca;
    EditText edtModelo;
    EditText edtPrecio;
    EditText edtDescripcion;
    Coche coche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_coches);

        ImageView imvImagen = (ImageView)findViewById(R.id.imvImagen);
        EditText edtMarca = (EditText)findViewById(R.id.edtMarca);
        EditText edtModelo = (EditText)findViewById(R.id.edtModelo);
        EditText edtPrecio = (EditText)findViewById(R.id.edtPrecio);
        EditText edtDescripcion = (EditText)findViewById(R.id.edtDescripcion);

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



}
