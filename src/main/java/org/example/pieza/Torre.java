package org.example.pieza;

import org.example.tablero.Casilla;

public class Torre extends Pieza{
    public Torre(String nombre, String color, String iniPosicion, String tipo) {
        super(nombre, color, iniPosicion, tipo);
    }

    @Override
    public Boolean valido(int[] posI, int[] posS, Casilla casilla) {
        return super.valido(posI, posS, casilla);
    }

    @Override
    public void moverPieza(int[] posS) {
        super.moverPieza(posS);
    }
}
