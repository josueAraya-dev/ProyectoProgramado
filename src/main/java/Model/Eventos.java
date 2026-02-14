package Model;

import excepciones.AsientoNoEncontradoException;
import excepciones.BoletoNoCreadoException;
import excepciones.BoletoNoPerteneceException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author josue
 */
public class Eventos {

    private String idEvento;
    private String nombre;
    private LocalDate fechaDelEvento;
    private double precioBase;
    private Asientos[][] asientos;
    private List<Boletos> boletosVendidos;
    private static int contadorID = 0;

    public Eventos(String nombre, LocalDate fechaDelEvento, double precioBase) {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del evento no puede estar vacío");
        }
        if (fechaDelEvento == null || fechaDelEvento.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha del evento no puede ser pasada");
        }
        if (precioBase <= 0) {
            throw new IllegalArgumentException("El precio base debe ser positivo");
        }

        contadorID++;
        this.idEvento = "EVT-" + String.format("%04d", contadorID);
        this.nombre = nombre;
        this.fechaDelEvento = fechaDelEvento;
        this.precioBase = precioBase;
        this.asientos = new Asientos[10][10];
        inicializarAsientos();
        this.boletosVendidos = new ArrayList<>();
    }

    private void inicializarAsientos() {
        for (int filas = 0; filas < 10; filas++) {
            for (int columnas = 0; columnas < 10; columnas++) {
                this.asientos[filas][columnas] = new Asientos(filas, columnas);
            }
        }
    }

    public String getIdEvento() {
        return idEvento;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaDelEvento() {
        return fechaDelEvento;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public Asientos obtenerAsiento(int fila, int columna) throws AsientoNoEncontradoException {

        if (fila >= 0 && fila < 10 && columna >= 0 && columna < 10) {

            return asientos[fila][columna];
        }
        throw new AsientoNoEncontradoException(fila, columna);
    }

    public void agregarBoleto(Boletos boleto) throws BoletoNoCreadoException, BoletoNoPerteneceException {
        if (boleto == null) {
            throw new BoletoNoCreadoException();
        }
        
        if (!boleto.getEvento().equals(this)) {
            throw new BoletoNoPerteneceException();
        }
        boletosVendidos.add(boleto);
    }

    public double recaudacionPorEvento() {
        double total = 0;
        for (Boletos boleto : boletosVendidos) {

            total += boleto.calcularPrecioFinal();

        }
        return total;
    }
}
