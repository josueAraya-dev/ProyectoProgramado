/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author josue
 */
public class BoletoNoPerteneceException extends RuntimeException{

    public BoletoNoPerteneceException() {
        super("El boleto no pertenece a este evento");
    }
    
    
}
