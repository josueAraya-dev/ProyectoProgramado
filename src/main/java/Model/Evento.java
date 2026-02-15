package Model;

import excepciones.AsientoLibreException;
import excepciones.AsientoNoEncontradoException;
import excepciones.BoletoNoPerteneceException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author josue
 */
public class Evento {

    private String idEvento;
    private String nombre;
    private LocalDate fechaDelEvento;
    private double precioBase;
    private Asiento[][] asientos;
    private List<Boleto> boletosVendidos;
    private static int contadorID = 0;

    private Evento(String idEvento, String nombre,
            LocalDate fechaDelEvento, double precioBase,
            boolean actualizarContador) {

        validarNombre(nombre);
        validarFecha(fechaDelEvento);
        validarPrecio(precioBase);

        this.idEvento = idEvento;
        this.nombre = nombre;
        this.fechaDelEvento = fechaDelEvento;
        this.precioBase = precioBase;

        this.asientos = new Asiento[10][10];
        inicializarAsientos();
        this.boletosVendidos = new ArrayList<>();

        if (actualizarContador) {
            actualizarContador(idEvento);
        }
    }//constructor "padre" del que llaman los otros dos

    public Evento(String nombre, LocalDate fechaDelEvento, double precioBase) {

        this("EVT-" + String.format("%04d", ++contadorID), nombre,
                fechaDelEvento, precioBase, false);
    }//constructor para creacion de eventos uso basico;

    public Evento(String idEvento, String nombre,
            LocalDate fechaDelEvento, double precioBase) {

        this(idEvento, nombre, fechaDelEvento, precioBase, true);
    }//constructor para cargar desde archivos y mantener persistencia del id

    //Validaciones 
    private void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del evento no puede estar vacío");
        }
        if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            throw new IllegalArgumentException("El nombre del evento solo puede contener letras y espacios");
        }
        if (nombre.trim().length() < 3) {
            throw new IllegalArgumentException("El nombre del evento debe tener al menos 3 caracteres");
        }
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

    public void editarDatos(String nuevoNombre, LocalDate nuevaFecha, double nuevoPrecioBase) {

        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre inválido");
        }

        if (nuevaFecha == null || nuevaFecha.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Fecha inválida");
        }

        if (nuevoPrecioBase <= 0) {
            throw new IllegalArgumentException("Precio inválido");
        }

        this.nombre = nuevoNombre;
        this.fechaDelEvento = nuevaFecha;
        this.precioBase = nuevoPrecioBase;
    }

    public void agregarBoleto(Boleto boleto) {
        if (boleto == null) {
            throw new IllegalArgumentException("El boleto no puede ser null");
        }

        if (boleto.getEvento() != this) {
            throw new BoletoNoPerteneceException();
        }
        boletosVendidos.add(boleto);
    }

    public void reiniciarSala() {
        for (int f = 0; f < asientos.length; f++) {
            for (int c = 0; c < asientos[f].length; c++) {
                try {
                    // Intentamos liberar el asiento
                    asientos[f][c].liberar();
                    boletosVendidos.clear();
                } catch (AsientoLibreException e) {

                }
            }
        }
    }//lanzar alerta que esto borrara todo el evento 

    public double recaudacionPorEvento() {
        double total = 0;
        for (Boleto boleto : boletosVendidos) {

            total += boleto.calcularPrecioFinal();

        }
        return total;
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

    private static void actualizarContador(String id) {
        int numero = Integer.parseInt(id.split("-")[1]);
        if (numero > contadorID) {
            contadorID = numero;
        }
    }

    private void validarFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha del evento no puede ser nula");
        }
        if (fecha.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha del evento no puede ser pasada");
        }
    }

    private void validarPrecio(double precio) {
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio base debe ser positivo");
        }
        if (precio > 1000000) {
            throw new IllegalArgumentException("El precio base excede el límite permitido");
        }
    }

}
