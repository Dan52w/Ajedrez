package org.example.pieza;

public class Pieza {
    protected String nombre;
    protected String color;
    protected String iniPosicion;
    protected String sigPosicion;
    protected String tipo;

    public Pieza(String nombre, String color, String iniPosicion, String tipo) {
        this.nombre = nombre;
        this.color = color;
        this.iniPosicion = iniPosicion;
        this.tipo = tipo;
    }

    public Boolean valido(String ini, String sig){ return null;}
    public void moverPieza(String ini, String sig){}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIniPosicion() {
        return iniPosicion;
    }

    public void setIniPosicion(String iniPosicion) {
        this.iniPosicion = iniPosicion;
    }

    public String getSigPosicion() {
        return sigPosicion;
    }

    public void setSigPosicion(String sigPosicion) {
        this.sigPosicion = sigPosicion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
