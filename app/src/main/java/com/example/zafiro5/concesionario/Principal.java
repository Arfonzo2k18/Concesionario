package com.example.zafiro5.concesionario;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;

public class Principal extends AppCompatActivity implements OnClickListener, OnItemClickListener {

    ArrayList<Coche> arrayCoches = new ArrayList<Coche>();

    private ListView lsvListado;

    private AdaptadorCoches adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //getting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setting the title
        toolbar.setTitle("Concesionario");

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);

        this.lsvListado = (ListView) findViewById(R.id.lsvListado);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        arrayCoches = databaseAccess.todos_los_coches_nuevos();
        databaseAccess.close();

        adapter = new AdaptadorCoches(this, arrayCoches);
        this.lsvListado.setAdapter(adapter);
        lsvListado.setOnItemClickListener(this);

    }

    //método para insertar el menu en la actividad
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    //método para controlar la acción que se pulsa sobre un menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id)
        {
            case R.id.menu_coches_nuevos:
                Log.i("Coches Nuevos", "Has pulsado Coches Nuevos");
                break;
            case R.id.menu_coches_usados:
                Log.i("Coches Usados", "Has pulsado Coches Usados");

                break;
            case R.id.menu_extras:
                Log.i("Extras", "Has pulsado Extras");
                break;
        }//fin switch

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
