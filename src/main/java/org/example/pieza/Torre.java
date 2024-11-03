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
            Casilla casilla = casillaList.get(0);
            if(casilla.getOcupada()){ //Validar que la casilla a la que estemos moviendo no tenga una pieza de nuestro color o sea un Rey
                if(!casilla.getPieza().getColor().equals(color) && !casilla.getPieza().getTipo().equals("Rey")){
                    if(comprobarCasillas(casillaList, posI, posS))return true;
                    return false;
                }
            }else{
                if(comprobarCasillas(casillaList, posI, posS)) return true;
                return false;
            }
        }
        return false;
    }

    @Override
    public Boolean comprobarCasillas(List<Casilla> casillaList, int[] posI, int[] posS) {
        if(posI[0] == posS[0]){ //Nos sirve para validar si se esta moviendo en las filas o columnas la torre
            return casillaOcupadaEntrePos(posI[1], posS[1], casillaList);
        } else if (posI[1] == posS[1]) { //Aqui en adelante haremo lo mismo que arriba solo que se hace de forma Horizontal
            return casillaOcupadaEntrePos(posI[0], posS[0], casillaList);
        }
        return false;
    }

    public Boolean casillaOcupadaEntrePos(int posI, int posS, List<Casilla> casillaList) {
        int i; //Creamos una variable i que nos servira para asignarle los valores desde donde empezara en el for en la lista
        if(posI > posS){ //Validamos en que direccion se esta moviendo
            for(i = posS+1; i < posI; i++){ //En caso de que la pos sea menor, le sumamos 1 a i para que no la tenga encuenta
                if(casillaList.get(i).getOcupada()) return false; //Retornamos Falso en caso de hallar una casilla ocupada
            }
            return true;
        }else{
            for(i = posI; i < posS; i++){
                if(casillaList.get(i).getOcupada()) return false;
            }
            return true;
        }
    }

    @Override
    public void moverPieza(int[] posS) {
        super.moverPieza(posS);
    }
}
