package com.example.zafiro5.concesionario;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class AdaptadorCoches extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<Coche> items;

    public AdaptadorCoches(Activity activity, ArrayList<Coche> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.layout_coches, null);
        }

        Coche dir = items.get(position);

        TextView txvMarca_coche = (TextView) v.findViewById(R.id.txvMarca);
        txvMarca_coche.setText(dir.getMarca_coche());

        TextView txvModelo_coche = (TextView) v.findViewById(R.id.txvModelo);
        txvModelo_coche.setText(dir.getModelo_coche());

        if(dir.getCoche_usado() == 1) {
            TextView txvUsado = (TextView) v.findViewById(R.id.txvUsado);
            txvUsado.setText("No");
        } else {
            TextView txvUsado = (TextView) v.findViewById(R.id.txvUsado);
            txvUsado.setText("Si");
        }

        ByteArrayInputStream imageStream = new ByteArrayInputStream(dir.getImagen_coche());
        Bitmap imagen= BitmapFactory.decodeStream(imageStream);

        ImageView imvImagen = (ImageView) v.findViewById(R.id.imvImagen);
        imvImagen.setImageBitmap(imagen);

        return v;
    }
}
