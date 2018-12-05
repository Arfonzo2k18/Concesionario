package com.example.zafiro5.concesionario;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.ArrayList;

public class AdaptadorExtras_Checkbox extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<Data> items;
    private ViewHolder viewHolder;

    public AdaptadorExtras_Checkbox(Activity activity, ArrayList<Data> items){
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
        final int pos = position;
        Data dir = items.get(position);

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.layout_check_box, null);
           /* viewHolder.checkBox = (CheckBox) v.findViewById(R.id.cbExtra);
            viewHolder.nombre = (TextView) v.findViewById(R.id.txvNombre);
            viewHolder.precio = (TextView) v.findViewById(R.id.txvPrecio);*/
        }else {
            viewHolder.checkBox.setChecked(false);
        }

        TextView txvNombre = (TextView) v.findViewById(R.id.txvNombre);
        txvNombre.setText(dir.getNombre_extra());

        TextView txvModelo_coche = (TextView) v.findViewById(R.id.txvPrecio);
        txvModelo_coche.setText(String.valueOf(dir.getPrecio_extra())+ "€");

        CheckBox cbExtra = (CheckBox) v.findViewById(R.id.cbExtra);
        cbExtra.setChecked(false);

        if(dir.isCheckbox()){
            cbExtra.setChecked(true);
        } else {
            cbExtra.setChecked(false);
        }
        return v;
    }

   public ArrayList<Data> getAllData(){
        return items;
    }

    public void setCheckBox(int position){
        Data item = items.get(position);
        item.setCheckbox(!item.isCheckbox());
        notifyDataSetChanged();
    }

    public class ViewHolder{
        TextView nombre;
        TextView precio;
        CheckBox checkBox;
    }
}
