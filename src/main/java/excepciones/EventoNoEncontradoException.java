/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author josue
 */
public class EventoNoEncontradoException extends RuntimeException {

    public EventoNoEncontradoException(String id) {
        super("Evento con id: "+id+" no encontrado");
    }
    
    
}
