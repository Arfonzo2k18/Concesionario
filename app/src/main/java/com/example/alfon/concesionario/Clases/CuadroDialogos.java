package com.example.alfon.concesionario.Clases;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.text.InputType;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.alfon.concesionario.R;

// CLASE PARA CREAR EL CUADRO DE DIALOGO
public class CuadroDialogos {

    public interface AcabadoDialogos {
        void ResultadoCuadroDialogo(String nombre, String apellidos, String email, int telefono, String poblacion, String direccion);
    }

    private AcabadoDialogos interfaz;

    public CuadroDialogos(Context context, AcabadoDialogos actividad) {

        interfaz = actividad;

        final Dialog dialogo = new Dialog(context);
        dialogo.getWindow();
        dialogo.setContentView(R.layout.layout_dialog);

        final EditText eNombre = (EditText)dialogo.findViewById(R.id.edtNombre);
        final EditText eApellidos = (EditText)dialogo.findViewById(R.id.edtApellidos);
        final EditText eEmail = (EditText)dialogo.findViewById(R.id.edtEmail);
        final EditText eTelefono = (EditText)dialogo.findViewById(R.id.edtTelefono);
        final EditText ePoblacion = (EditText)dialogo.findViewById(R.id.edtPoblacion);
        final EditText eDireccion = (EditText)dialogo.findViewById(R.id.edtDireccion);
        Button bCancelar = (Button)dialogo.findViewById(R.id.btnCancelar);
        Button bEnviar = (Button)dialogo.findViewById(R.id.btnEnviar);


        eNombre.setInputType(InputType.TYPE_CLASS_TEXT);
        eApellidos.setInputType(InputType.TYPE_CLASS_TEXT);
        eEmail.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
        eTelefono.setInputType(InputType.TYPE_CLASS_NUMBER);
        ePoblacion.setInputType(InputType.TYPE_CLASS_TEXT);
        eDireccion.setInputType(InputType.TYPE_CLASS_TEXT);

        bCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo.dismiss();
            }
        });

        bEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             String nombre = eNombre.getText().toString();
             String apellidos = eApellidos.getText().toString();
             String email = eEmail.getText().toString();
             int telefono;
                if(eTelefono.getText().toString().length() != 0) {
                    telefono = Integer.parseInt(eTelefono.getText().toString());
                } else {
                    telefono = 0;
                }
             String poblacion = ePoblacion.getText().toString();
             String direccion = eDireccion.getText().toString();
             if(nombre.length() != 0 && apellidos.length() != 0 && email.length() != 0 && telefono != 0 && poblacion.length() != 0 && direccion.length() != 0) {
                 interfaz.ResultadoCuadroDialogo(nombre, apellidos, email, telefono, poblacion, direccion);
                 dialogo.dismiss();
             } else {
                 Snackbar.make(view, "Has dejado algún campo sin rellenar. Por favor, rellena todos los campos.", Snackbar.LENGTH_LONG)
                         .setAction("Action", null).show();
             }
            }
        });

        dialogo.show();
    }

}