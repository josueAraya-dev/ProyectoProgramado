/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones_del_programa;

/**
 *
 * @author josue
 */
public class AsientoNoEncontradoException extends Exception{

    public AsientoNoEncontradoException(int fila, int columna) {
        super("El asiento en la poscicion ingresada no ha sido encontrado" +
                "Posicion buscada: Fila "+fila+" Columna "+columna);
    }
   
    
}
