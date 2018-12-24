package com.example.alfon.concesionario.Actividades;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alfon.concesionario.Adaptadores.AdaptadorExtras;
import com.example.alfon.concesionario.BaseDeDatos.DatabaseAccess;
import com.example.alfon.concesionario.Clases.Coche;
import com.example.alfon.concesionario.Clases.Extra;
import com.example.alfon.concesionario.R;

import java.util.ArrayList;

public class SeleccionExtras extends AppCompatActivity {

    TextView txvMarca, txvModelo;

    ListView lstListado;

    ArrayList<Extra> arrayExtras = new ArrayList<Extra>();

    private int posicion_lista;

    Coche coche;

    Extra extra;

    AdaptadorExtras adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_extras);

        TextView txvMarca = (TextView)findViewById(R.id.txvMarca);
        TextView txvModelo = (TextView)findViewById(R.id.txvModelo);
        ListView lstListado = (ListView)findViewById(R.id.lstListado);

        // RECOJO LA POSICION DEL COCHE QUE SELECCIONÉ EN LA ACTIVIDAD PRINCIPAL
        posicion_lista = getIntent().getIntExtra("idcoche", 0);

        /*
        ABRO LA BASE DE DATOS Y HAGO UNA CONSULTA CON LA POSICIÓN GUARDADA DE LA ACTIVIDAD ANTERIOR
        Y DESPUÉS DE ESTO HACEMOS UNA CONSULTA EN LA BDD PARA RELLENAR LOS TEXTVIEW CON LOS
        DATOS DEL COCHE SELECCIONADO ANTERIORMENTE.
         */
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        coche = databaseAccess.busqueda_coche(posicion_lista);
        txvMarca.setText(coche.getMarca_coche());
        txvModelo.setText(coche.getModelo_coche());

        // CERRAMOS CONEXION CON LA BDD
        databaseAccess.close();
        //CREAMOS UN OBJETO DE LA CLASE TOOLBAR LLAMADO TOOLBAR.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        /*
        LE DAMOS A DICHO OBJETO UN TÍTULO. EN ESTE CASO LE PONEMOS EL ID
        DEL COCHE SELECCIONADO Y EL PRECIO DE DICHO COCHE.
         */
        toolbar.setTitle("ID Coche: " + posicion_lista + "              Precio: " + String.valueOf(coche.getPrecio_coche()) + "€");

        //PONEMOS LA TOOLBAR EN EL SITIO DE LA TOOLBAR POR DEFECTO.
        setSupportActionBar(toolbar);

        DatabaseAccess databaseAccessExtras = DatabaseAccess.getInstance(this);
        databaseAccessExtras.open();
        //RECOJEMOS EL TOTAL DE EXTRAS QUE HAY EN NUESTRA BASE DE DATOS.
        arrayExtras = databaseAccessExtras.todos_los_extras();

        adapter = new AdaptadorExtras(this, arrayExtras);
        lstListado.setAdapter(adapter);


        databaseAccessExtras.close();

        final boolean[] array = new boolean [adapter.getCount()];
        lstListado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (array[position] == false) {
                    view.setBackgroundColor(getResources().getColor(R.color.seleccionlista)); //Coloreamos el elemento marcado
                    array[position] = true;
                } else if (array[position] == true) {
                    view.setBackgroundColor(Color.alpha(0));  //Quitamos el color al elemento
                    array[position] = false;
                }
            }
        });

        FloatingActionButton fab = findViewById(R.id.my_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            //ONCLICK PARA SABER CUANDO PULSAMOS EL FAB.
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Generar presupuesto.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //contar_checkbox();
                Intent gen = new Intent(getApplicationContext(), GenerarInforme.class);
                gen.putExtra("idcoche", posicion_lista);
                gen.putExtra("arrayextras", array);
                startActivityForResult(gen, 3);
            }
        });

    }

}
