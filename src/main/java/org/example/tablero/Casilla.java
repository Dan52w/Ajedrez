package org.example.tablero;

import org.example.pieza.*;

public class Casilla {
    private String nombre;
    private String color;
    private Pieza pieza;

    public Casilla(String nombre, String color, Pieza pieza) {
        this.nombre = nombre;
        this.color = color;
        this.pieza = pieza;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Pieza getPieza() {
        return pieza;
    }

    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
    }
}
