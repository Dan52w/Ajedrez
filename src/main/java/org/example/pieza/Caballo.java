package org.example.pieza;

import org.example.tablero.Casilla;

import java.util.List;

public class Caballo extends Pieza{
    public Caballo(String nombre, String color, String iniPosicion, String tipo) {
        super(nombre, color, iniPosicion, tipo);
    }

    @Override
    public Boolean valido(int[] posI, int[] posS, List<Casilla> casillaList) {
        if((posI[0] > 0 && posI[0] < 9)
                && (posI[1] > 0 && posI[1] < 9)
                && (posS[0] > 0 && posS[0] < 9)
                && (posS[1] > 0 && posS[1] < 9)){ //Validar que el peon si se este moviendo dentro del tablero y no por fuera de este
            Casilla casilla = casillaList.get(0);
            if(casilla.getOcupada()) { //Para validar los casos en donde se capturen piezas
                if (!casilla.getPieza().getColor().equals(color) && !casilla.getPieza().getTipo().equals("Rey")) {
                    return comprobarCasilla(casillaList, casilla); //Llamamos un metodo para ahorrar codigo
                }
                return false;
            }
            return comprobarCasilla(casillaList, casilla);
        }
        return false;
    }

    public boolean comprobarCasilla(List<Casilla> casillaList, Casilla nombreCasilla) {
        int i = 0; //Ya que el primero en el array sera siempre la casilla que se va a mover, tendremos que omitirlo en el for
        for (Casilla casilla : casillaList) {
            if (casilla.getNombre().equals(nombreCasilla.getNombre()) && i != 0) { //Aqui recorremos la lista para verificar que la casilla si este
                return true; //En caso de que la casilla si este en la lista retornaremos true
            }
            i++;
        }
        return false; //En caso de que no este, retornaremos false
    }

    @Override
    public void moverPieza(int[] posS) {
        super.moverPieza(posS);
    }
}
