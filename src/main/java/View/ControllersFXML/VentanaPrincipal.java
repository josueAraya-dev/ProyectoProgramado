package View.ControllersFXML;

import View.MainFXML.App;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;

/**
 * NOTA PARA JOSUÉ: Asegurate de que la clase Evento tenga un método toString() 
 * que devuelva el nombre, para que el ComboBox lo muestre bien.
 * * NOTA PARA ISMAEL: El comboEventosDisponibles ahora recibe Objetos. 
 * Al seleccionar uno, debés cargar su matriz de asientos correspondiente.
 */
public class VentanaPrincipal implements Initializable {

    @FXML private GridPane gridAsientos;
    @FXML private ComboBox<String> comboTipoBoleto;
    @FXML private TextField txtNombre;
    @FXML private TextField txtId;
    @FXML private Label lblPrecioBase;
    @FXML private Label lblPrecioFinal;
    
    // Cambiado a Object para que Ismael pueda pasarle el objeto Evento completo
    @FXML private ComboBox<Object> comboEventosDisponibles; 
    
    private Button[][] matrizAsientos = new Button[10][10];

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboTipoBoleto.setItems(FXCollections.observableArrayList("General", "VIP", "Estudiante"));
        
        crearMatrizAsientos();

        comboEventosDisponibles.setOnAction(e -> {
            Object seleccionado = comboEventosDisponibles.getValue();
            if (seleccionado != null) {
                // ISMAEL: 'seleccionado' es el objeto Evento. Usalo para cargar los asientos.
                actualizarMatrizPorEvento(seleccionado.toString());
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
        if(btn.getStyle().contains("green")) {
            btn.setStyle("-fx-background-color: orange; -fx-text-fill: white;");
        } else if(btn.getStyle().contains("orange")) {
            btn.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        }
    }

    @FXML
    private void confirmarCompra() {
        if (txtNombre.getText().isEmpty() || txtId.getText().isEmpty() || comboTipoBoleto.getValue() == null) {
            mostrarAlerta("Campos incompletos", "Por favor llene todos los datos del cliente.", Alert.AlertType.WARNING);
            return;
        }

        try {
            int idValidado = Integer.parseInt(txtId.getText());
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
        for (int f = 0; f < 10; f++) {
            for (int c = 0; c < 10; c++) {
                Button btn = matrizAsientos[f][c];
                btn.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                btn.setDisable(false);
            }
        }
        System.out.println("Cambiando vista a: " + nombreEvento);
    }

    @FXML
    private void switchToAdmin() {
        try {
            App.setRoot("Admin");
        } catch (Exception e) {
            mostrarAlerta("Error de Navegación", "No se pudo cargar la ventana de administración.", Alert.AlertType.ERROR);
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