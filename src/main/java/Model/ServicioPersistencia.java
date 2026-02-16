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

    // --- GUARDAR TODO ---
    public void guardarTodo(List<Evento> eventos, List<Cliente> clientes) throws IOException {
        guardarClientes(clientes);
        guardarEventos(eventos);
        guardarBoletos(eventos);
    }

    private void guardarEventos(List<Evento> eventos) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (Evento e : eventos) {
            sb.append(e.getIdEvento()).append(";")
              .append(e.getNombre()).append(";")
              .append(e.getFechaDelEvento()).append(";")
              .append(e.getPrecioBase()).append(";")
              .append(generarMapaAsientos(e)).append("\n");
        }
        Files.writeString(Paths.get(FILE_EVENTOS), sb.toString());
    }

    private String generarMapaAsientos(Evento e) {
        StringBuilder mapa = new StringBuilder();
        // Recorremos la matriz 10x10
        for (int f = 0; f < 10; f++) {
            for (int c = 0; c < 10; c++) {
                mapa.append(e.obtenerAsiento(f, c).getEstado() == EstadoAsiento.Ocupado ? "1" : "0");
            }
        }
        return mapa.toString();
    }

    private void guardarClientes(List<Cliente> clientes) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (Cliente c : clientes) {
            sb.append(c.getIdCliente()).append(";").append(c.getNombre()).append("\n");
        }
        Files.writeString(Paths.get(FILE_CLIENTES), sb.toString());
    }

    private void guardarBoletos(List<Evento> eventos) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (Evento e : eventos) {
            for (Boleto b : e.getBoletosVendidos()) {
                String tipo = b.getClass().getSimpleName(); // "BoletoVIP", "BoletoGeneral", etc.
                sb.append(tipo).append(";")
                  .append(b.getIdBoleto()).append(";")
                  .append(e.getIdEvento()).append(";")
                  .append(b.getCliente().getIdCliente()).append(";")
                  .append(b.getAsiento().getFila()).append(";")
                  .append(b.getAsiento().getColumna()).append("\n");
            }
        }
        Files.writeString(Paths.get(FILE_BOLETOS), sb.toString());
    }
    
    public List<Cliente> cargarClientes() throws IOException {
        List<Cliente> lista = new ArrayList<>();
        Path path = Paths.get(FILE_CLIENTES);
        if (!Files.exists(path)) return lista;

        for (String linea : Files.readAllLines(path)) {
            String[] d = linea.split(";");
            // Constructor: Cliente(String id, String nombre)
            lista.add(new Cliente(d[0], d[1])); 
        }
        return lista;
    }

    public List<Evento> cargarEventos() throws IOException {
        List<Evento> lista = new ArrayList<>();
        Path path = Paths.get(FILE_EVENTOS);
        if (!Files.exists(path)) return lista;

        for (String linea : Files.readAllLines(path)) {
            String[] d = linea.split(";");
            // Constructor: Evento(String id, String nombre, LocalDate fecha, double precio)
            Evento ev = new Evento(d[0], d[1], LocalDate.parse(d[2]), Double.parseDouble(d[3]));
            
            // Restaurar mapa de asientos (el String de 0s y 1s en la columna 4)
            String mapa = d[4];
            for (int i = 0; i < mapa.length(); i++) {
                if (mapa.charAt(i) == '1') {
                    ev.obtenerAsiento(i / 10, i % 10).ocupar();
                }
            }
            lista.add(ev);
        }
        return lista;
    }

    public void cargarVentas(GestorEventos ge, GestorClientes gc) throws IOException {
        Path path = Paths.get(FILE_BOLETOS);
        if (!Files.exists(path)) return;

        for (String linea : Files.readAllLines(path)) {
            String[] d = linea.split(";");
            String tipo = d[0];
            String idBoleto = d[1];
            Evento evento = ge.buscarEventoPorId(d[2]);
            Cliente cliente = gc.buscarclientePorId(d[3]);
            Asiento asiento = evento.obtenerAsiento(Integer.parseInt(d[4]), Integer.parseInt(d[5]));

            Boleto nuevo;
            if (tipo.equals("BoletoVIP")) {
                nuevo = new BoletoVIP(evento, cliente, asiento, idBoleto);
            } else if (tipo.equals("BoletoEstudiante")) {
                nuevo = new BoletoEstudiante(evento, cliente, asiento, idBoleto);
            } else {
                nuevo = new BoletoGeneral(evento, cliente, asiento, idBoleto);
            }
            // Importante: Agregar el boleto a la lista interna del evento
            evento.getBoletosVendidos().add(nuevo);
        }
    }
}
 
