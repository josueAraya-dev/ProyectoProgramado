package View.ControllersFXML;

import View.MainFXML.App;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Admin {

    @FXML private TextField txtEventoNombre;
    @FXML private TextField txtPrecioBase;
    @FXML private DatePicker dpFecha;
    @FXML private TextArea txtAreaReporte;
    @FXML private Label lblTotalRecaudado;

    @FXML
    private void switchToVentas() throws Exception {
        App.setRoot("VentanaPrincipal");
    }

    @FXML
    private void reiniciarSala() {
        // La lógica de limpieza de archivos y matriz le toca al controlador
        txtAreaReporte.appendText("\nSolicitud de reinicio de sala enviada.");
    }

    @FXML
    private void guardarEvento() {
        // Solo capturamos para confirmar que la vista funciona
        String nombre = txtEventoNombre.getText();
        txtAreaReporte.appendText("\nEvento '" + nombre + "' enviado al sistema.");
    }
}