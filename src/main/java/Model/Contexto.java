package Model;

import java.util.List;
import java.util.ArrayList;

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
        
        // Se eliminó la carga automática del constructor para que el inicio sea limpio.
    }

    public static Contexto getInstance() {
        if (instancia == null) {
            instancia = new Contexto();
        }
        return instancia;
    }

    // MÉTODO NUEVO: Ahora la carga es explícita y manual
    public void cargarDatosDesdeArchivos() {
        try {
            List<Evento> eventosCargados = persistencia.cargarEventos();
            if (!eventosCargados.isEmpty()) {
                this.gestorEventos.setEventosCreados(new ArrayList<>(eventosCargados));
            }

            List<Cliente> clientesCargados = persistencia.cargarClientes();
            if (!clientesCargados.isEmpty()) {
                this.gestorClientes.setClientesCreados(new ArrayList<>(clientesCargados));
            }

            persistencia.cargarVentas(gestorEventos, gestorClientes);
            
            // Sincronizar contador de boletos
            List<Boleto> todosLosBoletos = new ArrayList<>();
            for(Evento e : gestorEventos.getEventosCreados()){
                todosLosBoletos.addAll(e.getBoletosVendidos());
            }
            Boleto.sincronizarContador(todosLosBoletos);

            System.out.println("SISTEMA: Datos cargados manualmente con éxito.");

        } catch (Exception e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
        }
    }

    public GestorEventos getGestorEventos() { return gestorEventos; }
    public GestorClientes getGestorClientes() { return gestorClientes; }
    public GestorVentas getGestorVentas() { return gestorVentas; }
    public ServicioPersistencia getPersistencia() { return persistencia; }
    
    public void guardarTodo() {
        try {
            // Llamamos al método de persistencia que recibe las listas actuales
            persistencia.guardarTodo(
                gestorEventos.getEventosCreados(), 
                gestorClientes.getClientesCreados()
            );
            System.out.println("SISTEMA: Datos guardados con éxito.");
        } catch (Exception e) {
            System.err.println("Error al guardar: " + e.getMessage());
        }
    }
}