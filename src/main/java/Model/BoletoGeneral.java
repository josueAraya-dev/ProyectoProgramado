/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author josue
 */
public class BoletoGeneral  extends Boleto{
    
    public BoletoGeneral(Evento evento, Cliente cliente, String boletoId) {
        super(evento, cliente, boletoId);
    }

    @Override
    public double calcularPrecioFinal() {    
       return getEvento().getPrecioBase();
    }
    
}
