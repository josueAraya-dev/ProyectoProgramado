/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Model.Evento;

public class BoletoVIP extends Boleto {

    private double recargo = 5000;
    private boolean AccesoLounge;
    private String loungeId;

    
    public BoletoVIP(Evento evento, Cliente cliente, Asiento asiento, String idBoleto) {
        super(evento, cliente, asiento, idBoleto);
        this.AccesoLounge = true;
        this.loungeId = "PLATINUM-S1";
    }//constructor para creacion de boletos

    public double getRecargo() {
        return recargo;
    }

    public void setRecargo(double recargo) {
        this.recargo = recargo;
    }

      @Override
    public String imprimir() {
        return super.imprimir()+"\nAcceso a lounge:"+AccesoLounge;
                
    }
    
    @Override
    public double calcularPrecioFinal() {
        double precioFinal = getEvento().getPrecioBase() + recargo;
        return precioFinal;
    }

}
