package com.example.zafiro5.concesionario;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;

import static android.view.View.INVISIBLE;

public class Principal extends AppCompatActivity implements OnItemClickListener {

    ArrayList<Coche> arrayCoches = new ArrayList<Coche>();

    ArrayList<Extra> arrayExtras = new ArrayList<Extra>();

    private ListView lsvListado;

    private AdaptadorCoches adapter;

    private AdaptadorExtras adapterExtras;

    /*
       VARIABLE QUE INDICA QUE HAY CARGADO EN EL LISTVIEW.
       TAMBIÉN SE UTILIZA PARA DESHABILITAR LOS ITEMS DEL MENÚ.
    */
    private int wherethefuckiam = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //CREAMOS UN OBJETO DE LA CLASE TOOLBAR LLAMADO TOOLBAR.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //LE DAMOS A DICHO OBJETO UN TÍTULO.
        toolbar.setTitle("Concesionario");

        //PONEMOS LA TOOLBAR EN EL SITIO DE LA TOOLBAR POR DEFECTO.
        setSupportActionBar(toolbar);

        //INICIALIZAMOS EL LISTVIEW.
        this.lsvListado = (ListView) findViewById(R.id.lsvListado);

        //CONTROLAMOS DE LA ACTIVIDAD QUE VENIMOS PARA CARGAR LOS DATOS EN EL LISTVIEW
        wherethefuckiam = getIntent().getIntExtra("actividad", 4);
        if(wherethefuckiam == 2){
            actualizar_extras();
        } else if(wherethefuckiam == 0){
           actualizar_coches_usados();
        } else if(wherethefuckiam == 1){
            actualizar_coches_nuevos();
        }
        lsvListado.setOnItemClickListener(this);

        //CREAMOS EL BOTON QUE POSTERIORMENTE NOS LLEVA A LA ACTIVIDAD "NUEVO COCHE O NUEVO EXTRA".
        FloatingActionButton fab = findViewById(R.id.my_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            //ONCLICK PARA SABER CUANDO PULSAMOS EL FAB.
            @Override
            public void onClick(View view) {
                //ABRIR ACTIVIDAD "NUEVO COCHE".
                if(wherethefuckiam == 0) {
                    Snackbar.make(view, "Crear nuevo coche.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Intent Nuevo_Coche = new Intent(getApplicationContext(), NuevoCoche.class);
                    startActivityForResult(Nuevo_Coche, 1);
                } else if (wherethefuckiam == 1){
                    Snackbar.make(view, "Crear nuevo coche.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Intent Nuevo_Coche = new Intent(getApplicationContext(), NuevoCoche.class);
                    startActivityForResult(Nuevo_Coche, 1);
                } else if (wherethefuckiam == 2){
                    Snackbar.make(view, "Crear nuevo extra.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Intent Nuevo_Extra = new Intent(getApplicationContext(), NuevoExtra.class);
                    startActivityForResult(Nuevo_Extra, 2);
                }

            }
        });
    }

    //MÉTODO PARA CARGAR EL MENÚ EN LA ACTIVIDAD.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    //MÉTODO PARA SABER QUE SE PULSA EN EL MENÚ.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.menu_coches_nuevos:
                Log.i("Coches Nuevos", "Has pulsado Coches Nuevos");
                actualizar_coches_nuevos();
                wherethefuckiam = actualizar_coches_nuevos();
                wherethefuckiam = 0;
                break;
            case R.id.menu_coches_usados:
                Log.i("Coches Usados", "Has pulsado Coches Usados");
                actualizar_coches_usados();
                wherethefuckiam = actualizar_coches_usados();
                wherethefuckiam = 1;
                break;
            case R.id.menu_extras:
                Log.i("Extras", "Has pulsado Extras");
                actualizar_extras();
                wherethefuckiam = actualizar_extras();
                wherethefuckiam = 2;
                break;
            case R.id.menu_conocenos:
                Log.i("Extras", "Has pulsado Extras");
                Intent Conocenos = new Intent(getApplicationContext(), Mapas.class);
                startActivityForResult(Conocenos, 2);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //MÉTODO PARA SABER EN QUÉ ELEMENTO DEL LISTVIEW HEMOS PULSADO.
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(wherethefuckiam == 2) {
            Intent actModificar = new Intent(getApplicationContext(), Modificar_Extras.class);
            actModificar.putExtra("idextra", arrayExtras.get(position).getCod_extra());
            startActivityForResult(actModificar, 2);
        } else if (wherethefuckiam == 1){
            Intent actModificar = new Intent(getApplicationContext(), Modificar_Coches.class);
            actModificar.putExtra("idcoche", arrayCoches.get(position).getCod_coche());
            actModificar.putExtra("esnuevo", wherethefuckiam);
            startActivityForResult(actModificar, 4);
        } else if (wherethefuckiam == 0){
            Intent actModificar = new Intent(getApplicationContext(), Modificar_Coches.class);
            actModificar.putExtra("idcoche", arrayCoches.get(position).getCod_coche());
            actModificar.putExtra("esnuevo", wherethefuckiam);
            startActivityForResult(actModificar, 3);
        }

    }

    //MÉTODO PARA CARGAR AL LISTVIEW EL ADAPTADOR DE COCHES NUEVOS
    public int actualizar_coches_nuevos() {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        arrayCoches = databaseAccess.todos_los_coches_nuevos();
        databaseAccess.close();

        adapter = new AdaptadorCoches(this, arrayCoches);
        this.lsvListado.setAdapter(adapter);
        // VARIABLE PARA SABER EN QUE VENTANA ESTAMOS. (EN ESTE CASO EN LA DE COCHES NUEVOS)
        wherethefuckiam = 1;

        //CREAMOS UN OBJETO DE LA CLASE TOOLBAR LLAMADO TOOLBAR.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //LE DAMOS A DICHO OBJETO UN TÍTULO.
        toolbar.setTitle("Concesionario");

        //PONEMOS LA TOOLBAR EN EL SITIO DE LA TOOLBAR POR DEFECTO.
        setSupportActionBar(toolbar);
        return wherethefuckiam;
    }

    //MÉTODO PARA CARGAR AL LISTVIEW EL ADAPTADOR DE COCHES USADOS
    public int actualizar_coches_usados() {
        DatabaseAccess databaseAccessUsados = DatabaseAccess.getInstance(this);
        databaseAccessUsados.open();
        arrayCoches = databaseAccessUsados.todos_los_coches_usados();
        databaseAccessUsados.close();

        adapter = new AdaptadorCoches(this, arrayCoches);
        this.lsvListado.setAdapter(adapter);
        // VARIABLE PARA SABER EN QUE VENTANA ESTAMOS. (EN ESTE CASO EN LA DE COCHES USADOS)
        wherethefuckiam = 0;

        //CREAMOS UN OBJETO DE LA CLASE TOOLBAR LLAMADO TOOLBAR.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //LE DAMOS A DICHO OBJETO UN TÍTULO.
        toolbar.setTitle("Concesionario - USADOS");

        //PONEMOS LA TOOLBAR EN EL SITIO DE LA TOOLBAR POR DEFECTO.
        setSupportActionBar(toolbar);
        return wherethefuckiam;
    }

    //MÉTODO PARA CARGAR AL LISTVIEW EL ADAPTADOR DE EXTRAS
    public int actualizar_extras(){
        DatabaseAccess databaseAccessExtras = DatabaseAccess.getInstance(this);
        databaseAccessExtras.open();
        arrayExtras = databaseAccessExtras.todos_los_extras();
        databaseAccessExtras.close();

        adapterExtras = new AdaptadorExtras(this, arrayExtras);
        this.lsvListado.setAdapter(adapterExtras);
        // VARIABLE PARA SABER EN QUE VENTANA ESTAMOS. (EN ESTE CASO EN LA DE EXTRAS)
        wherethefuckiam = 2;

        //CREAMOS UN OBJETO DE LA CLASE TOOLBAR LLAMADO TOOLBAR.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //LE DAMOS A DICHO OBJETO UN TÍTULO.
        toolbar.setTitle("Concesionario - EXTRAS");

        //PONEMOS LA TOOLBAR EN EL SITIO DE LA TOOLBAR POR DEFECTO.
        setSupportActionBar(toolbar);
        return wherethefuckiam;
    }
    /*
        MÉTODO PARA DESHABILITAR LOS ELEMENTOS DEL MENÚ DEPENDIENDO DEL ADAPTADOR EN EL QUE NOS ENCONTREMOS.
        SI LA VARIABLE ES 0, SIGNIFICA QUE EN EL LISTVIEW ESTÁN CARGADOS LOS DATOS DE LOS COCHES NUEVOS.
        POR TANTO, EL ELEMENTO DEL MENÚ "COCHES NUEVOS" SE DESHABILITA.
        SI LA VARIABLE ES 1, SE DESHABILITA EL ELEMENTO DEL MENÚ "COCHES USADOS" Y SI ES 2, SE DESHABILITA "EXTRAS".
    */
    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        if(wherethefuckiam == 0) {
            menu.getItem(0).setEnabled(false);
            menu.getItem(1).setEnabled(true);
            menu.getItem(2).setEnabled(true);
        }
        if(wherethefuckiam == 1) {
            menu.getItem(1).setEnabled(false);
            menu.getItem(0).setEnabled(true);
            menu.getItem(2).setEnabled(true);
        }
        if(wherethefuckiam == 2) {
            menu.getItem(2).setEnabled(false);
            menu.getItem(0).setEnabled(true);
            menu.getItem(1).setEnabled(true);
        }
        return true;
    }


}
