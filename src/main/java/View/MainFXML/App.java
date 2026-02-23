package View.MainFXML;

import Model.Contexto;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // COMENTADO: Ya no cargamos datos automáticamente al iniciar
        // Contexto.getInstance(); 

        scene = new Scene(loadFXML("VentanaPrincipal"), 900, 600);
        stage.setTitle("Sistema de Boletos - Gestión de Eventos");
        
        // MODIFICADO: Al cerrar solo notificamos, no sobreescribimos los archivos
        stage.setOnCloseRequest(event -> {
            System.out.println("Cerrando sistema...");
            // Si quieres que el usuario elija guardar, esto debería estar en un botón, 
            // no aquí de forma obligatoria.
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/ViewFXML/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}