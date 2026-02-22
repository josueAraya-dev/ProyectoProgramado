package View.ControllersFXML;

import Model.Contexto;
import Model.Evento;
import Model.GestorEventos;
import Model.Cliente;
import View.MainFXML.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Admin implements Initializable {

    @FXML private TextField txtEventoId;
    @FXML private TextField txtEventoNombre;
    @FXML private TextField txtPrecioBase;
    @FXML private DatePicker dpFecha;
    @FXML private TextArea txtAreaReporte;
    @FXML private Label lblTotalRecaudado;
    @FXML private TextField txtBusquedaId;

    private GestorEventos gestorEventos;

    @Override
public void initialize(URL url, ResourceBundle rb) {
    gestorEventos = Contexto.getInstance().getGestorEventos();
    // Llamamos directamente al método que calcula y llena el área de texto
    verRecaudacion(); 
}

    @FXML
    private void guardarEvento() {
        try {
            String id = txtEventoId.getText().trim();
            String nombre = txtEventoNombre.getText().trim();
            String precioTxt = txtPrecioBase.getText().trim();
            LocalDate fecha = dpFecha.getValue();

            if (id.isEmpty() || nombre.isEmpty() || precioTxt.isEmpty() || fecha == null) {
                mostrarAlerta("Error", "Todos los campos son obligatorios.");
                return;
            }

            double precio = Double.parseDouble(precioTxt);
            gestorEventos.crearEvento(id, nombre, fecha, precio);

            txtAreaReporte.appendText("\nEvento '" + nombre + "' creado con ID: " + id);
            limpiarFormulario();
            actualizarReporte();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El precio debe ser un número válido.");
        } catch (IllegalArgumentException e) {
            mostrarAlerta("Error de validación", e.getMessage());
        }
    }

    @FXML
    private void reiniciarSala() {
        if (gestorEventos.getEventosCreados().isEmpty()) {
            mostrarAlerta("Aviso", "No hay eventos creados.");
            return;
        }
        for (Evento e : gestorEventos.getEventosCreados()) {
            e.ejecutarReinicioDeSala();
        }
        txtAreaReporte.appendText("\nSalas reiniciadas correctamente.");
    }

    @FXML
    private void buscarCliente() {
        try {
            String id = txtBusquedaId.getText().trim();
            Cliente cliente = Contexto.getInstance().getGestorClientes().buscarclientePorId(id);
            txtAreaReporte.appendText("\nCliente encontrado: " + cliente.getNombre());
        } catch (Exception e) {
            mostrarAlerta("No encontrado", e.getMessage());
        }
    }

    
    @FXML
private void verRecaudacion() {
    StringBuilder sb = new StringBuilder("=== REPORTE DETALLADO DE VENTAS ===\n");
    double totalGeneral = 0;
    
    if (gestorEventos.getEventosCreados().isEmpty()) {
        txtAreaReporte.setText("No hay eventos registrados en el sistema.");
        return;
    }

    for (Evento e : gestorEventos.getEventosCreados()) {
        // Obtenemos la recaudación usando el método de Josué
        double recaudado = e.recaudacionPorEvento(); 
        int cantidadBoletos = e.getBoletosVendidos().size();
        
        totalGeneral += recaudado;
        
        sb.append("\n📌 EVENTO: ").append(e.getNombre())
          .append("\n   ID: ").append(e.getIdEvento())
          .append("\n   Boletos Vendidos: ").append(cantidadBoletos)
          .append("\n   Total Evento: ₡").append(String.format("%.2f", recaudado))
          .append("\n-----------------------------------");
    }
    
    txtAreaReporte.setText(sb.toString());
    lblTotalRecaudado.setText("₡" + String.format("%.2f", totalGeneral));
}

    @FXML private void limpiarFormulario() {
        txtEventoId.clear(); txtEventoNombre.clear(); txtPrecioBase.clear(); dpFecha.setValue(null);
    }

    private void actualizarReporte() {
        txtAreaReporte.setText("Sistema listo. Eventos cargados: " + gestorEventos.getEventosCreados().size());
    }

    @FXML private void switchToVentas() throws Exception { App.setRoot("VentanaPrincipal"); }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo); alert.setContentText(mensaje); alert.showAndWait();
    }
}