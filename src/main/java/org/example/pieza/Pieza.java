package org.example.pieza;

import org.example.tablero.Casilla;

import java.util.List;

public class Pieza {
    protected String nombre;
    protected String color;
    protected String iniPosicion;
    protected String actPosicion;
    protected String tipo;

    public Pieza(String nombre, String color, String iniPosicion, String tipo) {
        this.nombre = nombre;
        this.color = color;
        this.iniPosicion = iniPosicion;
        this.tipo = tipo;
    }
    //Se pasan Posiciones (posI = Pos... Inicial y posS = Pos... Siguiente) como vectores ya que son dos filas y columnas
    //Se pasa una lista de casillas, para aquellas piezas que se pueden mover largas distancias y se pueda
    //verificar que entre esas distancias no haya una pieza que interrumpa el movimeinto
    public Boolean valido(int[] posI, int[] posS, List<Casilla> casillaList) { return null;}
    public Casilla buscarCasilla(List<Casilla> casillaList, int[] pos){return null;}
    public void moverPieza(int[] posS){}

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

    public String getActPosicion() {
        return actPosicion;
    }

    public void setActPosicion(String actPosicion) {
        this.actPosicion = actPosicion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
