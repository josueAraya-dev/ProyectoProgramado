/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author josue
 */
public class ClienteNoEncontradoException extends RuntimeException {

    public ClienteNoEncontradoException(String id) {
        super("El cliente con id: "+id+" no fue encontrado");
    }
    
    
    
}
