package View.ControllersFXML;
import View.MainFXML.App;
import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;
public class Admin {

    @FXML private TextField txtEventoNombre;
    @FXML private TextField txtPrecioBase;
    @FXML private DatePicker dpFecha;
    @FXML private TextArea txtAreaReporte;
    @FXML private Label lblTotalRecaudado;

    @FXML
    private void switchToVentas() throws IOException {
       
        App.setRoot("VentanaPrincipal");
    }


@FXML
private void reiniciarSala() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmación de Seguridad");
    alert.setHeaderText("¿Desea limpiar TODA la sala?");
    alert.setContentText("Esta acción pondrá los 100 asientos en verde. No se puede deshacer.");

   
    Optional<ButtonType> result = alert.showAndWait();

    if (result.isPresent() && result.get() == ButtonType.OK) {
        // Aquí Ismael pondrá el ciclo 'for' que recorre la matriz
        // y pone matrizAsientos[f][c].setStyle("-fx-background-color: green;");
        // Además de habilitarlos: setDisable(false);
        txtAreaReporte.appendText("\nSALA REINICIADA POR EL ADMINISTRADOR.");
    }
}
@FXML private TextField txtBusquedaId;


@FXML
private void guardarEvento() {
    try {
        double precio = Double.parseDouble(txtPrecioBase.getText());
        if (precio <= 0) {
            mostrarAlerta("Precio Inválido", "El precio debe ser mayor a 0", Alert.AlertType.ERROR);
            return;
        }
        // Si pasa, Ismael guarda el evento...
        txtAreaReporte.appendText("\nEvento guardado con precio: ₡" + precio);
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