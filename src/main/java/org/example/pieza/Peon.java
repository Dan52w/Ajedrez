package org.example.pieza;

import org.example.tablero.Casilla;

public class Peon extends Pieza{
    private boolean primerMovimiento = true;

    public Peon(String nombre, String color, String iniPosicion, String tipo) {
        super(nombre, color, iniPosicion, tipo);
    }

    @Override
    public Boolean valido(int[] posI, int[] posS, Casilla casilla) {
        if(casilla.getOcupada()){
            if(posI[1] == posS[1]-1 || posI[1] == posS[1]+1){
                primerMovimiento = false;
                moverPieza(posS);
                return true;
            }else{
                return false;
            }
        } else if (!primerMovimiento) {
            if(posI[1] == posS[1]+1){
                moverPieza(posS);
                return true;
            }else{
                return false;
            }
        } else if (primerMovimiento) {
            if(posI[1] == posS[1]+1 || posI[1] == posS[1]+2){
                primerMovimiento = false;
                moverPieza(posS);
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    @Override
    public void moverPieza(int[] posS) {
        String newPosInicial = "" + posS[0] + "" + posS[1];
        iniPosicion = newPosInicial;
    }
}
