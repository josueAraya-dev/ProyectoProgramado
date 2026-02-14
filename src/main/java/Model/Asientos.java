package Model;

import excepciones.AsientoLibreException;
import excepciones.AsientoOcupadoException;

public class Asientos {

    private int fila;
    private int columna;
    private EstadoAsientos estado;

    public Asientos(int fila, int columna) {
      this(fila,columna, EstadoAsientos.Disponible);
    }

    public Asientos(int fila, int columna, EstadoAsientos estado) {
        this.fila = fila;
        this.columna = columna;
        this.estado = estado;
    }//sobrecarga de constructores para facilitar lectura de archivos

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public EstadoAsientos getEstado() {
        return estado;
    }

    private void setEstado(EstadoAsientos estado) {
        this.estado = estado;
    }

    public void ocupar() throws AsientoOcupadoException {

        if (this.estado == EstadoAsientos.Ocupado) {
            throw new AsientoOcupadoException();
        }
        this.estado = EstadoAsientos.Ocupado;
    }
    
    public void liberar() throws AsientoLibreException{
    
        if(this.estado == EstadoAsientos.Disponible){
            throw new AsientoLibreException();
        }
        this.estado = EstadoAsientos.Disponible;
    }

}
