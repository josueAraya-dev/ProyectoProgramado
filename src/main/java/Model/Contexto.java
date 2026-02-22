/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

public class Contexto {
    private static Contexto instancia;

    private final GestorEventos gestorEventos;
    private final GestorClientes gestorClientes;
    private final GestorVentas gestorVentas;
    private final ServicioPersistencia persistencia;

    private Contexto() {
        this.gestorEventos = new GestorEventos();
        this.gestorClientes = new GestorClientes();
        this.gestorVentas = new GestorVentas(gestorEventos, gestorClientes);
        this.persistencia = new ServicioPersistencia();
    }

    public static Contexto getInstance() {
        if (instancia == null) {
            instancia = new Contexto();
        }
        return instancia;
    }

    public GestorEventos getGestorEventos() { return gestorEventos; }
    public GestorClientes getGestorClientes() { return gestorClientes; }
    public GestorVentas getGestorVentas() { return gestorVentas; }
    public ServicioPersistencia getPersistencia() { return persistencia; }
}