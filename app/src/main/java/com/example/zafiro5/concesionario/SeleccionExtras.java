package com.example.zafiro5.concesionario;

import android.os.Bundle;
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

    private int posicion_lista;

    Coche coche;

    Extra extra;

    ArrayList<Extra> arrayAux = new ArrayList<Extra>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_extras);

        TextView txvMarca = (TextView)findViewById(R.id.txvMarca);
        TextView txvModelo = (TextView)findViewById(R.id.txvModelo);

        LinearLayout lytVertical = (LinearLayout) findViewById(R.id.lytVertical);

        posicion_lista = getIntent().getIntExtra("idcoche", 0);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        coche = databaseAccess.busqueda_coche(posicion_lista);
        txvMarca.setText(coche.getMarca_coche());
        txvModelo.setText(coche.getModelo_coche());

        //CREAMOS UN OBJETO DE LA CLASE TOOLBAR LLAMADO TOOLBAR.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //LE DAMOS A DICHO OBJETO UN TÍTULO.
        toolbar.setTitle("ID Coche: " + posicion_lista + "              Precio: " + String.valueOf(coche.getPrecio_coche()));
        databaseAccess.close();
        //PONEMOS LA TOOLBAR EN EL SITIO DE LA TOOLBAR POR DEFECTO.
        setSupportActionBar(toolbar);

        DatabaseAccess databaseAccesstotal = DatabaseAccess.getInstance(this);
        databaseAccesstotal.open();
        int totalextras = databaseAccesstotal.total_extras();

        for(int i=1; i<=totalextras;i++){
            DatabaseAccess dabataseAcessExtra = DatabaseAccess.getInstance(this);
            dabataseAcessExtra.open();
            extra = dabataseAcessExtra.busqueda_extra(i);
            CheckBox cb = new CheckBox(this);
            cb.setText(extra.getNombre_extra() + "      " + String.valueOf(extra.getPrecio_extra()) + "€");
            cb.setOnClickListener(ckListener);
            lytVertical.addView(cb);
        }

    }

    private View.OnClickListener ckListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            boolean checked = ((CheckBox) view).isChecked();
            if(checked){
                arrayAux.add(extra);
            } else {
                arrayAux.remove(new Extra());
            }
        }
    };

}
