/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import excepciones.AsientoLibreException;
import excepciones.AsientoNoEncontradoException;

/**
 *
 * @author josue
 */
public class Sala {

    private Asiento[][] asientos;

    public Sala() {
        this.asientos = new Asiento[10][10];
        inicializarAsientos();

    }

    private void inicializarAsientos() {
        for (int filas = 0; filas < 10; filas++) {
            for (int columnas = 0; columnas < 10; columnas++) {
                this.asientos[filas][columnas] = new Asiento(filas, columnas);
            }
        }
    }

    public Asiento obtenerAsiento(int fila, int columna) {

        if (fila >= 0 && fila < 10 && columna >= 0 && columna < 10) {

            return asientos[fila][columna];
        }
        throw new AsientoNoEncontradoException(fila, columna);
    }

    public void reiniciarSala() {
        for (int f = 0; f < asientos.length; f++) {
            for (int c = 0; c < asientos[f].length; c++) {
                try {
                    // Intentamos liberar el asiento
                    asientos[f][c].liberar();
                } catch (AsientoLibreException e) {

                }
            }
        }
    }

    public String exportarEstadoAsientos() {
        StringBuilder sb = new StringBuilder();
        for (Asiento[] fila : asientos) {
            for (Asiento a : fila) {
                sb.append(a.getEstado() == EstadoAsiento.Ocupado ? "1" : "0");
            }
        }
        return sb.toString();
    }//escribe el estado de la matriz en 0 y 1 para guardar el archivo txt (1= ocupado, 0= disponible)

    public void importarEstadoAsientos(String mapa) {
        for (int i = 0; i < mapa.length(); i++) {
            if (mapa.charAt(i) == '1') {
                int f = i / 10;
                int c = i % 10;
                this.asientos[f][c].ocupar();
            }
        }
    }//recibe la cadena de texto y llama al metodo ocupar cuando encuentra un 1

}
