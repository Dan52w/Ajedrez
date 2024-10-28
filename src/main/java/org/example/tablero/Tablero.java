package org.example.tablero;

import org.example.pieza.*;

public class Tablero {
    private Casilla tablero[][];
    private String col = "N";
    private int fila;
    private int columna;
    private String nom;

    public Tablero() {
        tablero = new Casilla[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                fila = i + 1;
                columna = j + 1;
                nom = fila + "" + columna;
                if (columna != 1) {
                    col = altenarColor();
                }
                Pieza pieza = crearPieza(fila, columna, col, nom);
                if(pieza != null) {
                    tablero[i][j] = new Casilla(nom, col, pieza, true);
                }else{
                    tablero[i][j] = new Casilla(nom, col, pieza);
                }
            }
        }
    }

    public Boolean validarMovimiento(String posIni, String posSig) {
        boolean validar = false;
        int[] posI = descomponer(posIni);
        int[] posS = descomponer(posSig);
        Casilla casilla1 = obtenerCasilla(posIni);
        Casilla casilla2 = obtenerCasilla(posSig);

        Pieza pieza = casilla1.getPieza();
        if(pieza instanceof Peon){
            if(pieza.valido(posI, posS, casilla2)){
                editarCasilla(posIni, desocuparCasilla(casilla1));
                editarCasilla(posSig, ocuparCasilla(casilla2, pieza));
                return true;
            }else{
                return false;
            }
        } else if (pieza instanceof Torre) {

        }
        return false;
    }

    public int[] descomponer(String sig){
        int[] movimientos = new int[2];
        char[] sig2 = sig.toCharArray();
        for (int i = 0; i < sig2.length; i++) {
            movimientos[i] = Character.getNumericValue(sig2[i]);
        }
        return movimientos;
    }

    public void editarCasilla(String pos, Casilla casilla){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(tablero[i][j].getNombre().equals(pos)){
                   tablero[i][j] = casilla;
                }
            }
        }
    }

    public Casilla desocuparCasilla(Casilla casilla){
        casilla.setOcupada(false);
        casilla.setPieza(null);
        return casilla;
    }

    public Casilla ocuparCasilla(Casilla casilla, Pieza pieza){
        casilla.setOcupada(true);
        casilla.setPieza(pieza);
        return casilla;
    }

    public Casilla obtenerCasilla(String pos){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(tablero[i][j].getNombre().equals(pos)){
                    return tablero[i][j];
                }
            }
        }
        return null;
    }

    public Pieza crearPieza(int fila, int columna, String color, String nom) {
        if(fila == 2 || fila == 7){
            return crearPeon(fila, columna, nom);
        } else if ((fila == 1 &&  (columna == 1 || columna == 8)) || (fila == 8 &&  (columna == 1 || columna == 8))) {
            return crearTorre(fila, columna, nom);
        } else if ((fila == 1 &&  (columna == 2 || columna == 7)) || (fila == 8 &&  (columna == 2 || columna == 7))) {
            return crearCaballo(fila, columna, nom);
        } else if ((fila == 1 &&  (columna == 3 || columna == 6)) || (fila == 8 &&  (columna == 3 || columna == 6))) {
            return crearAlfil(fila, columna, color, nom);
        } else if ((fila == 1 &&  columna == 5) || (fila == 8 && columna == 5)) {
            return crearReina(fila, columna, nom);
        } else if ((fila == 1 &&  columna == 4) || (fila == 8 && columna == 4)) {
            return crearRey(fila, columna, nom);
        }
        return null;
    }

    public Pieza crearRey(int fila, int columna, String nom) {
        Rey rey  = null;
        if(fila == 1 && columna == 5) rey = new Rey(nom, "Blanco", nom, "Rey");
        else rey = new Rey(nom, "Negro", nom, "Rey");
        return rey;
    }

    public Pieza crearReina(int fila, int columna, String nom) {
        Reina reina = null;
        if(fila == 1 && columna == 5) reina = new Reina(nom, "Blanco", nom, "Reina");
        else reina = new Reina(nom, "Negro", nom, "Reina");
        return reina;
    }

    public Pieza crearAlfil(int fila, int columna, String color, String nom) {
        Alfil alfil = null;
        if (fila == 1 && (columna == 3 || columna == 6)) {
            if(color.equals("N")){
                alfil = new Alfil(nom, "Blanco", nom, "AlfilN");
            }else {
                alfil = new Alfil(nom, "Blanco", nom, "AlfilB");
            }
        }else{
            if(color.equals("N")){
                alfil = new Alfil(nom, "Negro", nom, "AlfilN");
            }else {
                alfil = new Alfil(nom, "Negro", nom, "AlfilB");
            }
        }
        return alfil;
    }

    public Pieza crearCaballo(int fila, int columna, String nom) {
        Caballo caballo = null;
        if (fila == 1 && (columna == 2 || columna == 7)) {
            caballo = new Caballo(nom, "Blanco", nom, "Caballo");
        }else{
            caballo = new Caballo(nom, "Negro", nom, "Caballo");
        }
        return caballo;
    }

    public Pieza crearTorre(int fila, int columna, String nom) {
        Torre torre = null;
        if (fila == 1 && (columna == 1 || columna == 8)) {
            torre = new Torre(nom, "Blanco", nom, "Torre");
        }else{
            torre = new Torre(nom, "Negro", nom, "Torre");
        }
        return torre;
    }

    public Peon crearPeon(int fila, int columna, String nom) {
        Peon peon = null;
        if(fila == 2 || fila == 7){
            if(fila == 2){
                peon = new Peon(nom, "Blanco", nom, "Peon");
            }else {
                peon = new Peon(nom, "Negro", nom, "Peon");
            }

        }
        return peon;
    }

    public String altenarColor() {
        if (col.equals("N")) {
            col = "B";
        }else if (col.equals("B")) {
            col = "N";
        }
        return col;
    }

    public void imprimirTablero(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(tablero[i][j].getPieza() == null){
                    System.out.print("[" + tablero[i][j].getOcupada() + "]");
                }else {
                    System.out.print("[" + tablero[i][j].getOcupada() + "]");
                }
            }
            System.out.println();
        }
    }


}
