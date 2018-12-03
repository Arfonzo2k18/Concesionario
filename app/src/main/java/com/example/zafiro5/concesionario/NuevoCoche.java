package com.example.zafiro5.concesionario;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class NuevoCoche extends AppCompatActivity implements View.OnClickListener {

    private EditText edtMarca;
    private EditText edtModelo;
    private EditText edtPrecio;
    private EditText edtDescripcion;
    private Switch swNuevo;
    private boolean estado_switch;
    private ImageView imvImagen;
    private Button btnHacerFoto, btnCrear;
    private static int TAKE_PICTURE = 1;
    private String nombrefoto = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_coche);
        checkCameraPermission();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setting the title
        toolbar.setTitle("Nuevo Coche");

        //placing toolbar in place of actionbar
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

        swNuevo.setChecked(estado_switch);

        swNuevo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked){
                    estado_switch = true;
                }
            }
        });

    }

    public void onClick(View view) {
    if(view.getId()==findViewById(R.id.btnHacerFoto).getId()) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
        }
    }

    private void checkCameraPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para la camara!.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 225);
        } else {
            Log.i("Mensaje", "Tienes permiso para usar la camara.");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 0) {
            nombrefoto = Environment.getExternalStorageDirectory() + "/test.jpg";
            int code = TAKE_PICTURE;
            Uri output = Uri.fromFile(new File(nombrefoto));
            data.putExtra(MediaStore.EXTRA_OUTPUT, output);

            Uri selectedImage = data.getData();
            InputStream is;
            try {
                is = getContentResolver().openInputStream(output);
                BufferedInputStream bis = new BufferedInputStream(is);
                Bitmap bitmap = BitmapFactory.decodeStream(bis);
                imvImagen.setImageBitmap(bitmap);
            } catch (FileNotFoundException e){}



        }
    }
}
