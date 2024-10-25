package org.example.pieza;

public class Peon extends Pieza{


    public Peon(String nombre, String color, String iniPosicion, String tipo) {
        super(nombre, color, iniPosicion, tipo);
    }

    @Override
    public Boolean valido(String ini, String sig) {
        return null;
    }

    @Override
    public void moverPieza(String ini, String sig) {

    }
}
