/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author josue
 */
public class AsientoLibreException extends RuntimeException {

    public AsientoLibreException() {
        super("El asiento ya esta disponible, verifique el asiento ingresado!!");
    }
    
    
    
}
