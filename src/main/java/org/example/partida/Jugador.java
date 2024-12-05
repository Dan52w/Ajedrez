package org.example.partida;

public class Jugador {
    private String user;
    private String juegaCon;

    public Jugador(String user, String juegaCon) {
        this.user = user;
        this.juegaCon = juegaCon;
    }

    public Jugador(String juegaCon) {
        this.juegaCon = juegaCon;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getJuegaCon() {
        return juegaCon;
    }

    public void setJuegaCon(String juegaCon) {
        this.juegaCon = juegaCon;
    }
}
