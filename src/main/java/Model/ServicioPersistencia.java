
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
public void guardarEventos(List<Evento> eventos) throws IOException {
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
    
// --- GUARDAR CLIENTES ---
    public void guardarClientes(List<Cliente> clientes) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (Cliente c : clientes) {
            sb.append(c.getIdCliente()).append(";")
              .append(c.getNombre()).append("\n");
        }
        Files.writeString(Paths.get(FILE_CLIENTES), sb.toString());
    }
    
    public List<Cliente> cargarClientes() throws IOException {
    List<Cliente> lista = new ArrayList<>();
    Path path = Paths.get(FILE_CLIENTES);
    if (!Files.exists(path)) return lista;

    for (String linea : Files.readAllLines(path)) {
        String[] d = linea.split(";");
        // d[0] = id, d[1] = nombre
        lista.add(new Cliente(d[1], d[0]));
    }
    return lista;
}

    // --- GUARDAR BOLETOS (Las Ventas) ---
    public void guardarBoletos(List<Evento> eventos) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (Evento e : eventos) {
            for (Boleto b : e.getBoletosVendidos()) {
                sb.append(b.getClass().getSimpleName()).append(";") // Para saber si es VIP, etc.
                  .append(b.getIdBoleto()).append(";")
                  .append(e.getIdEvento()).append(";")
                  .append(b.getCliente().getIdCliente()).append(";")
                  .append(b.getAsiento().getFila()).append(";")
                  .append(b.getAsiento().getColumna()).append("\n");
            }
        }
        Files.writeString(Paths.get(FILE_BOLETOS), sb.toString());
    }
    
    public void cargarVentas(GestorEventos ge, GestorClientes gc) throws IOException {
    Path path = Paths.get(FILE_BOLETOS);
    if (!Files.exists(path)) return;

    for (String linea : Files.readAllLines(path)) {
        String[] d = linea.split(";");
        
        String tipoBoleto = d[0]; // BoletoVIP, BoletoEstudiante, etc.
        String idBoleto = d[1];
        String idEvento = d[2];
        String idCliente = d[3];
        int fila = Integer.parseInt(d[4]);
        int col = Integer.parseInt(d[5]);

        // Buscamos los objetos reales que ya están en los gestores
        Evento evento = ge.buscarEventoPorId(idEvento);
        Cliente cliente = gc.buscarclientePorId(idCliente);
        
        if (evento != null && cliente != null) {
            Asiento asiento = evento.obtenerAsiento(fila, col);
            Boleto nuevoBoleto;

            // Polimorfismo: creamos el tipo correcto de boleto
            switch (tipoBoleto) {
                case "BoletoVIP":
                    nuevoBoleto = new BoletoVIP(evento, cliente, asiento, idBoleto);
                    break;
                case "BoletoEstudiante":
                    nuevoBoleto = new BoletoEstudiante(evento, cliente, asiento, idBoleto);
                    break;
                default:
                    nuevoBoleto = new BoletoGeneral(evento, cliente, asiento, idBoleto);
                    break;
            }
            
            // Lo agregamos a la lista de ventas del evento para que el reporte sea correcto
            evento.agregarBoleto(nuevoBoleto);
        }
    }
}
    
}
 

/**
 * FLUJO DE PERSISTENCIA
 * 
 * CARGA (orden estricto):
 *   1. Eventos    → Objetos base con sus salas
 *   2. Clientes   → Compradores registrados
 *   3. Ventas     → Relaciones Evento-Cliente-Asiento
 *   4. Sincronizar contador → Evita IDs duplicados (CRÍTICO)
 * 
 * GUARDADO (orden recomendado):
 *   1. Eventos    → eventos.csv
 *   2. Clientes   → clientes.csv
 *   3. Boletos    → boletos.csv
 * 
 * ⚠️ IMPORTANTE: No alterar el orden de carga. Los boletos dependen de
 * que eventos y clientes ya estén cargados en memoria.
 */
