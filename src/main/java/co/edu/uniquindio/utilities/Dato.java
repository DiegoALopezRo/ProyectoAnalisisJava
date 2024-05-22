package co.edu.uniquindio.utilities;

public class Dato {
    private int n;
    private String algoritmo;
    private double tiempoEjecucion;

    public Dato(int n, String algoritmo, double tiempoEjecucion) {
        this.n = n;
        this.algoritmo = algoritmo;
        this.tiempoEjecucion = tiempoEjecucion;
    }

    public int getN() {
        return n;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    public double getTiempoEjecucion() {
        return tiempoEjecucion;
    }
}

