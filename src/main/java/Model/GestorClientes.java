/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import excepciones.ClienteNoEncontradoException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author josue
 */
public class GestorClientes {
    
    private List<Cliente> clientesCreados;

    public GestorClientes() {
        this.clientesCreados = new ArrayList<>();
    }
    
    public Cliente crearCliente(String nombre, String id){
        
        String idTrm = id.trim();
        if(buscarclientePorId(idTrm) != null){
            throw new IllegalArgumentException("Ya existe un cliente registrado con el ID: "+idTrm);
        }
         
        Cliente cliente  = new Cliente(nombre, idTrm);
        agregarCliente(cliente);

        return cliente;
    }
    
    public void agregarCliente(Cliente cliente){
    
        if(cliente == null){
            throw new IllegalArgumentException("Cliente no puede ser null");
        }
        clientesCreados.add(cliente);
    }
    
    public Cliente buscarclientePorId(String id) {

        for (Cliente clt : clientesCreados) {
            if (clt.getIdCliente().equals(id)) {

                return clt;
            }
        }
        return null;
    }

    
    public List<Cliente> getClientesCreados() {
        return Collections.unmodifiableList(clientesCreados);
    }

   public void setClientesCreados(List<Cliente> clientesCreados) {
    // Aseguramos que la lista sea siempre un ArrayList modificable y no nulo
    if (clientesCreados == null) {
        this.clientesCreados = new ArrayList<>();
    } else {
        this.clientesCreados = new ArrayList<>(clientesCreados);
    }
}
}