package View.ControllersFXML;

import View.MainFXML.App;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
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
    }

    private void crearMatrizAsientos() {
        for (int f = 0; f < 10; f++) {
            for (int c = 0; c < 10; c++) {
                Button btn = new Button((f + 1) + "-" + (c + 1));
                btn.setPrefSize(45, 45);
                btn.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                btn.setOnAction(e -> seleccionarAsiento(btn));
                
                matrizAsientos[f][c] = btn;
                gridAsientos.add(btn, c, f);
            }
        }
    }

    private void seleccionarAsiento(Button btn) {
        // Lógica visual simple: Verde <-> Naranja
        if(btn.getStyle().contains("green")) {
            btn.setStyle("-fx-background-color: orange; -fx-text-fill: white;");
        } else {
            btn.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        }
    }

    @FXML
    private void confirmarCompra() {
        // Solo cambia el color de los seleccionados a Rojo (Ocupado)
        for (int f = 0; f < 10; f++) {
            for (int c = 0; c < 10; c++) {
                Button btn = matrizAsientos[f][c];
                if (btn.getStyle().contains("orange")) {
                    btn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                    btn.setDisable(true); 
                }
            }
        }
    }

    @FXML
    private void switchToAdmin() throws Exception {
        App.setRoot("Admin");
    }
}