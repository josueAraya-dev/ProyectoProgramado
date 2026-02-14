package Model;

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

    public Eventos(String idEvento, String nombre, LocalDate fechaDelEvento, double precioBase) {
        this.idEvento = idEvento;
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
                this.asientos[filas][columnas] = new Asientos(filas,columnas);
            }
        }
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaDelEvento() {
        return fechaDelEvento;
    }

    public void setFechaDelEvento(LocalDate fechaDelEvento) {
        this.fechaDelEvento = fechaDelEvento;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public Asientos[][] getAsientos() {
        return asientos;
    }

    public void setAsientos(Asientos[][] asientos) {
        this.asientos = asientos;
    }

    public List<Boletos> getBoletosVendidos() {
        return boletosVendidos;
    }

    public void setBoletosVendidos(List<Boletos> boletosVendidos) {
        this.boletosVendidos = boletosVendidos;
    }
}
