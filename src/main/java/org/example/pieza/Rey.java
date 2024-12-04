package org.example.pieza;

import org.example.tablero.Casilla;

import java.util.List;

public class Rey extends Pieza{
    public Rey(String nombre, String color, String iniPosicion, String tipo) {
        super(nombre, color, iniPosicion, tipo);
    }

    @Override
    public Boolean valido(int[] posI, int[] posS, List<Casilla> casillaList) {
        if((posI[0] > 0 && posI[0] < 9)
                && (posI[1] > 0 && posI[1] < 9)
                && (posS[0] > 0 && posS[0] < 9)
                && (posS[1] > 0 && posS[1] < 9)) { //Validar que el peon si se este moviendo dentro del tablero y no por fuera de este
            Casilla casilla = casillaList.getFirst();
            if(casilla.getOcupada()) { //Para validar los casos en donde se capturen piezas
                if (!casilla.getPieza().getColor().equals(color) && !casilla.getPieza().getTipo().equals("Rey")) {
                    return comprobarCasillas(casillaList, posI, posS);
                }
            }
            return comprobarCasillas(casillaList, posI, posS);
        }
        return false;
    }

    @Override
    public Boolean comprobarCasillas(List<Casilla> casillaList, int[] posI, int[] posS) {
        int i = 0;
        for (Casilla c: casillaList) {
            System.out.println("Casillas " + c.getNombre() + " " + c.getOcupada());
        }
        for (Casilla casilla : casillaList) {
            System.out.println("Casilla " + i + " " + casilla.getNombre() + " " + casilla.getOcupada());
            if (i != 0){
                if (casilla.getOcupada() && casilla.getNombre().equals(casillaList.getFirst().getNombre())){
                    if (casilla.getPieza().getColor().equals(color)){
                        return false;
                    }
                    return true;
                }
                if (casilla.getNombre().equals(casillaList.getFirst().getNombre())) {
                    return true;
                }
            }
            i++;
        }
        return false;
    }

    @Override
    public void moverPieza(int[] posS) {
        super.moverPieza(posS);
    }
}
