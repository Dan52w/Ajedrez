package org.example.tablero;

import org.example.pieza.*;

import java.util.ArrayList;
import java.util.List;

public class Tablero {
    private Casilla tablero[][];
    private String col = "N";
    private int fila;
    private int columna;
    private String nom;

    public Tablero() {
        tablero = new Casilla[8][8]; //Creamos el tablero de 8x8 con todas las casillas
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) { //Para recorrer todo el tablero
                fila = i + 1;
                columna = j + 1;
                nom = fila + "" + columna; //El i/j + 1, es porque el i/j empiezan en cero y esto es para contar el nombre desde el 1 hacia arriba
                if (columna != 1) { //La primera colum siempre sera del mismo color que la ultima colum de la fila anterior y todas empiezan por 1
                    col = altenarColor(); //Llamamos el metodo para cambiar el color de la casilla, asi podremos tener todo alternado
                }
                //Pieza es una clase padre de las piezas peon, caballo, etc...
                Pieza pieza = crearPieza(fila, columna, col, nom); //Llamamos el metodo crearPieza que nos creara cualquier Piez que necesitemos del tablero inicial.¿
                if(pieza != null) { //En casillas tenemos un atributo llamado "ocupada" si alguna pieza esta alli, en caso de que se le pase una pieza a esa casilla
                    tablero[i][j] = new Casilla(nom, col, pieza, true); //se llama al Constructor que nos permita especificar que si lo esta
                }else{
                    tablero[i][j] = new Casilla(nom, col, pieza); //Aqui si la casilla no se le pasa una pieza != de null, no necesita especificar que esta vacia.
                }
            }
        }
    }

    public Boolean validarMovimiento(String posIni, String posSig) { //Sin comentar por el momento, ya que aun hay que hacer ajustes.
        boolean validar = false;
        int[] posI = descomponer(posIni); //Convertimos los String en enteros para poder trabajar la validacion
        int[] posS = descomponer(posSig);
        Casilla casilla1 = obtenerCasilla(posIni); //Obtenemos las casillas para despues poder editarlas
        Casilla casilla2 = obtenerCasilla(posSig);
        List<Casilla> casillas = new ArrayList<>(); //Creamos la lista para guardar una o mas casillas en caso de ser necesario
        Pieza pieza = casilla1.getPieza();

        if(pieza instanceof Peon){ //En Caso de que sea un peon hacer
            casillas.add(casilla2); //Add la casilla2 para asegurarnos de que sea la primera en entrar.
            int p = posI[0]-1;
            casillas = obtenerCasillasVerticales(p, casillas);
            System.out.println(casillas.get(4).getNombre());
            if(pieza.valido(posI, posS, casillas)){
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

    public int[] descomponer(String sig){ //Ya que los datos se estan pasando por String, necesitamos pasarlos como enteros en una matriz
        int[] movimientos = new int[2];
        char[] sig2 = sig.toCharArray(); //Aqui descomponemos el String en char en un vector,
        for (int i = 0; i < sig2.length; i++) {
            movimientos[i] = Character.getNumericValue(sig2[i]); //Aqui convertimos de Char a entero y lo guardamos en una matriz
        }
        return movimientos;
    }

    public void editarCasilla(String pos, Casilla casilla){ //Cuando hacemos un moviento necestamos editar esa casilla
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) { //Para buscar la casilla la cual se va a editar
                if(tablero[i][j].getNombre().equals(pos)){ //Si la casilla tiene el mismo nombre de la casilla que se busca
                   tablero[i][j] = casilla; //Si si asignamos cambiamos/editamos esa casilla
                }
            }
        }
    }

    public Casilla desocuparCasilla(Casilla casilla){ //cuando necesitamos movemos una pieza la
        casilla.setOcupada(false); //anterior casilla debe estar desocupada
        casilla.setPieza(null); //y su pieza null
        return casilla;
    }

    public Casilla ocuparCasilla(Casilla casilla, Pieza pieza){ //Cuando movemos una pieza la
        casilla.setOcupada(true); //Se debe ocupar
        casilla.setPieza(pieza); //y se debe pasar la pieza que van a tener
        return casilla;
    }

    public Casilla obtenerCasilla(String pos){ //Cuando necesitamos obtener una casilla para editarlo o lo que sea necesario hacer
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(tablero[i][j].getNombre().equals(pos)){ //Si el nombre de la casilla es igual a el de pos (Posicion)
                    return tablero[i][j]; //Retornarmo esa casilla
                }
            }
        }
        return null; //Si no la encuentra
    }

    public List<Casilla> obtenerCasillasVerticales(int columna, List<Casilla> casillas) { //Al momento de mover una torre necesitamos toda la fila en la que se mueve
        for (int i = 0; i < 8; i++) { //Se encargara de recorrer toda la fila de y agregarla a la lista
            casillas.add(tablero[i][columna]);
        }
        return casillas; //Retornamos
    }

    public List<Casilla> obtenerCasillasHorizontales(int fila) { //Al momento de mover una torre necesitamos toda la columa en la que se mueve
        List<Casilla> casillas = new ArrayList<>(); //Creamos una lista de Casillas que regresaremos.
        for (int i = 0; i < 8; i++) { //Se encargara de recorrer toda la fila de y agregarla a la lista
            casillas.add(tablero[fila][i]);
        }
        return casillas; //Retornamos
    }

    public Pieza crearPieza(int fila, int columna, String color, String nom) { //Esta funcion crea una pieza en el TableroInicial, dependiendo las pos que se le pasen
        if(fila == 2 || fila == 7){ //Aqui validamos que esten en la fila 2/7 ya son los lugares donde se encuentran los peones
            return crearPeon(fila, columna, nom);
        } else if ((fila == 1 &&  (columna == 1 || columna == 8))
                || (fila == 8 &&  (columna == 1 || columna == 8))) { //Validamos Fila 1/8 y Columbas 1/8, que son donde van las torres
            return crearTorre(fila, columna, nom);
        } else if ((fila == 1 &&  (columna == 2 || columna == 7))
                || (fila == 8 &&  (columna == 2 || columna == 7))) { //Validamos Fila 1/8 y Columbas 2/7, que son donde van las caballos
            return crearCaballo(fila, columna, nom);
        } else if ((fila == 1 &&  (columna == 3 || columna == 6))
                || (fila == 8 &&  (columna == 3 || columna == 6))) { //Validamos Fila 1/8 y Columbas 3/6, que son donde van las alfiles
            return crearAlfil(fila, columna, color, nom);
        } else if ((fila == 1 &&  columna == 5)
                || (fila == 8 && columna == 5)) { //Validamos Fila 1/8 y Columbas 5, que es donde va la reina
            return crearReina(fila, columna, nom);
        } else if ((fila == 1 &&  columna == 4)
                || (fila == 8 && columna == 4)) { //Validamos Fila 1/8 y Columbas 4, que es donde va el rey
            return crearRey(fila, columna, nom);
        }
        return null;
    }

    public Pieza crearRey(int fila, int columna, String nom) {
        Rey rey  = null; //Aqui validamos si el rey es Blanco o negro
        if(fila == 1 && columna == 5) rey = new Rey(nom, "Blanco", nom, "Rey");
        else rey = new Rey(nom, "Negro", nom, "Rey");
        return rey;
    }

    public Pieza crearReina(int fila, int columna, String nom) {
        Reina reina = null; //Aqui validamos si la reina es Blanco o negro
        if(fila == 1 && columna == 5) reina = new Reina(nom, "Blanco", nom, "Reina");
        else reina = new Reina(nom, "Negro", nom, "Reina");
        return reina;
    }

    public Pieza crearAlfil(int fila, int columna, String color, String nom) {
        Alfil alfil = null; //Aqui validamos si los alfiles son blanco o negros
        if (fila == 1 && (columna == 3 || columna == 6)) {
            if(color.equals("N")){ //Para saber que tipo de alfil hay que poner
                alfil = new Alfil(nom, "Blanco", nom, "AlfilN");
            }else { //Ya que los alfiles pueden ser de la casilla blana o negras
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
        Caballo caballo = null; //Aqui validamos si los caballos son blanco o negros
        if (fila == 1 && (columna == 2 || columna == 7)) {
            caballo = new Caballo(nom, "Blanco", nom, "Caballo");
        }else{
            caballo = new Caballo(nom, "Negro", nom, "Caballo");
        }
        return caballo;
    }

    public Pieza crearTorre(int fila, int columna, String nom) {
        Torre torre = null; //Aqui validamos si las torres son blanco o negros
        if (fila == 1 && (columna == 1 || columna == 8)) {
            torre = new Torre(nom, "Blanco", nom, "Torre");
        }else{
            torre = new Torre(nom, "Negro", nom, "Torre");
        }
        return torre;
    }

    public Peon crearPeon(int fila, int columna, String nom) {
        Peon peon = null; //Aqui validamos si los peones son blanco o negros
        if(fila == 2 || fila == 7){
            if(fila == 2){
                peon = new Peon(nom, "Blanco", nom, "Peon");
            }else {
                peon = new Peon(nom, "Negro", nom, "Peon");
            }

        }
        return peon;
    }

    public String altenarColor() { //Para altenernar el color de la casilla
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
                    System.out.print("[" + tablero[i][j].getNombre()+ "]");
                }else {
                    System.out.print("[" + tablero[i][j].getNombre() + "]");
                }
            }
            System.out.println();
        }
    }

    public void imprimirTableroOcupado(){
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
