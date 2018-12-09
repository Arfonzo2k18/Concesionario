package com.example.zafiro5.concesionario;

public class Extra {
    private int cod_extra;
    private double precio_extra;
    private String nombre_extra, descripcion_extra;

    Extra(){
        this.cod_extra = 0;
        this.precio_extra = 0;
        this.nombre_extra = null;
    }
    Extra(String nombre_extra, double precio_extra){
        this.nombre_extra = nombre_extra;
        this.precio_extra = precio_extra;
    }

    Extra(int cod_extra, String nombre_extra, double precio_extra){
        this.cod_extra = cod_extra;
        this.nombre_extra = nombre_extra;
        this.precio_extra = precio_extra;
    }

    Extra(int cod_extra, String nombre_extra, double precio_extra, String descripcion_extra){
        this.cod_extra = cod_extra;
        this.nombre_extra = nombre_extra;
        this.precio_extra = precio_extra;
        this.descripcion_extra = descripcion_extra;
    }

    public int getCod_extra() {
        return cod_extra;
    }

    public void setCod_extra(int cod_extra) {
        this.cod_extra = cod_extra;
    }

    public double getPrecio_extra() {
        return precio_extra;
    }

    public void setPrecio_extra(double precio_extra) {
        this.precio_extra = precio_extra;
    }

    public String getNombre_extra() {
        return nombre_extra;
    }

    public void setNombre_extra(String nombre_extra) {
        this.nombre_extra = nombre_extra;
    }

    public String getDescripcion_extra() {
        return descripcion_extra;
    }

    public void setDescripcion_extra(String descripcion_extra) {
        this.descripcion_extra = descripcion_extra;
    }
}
