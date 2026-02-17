module maven.proyecto_programado {
    requires javafx.controls;
    requires javafx.fxml;

    // Solo dejamos lo que SI existe físicamente en tus carpetas
    exports Model;

    // Abrimos y exportamos tus carpetas de Vistas
    opens View.ControllersFXML to javafx.fxml;
    opens View.MainFXML to javafx.fxml;

    exports View.ControllersFXML;
    exports View.MainFXML;
}