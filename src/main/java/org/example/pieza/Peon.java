package org.example.pieza;

import org.example.tablero.Casilla;

import java.util.List;

public class Peon extends Pieza{
    private boolean primerMovimiento = true;

    public Peon(String nombre, String color, String iniPosicion, String tipo) {
        super(nombre, color, iniPosicion, tipo);
    }

    @Override
    public Boolean valido(int[] posI, int[] posS, List<Casilla> casillaList) {
        if((posI[0] > 0 && posI[0] < 9)
                && (posI[1] > 0 && posI[1] < 9)
                && (posS[0] > 0 && posS[0] < 9)
                && (posS[1] > 0 && posS[1] < 9)){ //Validar que el peon si se este moviendo dentro del tablero y no por fuera de este
            Casilla casilla = casillaList.get(0);
            if(casilla.getOcupada()){ //Para validar los casos en donde se capturen piezas
                if(!casilla.getPieza().getColor().equals(color) && !casilla.getPieza().getTipo().equals("Rey")){
                    if((posI[1] == posS[1]-1 || posI[1] == posS[1]+1) //Validar si es hacia la derecha o izquierda
                            && posI[0] != posS[0] // validar que la pieza no este al frente
                            && (posI[0] == posS[0]-1 || posI[0] == posS[0]+1)){ //Validar que el peon no se mueva mas de una casilla hacia arriba
                        primerMovimiento = false;
                        return true;
                    }else{
                        return false;
                    }
                }else{
                    return false;
                }
            } else if (!primerMovimiento) {
                if((posS[0] == posI[0]+1) || (posS[0] == posI[0]-1) && posS[1] == posI[1]){ //Validar que el peon solo se mueva una casilla
                    return true;
                }else{
                    return false;
                }
            } else if (primerMovimiento) { //En caso de que sea el primer movimiento del peon permitirle mover dos casilla
                if((posS[0] == posI[0]+1 || posS[0] == posI[0]+2) || (posS[0] == posI[0]-1 || posS[0] == posI[0]-2) && posI[1] == posS[1]){
                    Casilla c = buscarCasilla(casillaList, posI); //Obtenemos la casilla
                    if(c.getOcupada()){
                        return false; //Por si el peon salta dos casilla y la casilla intermedia este ocupada retorna false
                    } else{
                        primerMovimiento = false; //En caso de que no este ocupada permitir el movimiento y retornar true
                    }
                    return true;
                }else {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public Casilla buscarCasilla(List<Casilla> casillaList, int[] pos) {
        int i = 0;
        if(color.equals("Negro")){
            i = pos[0]-1;
        } else if (color.equals("Blanco")) {
            i = pos[0]+1;
        }
        String p = "" + i + "" + pos[1]; //Para obtener la casilla del frente
        for (Casilla c : casillaList) {
            if(c.getNombre().equals(p)){
                return c;
            }
        }
        return null;
    }

    @Override
    public void moverPieza(int[] posS) {
        String newPos = "" + posS[0] + "" + posS[1];
        actPosicion = newPos;
    }
}
