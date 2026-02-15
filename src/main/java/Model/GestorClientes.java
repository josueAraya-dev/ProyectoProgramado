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
    
    private List<Clientes> clientesCreados;

    public GestorClientes() {
        this.clientesCreados = new ArrayList<>();
    }
    
    public Clientes crearCliente(String nombre, String id){
    
        Clientes cliente = new Clientes(nombre, id);
        agregarCliente(cliente);
        return cliente;
    }
    
    public void agregarCliente(Clientes cliente){
    
        if(cliente == null){
            throw new IllegalArgumentException("Cliente no puede ser null");
        }
        clientesCreados.add(cliente);
    }
    
    public Clientes buscarclientePorId(String id) {

        for (Clientes clt : clientesCreados) {
            if (clt.getIdCliente().equals(id)) {

                return clt;
            }
        }
        throw new ClienteNoEncontradoException(id);
    }

    //si da chance agregar metodos de editar y eleminar ya que estos requieren agregar logica
    //de vistas y controladores extra
    
    public List<Clientes> getClientesCreados() {
        return Collections.unmodifiableList(clientesCreados);
    }
}
