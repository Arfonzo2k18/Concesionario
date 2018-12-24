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

public class Coches_usados extends Fragment {

    //Propiedades
    private AdaptadorCoches adaptadorCocheUsado;
    private ArrayList<Coche> listaCochesUsados;
    private View rootView;

    //Objetos para vincularlo con el XML
    private FloatingActionButton btnFlotAdd;
    private ListView lisvCochesUsados;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Indicamos que este fragmento debe rellenar el menú
        setHasOptionsMenu(true);

        //Inflamos la Vista rootView para Visualizar el Adaptador personalizado
        rootView = inflater.inflate(R.layout.fragment_coches_usados, container, false);

        //Vinculamos el ListView y el FloatingActionButton con el del XML
        lisvCochesUsados = (ListView) rootView.findViewById(R.id.lisvCochesUsados);
        btnFlotAdd = (FloatingActionButton) rootView.findViewById(R.id.btnFlotAdd);

        //Creamos una escucha para el ListView, para abrir en una nueva actividad y ver los detalles del coche
        lisvCochesUsados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent actModificar = new Intent(getActivity(), Modificar_Coches.class);
                actModificar.putExtra("idcoche", listaCochesUsados.get(i).getCod_coche());
                actModificar.putExtra("esnuevo", 1);
                startActivityForResult(actModificar, 2);
            }
        });

        //Creamos una escucha para el btnFlotAdd
        btnFlotAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actividadCrearCocheUsado = new Intent(getActivity(), NuevoCoche.class);
                startActivityForResult(actividadCrearCocheUsado, 2);
            }
        });

        //Llamamos al método adaptadorUsado para adaptar los datos al ListView
        adaptadorUsado();


        //Devolvemos la Vista
        return rootView;
    }

    //Método para comprobar el resultado de las otras actividades
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if((requestCode == 1) && (resultCode == Modificar_Coches.RESULT_OK)) {
            adaptadorUsado(); //Llamamos al método del adaptador para que se actualice
        }

        if((requestCode == 2) && (resultCode == NuevoCoche.RESULT_OK)) {
            adaptadorUsado(); //Llamamos al método del adaptador para que se actualice
        }
    }

    //Método para llamar al adaptador
    public void adaptadorUsado() {
        //Creamos un objeto DatabaseAccess para tener acceso a la Base de Datos
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getContext());
        databaseAccess.open();

        //Obtenemos todas los Coches de la Tabla Coches Nuevos
        listaCochesUsados = databaseAccess.todos_los_coches_usados();

        //Cerramos la conexión con la Base de Datos
        databaseAccess.close();

        adaptadorCocheUsado = new AdaptadorCoches(getActivity(), listaCochesUsados);

        lisvCochesUsados.setAdapter(adaptadorCocheUsado);
    }
}