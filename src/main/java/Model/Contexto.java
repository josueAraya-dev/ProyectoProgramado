package Model;

import java.util.List;

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

        // Carga automática al iniciar el sistema
        try {
            List<Evento> eventosCargados = persistencia.cargarEventos();
            if (!eventosCargados.isEmpty()) {
                this.gestorEventos.setEventosCreados(eventosCargados);
            }

            List<Cliente> clientesCargados = persistencia.cargarClientes();
            if (!clientesCargados.isEmpty()) {
                this.gestorClientes.setClientesCreados(clientesCargados);
            }

            persistencia.cargarVentas(gestorEventos, gestorClientes);
            
            // Sincronizar el contador de IDs de boletos para no repetir
            // (Usamos la lista de todos los boletos de todos los eventos)
            java.util.List<Boleto> todosLosBoletos = new java.util.ArrayList<>();
            for(Evento e : gestorEventos.getEventosCreados()){
                todosLosBoletos.addAll(e.getBoletosVendidos());
            }
            Boleto.sincronizarContador(todosLosBoletos);

        } catch (Exception e) {
            System.err.println("Aviso: No se pudieron cargar datos previos (puede ser el primer inicio): " + e.getMessage());
        }
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
    
    // Método auxiliar para guardar todo de un golpe
    public void guardarTodo() {
        try {
            // Necesitas que Josué haga públicos los métodos de guardado o añadirlos aquí
            // Por ahora llamaremos a los métodos individuales si existen en ServicioPersistencia
            // Si ServicioPersistencia tiene guardarEventos privado, cámbialo a público.
        } catch (Exception e) {
            System.err.println("Error al guardar: " + e.getMessage());
        }
    }
}