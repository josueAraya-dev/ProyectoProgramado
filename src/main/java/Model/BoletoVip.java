/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Model.Evento;

public class BoletoVip extends Boleto {

    private double recargo = 5000; 
    private boolean AccesoLounge;
    private String loungeId;
            
    public BoletoVip(Evento evento, Cliente cliente, String boletoId, String loungeId) {
        super(evento, cliente, boletoId);
        this.AccesoLounge = true;
        loungeId = loungeId;
    }
    
    
    @Override
    public double calcularPrecioFinal() {
        double precioFinal = getEvento().getPrecioBase() + recargo;
        return precioFinal;
    }
    
    
}
