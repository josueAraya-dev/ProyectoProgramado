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
    
   public BoletoEstudiante(Evento evento, Cliente cliente, Asiento asiento) {
        super(evento, cliente, asiento);
    }//constructor para nuevos estudiantes 
   
   public BoletoEstudiante(Evento evento, Cliente cliente,Asiento asiento, String idBoleto) {
        super(evento, cliente, asiento, idBoleto);
    }//constructor para creacion de estudiantes //recibe id como parametro
    
    
 
      @Override
    public String imprimir() {
        return super.imprimir()+"\nDescuento:"+ descuento*100 +"%";
                
    }
    
    @Override
    public double calcularPrecioFinal() {
       // Se cambia la resta por la multiplicación para aplicar el porcentaje real
       double precioFinal = getEvento().getPrecioBase() * (1 - descuento);
       return precioFinal;
    }
    
}