package Model;

import excepciones.BoletoNoPerteneceException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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
    private Sala sala;
    private List<Boleto> boletosVendidos;

    public Evento(String idEvento, String nombre,
            LocalDate fechaDelEvento, double precioBase) {

        validarNombre(nombre);
        validarFecha(fechaDelEvento);
        validarPrecio(precioBase);

        this.idEvento = idEvento;
        this.nombre = nombre;
        this.fechaDelEvento = fechaDelEvento;
        this.precioBase = precioBase;
        this.sala = new Sala();
        this.boletosVendidos = new ArrayList<>();

    }

    // Nuevo método para permitir el cambio de ID en edición
    public void setIdEvento(String idEvento) {
        if (idEvento == null || idEvento.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID no puede estar vacío");
        }
        this.idEvento = idEvento;
    }

    public void editarDatos(String nuevoNombre, LocalDate nuevaFecha, double nuevoPrecioBase) {

        validarNombre(nuevoNombre);
        validarFecha(nuevaFecha);
        validarPrecio(nuevoPrecioBase);

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

    public double recaudacionPorEvento() {
        double total = 0;
        for (Boleto boleto : boletosVendidos) {
            total += boleto.calcularPrecioFinal();
        }
        return total;
    }

    public Asiento obtenerAsiento(int fila, int columna) {
        return this.sala.obtenerAsiento(fila, columna);
    }

    public void ejecutarReinicioDeSala() {
        this.sala.reiniciarSala();
        // CORRECCIÓN: Se limpia la lista de boletos para que las nuevas ventas se registren
        this.boletosVendidos.clear();
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

    public List<Boleto> getBoletosVendidos() {
        return Collections.unmodifiableList(boletosVendidos);
    }

    public Sala getSala() {
        return sala;
    }

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
      
    }
}