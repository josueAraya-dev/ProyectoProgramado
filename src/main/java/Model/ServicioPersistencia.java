/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

    import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;


public class ServicioPersistencia {

    private final String FILE_EVENTOS = "eventos.csv";
    private final String FILE_CLIENTES = "clientes.csv";
    private final String FILE_BOLETOS = "boletos.csv";

    // --- DENTRO DE guardarEventos ---
private void guardarEventos(List<Evento> eventos) throws IOException {
    StringBuilder sb = new StringBuilder();
    for (Evento e : eventos) {
        sb.append(e.getIdEvento()).append(";")
          .append(e.getNombre()).append(";")
          .append(e.getFechaDelEvento()).append(";")
          .append(e.getPrecioBase()).append(";")
          // Delegamos la generación del mapa a la Sala a través del Evento
          .append(e.getSala().exportarEstadoAsientos()).append("\n");
    }
    Files.writeString(Paths.get(FILE_EVENTOS), sb.toString());
}

// --- DENTRO DE cargarEventos ---
public List<Evento> cargarEventos() throws IOException {
    List<Evento> lista = new ArrayList<>();
    Path path = Paths.get(FILE_EVENTOS);
    if (!Files.exists(path)) return lista;

    for (String linea : Files.readAllLines(path)) {
        String[] d = linea.split(";");
        // Usamos tu método reconstruir (o el constructor que maneja el ID)
        Evento ev = new Evento(d[0], d[1], LocalDate.parse(d[2]), Double.parseDouble(d[3]));
        
        // La Sala del nuevo evento se encarga de "pintar" sus propios asientos
        ev.getSala().importarEstadoAsientos(d[4]);
        
        lista.add(ev);
    }
    return lista;
}



}
 
