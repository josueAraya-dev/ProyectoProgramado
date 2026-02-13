package View.ControllersFXML;

import View.MainFXML.App;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;

public class VentanaPrincipal implements Initializable {

    @FXML private GridPane gridAsientos;
    @FXML private ComboBox<String> comboTipoBoleto;
    @FXML private TextField txtNombre;
    @FXML private TextField txtId;
    @FXML private Label lblPrecioBase;
    @FXML private Label lblPrecioFinal;
    @FXML private ComboBox<String> comboEventosDisponibles;
    
    private Button[][] matrizAsientos = new Button[10][10];

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        comboTipoBoleto.setItems(FXCollections.observableArrayList("General", "VIP", "Estudiante"));
        
      
        crearMatrizAsientos();
        comboEventosDisponibles.setOnAction(e -> {
        String eventoSeleccionado = comboEventosDisponibles.getValue();
        if (eventoSeleccionado != null) {
            System.out.println("Cambiando a: " + eventoSeleccionado);
            // Aquí Ismael cargará los asientos rojos de este evento específico [cite: 21]
            actualizarMatrizPorEvento(eventoSeleccionado);
        }
    });
    }

    private void crearMatrizAsientos() {
        for (int f = 0; f < 10; f++) {
            for (int c = 0; c < 10; c++) {
                Button btn = new Button((f + 1) + "-" + (c + 1));
                btn.setPrefSize(45, 45);
                btn.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-size: 10px;");
                
                btn.setOnAction(e -> seleccionarAsiento(btn));
                
                matrizAsientos[f][c] = btn;
                gridAsientos.add(btn, c, f);
            }
        }
    }

    private void seleccionarAsiento(Button btn) {
        // Por ahora solo cambia color, Ismael pondrá la lógica de reserva aquí
        if(btn.getStyle().contains("green")) {
            btn.setStyle("-fx-background-color: orange; -fx-text-fill: white;");
        } else {
            btn.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        }
    }
@FXML
private void confirmarCompra() {
    // 1. Validar que los campos no estén vacíos
    if (txtNombre.getText().isEmpty() || txtId.getText().isEmpty() || comboTipoBoleto.getValue() == null) {
        mostrarAlerta("Campos incompletos", "Por favor llene todos los datos del cliente.", Alert.AlertType.WARNING);
        return;
    }

    // 2. Validar que el ID sea numérico (Blindaje)
    try {
        int idValidado = Integer.parseInt(txtId.getText());
        
        // 3. Si el ID es válido, procedemos a marcar los asientos
        boolean algunAsientoSeleccionado = false;
        for (int f = 0; f < 10; f++) {
            for (int c = 0; c < 10; c++) {
                Button btn = matrizAsientos[f][c];
                if (btn.getStyle().contains("orange")) {
                    btn.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white; -fx-font-weight: bold;");
                    btn.setDisable(true); 
                    algunAsientoSeleccionado = true;
                }
            }
        }

        if (algunAsientoSeleccionado) {
            mostrarAlerta("Éxito", "Compra confirmada para el ID: " + idValidado, Alert.AlertType.INFORMATION);
            txtNombre.clear();
            txtId.clear();
        } else {
            mostrarAlerta("Atención", "No has seleccionado ningún asiento.", Alert.AlertType.WARNING);
        }

    } catch (NumberFormatException e) {
        mostrarAlerta("Error de Formato", "La identificación debe ser solo números.", Alert.AlertType.ERROR);
    }
}
private void actualizarMatrizPorEvento(String nombreEvento) {
    // 1. Primero ponemos todos los asientos en VERDE (limpieza visual)
    for (int f = 0; f < 10; f++) {
        for (int c = 0; c < 10; c++) {
            Button btn = matrizAsientos[f][c];
            btn.setStyle("-fx-background-color: green; -fx-text-fill: white;");
            btn.setDisable(false); // Los habilitamos todos de nuevo
        }
    }

    // 2. NOTA PARA ISMAEL: 
    // Aquí debés leer el archivo del evento seleccionado y 
    // poner en ROJO los asientos que ya estén ocupados en ese .txt
    System.out.println("Cambiando vista a la cartelera de: " + nombreEvento);
}
    @FXML
    private void switchToAdmin() throws IOException {
        App.setRoot("Admin");
    }
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
    Alert alert = new Alert(tipo);
    alert.setTitle(titulo);
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.showAndWait();
}
}