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
        // Al llamar a getInstance(), el constructor de Contexto ya carga los archivos.
        Contexto.getInstance(); 

        scene = new Scene(loadFXML("VentanaPrincipal"), 900, 600);
        stage.setTitle("Sistema de Boletos - Gestión de Eventos");
        
        // GUARDADO AUTOMÁTICO AL CERRAR
        stage.setOnCloseRequest(event -> {
            System.out.println("Cerrando sistema... Guardando datos.");
            Contexto.getInstance().guardarTodo();
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        // Asegúrate de que la ruta "/ViewFXML/" sea correcta en tu estructura de carpetas
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/ViewFXML/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}