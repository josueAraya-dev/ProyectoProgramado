/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Model.Eventos;

public class BoletosVip extends Boletos {

    private double recargo = 5000; 
    private boolean AccesoLounge;
    private String loungeId;
            
    public BoletosVip(Eventos evento, Clientes cliente, Asiento asiento, String boletoId, String loungeId) {
        super(evento, cliente, asiento, boletoId);
        this.AccesoLounge = true;
        loungeId = loungeId;
    }

    public double getRecargo() {
        return recargo;
    }

    public void setRecargo(double recargo) {
        this.recargo = recargo;
    }
    
    @Override
    public double calcularPrecioFinal() {
        double precioFinal = getEvento().getPrecioBase() + recargo;
        return precioFinal;
    }
    
    
}
