package com.example.alfon.concesionario.Adaptadores;

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

import com.example.alfon.concesionario.Clases.Extra;
import com.example.alfon.concesionario.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class AdaptadorExtras extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<Extra> items;

    public AdaptadorExtras(Activity activity, ArrayList<Extra> items){
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
            v = inf.inflate(R.layout.layout_extras, null);
        }
        // CARGO LOS DATOS DE LOS EXTRAS EN SUS CORRESPONDIENTES TEXTVIEW
        Extra dir = items.get(position);

        TextView txvNombreExtra = (TextView) v.findViewById(R.id.txvNombreExtra);
        txvNombreExtra.setText(dir.getNombre_extra());

        TextView txvPrecio = (TextView) v.findViewById(R.id.txvPrecio);
        txvPrecio.setText(String.valueOf(dir.getPrecio_extra())+ "€");

        return v;
    }
}
