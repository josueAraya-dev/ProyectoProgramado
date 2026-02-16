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

        if (evento == null) {
            throw new IllegalArgumentException("No existe un evento con el id: " + id);
        }

        eventosCreados.remove(evento);
    }

    public void editarEvento(String id, String nuevoNombre, LocalDate nuevaFecha, double nuevoPrecioBase) {

        Evento evento = buscarEventoPorId(id);

        if (evento == null) {
            throw new IllegalArgumentException("Evento no encontrado");
        }

        evento.editarDatos(nuevoNombre, nuevaFecha, nuevoPrecioBase);
    }

    public List<Evento> getEventos() {
        return Collections.unmodifiableList(eventosCreados);
    }
}
