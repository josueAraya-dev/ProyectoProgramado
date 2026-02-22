package Model;

import excepciones.EventoNoEncontradoException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author josue
 */
public class GestorEventos {

    private List<Evento> eventosCreados;

    public GestorEventos() {
        this.eventosCreados = new ArrayList<>();
    }

    public Evento crearEvento(String id, String nombre, LocalDate fecha, double precioBase) {
        // Validación de ID duplicado antes de crear
        if (existeEvento(id)) {
            throw new IllegalArgumentException("Ya existe un evento con el ID: " + id);
        }
        Evento evento = new Evento(id, nombre, fecha, precioBase);
        agregarEvento(evento);
        return evento;
    }

    public void agregarEvento(Evento evento) {
        if (evento == null) {
            throw new IllegalArgumentException("Evento no puede ser null");
        }
        eventosCreados.add(evento);
    }

    // Método nuevo para evitar que el programa falle al validar existencia
    public boolean existeEvento(String id) {
        for (Evento evt : eventosCreados) {
            if (evt.getIdEvento().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public Evento buscarEventoPorId(String id) {
        for (Evento evt : eventosCreados) {
            if (evt.getIdEvento().equals(id)) {
                return evt;
            }
        }
        throw new EventoNoEncontradoException(id);
    }

    public void eliminarEvento(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El id no puede ser null o vacío");
        }

        Evento evento = buscarEventoPorId(id);
        eventosCreados.remove(evento);
    }

    public void editarEvento(String id, String nuevoNombre, LocalDate nuevaFecha, double nuevoPrecioBase) {
        Evento evento = buscarEventoPorId(id);
        evento.editarDatos(nuevoNombre, nuevaFecha, nuevoPrecioBase);
    }

    public List<Evento> getEventosCreados() {
        return Collections.unmodifiableList(eventosCreados);
    }

    // Cambiado para que permita manipular la lista internamente al cargar archivos
    public void setEventosCreados(List<Evento> eventosCreados) {
        this.eventosCreados = new ArrayList<>(eventosCreados);
    }
}