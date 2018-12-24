package com.example.alfon.concesionario.Fragmentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.alfon.concesionario.R;

public class Mapas extends Fragment {

    private SupportMapFragment mapFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mapas, container, false);

        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    LatLng empresa = new LatLng(37.582325, -4.748406); //Coordenadas
                    googleMap.addMarker(new MarkerOptions().position(empresa).title("Concesionario"));//Marcador
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(empresa, 15));//Zoom del mapa
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(empresa));
                }
            });
        }
        //Cargamos el mapa en el fragment map
        getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();

        return rootView;
    }
}