package org.example.tablero;

import org.example.pieza.Peon;
import org.example.pieza.Pieza;
import org.example.pieza.Torre;

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
                tablero[i][j] = new Casilla(nom, col, pieza);
            }
        }
    }

    public Pieza crearPieza(int fila, int columna, String color, String nom) {
        if(fila == 2 || fila == 7){
            return crearPeon(fila, columna, nom);
        } else if ((fila == 1 &&  (columna == 1 || columna == 8)) || (fila == 8 &&  (columna == 1 || columna == 8))) {
            return crearTorre(fila, columna, nom);
        }
        return null;
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
                    System.out.print("[" + tablero[i][j].getNombre() + "]");
                }else {
                    System.out.print("[" + tablero[i][j].getPieza().getTipo() + "]");
                }
            }
            System.out.println();
        }
    }
}
