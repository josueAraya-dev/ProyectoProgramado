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

    private List<Eventos> eventosCreados;

    public GestorEventos() {
        this.eventosCreados = new ArrayList<>();
    }

    public Eventos crearEvento(String nombre, LocalDate fecha, double precioBase) {

        Eventos evento = new Eventos(nombre, fecha, precioBase);
        agregarEvento(evento);
        return evento;
    }

    public void agregarEvento(Eventos evento) {

        if (evento == null) {
            throw new IllegalArgumentException("Evento no puede ser null");
        }
        eventosCreados.add(evento);
    }

    public Eventos buscarEventoPorId(String id) {

        for (Eventos evt : eventosCreados) {
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

        Eventos evento = buscarEventoPorId(id);

        if (evento == null) {
            throw new IllegalArgumentException("No existe un evento con el id: " + id);
        }
        
         eventosCreados.remove(evento);
    }

    public List<Eventos> getEventos() {
        return Collections.unmodifiableList(eventosCreados);
    }
}
