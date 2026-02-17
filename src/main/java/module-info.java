module maven.proyecto_programado {
    requires javafx.controls;
    requires javafx.fxml;

    opens maven.proyecto_programado to javafx.fxml;
    exports maven.proyecto_programado;
    exports Model;
}
