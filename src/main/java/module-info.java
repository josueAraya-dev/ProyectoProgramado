module maven.proyecto_programado {
    requires javafx.controls;
    requires javafx.fxml;

    // Abrimos los paquetes a FXML para que JavaFX pueda cargar las vistas
    opens View.ControllersFXML to javafx.fxml;
    opens View.MainFXML to javafx.fxml;

    // Exportamos los paquetes para que sean accesibles
    exports View.ControllersFXML;
    exports View.MainFXML;
    exports Model;
}