package com.example.alfon.concesionario.Fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.alfon.concesionario.Actividades.Modificar_Coches;
import com.example.alfon.concesionario.Actividades.NuevoCoche;
import com.example.alfon.concesionario.Adaptadores.AdaptadorCoches;
import com.example.alfon.concesionario.BaseDeDatos.DatabaseAccess;
import com.example.alfon.concesionario.Clases.Coche;
import com.example.alfon.concesionario.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Coches_nuevos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Coches_nuevos#newInstance} factory method to
 * create an instance of this fragment.
 */
import java.util.ArrayList;

public class Coches_nuevos extends Fragment {

    //Propiedades
    private AdaptadorCoches adaptadorCocheNuevo;
    private ArrayList<Coche> listaCochesNuevos;
    private View rootView;

    //Objetos para vincularlo con el XML
    private FloatingActionButton btnFlotAdd;
    private ListView lisvCochesNuevos;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Indicamos que este fragmento debe rellenar el menú
        setHasOptionsMenu(true);

        //Inflamos la Vista rootView para Visualizar el Adaptador personalizado
        rootView = inflater.inflate(R.layout.fragment_coches_nuevos, container, false);

        //Vinculamos el ListView y el FloatingActionButton con el del XML
        lisvCochesNuevos = (ListView) rootView.findViewById(R.id.lisvCochesNuevos);
        btnFlotAdd = (FloatingActionButton) rootView.findViewById(R.id.btnFlotAdd);

        //Creamos una escucha para el ListView, para abrir en una nueva actividad y ver los detalles del coche
        lisvCochesNuevos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent actModificar = new Intent(getActivity(), Modificar_Coches.class);
                actModificar.putExtra("idcoche", listaCochesNuevos.get(i).getCod_coche());
                actModificar.putExtra("esnuevo", 0);
                startActivityForResult(actModificar, 3);
            }
        });

        //Creamos una escucha para el btnFlotAdd
        btnFlotAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actividadCrearCocheNuevo = new Intent(getActivity(), NuevoCoche.class);
                startActivityForResult(actividadCrearCocheNuevo, 2);
            }
        });

        //Llamamos al método adaptadorNuevo para adaptar los datos al ListView
        adaptadorNuevo();


        //Devolvemos la Vista
        return rootView;
    }

    //Método para comprobar el resultado de las otras actividades
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if((requestCode == 1) && (resultCode == Modificar_Coches.RESULT_OK)) {
            adaptadorNuevo(); //Llamamos al método del adaptador para que se actualice
        }

        if((requestCode == 2) && (resultCode == NuevoCoche.RESULT_OK)) {
            adaptadorNuevo(); //Llamamos al método del adaptador para que se actualice
        }
    }

    //Método para llamar al adaptador
    public void adaptadorNuevo() {
        //Creamos un objeto DatabaseAccess para tener acceso a la Base de Datos
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getContext());
        databaseAccess.open();

        //Obtenemos todas los Coches de la Tabla Coches Nuevos
        listaCochesNuevos = databaseAccess.todos_los_coches_nuevos();

        //Cerramos la conexión con la Base de Datos
        databaseAccess.close();

        adaptadorCocheNuevo = new AdaptadorCoches(getActivity(), listaCochesNuevos);

        lisvCochesNuevos.setAdapter(adaptadorCocheNuevo);
    }
}