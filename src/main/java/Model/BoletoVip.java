/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author josue
 */
public class BoletoVip extends Boleto {

    private double recargo = 5000; 
    private boolean AccesoLounge;
    private String loungeId;
            
    public BoletoVip(String boletoId, String loungeId) {
        super(boletoId);
        this.AccesoLounge = true;
        loungeId = loungeId;
    }

    public double getRecargo() {
        return recargo;
    }

    public void setRecargo(double recargo) {
        this.recargo = recargo;
    }

    public boolean isAccesoLounge() {
        return AccesoLounge;
    }

    public void setAccesoLounge(boolean AccesoLounge) {
        this.AccesoLounge = AccesoLounge;
    }

    public String getLoungeId() {
        return loungeId;
    }

    public void setLoungeId(String loungeId) {
        this.loungeId = loungeId;
    }

    
    
    @Override
    public double calcularPrecioFinal(double precioBase) {
       
        double precioFinal = precioBase+recargo;
        
        return precioFinal;
    }
    
    
}
