package com.example.zafiro5.concesionario;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class GenerarInforme extends AppCompatActivity {

    TextView txvMarca, txvModelo, txvTotal;
    ListView lsvListado;
    ArrayList<Extra> arrayExtras, arrayAux = new ArrayList<Extra>();

    private int posicion_lista, i;

    private boolean[] arrayseleccionados;

    AdaptadorExtras adapter, adaptadorFinal;

    Coche coche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_informe);

        TextView txvMarca = (TextView)findViewById(R.id.txvMarca);
        TextView txvModelo = (TextView)findViewById(R.id.txvModelo);
        TextView txvTotal = (TextView)findViewById(R.id.txvTotal);
        ListView lsvListado = (ListView)findViewById(R.id.lsvListado);

        posicion_lista = getIntent().getIntExtra("idcoche", 0);
        arrayseleccionados = getIntent().getBooleanArrayExtra("arrayextras");

        //CREAMOS UN OBJETO DE LA CLASE TOOLBAR LLAMADO TOOLBAR.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //LE DAMOS A DICHO OBJETO UN TÍTULO.
        toolbar.setTitle("Resumen");

        //PONEMOS LA TOOLBAR EN EL SITIO DE LA TOOLBAR POR DEFECTO.
        setSupportActionBar(toolbar);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        coche = databaseAccess.busqueda_coche(posicion_lista);
        txvMarca.setText(coche.getMarca_coche());
        txvModelo.setText(coche.getModelo_coche());

        databaseAccess.close();
        DatabaseAccess databaseAcessExtra = DatabaseAccess.getInstance(this);
        databaseAcessExtra.open();

        arrayExtras = databaseAcessExtra.todos_los_extras();

        adapter = new AdaptadorExtras(this, arrayExtras);

        int tamanio = adapter.getCount();

        for(i = 0; i < tamanio; i++){
            if(arrayseleccionados[i] == true) {
                if(i == 0) {
                    arrayAux.add(databaseAcessExtra.busqueda_extra(1));
                } else {
                    arrayAux.add(databaseAcessExtra.busqueda_extra(i+1));
                }
            }
        }

        Double preciototal=coche.getPrecio_coche();

        for(i = 0; i < arrayAux.size(); i++){
            preciototal = preciototal + arrayAux.get(i).getPrecio_extra();
        }

        txvTotal.setText(preciototal.toString());

        adaptadorFinal = new AdaptadorExtras(this, arrayAux);

        lsvListado.setAdapter(adaptadorFinal);

        databaseAcessExtra.close();

       /* arrayExtras = databaseAccess.generar_informe(posicion_lista);
        adapter = new AdaptadorExtras(this,arrayExtras);
        lsvListado.setAdapter(adapter); */
    }
}
