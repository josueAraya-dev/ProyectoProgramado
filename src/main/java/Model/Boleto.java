/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author josue
 */
public abstract class Boleto {
   
    private String boletoId;
    private Cliente cliente;
    private Asiento asiento;
    
    public Boleto(String boletoId) {
        this.boletoId = boletoId;
    }

    public String getBoletoId() {
        return boletoId;
    }

    public void setBoletoId(String boletoId) {
        this.boletoId = boletoId;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }
    
    
    
    public abstract double calcularPrecioFinal(double precioBase);
    
}
