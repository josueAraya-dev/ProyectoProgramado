/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author josue
 */
public class BoletoEstudiante extends Boleto {

   private double descuento = 0.10;
    
    public BoletoEstudiante(Evento evento, Cliente cliente,Asiento asiento, String boletoId) {
        super(evento, cliente, asiento, boletoId);
    }

    @Override
    public double calcularPrecioFinal() {
       double precioFinal = getEvento().getPrecioBase() - descuento;
       return precioFinal;
    }
    
}
