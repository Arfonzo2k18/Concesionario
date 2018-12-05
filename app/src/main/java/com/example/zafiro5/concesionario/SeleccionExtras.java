package com.example.zafiro5.concesionario;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SeleccionExtras extends AppCompatActivity {

    TextView txvMarca, txvModelo;

    LinearLayout lytVertical;

    CheckBox cb;

    private int posicion_lista;

    Coche coche;

    Extra extra;

    ArrayList<Extra> arrayAux = new ArrayList<Extra>();

    private int totalextras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_extras);

        TextView txvMarca = (TextView)findViewById(R.id.txvMarca);
        TextView txvModelo = (TextView)findViewById(R.id.txvModelo);

        // CREO UN LAYOUT VERTICAL
        LinearLayout lytVertical = (LinearLayout) findViewById(R.id.lytVertical);

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

        //CREAMOS UN OBJETO DE LA CLASE TOOLBAR LLAMADO TOOLBAR.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        /*
        LE DAMOS A DICHO OBJETO UN TÍTULO. EN ESTE CASO LE PONEMOS EL ID
        DEL COCHE SELECCIONADO Y EL PRECIO DE DICHO COCHE.
         */
        toolbar.setTitle("ID Coche: " + posicion_lista + "              Precio: " + String.valueOf(coche.getPrecio_coche()) + "€");

        //PONEMOS LA TOOLBAR EN EL SITIO DE LA TOOLBAR POR DEFECTO.
        setSupportActionBar(toolbar);

        //RECOJEMOS EL TOTAL DE EXTRAS QUE HAY EN NUESTRA BASE DE DATOS.
        totalextras = databaseAccess.total_extras();

        // CREAMOS UN CHECKBOX POR CADA EXTRA QUE HAYA EN NUESTRA BDD
        for(int i=1; i<=totalextras;i++){

            DatabaseAccess dabataseAcessExtra = DatabaseAccess.getInstance(this);
            dabataseAcessExtra.open();
            extra = dabataseAcessExtra.busqueda_extra(i);
            cb = new CheckBox(this);

            cb.setId(i);
            cb.setText(extra.getNombre_extra() + "     " + String.valueOf(extra.getPrecio_extra()) + "€");

            lytVertical.addView(cb);
            dabataseAcessExtra.close();
        }
        // CERRAMOS CONEXION CON LA BDD
        databaseAccess.close();

        FloatingActionButton fab = findViewById(R.id.my_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            //ONCLICK PARA SABER CUANDO PULSAMOS EL FAB.
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Generar presupuesto.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        contar_checkbox();
            }
        });

    }

    public void contar_checkbox(){
        for(int i=1; i<=totalextras;i++) {
            if (cb.getId() == i) {
                if(cb.isChecked()) {
                    DatabaseAccess databaseaccessCheckbox = DatabaseAccess.getInstance(this);
                    databaseaccessCheckbox.open();
                    databaseaccessCheckbox.insertar_extras(posicion_lista, i);
                    databaseaccessCheckbox.close();
                }
            }
        }
    }

   /* private View.OnClickListener ckListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            boolean checked = ((CheckBox) view).isChecked();
            if(checked){
                arrayAux.add(extra);
            } else {
                arrayAux.remove(new Extra());
            }
        }
    };*/

}
