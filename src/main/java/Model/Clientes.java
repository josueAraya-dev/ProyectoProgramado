/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author josue
 */

public class Clientes {
    
    private String nombre;
    private String idCliente;

    public Clientes(String nombre, String idCliente) {
        
        validarNombre(nombre);
        validarId(idCliente);
        
        this.nombre = nombre;
        this.idCliente = idCliente;
    }
    
    private void validarNombre(String nombre){
        
        // Validación del nombre
    if (nombre == null || nombre.trim().isEmpty()) {
        throw new IllegalArgumentException("El nombre del cliente no puede estar vacío");
    }
    if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
        throw new IllegalArgumentException("El nombre del cliente solo puede contener letras y espacios");
    }
    if (nombre.trim().length() < 2) {
        throw new IllegalArgumentException("El nombre del cliente debe tener al menos 2 caracteres");
    }
    
    }
    
    private void validarId(String id){
    if (idCliente == null || idCliente.trim().isEmpty()) {
        throw new IllegalArgumentException("El ID del cliente no puede estar vacío");
    }
   
    if (idCliente.length() < 5 || idCliente.length() > 20) {
        throw new IllegalArgumentException("El ID del cliente debe tener entre 5 y 20 caracteres");
    }
        
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }
    
}
