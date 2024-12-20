package org.example.tablero;

import org.example.pieza.*;

import java.util.ArrayList;
import java.util.List;

public class Tablero {
    private Casilla[][] tablero;
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

    public Boolean validarMovimiento(String posIni, String posSig) {
        int[] posI = descomponer(posIni); //Convertimos los String en enteros para poder trabajar la validacion
        int[] posS = descomponer(posSig);
        Casilla casilla1 = obtenerCasilla(posIni); //Obtenemos las casillas para despues poder editarlas
        Casilla casilla2 = obtenerCasilla(posSig);
        List<Casilla> casillas = new ArrayList<>(); //Creamos la lista para guardar una o mas casillas en caso de ser necesario
        casillas.add(casilla2); //Add la casilla2 para asegurarnos de que sea la primera en entrar y ubicarlo con .get(0).
        Pieza pieza = casilla1.getPieza(); //Extraemos la pieza, para poder identificar que tipo de pieza es
        int p;

        if(pieza instanceof Peon){ //En caso de que la pieza sea un peon hacer:
            p = posI[1]-1; //Asegurarnos de que pasara toda la Columna del peon
            casillas = obtenerCasillasVerticales(p, casillas); //Obtenemos todas la casilla vertical para poder mover el peon
            if(pieza.valido(posI, posS, casillas)){ //Validar el movimiento
                editarCasilla(posIni, desocuparCasilla(casilla1)); //Si el movimiento es verdadero desocupar la casilla del peon
                editarCasilla(posSig, ocuparCasilla(casilla2, pieza)); //Aqui ocupamos la nueva casilla donde estara el peon
                return true; //Retornamos True para indicar que se hizo el movimiento
            }
            return false; //En caso de que el movimiento sea invalido, retornamos falo
        } else if (pieza instanceof Torre) { //En caso de que la pieza sea una torre hacer:
            if(posI[1] == posS[1]){ //Validamos en que direccion se esta moviendo la Torre (si en vertical o horizontal)
                p = posI[1]-1; //Ya que el tablero es una matriz y cada pos esta en +1, le restamos ese uno a la columna para obtener la columna correcta
                casillas = obtenerCasillasVerticales(p, casillas); //Como la torre en este caso se mueve en vertical llamamos a un metodo que nos obtenga esos datos
            } else if (posI[0] == posS[0]) { //En caso de que la torre se este moviendo en Horizontal
                p = posI[0]-1; //Ya que el tablero es una matriz y cada pos esta en +1, le restamos ese uno a la fila para obtener la fila correcta
                casillas = obtenerCasillasHorizontales(p, casillas); //Llamamos a un metodo que nos obtenga las casillas horizontales
            } else return false; //Por si ninguna de las dos omitimos todo y nos retorna falso
            if(pieza.valido(posI, posS, casillas)){ //Si el movimiento es valido desocupamos una casilla y ocupamos la otra
                editarCasilla(posIni, desocuparCasilla(casilla1));
                editarCasilla(posSig, ocuparCasilla(casilla2, pieza));
                return true;
            }
        } else if (pieza instanceof Caballo) { //En caso de que la pieza sea un Caballo
            casillas = obtenerLCasillas(posI[0]-1, posI[1]-1, casillas); //El caballo se mueve en L, asi que adiferencia de las otras, solo llamamos al metodo
            if (pieza.valido(posI, posS, casillas)){ //Si el movimiento es valido desocupamos una casilla y ocupamos la otra
                editarCasilla(posIni, desocuparCasilla(casilla1));
                editarCasilla(posSig, ocuparCasilla(casilla2, pieza));
                return true;
            }
        } else if (pieza instanceof Alfil) { //Imaginen que el alfil es el punto 0.0 del plano cartesiano
            if(posI[0] < posS[0]){ //Lo que haremos es dividirlo en 4 partes, esta parte miramos que la fila sea "Positiva"
                if (posI[1] < posS[1]){ //Tanto columna y fila son "Positivas" por ende hay que sumar
                    casillas = obtenerCasillasDiagonalArribaDerecha(posI[0], posI[1], casillas);
                } else if (posI[1] > posS[1]) { //La columna es "Negativa" por ende hay que restar
                    casillas = obtenerCasillasDiagonalArribaIzquierda(posI[0], posI[1], casillas);
                }
            } else if (posI[0] > posS[0]) { //Esta parte miramos que la fila sea "Negativa"
                if (posI[1] < posS[1]){
                    casillas = obtenerCasillasDiagonalAbajoDerecha(posI[0], posI[1], casillas);
                } else if (posI[1] > posS[1]) {
                    casillas = obtenerCasillasDiagonalAbajoIzquierda(posI[0], posI[1], casillas);
                }
            }else return false; //Por si ninguna de las dos omitimos todo y nos retorna falso
            if (pieza.valido(posI, posS, casillas)){
                editarCasilla(posIni, desocuparCasilla(casilla1));
                editarCasilla(posSig, ocuparCasilla(casilla2, pieza));
                return true;
            }
        } else if (pieza instanceof Reina) { //En caso de ser Reina hacer:
            //La reina se mueve en horizontal, vertical y diagonal, asi que validamos de que manera se esta moviendo
            if (posI[0] == posS[0]) {
                p = posI[0]-1;
                casillas = obtenerCasillasHorizontales(p, casillas);
            } else if (posI[1] == posS[1]) {
                p = posI[1]-1;
                casillas = obtenerCasillasVerticales(p, casillas);
            } else if (posI[1] < posS[1]){ //Tanto columna y fila son "Positivas" por ende hay que sumar
                casillas = obtenerCasillasDiagonalArribaDerecha(posI[0], posI[1], casillas);
            } else if (posI[1] > posS[1]) { //La columna es "Negativa" por ende hay que restar
                casillas = obtenerCasillasDiagonalArribaIzquierda(posI[0], posI[1], casillas);
            } else if (posI[1] < posS[1]){
                casillas = obtenerCasillasDiagonalAbajoDerecha(posI[0], posI[1], casillas);
            } else if (posI[1] > posS[1]) {
                casillas = obtenerCasillasDiagonalAbajoIzquierda(posI[0], posI[1], casillas);
            }else return false; //Por si ninguna de las dos omitimos todo y nos retorna falso
            if (pieza.valido(posI, posS, casillas)){
                editarCasilla(posIni, desocuparCasilla(casilla1));
                editarCasilla(posSig, ocuparCasilla(casilla2, pieza));
                return true;
            }
        } else if (pieza instanceof Rey) {
            casillas = obtenerCasilla3x3(posI[0]-1, posI[1]-1, casillas);
            if(pieza.valido(posI, posS, casillas)){
                editarCasilla(posIni, desocuparCasilla(casilla1));
                editarCasilla(posSig, ocuparCasilla(casilla2, pieza));
                return true;
            }
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

    public List<Casilla> obtenerCasillasHorizontales(int fila, List<Casilla> casillas) { //Al momento de mover una torre necesitamos toda la columa en la que se mueve
        for (int i = 0; i < 8; i++) { //Se encargara de recorrer toda la fila de y agregarla a la lista
            casillas.add(tablero[fila][i]);
        }
        return casillas; //Retornamos
    }

    public List<Casilla> obtenerLCasillas(int fila, int columna, List<Casilla> casillas) {
        if (esCasillaValida(fila + 2, columna + 1)) //Validamos en L, y como siempre sumamos  o restastamos dos y 1, debemos ver que la casilla exista
            if (tablero[fila + 2][columna + 1] != null) casillas.add(tablero[fila + 2][columna + 1]);
        if (esCasillaValida(fila + 2, columna - 1))
            if (tablero[fila + 2][columna - 1] != null) casillas.add(tablero[fila + 2][columna - 1]);
        if (esCasillaValida(fila - 2, columna + 1))
            if (tablero[fila - 2][columna + 1] != null) casillas.add(tablero[fila - 2][columna + 1]);
        if (esCasillaValida(fila - 2, columna - 1))
            if (tablero[fila - 2][columna - 1] != null) casillas.add(tablero[fila - 2][columna - 1]);
        if (esCasillaValida(fila + 1, columna + 2))
            if (tablero[fila + 1][columna + 2] != null) casillas.add(tablero[fila + 1][columna + 2]);
        if (esCasillaValida(fila - 1, columna + 2))
            if (tablero[fila - 1][columna + 2] != null) casillas.add(tablero[fila - 1][columna + 2]);
        if (esCasillaValida(fila + 1, columna - 2))
            if (tablero[fila + 1][columna - 2] != null) casillas.add(tablero[fila + 1][columna - 2]);
        if (esCasillaValida(fila - 1, columna - 2))
            if (tablero[fila - 1][columna - 2] != null) casillas.add(tablero[fila - 1][columna - 2]);
        return casillas;
    }

    public List<Casilla> obtenerCasillasDiagonalArribaDerecha(int fila, int columna, List<Casilla> casillas){
        int filaSig = fila + 1; //Le sumamos uno ya que vamos a obtener las siguientes casillas de la sig fila
        int columnaSig = columna + 1; //Le sumamos uno porque estos siempre seran mayores a la Pos de la pieza
        String nomb = ""; //Creamos un String, para iterar el nombre por cada vuelta que de el for
        for (int i = fila; i < 8; i++) {
            if (filaSig <= 8) { //Validamos que fila, no supere a 8 para poder buscar por nombres
                if (columnaSig <= 8) {
                    nomb = ""+filaSig + columnaSig;
                }
            }
            System.out.println("Nomb " + nomb);
            for (int j = 0; j < 8; j++){
                System.out.println("Tablero " + tablero[i][j].getNombre());
                if(tablero[i][j].getNombre().equals(nomb)) {
                    casillas.add(tablero[i][j]); //Si el nombre de la posicion del tablero es igual a alguno de los dos nomb lo agg a la List
                }
            }
            filaSig++; //Para buscar en la siguiente fila y poder alterar el nombre
            columnaSig++;
        }

        return casillas;
    }

    public List<Casilla> obtenerCasillasDiagonalArribaIzquierda(int fila, int columna, List<Casilla> casillas){
        int filaSig = fila + 1;
        int columnaSig = columna - 1; //Le restamos uno porque estos siempre seran menores a la Pos de la pieza
        String nomb = "";
        for (int i = fila; i < 8; i++) {
            if (filaSig <= 8) { //Validamos que fila, no supere a 8 para poder buscar por nombres
                if (columnaSig >= 0) {
                    nomb = ""+filaSig + columnaSig;
                }
            }
            for (int j = 0; j < 8; j++){
                if(tablero[i][j].getNombre().equals(nomb)) {
                    casillas.add(tablero[i][j]); //Si el nombre de la posicion del tablero es igual a alguno de los dos nomb lo agg a la List
                }
            }
            filaSig++;
            if (columna > 1){
                columnaSig--;
            }
        }

        return casillas;
    }
    //Muchas de las carracteristicas de este son iguales a las de arriba asi que se comentaran las diferencias
    public List<Casilla> obtenerCasillasDiagonalAbajoDerecha(int fila, int columna, List<Casilla> casillas){
        int filaSig = fila - 1; //Las filas siempre seran menores
        int columnaSig = columna + 1; //Le restamos uno porque estos siempre seran mayores a la Pos de la pieza
        String nomb = "";
        for (int i = fila-2; i >= 0; i--) {
            if (filaSig >= 0) { //Validamos que fila, no supere a 8 para poder buscar por nombres
                if (columnaSig <= 8) {
                    nomb = ""+filaSig + columnaSig;
                }
            }
            for (int j = columna; j < 8; j++){
                if(tablero[i][j].getNombre().equals(nomb)) {
                    casillas.add(tablero[i][j]); //Si el nombre de la posicion del tablero es igual a alguno de los dos nomb lo agg a la List
                }
            }
            filaSig--;
            columnaSig++;
        }

        return casillas;
    }

    public List<Casilla> obtenerCasillasDiagonalAbajoIzquierda(int fila, int columna, List<Casilla> casillas){
        int filaSig = fila-1;
        int columnaSig = columna - 1; //Le restamos uno porque estos siempre seran menores a la Pos de la pieza
        String nomb = "";
        for (int i = fila-2; i >= 0; i--) {
            if (filaSig >= 0) { //Validamos que fila, no supere a 8 para poder buscar por nombres
                if (columnaSig > 0) {
                    nomb = ""+filaSig + columnaSig;
                }
            }
            for (int j = 0; j < 8; j++){
                if(tablero[i][j].getNombre().equals(nomb)) {
                    casillas.add(tablero[i][j]); //Si el nombre de la posicion del tablero es igual a alguno de los dos nomb lo agg a la List
                }
            }
            if (filaSig > 1){
                filaSig--;
            }
            if (columna > 1){
                columnaSig--;
            }
        }

        return casillas;
    }

    public List<Casilla> obtenerCasilla3x3(int fila, int columna, List<Casilla> casillas){ //Para obtener todas las casillas del alrededor de la pieza
        if (esCasillaValida(fila-1, columna)) casillas.add(tablero[fila-1][columna]);
        if (esCasillaValida(fila-1, columna-1)) casillas.add(tablero[fila-1][columna-1]);
        if (esCasillaValida(fila-1, columna+1)) casillas.add(tablero[fila-1][columna+1]);
        if (esCasillaValida(fila, columna-1)) casillas.add(tablero[fila][columna-1]);
        if (esCasillaValida(fila, columna+1)) casillas.add(tablero[fila][columna-1]);
        if (esCasillaValida(fila+1, columna)) casillas.add(tablero[fila+1][columna]);
        if (esCasillaValida(fila+1, columna-1)) casillas.add(tablero[fila+1][columna-1]);
        if (esCasillaValida(fila+1, columna+1)) casillas.add(tablero[fila+1][columna+1]);
        return casillas;
    }

    private boolean esCasillaValida(int fila, int columna) { //Esta funcion esta echa para validar que las casillas si esten entre los rango asignados
        return (fila >= 0 && fila < 8) && (columna >= 0 && columna < 8);
    }

    public Pieza crearPieza(int fila, int columna, String color, String nom) { //Esta funcion crea una pieza en el TableroInicial, dependiendo las pos que se le pasen
        if(fila == 2 || fila == 7){ //Aqui validamos que esten en la fila 2/7 ya son los lugares donde se encuentran los peones
            return crearPeon(fila, nom);
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
        Rey rey; //Aqui validamos si el rey es Blanco o negro
        if(fila == 1 && columna == 4) rey = new Rey(nom, "Blanco", nom, "Rey");
        else rey = new Rey(nom, "Negro", nom, "Rey");
        return rey;
    }

    public Pieza crearReina(int fila, int columna, String nom) {
        Reina reina; //Aqui validamos si la reina es Blanco o negro
        if(fila == 1 && columna == 5) reina = new Reina(nom, "Blanco", nom, "Reina");
        else reina = new Reina(nom, "Negro", nom, "Reina");
        return reina;
    }

    public Pieza crearAlfil(int fila, int columna, String color, String nom) {
        Alfil alfil; //Aqui validamos si los alfiles son blanco o negros
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
        Caballo caballo; //Aqui validamos si los caballos son blanco o negros
        if (fila == 1 && (columna == 2 || columna == 7)) {
            caballo = new Caballo(nom, "Blanco", nom, "Caballo");
        }else{
            caballo = new Caballo(nom, "Negro", nom, "Caballo");
        }
        return caballo;
    }

    public Pieza crearTorre(int fila, int columna, String nom) {
        Torre torre; //Aqui validamos si las torres son blanco o negros
        if (fila == 1 && (columna == 1 || columna == 8)) {
            torre = new Torre(nom, "Blanco", nom, "Torre");
        }else{
            torre = new Torre(nom, "Negro", nom, "Torre");
        }
        return torre;
    }

    public Peon crearPeon(int fila, String nom) {
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

    public void imprimirTableroJuego(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(tablero[i][j].getPieza() == null){
                    System.out.print("[  " + tablero[i][j].getNombre() + " " + tablero[i][j].getColor()+ "  ]");
                }else {
                    System.out.print("[ " + tablero[i][j].getPieza().getTipo() + tablero[i][j].getNombre() + " ]");
                }
            }
            System.out.println();
        }
    }
}
