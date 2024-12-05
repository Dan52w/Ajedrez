package org.example.partida;

import org.example.tablero.Tablero;

import java.util.Scanner;

public class Partida {
    private Jugador jugador1; //Ya que son dos jugadores necesitamos dos
    private Jugador jugador2;
    private int turno; //Para validar a que jugador le corresponde y que piezas poder mover
    private Tablero tablero;

    public Partida() {
        jugador1 = new Jugador("Jugador1", "Blanco");
        jugador2 = new Jugador("Jugador2", "Negro");
        turno = 1;
        tablero = new Tablero();
    }

    public void jugar(String pos1, String pos2) { //Llamamos a la funcion para poder iniciar la partida
        Scanner scanner = new Scanner(System.in); //Para poder obtener las posiciones de la terminal
        String juega = ""; //Para identificar el color de la pieza que debe mover
        int trn = turno;
        if (trn == 1) { //Si el turno es 1 Juega las blancas
            juega = jugador1.getJuegaCon();
        } else if (trn == 2) { //Si el turno es dos juega las Negras
            juega = jugador2.getJuegaCon();
        }
        if (trn == turno) { //Validar que no sea un turno distinto
            if (tablero.obtenerCasilla(pos1).getOcupada()) { //Para validar que en esa posicion si haya una pieza
                if (tablero.obtenerCasilla(pos1).getPieza().getColor().equals(juega)) { //Que esa pieza si sea del color que corresponde jugar
                    System.out.println("Color de pieza correcto en " + pos1);
                    boolean movimientoValido = tablero.validarMovimiento(pos1, pos2); //Validar que el moviento sea verdadero
                    System.out.println("Movimiento valido: " + movimientoValido);
                    if (movimientoValido) { //Si el movimiento es verdadero hacer
                        tablero.imprimirTableroJuego(); //Imprimimos el nuevo tablero
                        alternarTurno(); //Alterarmos el turno
                    } else {
                        System.out.println("Introduzca un movimiento valido"); //En caso de que el movimiento no se pueda ejecutar
                    }
                } else {
                    System.out.println("Tome una pieza del color " + juega); //En caso de que tomo una pieza que no es
                }
            } else {
                System.out.println("Escoja una pieza de color " + juega + " para mover"); //En caso de que tomo una pieza que no es
            }
        } else {
            System.out.println("No es el turno del jugador");
        }
        System.out.println("Introduce la posicion de la pieza que quieres mover: ");
        pos1 = scanner.nextLine(); //Para obtener la pos de la pieza que se desea mover
        System.out.println("Introduce a donde la quieres mover: ");
        pos2 = scanner.nextLine(); //Para obtener la pos a donde se quiere mover la pieza
        jugar(pos1, pos2); //Llamado recursivo del metodo
    }


    public void alternarTurno(){
        if(turno == 1){
            turno = 2;
        } else if (turno == 2) {
            turno = 1;
        }
    }

    public Jugador getJugador1() {
        return jugador1;
    }

    public void setJugador1(Jugador jugador1) {
        this.jugador1 = jugador1;
    }

    public Jugador getJugador2() {
        return jugador2;
    }

    public void setJugador2(Jugador jugador2) {
        this.jugador2 = jugador2;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }
}
