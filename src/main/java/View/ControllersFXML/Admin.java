package View.ControllersFXML;

import Model.DataSystem;
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
    DataSystem.limpiarSala(); 
    txtAreaReporte.appendText("\nSala reiniciada. Todos los asientos están disponibles.");
}

@FXML
private void guardarEvento() {
    String nombre = txtEventoNombre.getText();
    
    if (!nombre.isEmpty()) {
      
        DataSystem.listaEventos.add(nombre); 
        
        txtAreaReporte.appendText("\nEvento '" + nombre + "' guardado en la lista global.");
        txtEventoNombre.clear();
    }
}
}