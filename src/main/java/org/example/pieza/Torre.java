package org.example.pieza;

import org.example.tablero.Casilla;

import java.util.List;

public class Torre extends Pieza{
    public Torre(String nombre, String color, String iniPosicion, String tipo) {
        super(nombre, color, iniPosicion, tipo);
    }

    @Override
    public Boolean valido(int[] posI, int[] posS, List<Casilla> casillaList) {
        if((posI[0] > 0 && posI[0] < 9)
                && (posI[1] > 0 && posI[1] < 9)
                && (posS[0] > 0 && posS[0] < 9)
                && (posS[1] > 0 && posS[1] < 9)){ //Validar que el peon si se este moviendo dentro del tablero y no por fuera de este
            return true;
        }
        return false;
    }

    @Override
    public void moverPieza(int[] posS) {
        super.moverPieza(posS);
    }
}
