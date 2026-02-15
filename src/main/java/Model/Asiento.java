package Model;

import excepciones.AsientoLibreException;
import excepciones.AsientoOcupadoException;

public class Asiento {

    private int fila;
    private int columna;
    private EstadoAsiento estado;

    public Asiento(int fila, int columna) {
        this(fila, columna, EstadoAsiento.Disponible);
    }

    public Asiento(int fila, int columna, EstadoAsiento estado) {
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

    public EstadoAsiento getEstado() {
        return estado;
    }

    private void setEstado(EstadoAsiento estado) {
        this.estado = estado;
    }

    public void ocupar() {

        if (this.estado == EstadoAsiento.Ocupado) {
            throw new AsientoOcupadoException();
        }
        this.estado = EstadoAsiento.Ocupado;
    }

    public void liberar() {

        if (this.estado == EstadoAsiento.Disponible) {
            throw new AsientoLibreException();
        }
        this.estado = EstadoAsiento.Disponible;
    }

}
