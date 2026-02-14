package Model;

import excepciones.AsientoLibreException;
import excepciones.AsientoOcupadoException;

public class Asiento {

    private int fila;
    private int columna;
    private EstadoAsientos estado;

    public Asiento(int fila, int columna) {
        this(fila, columna, EstadoAsientos.Disponible);
    }

    public Asiento(int fila, int columna, EstadoAsientos estado) {
        if (fila < 0 || columna < 0) {
            throw new IllegalArgumentException("Ingrese solo valores dentro del rango");
        }

        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede er nulo");
        }

        this.fila = fila;
        this.columna = columna;
        this.estado = estado;
    }//sobrecarga de constructores para facilitar lectura de archivos

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public EstadoAsientos getEstado() {
        return estado;
    }

    private void setEstado(EstadoAsientos estado) {
        this.estado = estado;
    }

    public void ocupar() {

        if (this.estado == EstadoAsientos.Ocupado) {
            throw new AsientoOcupadoException();
        }
        this.estado = EstadoAsientos.Ocupado;
    }

    public void liberar() {

        if (this.estado == EstadoAsientos.Disponible) {
            throw new AsientoLibreException();
        }
        this.estado = EstadoAsientos.Disponible;
    }

}
