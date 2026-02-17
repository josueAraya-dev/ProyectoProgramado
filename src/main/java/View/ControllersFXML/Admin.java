package View.ControllersFXML;

import View.MainFXML.App;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Optional;

/**
 * NOTA PARA JOSUÉ (MODELO): Los campos txtEventoNombre, txtPrecioBase y dpFecha 
 * deben usarse para instanciar tu clase Evento.
 * * NOTA PARA ISMAEL (CONTROLADOR): En guardarEvento() debés capturar el objeto 
 * creado y añadirlo a la lista global que usará el ComboBox de la VentanaPrincipal.
 */
public class Admin {

    @FXML private TextField txtEventoNombre;
    @FXML private TextField txtPrecioBase;
    @FXML private DatePicker dpFecha;
    @FXML private TextArea txtAreaReporte;
    @FXML private Label lblTotalRecaudado;
    @FXML private TextField txtBusquedaId;

    @FXML
    private void switchToVentas() {
        try {
            App.setRoot("VentanaPrincipal");
        } catch (Exception e) {
            mostrarAlerta("Error de Navegación", "No se pudo cargar la ventana de ventas.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void reiniciarSala() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de Seguridad");
        alert.setHeaderText("¿Desea limpiar TODA la sala?");
        alert.setContentText("Esta acción pondrá los 100 asientos en verde. No se puede deshacer.");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // ISMAEL: Aquí va tu lógica para resetear la matriz de botones y archivos .txt
            txtAreaReporte.appendText("\nSALA REINICIADA POR EL ADMINISTRADOR.");
        }
    }

    @FXML
    private void guardarEvento() {
        try {
            String nombre = txtEventoNombre.getText();
            double precio = Double.parseDouble(txtPrecioBase.getText());
            
            if (nombre.isEmpty() || precio <= 0) {
                mostrarAlerta("Datos Incompletos", "Asegúrese de poner un nombre y precio mayor a 0", Alert.AlertType.WARNING);
                return;
            }

            // ISMAEL: Aquí debés crear el objeto 'new Evento(nombre, precio, fecha)' 
            // y guardarlo en tu estructura de datos.
            txtAreaReporte.appendText("\nEvento '" + nombre + "' guardado. Precio: ₡" + precio);
            
        } catch (NumberFormatException e) {
            mostrarAlerta("Error de Precio", "Por favor, ingrese solo números en el precio base.", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}