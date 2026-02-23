package View.ControllersFXML;

import Model.Boleto;
import Model.Contexto;
import Model.Evento;
import Model.GestorEventos;
import Model.Cliente;
import Model.ServicioPersistencia;
import View.MainFXML.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Heyne
 */
public class Admin implements Initializable {

    @FXML private TextField txtEventoId;
    @FXML private TextField txtEventoNombre;
    @FXML private TextField txtPrecioBase;
    @FXML private DatePicker dpFecha;
    @FXML private TextArea txtAreaReporte;
    @FXML private Label lblTotalRecaudado;
    @FXML private TextField txtBusquedaId;

    private GestorEventos gestorEventos;
    private Contexto contexto;
    private String idMemoriaEdicion = null; 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        contexto = Contexto.getInstance();
        gestorEventos = Contexto.getInstance().getGestorEventos();
        verRecaudacion(); 
        dpFecha.setEditable(false);
    }

    // --- LÓGICA DE REINICIO ---

    @FXML
    private void reiniciarSalaPorId() {
        String id = txtEventoId.getText().trim();
        
        if (id.isEmpty()) {
            mostrarAlerta("Dato Requerido", "Debe ingresar el ID del evento en el campo 'ID Evento' para reiniciar su sala.");
            return;
        }

        try {
            // Intentamos buscar el evento. Si no existe, gestorEventos lanzará la excepción automáticamente.
            Evento e = gestorEventos.buscarEventoPorId(id);

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmar Reinicio Individual");
            confirm.setHeaderText("¿Reiniciar el evento: " + e.getNombre() + "?");
            confirm.setContentText("Se borrarán permanentemente todos los boletos vendidos de esta sala.");

            if (confirm.showAndWait().get() == ButtonType.OK) {
                e.ejecutarReinicioDeSala();
                guardarYRefrescar();
                txtAreaReporte.appendText("\n[SISTEMA] Sala " + id + " reiniciada con éxito.");
            }
        } catch (Exception e) {
            // Aquí se captura la excepción si el ID no existe
            mostrarAlerta("Error", "No se encontró el evento: " + e.getMessage());
        }
    }

    @FXML
    private void reiniciarTodoElSistema() {
        if (gestorEventos.getEventosCreados().isEmpty()) {
            mostrarAlerta("Información", "No hay eventos registrados para reiniciar.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("¡ADVERTENCIA!");
        confirm.setHeaderText("¿Desea reiniciar TODAS las salas del sistema?");
        confirm.setContentText("Esta acción eliminará las ventas de TODOS los eventos. Es irreversible.");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                for (Evento e : gestorEventos.getEventosCreados()) {
                    e.ejecutarReinicioDeSala();
                }
                guardarYRefrescar();
                txtAreaReporte.appendText("\n[SISTEMA] Todas las salas han sido limpiadas.");
            } catch (Exception e) {
                mostrarAlerta("Error", "Error al guardar los cambios: " + e.getMessage());
            }
        }
    }

    private void guardarYRefrescar() throws Exception {
        Contexto.getInstance().getPersistencia().guardarTodo(
            gestorEventos.getEventosCreados(), 
            Contexto.getInstance().getGestorClientes().getClientesCreados()
        );
        verRecaudacion();
        limpiarFormulario();
    }

    // --- MANTENIMIENTO DE EVENTOS ---

    @FXML
    private void guardarEvento() {
        try {
            String id = txtEventoId.getText().trim();
            String nombre = txtEventoNombre.getText().trim();
            String precioTxt = txtPrecioBase.getText().trim();
            LocalDate fecha = dpFecha.getValue();

            if (id.isEmpty() || nombre.isEmpty() || precioTxt.isEmpty() || fecha == null) {
                mostrarAlerta("Campos Incompletos", "Por favor, llene todos los campos para crear el evento.");
                return;
            }

            if (!id.matches("\\d+")) {
                mostrarAlerta("Error de Formato", "El ID del evento debe contener solo números.");
                return;
            }

            double precio;
            try {
                precio = Double.parseDouble(precioTxt);
                if (precio < 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                mostrarAlerta("Precio Inválido", "El precio debe ser un número positivo (ej: 2500).");
                return;
            }

            gestorEventos.crearEvento(id, nombre, fecha, precio);
            txtAreaReporte.appendText("\n[ÉXITO] Evento '" + nombre + "' creado.");
            verRecaudacion();
            limpiarFormulario();
            
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    @FXML
    private void editarEventoPorId() {
        String idActualCampo = txtEventoId.getText().trim();
        
        if (idMemoriaEdicion == null) {
            if (idActualCampo.isEmpty()) {
                mostrarAlerta("Dato Requerido", "Ingrese el ID del evento que desea buscar para editar.");
                return;
            }
            try {
                Evento ev = gestorEventos.buscarEventoPorId(idActualCampo);
                txtEventoNombre.setText(ev.getNombre());
                dpFecha.setValue(ev.getFechaDelEvento());
                txtPrecioBase.setText(String.valueOf(ev.getPrecioBase()));
                idMemoriaEdicion = idActualCampo;
                txtAreaReporte.appendText("\n[INFO] Editando evento: " + idActualCampo);
            } catch (Exception e) {
                mostrarAlerta("No encontrado", "No existe un evento con el ID: " + idActualCampo);
            }
        } else {
            try {
                String nombre = txtEventoNombre.getText().trim();
                String precioTxt = txtPrecioBase.getText().trim();
                LocalDate fecha = dpFecha.getValue();

                if (nombre.isEmpty() || precioTxt.isEmpty() || fecha == null) {
                    mostrarAlerta("Campos Incompletos", "Debe llenar Nombre, Fecha y Precio para actualizar.");
                    return;
                }

                double precio = Double.parseDouble(precioTxt);
                Evento ev = gestorEventos.buscarEventoPorId(idMemoriaEdicion);
                
                if (!idActualCampo.equals(idMemoriaEdicion)) {
                    if (gestorEventos.existeEvento(idActualCampo)) {
                        mostrarAlerta("ID Duplicado", "El nuevo ID ya pertenece a otro evento.");
                        return;
                    }
                    ev.setIdEvento(idActualCampo);
                }

                ev.editarDatos(nombre, fecha, precio);
                txtAreaReporte.appendText("\n[ACTUALIZADO] El evento ha sido modificado con éxito.");
                idMemoriaEdicion = null;
                verRecaudacion();
                limpiarFormulario();
                
            } catch (NumberFormatException e) {
                mostrarAlerta("Error de Precio", "Asegúrese de que el precio sea un número válido.");
            } catch (Exception e) {
                mostrarAlerta("Error", e.getMessage());
            }
        }
    }

    @FXML
    private void eliminarEventoPorId() {
        try {
            String id = txtEventoId.getText().trim();
            gestorEventos.eliminarEvento(id);
            contexto.guardarTodo();
            txtAreaReporte.appendText("\n[ELIMINADO] Evento con ID: " + id);
            verRecaudacion();
            limpiarFormulario();
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    // --- REPORTES Y CONSULTAS (TAL CUAL LOS TENÍAS) ---

    @FXML
    private void buscarCliente() {
        String busqueda = txtBusquedaId.getText().trim();
        StringBuilder sb = new StringBuilder();
        sb.append("\n==========================================\n");

        if (busqueda.isEmpty()) {
            sb.append("--- LISTADO DE CLIENTES REGISTRADOS ---\n");
            List<Cliente> clientes = Contexto.getInstance().getGestorClientes().getClientesCreados();
            if (clientes.isEmpty()) {
                sb.append("No hay clientes en el sistema.\n");
            } else {
                for (Cliente c : clientes) {
                    sb.append("ID: ").append(c.getIdCliente())
                      .append(" | Nombre: ").append(c.getNombre()).append("\n");
                }
            }
        } else {
            try {
                Cliente c = Contexto.getInstance().getGestorClientes().buscarclientePorId(busqueda);
                if (c == null) {
                    mostrarAlerta("No encontrado", "No existe cliente con ID: " + busqueda);
                    return;
                }

                sb.append("--- CLIENTE ENCONTRADO ---\n")
                  .append("Nombre: ").append(c.getNombre()).append("\n")
                  .append("Identificación: ").append(c.getIdCliente()).append("\n\n");

                sb.append("--- HISTORIAL DE COMPRAS ---\n");
                int contadorBoletos = 0;
                for (Evento evento : gestorEventos.getEventosCreados()) {
                    for (Boleto boleto : evento.getBoletosVendidos()) {
                        if (boleto.getCliente().getIdCliente().equals(busqueda)) {
                            contadorBoletos++;
                            sb.append(" > Evento: ").append(evento.getNombre())
                              .append(" | Ticket: ").append(boleto.getIdBoleto())
                              .append(" | Asiento: ").append(boleto.getAsiento().getFila() + 1)
                              .append("-").append(boleto.getAsiento().getColumna() + 1)
                              .append("\n");
                        }
                    }
                }
                if (contadorBoletos == 0) sb.append("Este cliente no ha realizado compras aún.\n");
                else sb.append("\nTotal de boletos adquiridos: ").append(contadorBoletos).append("\n");

            } catch (Exception e) {
                mostrarAlerta("Error", "Ocurrió un error al buscar: " + e.getMessage());
                return;
            }
        }
        txtAreaReporte.appendText(sb.toString()); 
        txtAreaReporte.setScrollTop(Double.MAX_VALUE); 
    }

    @FXML
    private void verRecaudacion() {
        StringBuilder sb = new StringBuilder("--- REPORTE DETALLADO DE VENTAS ---\n\n");
        double totalGeneral = 0;

        for (Evento e : gestorEventos.getEventosCreados()) {
            double recEvento = e.recaudacionPorEvento();
            totalGeneral += recEvento;

            sb.append("EVENTO: ").append(e.getNombre()).append(" (ID: ").append(e.getIdEvento()).append(")\n");
            sb.append("Fecha: ").append(e.getFechaDelEvento()).append("\n");
            sb.append("Detalle de Ventas:\n");

            if (e.getBoletosVendidos().isEmpty()) {
                sb.append("   - No hay boletos vendidos aún.\n");
            } else {
                for (Boleto b : e.getBoletosVendidos()) {
                    sb.append("   > Ticket: ").append(b.getIdBoleto())
                      .append(" | Cliente: ").append(b.getCliente().getNombre())
                      .append(" (ID: ").append(b.getCliente().getIdCliente()).append(")")
                      .append(" | Asiento: ").append(b.getAsiento().getFila()+1).append("-").append(b.getAsiento().getColumna()+1)
                      .append(" | Pago: ₡").append(String.format("%.2f", b.calcularPrecioFinal()))
                      .append("\n");
                }
            }
            sb.append("Subtotal Evento: ₡").append(String.format("%.2f", recEvento)).append("\n");
            sb.append("--------------------------------------------------\n\n");
        }

        txtAreaReporte.setText(sb.toString());
        lblTotalRecaudado.setText("₡" + String.format("%.2f", totalGeneral));
    }

    @FXML private void limpiarFormulario() {
        txtEventoId.clear(); txtEventoNombre.clear(); txtPrecioBase.clear(); dpFecha.setValue(null);
        idMemoriaEdicion = null;
    }

    @FXML private void guardarEnArchivo() throws Exception { Contexto.getInstance().getPersistencia().guardarTodo(gestorEventos.getEventosCreados(), Contexto.getInstance().getGestorClientes().getClientesCreados()); }

    @FXML private void cargarDesdeArchivo() throws Exception {
        ServicioPersistencia sp = Contexto.getInstance().getPersistencia();
        gestorEventos.setEventosCreados(new ArrayList<>(sp.cargarEventos()));
        Contexto.getInstance().getGestorClientes().setClientesCreados(new ArrayList<>(sp.cargarClientes()));
        sp.cargarVentas(gestorEventos, Contexto.getInstance().getGestorClientes());
        limpiarFormulario(); 
        verRecaudacion();
    }

    @FXML private void switchToVentas() throws Exception { App.setRoot("VentanaPrincipal"); }

    private void mostrarAlerta(String t, String m) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle(t); a.setHeaderText(null); a.setContentText(m); a.showAndWait();
    }
}