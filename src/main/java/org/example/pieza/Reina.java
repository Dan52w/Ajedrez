package org.example.pieza;

public class Reina extends Pieza{
    public Reina(String nombre, String color, String iniPosicion, String tipo) {
        super(nombre, color, iniPosicion, tipo);
    }

    @Override
    public Boolean valido(String ini, String sig) {
        return super.valido(ini, sig);
    }

    @Override
    public void moverPieza(String ini, String sig) {
        super.moverPieza(ini, sig);
    }
}
