package com.example.zafiro5.concesionario;

public class Data {
    private boolean check;
    private int cod_extra;
    private double precio_extra;
    private String nombre_extra;

    public Data() {}

    public Data(int cod_extra, String nombre_extra, double precio_extra) {
        this.cod_extra = cod_extra;
        this.nombre_extra = nombre_extra;
        this.precio_extra = precio_extra;
    }

    public Data(boolean check, int cod_extra, String nombre_extra, double precio_extra) {
        this.check = check;
        this.cod_extra = cod_extra;
        this.nombre_extra = nombre_extra;
        this.precio_extra = precio_extra;
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

    public boolean isCheckbox() {
        return check;
    }

    public void setCheckbox(boolean check) {
        this.check = check;
    }

}
