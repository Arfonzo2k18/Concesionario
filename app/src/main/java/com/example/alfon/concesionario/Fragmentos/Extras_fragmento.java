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
import com.example.alfon.concesionario.Actividades.Modificar_Extras;
import com.example.alfon.concesionario.Actividades.NuevoCoche;
import com.example.alfon.concesionario.Actividades.NuevoExtra;
import com.example.alfon.concesionario.Adaptadores.AdaptadorExtras;
import com.example.alfon.concesionario.BaseDeDatos.DatabaseAccess;
import com.example.alfon.concesionario.Clases.Extra;
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

public class Extras_fragmento extends Fragment {

    //Propiedades
    private AdaptadorExtras adaptadorExtras;
    private ArrayList<Extra> listaExtras;
    private View rootView;

    //Objetos para vincularlo con el XML
    private FloatingActionButton btnFlotAdd;
    private ListView lisvExtras;

    private int valor = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Indicamos que este fragmento debe rellenar el menú
        setHasOptionsMenu(true);

        //Inflamos la Vista rootView para Visualizar el Adaptador personalizado
        rootView = inflater.inflate(R.layout.fragment_extras, container, false);

        //Vinculamos el ListView y el FloatingActionButton con el del XML
        lisvExtras = (ListView) rootView.findViewById(R.id.lisvExtras);
        btnFlotAdd = (FloatingActionButton) rootView.findViewById(R.id.btnFlotAdd);

        //Creamos una escucha para el ListView, para abrir en una nueva actividad y ver los detalles del coche
        lisvExtras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent actModificar = new Intent(getActivity(), Modificar_Extras.class);
                actModificar.putExtra("idextra", listaExtras.get(i).getCod_extra());
                startActivityForResult(actModificar, 3);
            }
        });

        //Creamos una escucha para el btnFlotAdd
        btnFlotAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actividadCrearExtra = new Intent(getActivity(), NuevoExtra.class);
                startActivityForResult(actividadCrearExtra, 2);
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
        listaExtras = databaseAccess.todos_los_extras();

        //Cerramos la conexión con la Base de Datos
        databaseAccess.close();

        adaptadorExtras = new AdaptadorExtras(getActivity(), listaExtras);

        lisvExtras.setAdapter(adaptadorExtras);
    }
}