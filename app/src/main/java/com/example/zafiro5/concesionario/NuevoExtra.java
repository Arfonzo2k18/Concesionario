package com.example.zafiro5.concesionario;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NuevoExtra extends AppCompatActivity implements View.OnClickListener{

    private EditText edtNombre, edtPrecio, edtDescripcion;
    private Button btnCrear;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_extra);

        edtNombre = findViewById(R.id.edtNombre);
        edtPrecio = findViewById(R.id.edtPrecio);
        edtDescripcion = findViewById(R.id.edtDescripcion);
        Button btnCrear = (Button)findViewById(R.id.btnCrear);

        btnCrear.setOnClickListener(this);

        //CREAMOS UN OBJETO DE LA CLASE TOOLBAR LLAMADO TOOLBAR.
        Toolbar toolbar = findViewById(R.id.toolbar);

        //LE DAMOS A DICHO OBJETO UN TÍTULO.
        toolbar.setTitle("Nuevo Extra");

        //PONEMOS LA TOOLBAR EN EL SITIO DE LA TOOLBAR POR DEFECTO.
        setSupportActionBar(toolbar);

    }


    @Override
    public void onClick(View view) {
        if(view.getId()==findViewById(R.id.btnCrear).getId()){
            String nombre, descripcion;
            Double precio;

            nombre = edtNombre.getText().toString();
            descripcion = edtDescripcion.getText().toString();
            precio = Double.parseDouble(edtPrecio.getText().toString());

            if(edtPrecio.getText().toString().length() != 0) {
                precio = Double.parseDouble(edtPrecio.getText().toString());
            } else {
                precio = 0.0;
            }

            if(nombre.trim().length() != 0 && descripcion.trim().length() != 0 && precio != 0.0){
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
                databaseAccess.open();
                databaseAccess.crear_nuevo_extra(new Extra(nombre, precio, descripcion));
                databaseAccess.close();
                Toast toast1 = Toast.makeText(getApplicationContext(), "El extra se ha creado correctamente.", Toast.LENGTH_LONG);
                toast1.show();
                Intent vuelta = new Intent(getApplicationContext(), Principal.class);
                vuelta.putExtra("actividad", 2);
                startActivityForResult(vuelta, 2);
            } else { // SI NOS HEMOS DEJADO ALGÚN CAMPO SIN RELLENAR, APARECERÁ UN FLOAT ADVIRTIENDOLO Y NO PODREMOS CREAR EL EXTRA HASTA QUE NO ESTÉN LOS CAMPOS COMPLETOS.
                Snackbar.make(view, "Has dejado algún campo sin rellenar. Por favor, rellena todos los campos.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        }
    }
}
