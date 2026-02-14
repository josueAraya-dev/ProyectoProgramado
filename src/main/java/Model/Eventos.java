package Model;

import Excepciones_del_programa.AsientoNoEncontradoException;
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
        
        contadorID++;
        this.idEvento = "EVT-"+contadorID;
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

    public static int getContadorID() {
        return contadorID;
    }

    public static void setContadorID(int contadorID) {
        Eventos.contadorID = contadorID;
    }
    
    public Asientos obtenerAsiento(int fila, int columna) throws AsientoNoEncontradoException{
    
        if (fila >=0 && fila <10 && columna >=0 && columna <10 ) {
        
            return asientos[fila][columna];
        }
        throw new AsientoNoEncontradoException(fila,columna);
    }
    
    public void agregarBoleto(Boletos boleto){
    
        boletosVendidos.add(boleto);
    }

}
