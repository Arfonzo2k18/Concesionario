package com.example.zafiro5.concesionario;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class GenerarInforme extends AppCompatActivity {

    TextView txvMarca, txvModelo;
    ListView lsvListado;

    ArrayList<Data> arrayExtras = new ArrayList<Data>();

    private int posicion_lista;

    AdaptadorExtras adapter;

    Coche coche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_informe);

        TextView txvMarca = (TextView)findViewById(R.id.txvMarca);
        TextView txvModelo = (TextView)findViewById(R.id.txvModelo);
        ListView lsvListado = (ListView)findViewById(R.id.lsvListado);

        posicion_lista = getIntent().getIntExtra("idcoche", 0);

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
        DatabaseAccess dabataseAcessExtra = DatabaseAccess.getInstance(this);
        dabataseAcessExtra.open();
        arrayExtras = databaseAccess.generar_informe(posicion_lista);
        adapter = new AdaptadorExtras(this,arrayExtras);
        lsvListado.setAdapter(adapter);
    }
}
