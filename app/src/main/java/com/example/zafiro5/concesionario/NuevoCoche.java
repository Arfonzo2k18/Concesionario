package com.example.zafiro5.concesionario;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;

public class NuevoCoche extends AppCompatActivity implements View.OnClickListener {

    private EditText edtMarca;
    private EditText edtModelo;
    private EditText edtPrecio;
    private EditText edtDescripcion;
    private Switch swNuevo;
    private boolean estado_switch = false;
    private ImageView imvImagen;
    private Button btnHacerFoto, btnCrear;
    private byte[] foto_coche;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_coche);

        //CREAMOS UN OBJETO DE LA CLASE TOOLBAR LLAMADO TOOLBAR.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //LE DAMOS A DICHO OBJETO UN TÍTULO.
        toolbar.setTitle("Nuevo Coche");

        //PONEMOS LA TOOLBAR EN EL SITIO DE LA TOOLBAR POR DEFECTO.
        setSupportActionBar(toolbar);

        imvImagen = (ImageView)findViewById(R.id.imvImagen);
        edtMarca = (EditText)findViewById(R.id.edtMarca);
        edtModelo = (EditText)findViewById(R.id.edtModelo);
        edtPrecio = (EditText)findViewById(R.id.edtPrecio);
        edtDescripcion = (EditText)findViewById(R.id.edtDescripcion);
        swNuevo = (Switch)findViewById(R.id.swNuevo);
        btnCrear = (Button)findViewById(R.id.btnCrear);
        btnHacerFoto = (Button)findViewById(R.id.btnHacerFoto);

        btnHacerFoto.setOnClickListener(this);
        btnCrear.setOnClickListener(this);

        // PONGO UN ESCUCHADOR PARA SABER SI EL SWITCH ESTÁ PULSADO O NO.
        swNuevo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(swNuevo.isChecked()){
                    //GUARDO EL VALOR DEL SWITCH EN UNA VARIABLE QUE DESPUÉS SE UTILIZA PARA EL CAMPO "NUEVO" DE LA BASE DE DATOS.
                    estado_switch = true;
                }else {
                    estado_switch = false;
                }
            }
        });

    }

    public void onClick(View view) {
        if(view.getId()==findViewById(R.id.btnHacerFoto).getId()) {
            /*
                SI PULSAMOS EN EL BOTÓN HACER FOTO NOS EJECUTA CÁMARA QUE TENEMOS EN NUESTRO TELÉFONO POR DEFECTO.
                TENEMOS QUE INTRODUCIR DOS LINEAS EN EL MANIFIESTO PARA QUE SE PUEDA EJECUTAR.
                ADEMÁS DE PEDIR PERMISOS PARA SABER SI SE PUEDE EJECUTAR LA CÁMARA O NO.
            */
            if(checkCameraPermission()) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        }

        if(view.getId()==findViewById(R.id.btnCrear).getId()){

            // CREAMOS ATRIBUTOS PARA HACER LA INSERCIÓN EN LA BDD
            String marca_coche;
            String modelo_coche;
            Double precio_coche;
            String descripcion_coche;
            int nuevo;

            //RECOGEMOS DATOS DE LOS EDIT TEXT MARCACOCHE Y MODELOCOCHE.
            marca_coche = edtMarca.getText().toString();
            modelo_coche = edtModelo.getText().toString();


            // CREAMOS UN MAPA DE BITS CON EL CONTENIDO DE DICHO IMAGE VIEW.
            imvImagen.buildDrawingCache();
            Bitmap bmp = imvImagen.getDrawingCache();

            // CREAMOS UN ARRAY DE BYTES DE SALIDA.
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            // COMPRIMIMOS EN PNG LA FOTO DEL MAPA DE BITS Y LA GUARDAMOS EN EL ARRAY DE BYTES DE SALIDA.
            bmp.setHeight(180);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

            // PASAMOS AL ARRAY DE BYTES EL FLUJO DE SALIDA TRANSFORMADO AL TIPO ARRAY DE BYTE.
            foto_coche = stream.toByteArray();

            // COMPROBAMOS QUE LA LONGITUD DEL EDIT TEXT PRECIO SEA MAYOR QUE 0. Y LE ASIGNAMOS UN VALOR EN CASO DE QUE NO SEA MAYOR.
            if(edtPrecio.getText().toString().length() != 0) {
                precio_coche = Double.parseDouble(edtPrecio.getText().toString());
            } else {
                precio_coche = 0.0;
            }
            // RECOGEMOS DATOS DEL EDIT TEXT DESCRIPCIONCOCHE
            descripcion_coche = edtDescripcion.getText().toString();

            // COMPROBAMOS SI EL SWITCH ESTÁ PULSADO O NO Y PASAMOS EL ESTADO A UN ENTERO PARA INSERTARLO EN LA BDD.
            if(estado_switch == true){
                nuevo = 1;
            } else {
                nuevo = 0;
            }

            // COMPROBAMOS QUE LA LONGITUD
            if (marca_coche.trim().length() != 0 && modelo_coche.trim().length() != 0 && precio_coche != 0.0 && descripcion_coche.trim().length() != 0) {
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
                databaseAccess.open();
                databaseAccess.crear_nuevo_coche(new Coche(marca_coche, modelo_coche, foto_coche, precio_coche, descripcion_coche, nuevo));
                databaseAccess.close();

                Toast toast1 = Toast.makeText(getApplicationContext(), "El coche se ha creado correctamente.", Toast.LENGTH_LONG);
                toast1.show();

                if (nuevo == 1) {
                    Intent Principal = new Intent(getApplicationContext(), Principal.class);
                    startActivityForResult(Principal, 3);
                } else {
                    Intent Principal = new Intent(getApplicationContext(), Principal.class);
                    startActivityForResult(Principal, 4);
                }
            } else {
                Snackbar.make(view, "Has dejado algún campo sin rellenar. Por favor, rellena todos los campos.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        }
    }

    // MÉTODO PARA COMPROBAR LOS PERMISOS DE LA CÁMARA.
    private boolean checkCameraPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para la camara!.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 225);
            return false;
        } else {
            Log.i("Mensaje", "Tienes permiso para usar la camara.");
            return true;
        }
    }

    // MÉTODO PARA TRANSFORMAR LA IMAGEN QUE TOMAMOS EN UN ARRAY DE BYTES DE SALIDA.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        imvImagen.setImageBitmap(bitmap);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        foto_coche = stream.toByteArray();
    }
}
