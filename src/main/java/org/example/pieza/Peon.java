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
            if(!casilla.getPieza().getColor().equals(color) && !casilla.getPieza().getTipo().equals("Rey")){
                if(posI[1] == posS[1]-1 || posI[1] == posS[1]+1){
                    primerMovimiento = false;
                    //moverPieza(posS);
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        } else if (!primerMovimiento) {
            if(posS[0] == posI[0]+1){
                //moverPieza(posS);
                return true;
            }else{
                return false;
            }
        } else if (primerMovimiento) {
            if(posS[0] == posI[0]+1 || posS[0] == posI[0]+2){
                primerMovimiento = false;
                //moverPieza(posS);
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    @Override
    public void moverPieza(int[] posS) {
        String newPos = "" + posS[0] + "" + posS[1];
        actPosicion = newPos;
    }
}
