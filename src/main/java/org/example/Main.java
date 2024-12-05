package org.example;

import org.example.partida.Partida;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Partida partida = new Partida();
        Scanner scanner = new Scanner(System.in);
        partida.getTablero().imprimirTableroJuego();
        System.out.println("Introduce la posicion de la pieza que quieres mover: ");
        String posI = scanner.nextLine();
        System.out.println("Introduce a donde la quieres mover: ");
        String posS = scanner.nextLine();
        partida.jugar(posI, posS);

    }
}
