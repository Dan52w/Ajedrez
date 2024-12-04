package org.example.pieza;

import org.example.tablero.Casilla;

import java.util.List;

public class Reina extends Pieza{
    public Reina(String nombre, String color, String iniPosicion, String tipo) {
        super(nombre, color, iniPosicion, tipo);
    }

    @Override
    public Boolean valido(int[] posI, int[] posS, List<Casilla> casillaList) {
        if((posI[0] > 0 && posI[0] < 9)
                && (posI[1] > 0 && posI[1] < 9)
                && (posS[0] > 0 && posS[0] < 9)
                && (posS[1] > 0 && posS[1] < 9)) { //Validar que el Alfil si se este moviendo dentro del tablero y no por fuera de este
            Casilla casilla = casillaList.getFirst();
            if (casilla.getOcupada()) { //Para validar los casos en donde se capturen piezas
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
        Casilla casilla = casillaList.getFirst();
        if (posI[0] == posS[0]) {
            return casillaOcupadaEntrePosHoriYVert(posI[1], posS[1], casillaList);
        } else if (posI[1] == posS[1]) {
            return casillaOcupadaEntrePosHoriYVert(posI[0], posS[0], casillaList);
        } else if (posI[0] > posS[0]) {
            return comprobarCasillasAbajo(casillaList, casilla, posI);
        } else if (posI[0] < posS[0]) {
            return comprobarCasillasArriba(casillaList, casilla, posI);
        }
        return false;
    }

    private Boolean casillaOcupadaEntrePosHoriYVert(int posI, int posS, List<Casilla> casillaList) {
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

    private Boolean comprobarCasillasAbajo(List<Casilla> casillaList, Casilla casilla, int[] posI) {
        int i = 0; //Para validar que no tome al primero que este siempre sera la casilla donde debe dirigirse
        int[] mov; //Vector donde guardaremos las pos de cada casilla
        for (Casilla c : casillaList) {
            if(i != 0){ //Validar que no tome el primero
                mov = descomponer(c.getNombre()); //Llamamos el metodo descomponer que nos retornara un vector de 2
                if (mov[0] < posI[0]) { //Como nos movemos hacia abajo la fila sig debe ser siempre menor que la fila Ini
                    if(c.getOcupada() && !c.getNombre().equals(casilla.getNombre())){
                        return false; //En caso de que alguna casilla esta ocupada y sea distinta de la casilla Sig el movimiento no debe ser valido
                    } else if (c.getNombre().equals(casilla.getNombre())) {
                        return true; //Pero en caso de que este ocupada y si sea la casilla sig el movimiento se ejecuta
                    }
                }
            }
            i++;
        }
        return false;
    }
    //Muchas de las carracteristicas de este son iguales a las de arriba asi que se comentaran las diferencias
    private Boolean comprobarCasillasArriba(List<Casilla> casillaList, Casilla casilla, int[] posI) {
        int i = 0;
        int[] mov;
        for (Casilla c : casillaList) {
            if(i != 0){
                mov = descomponer(c.getNombre());
                if (posI[0] <= mov[0]) { //Como nos movemos hacia arriba la fila sig debe ser siempre mayor que la fila Ini
                    if(c.getOcupada() && !c.getNombre().equals(casilla.getNombre())){
                        return false;
                    } else if (c.getNombre().equals(casilla.getNombre())) {
                        return true;
                    }
                }
            }
            i++;
        }
        return false;
    }

    public int[] descomponer(String nom){
        int[] movimientos = new int[2];
        char[] nom2 = nom.toCharArray(); //Aqui descomponemos el String en char en un vector,
        for (int i = 0; i < nom2.length; i++) {
            movimientos[i] = Character.getNumericValue(nom2[i]); //Aqui convertimos de Char a entero y lo guardamos en una matriz
        }
        return movimientos;
    }

    @Override
    public void moverPieza(int[] posS) {
        super.moverPieza(posS);
    }
}
