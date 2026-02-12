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

    public Boleto(String boletoId) {
        this.boletoId = boletoId;
    }
    
    public abstract double calcularPrecioFinal(double precioBase);
    
}
