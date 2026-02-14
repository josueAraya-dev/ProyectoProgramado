/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;


public abstract class Boletos {
   
    private String boletoId;
    private Clientes cliente;
    private Asiento asiento;
    private Eventos evento;
    
    public Boletos(Eventos evento, Clientes cliente, Asiento asiento, String boletoId) {
        this.evento = evento;
        this.cliente = cliente;
        this.asiento = asiento;
        this.boletoId = boletoId;
    }

    public String getBoletoId() {
        return boletoId;
    }

    public void setBoletoId(String boletoId) {
        this.boletoId = boletoId;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    } 

    public Eventos getEvento() {
        return evento;
    }

    public void setEvento(Eventos evento) {
        this.evento = evento;
    }
    
    
    public abstract double calcularPrecioFinal();
    
}
