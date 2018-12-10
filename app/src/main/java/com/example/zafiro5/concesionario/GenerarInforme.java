package com.example.zafiro5.concesionario;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import java.util.Calendar;


public class GenerarInforme extends AppCompatActivity implements CuadroDialogos.AcabadoDialogos {

    TextView txvMarca, txvModelo, txvTotal;
    ListView lsvListado;
    ArrayList<Extra> arrayExtras, arrayAux = new ArrayList<Extra>();

    String extras_mensaje;

    private int posicion_lista, i;

    private boolean[] arrayseleccionados;

    AdaptadorExtras adapter, adaptadorFinal;

    Coche coche;

    Double preciototal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_informe);

        final TextView txvMarca = (TextView)findViewById(R.id.txvMarca);
        final TextView txvModelo = (TextView)findViewById(R.id.txvModelo);
        TextView txvTotal = (TextView)findViewById(R.id.txvTotal);
        ListView lsvListado = (ListView)findViewById(R.id.lsvListado);

        //NOS TRAEMOS LA POSICIÓN DEL COCHE SELECCIONADO ANTERIORMENTE Y EL ARRAY DE LOS EXTRAS SELECCIONADOS.
        posicion_lista = getIntent().getIntExtra("idcoche", 0);
        arrayseleccionados = getIntent().getBooleanArrayExtra("arrayextras");

        //CREAMOS UN OBJETO DE LA CLASE TOOLBAR LLAMADO TOOLBAR.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //LE DAMOS A DICHO OBJETO UN TÍTULO.
        toolbar.setTitle("Resumen");

        //PONEMOS LA TOOLBAR EN EL SITIO DE LA TOOLBAR POR DEFECTO.
        setSupportActionBar(toolbar);

        //ACCEDEMOS A LA BASE DE DATOS PARA TRAERNOS LOS DATOS DEL COCHE SELECCIONADO.
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        coche = databaseAccess.busqueda_coche(posicion_lista);
        //COLOCAMOS LOS DATOS EN LOS TEXTVIEW.
        txvMarca.setText(coche.getMarca_coche());
        txvModelo.setText(coche.getModelo_coche());

        databaseAccess.close();
        DatabaseAccess databaseAcessExtra = DatabaseAccess.getInstance(this);
        databaseAcessExtra.open();
        //NOS TRAEMOS TODOS LOS ESTRAS Y LOS COLOCAMOS EN UN ARRAYLIST DE EXTRAS.
        arrayExtras = databaseAcessExtra.todos_los_extras();

        //CREAMOS UN NUEVO ADAPTADOR DE EXTRAS CON EL ARRAYLIST ANTERIOR.
        adapter = new AdaptadorExtras(this, arrayExtras);

        //CREAMOS UN ENTERO QUE SERÁ LA CANTIDAD DE EXTRAS QUE TENGAMOS EN NUESTRA BDD.
        int tamanio = adapter.getCount();

        //RECORREMOS EL ARRAY DE EXTRAS SELECCIONADOS EN LA ACTIVIDAD ANTERIOR.
        for(i = 0; i < tamanio; i++){
            //SI LA POSICIÓN ESTÁ SELECCIONADA (ES TRUE), NOS AÑADE DICHO EXTRA A UN ARRAYLIST AUXILIAR DE EXTRAS CREADO ANTERIORMENTE.
            if(arrayseleccionados[i] == true) {
                if(i == 0) {
                    arrayAux.add(databaseAcessExtra.busqueda_extra(1));
                } else {
                    arrayAux.add(databaseAcessExtra.busqueda_extra(i+1));
                }
            }
        }

        //CREAMOS UN ENTERO QUE RECOGERÁ POSTERIORMENTE EL PRECIO DEL COCHE Y A CONTINUACIÓN LE SUMAREMOS EL PRECIO CON LOS EXTRAS SELECCIONADOS.
        preciototal=coche.getPrecio_coche();

        //VARIABLE PARA QUE EL PRIMER NOMBRE DE EXTRA A LA HORA DE ENVIAR EL MENSAJE POR CORREO NO SEA NULO.
        boolean check = true;

        for(i = 0; i < arrayAux.size(); i++){
            //SUMAMOS EL PRECIO DE LOS EXTRAS SELECIONADOS AL PRECIO TOTAL.
            preciototal = preciototal + arrayAux.get(i).getPrecio_extra();
                if(check == true) {
                    //AÑADIMOS EL NOMBRE DE CADA EXTRA SELECCIONADO A UN STRING CREADO ANTERIORMENTE.
                    extras_mensaje = arrayAux.get(i).getNombre_extra();
                    check = false;
                } else { // CONCADENAMOS LOS NOMBRES DE LOS EXTRAS Y LOS SEPARAMOS CON COMAS.
                    extras_mensaje = extras_mensaje + ", " + arrayAux.get(i).getNombre_extra();
                }
        }

        //LE ASIGNAMOS EL PRECIO TOTAL AL TEXTVIEW DEL PRECIO.
        txvTotal.setText(preciototal.toString());

        //CREAMOS UN ADAPTADOR CON EL ADAPTADOR AUXILIAR QUE GUARDA LOS EXTRAS SELECCIONADOS.
        adaptadorFinal = new AdaptadorExtras(this, arrayAux);

        //CARGAMOS EL ADAPTADOR EN EL LISTVIEW
        lsvListado.setAdapter(adaptadorFinal);

        //CERRAMOS LA BDD
        databaseAcessExtra.close();

        //CREAMOS UN CONTEXTO SOBRE EL QUE SE VA A EJECUTAR EL CUADRO DE DIÁLOGO AL PULSAR EN EL FAB.
        final Context contexto = this;

        FloatingActionButton fab = findViewById(R.id.my_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            //ONCLICK PARA SABER CUANDO PULSAMOS EL FAB.
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Se enviará el presupuesto por correo.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                new CuadroDialogos(contexto, GenerarInforme.this);
            }
        });
    }

    //MÉTODO PARA CREAR EL CORREO A ENVIAR.
    @Override
    public void ResultadoCuadroDialogo(String nombre, String apellidos, String email, int telefono, String poblacion, String direccion) {
        String[] TO = {email};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, "");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Formulario del cliente con su pedido");

        emailIntent.putExtra(Intent.EXTRA_TEXT, "<-------- NOMBRE -------->" + "\n" + "\n" + nombre + " " + apellidos + "\n" + "\n" + "<-------- TELEFONO -------->" + "\n" + "\n" + telefono + "\n" + "\n" + "<-------- DIRECCION -------->" + "\n" + "\n" + direccion +
              "\n" + "\n" + "<-------- POBLACION -------->" + "\n" + "\n" + poblacion + "\n" + "\n" + "<-------- FECHA -------->" + "\n" + "\n" + Calendar.getInstance().getTime() + "\n" + "\n" + "<-------- NOMBRE DEL COCHE -------->" + " \n " + "\n" + coche.getMarca_coche() + " " + coche.getModelo_coche() + "\n" + "\n" +
                "<-------- EXTRAS -------->" + " \n " + " \n " + extras_mensaje + " \n " + " \n " + "<-------- PRECIO -------->" + " \n " + "\n" + preciototal + "€");

        startActivity(Intent.createChooser(emailIntent, "Formulario del cliente con su pedido"));
    }
}
