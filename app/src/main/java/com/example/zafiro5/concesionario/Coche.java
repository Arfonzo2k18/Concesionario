package com.example.zafiro5.concesionario;

public class Coche {
    private int cod_coche, coche_usado;
    private String marca_coche, descripcion_coche, modelo_coche;
    private double precio_coche;
    private byte[] imagen_coche;

    Coche(){
        this.cod_coche = cod_coche;
        this.marca_coche = null;
        this.modelo_coche = null;
        this.imagen_coche = null;
        this.descripcion_coche = null;
        this.precio_coche = 0;
        this.coche_usado = 0;
    }

    Coche(int cod_coche, String marca_coche, String modelo_coche, byte[] imagen_coche, double precio_coche, String descripcion_coche, int coche_usado){
        this.cod_coche = cod_coche;
        this.marca_coche = marca_coche;
        this.modelo_coche = modelo_coche;
        this.imagen_coche = imagen_coche;
        this.precio_coche = precio_coche;
        this.descripcion_coche = descripcion_coche;
        this.coche_usado = coche_usado;
    }

    public int getCod_coche() {
        return cod_coche;
    }

    public void setCod_coche(int cod_coche) {
        this.cod_coche = cod_coche;
    }

    public int getCoche_usado() {
        return coche_usado;
    }

    public void setCoche_usado(int coche_usado) {
        this.coche_usado = coche_usado;
    }

    public String getMarca_coche() {
        return marca_coche;
    }

    public void setMarca_coche(String marca_coche) {
        this.marca_coche = marca_coche;
    }

    public String getModelo_coche() {
        return modelo_coche;
    }

    public void setModelo_coche(String modelo_coche) {
        this.modelo_coche = modelo_coche;
    }

    public byte[] getImagen_coche() {
        return imagen_coche;
    }

    public void setImagen_coche(byte[] imagen_coche) {
        this.imagen_coche = imagen_coche;
    }

    public String getDescripcion_coche() {
        return descripcion_coche;
    }

    public void setDescripcion_coche(String descripcion_coche) {
        this.descripcion_coche = descripcion_coche;
    }

    public double getPrecio_coche() {
        return precio_coche;
    }

    public void setPrecio_coche(double precio_coche) {
        this.precio_coche = precio_coche;
    }
}
