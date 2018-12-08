package com.example.zafiro5.concesionario;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.pdf.PrintedPdfDocument;
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

import java.io.FileOutputStream;
import java.util.ArrayList;


public class GenerarInforme extends AppCompatActivity {

    TextView txvMarca, txvModelo, txvTotal;
    ListView lsvListado;
    ArrayList<Extra> arrayExtras, arrayAux = new ArrayList<Extra>();

    private int posicion_lista, i;

    private boolean[] arrayseleccionados;

    AdaptadorExtras adapter, adaptadorFinal;

    Coche coche;

    private final static String NOMBRE_DIRECTORIO = "MiPdf";
    private final static String NOMBRE_DOCUMENTO = "prueba.pdf";
    private final static String ETIQUETA_ERROR = "ERROR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_informe);

        final TextView txvMarca = (TextView)findViewById(R.id.txvMarca);
        final TextView txvModelo = (TextView)findViewById(R.id.txvModelo);
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

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            }
        }

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

        FloatingActionButton fab = findViewById(R.id.my_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            //ONCLICK PARA SABER CUANDO PULSAMOS EL FAB.
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Generar PDF y enviar correo con presupuesto.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if(!checkWritePermission()) {
                    generarpdf();
                }
            }
        });
    }

    public void generarpdf() {

    }

    // MÉTODO PARA COMPROBAR LOS PERMISOS DE LA CÁMARA.
    private boolean checkWritePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para la camara!.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
            return false;
        } else {
            Log.i("Mensaje", "Tienes permiso para usar la camara.");
            return true;
        }
    }
}
