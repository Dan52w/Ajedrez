package org.example.pieza;

import org.example.tablero.Casilla;

import java.util.List;

public class Rey extends Pieza{
    public Rey(String nombre, String color, String iniPosicion, String tipo) {
        super(nombre, color, iniPosicion, tipo);
    }

    @Override
    public Boolean valido(int[] posI, int[] posS, List<Casilla> casillaList) {
        return super.valido(posI, posS, casillaList);
    }

    @Override
    public void moverPieza(int[] posS) {
        super.moverPieza(posS);
    }
}
