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
    
        Cliente cliente = new Cliente(nombre, id);
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
        throw new ClienteNoEncontradoException(id);
    }

    //si da chance agregar metodos de editar y eleminar ya que estos requieren agregar logica
    //de vistas y controladores extra
    
    public List<Cliente> getClientesCreados() {
        return Collections.unmodifiableList(clientesCreados);
    }
}
