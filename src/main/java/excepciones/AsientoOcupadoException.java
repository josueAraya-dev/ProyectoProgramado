/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author josue
 */
public class AsientoOcupadoException extends RuntimeException{

    public AsientoOcupadoException() {
        super("El asiento ya esta Ocupado, no se puede ocupar nuevamente!!");
    }
    
    
}
