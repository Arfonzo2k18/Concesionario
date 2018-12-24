package com.example.alfon.concesionario.Actividades;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.alfon.concesionario.Fragmentos.Coches_nuevos;
import com.example.alfon.concesionario.Fragmentos.Coches_usados;
import com.example.alfon.concesionario.Fragmentos.Extras_fragmento;
import com.example.alfon.concesionario.Fragmentos.Mapas;
import com.example.alfon.concesionario.R;

public class Principal extends AppCompatActivity {

    //Objetos con los que identificaremos a los componentes del XML
    private BottomNavigationView btnNavegacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //Creamos un objeto Toolbar y lo vinculamos con el del XML
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Concesionario");

        setSupportActionBar(toolbar);

        //Vinculamos el BottomNavigationView con el del XML
        btnNavegacion = (BottomNavigationView) findViewById(R.id.btnNavegacion);

        //Creamos una escucha para comprobar si se ha pulsado sobre él
        btnNavegacion.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.navegacion_coche_nuevo:
                        fragment = new Coches_nuevos();
                        break;

                    case R.id.navegacion_coche_usado:
                        fragment = new Coches_usados();
                        break;

                    case R.id.navegacion_extras:
                        fragment = new Extras_fragmento();
                        break;

                    case R.id.navegacion_conocenos:
                        fragment = new Mapas();
                        break;
                }
                replaceFragment(fragment);
                return true;
            }
        });

        //Llamamos al método para iniciar el primer fragmento, en este caso el NuevoFragment
        setInitialFragment();
    }

    //Método para iniciar los Fragmentos, en este caso cargará NuevoFragment
    private void setInitialFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.contenedor, new Coches_nuevos());
        fragmentTransaction.commit();
    }

    //Método que cambiará el Fragmento
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contenedor, fragment);
        fragmentTransaction.commit();
    }
}
