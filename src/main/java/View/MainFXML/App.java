package View.MainFXML;

import Model.DataSystem;
import Model.Evento;
import Model.ServicioPersistencia;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
       
        cargarDatosDesdeArchivos();

        scene = new Scene(loadFXML("VentanaPrincipal"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    private void cargarDatosDesdeArchivos() {
        try {
            ServicioPersistencia persistencia = new ServicioPersistencia();
            
            
            List<Evento> eventosGuardados = persistencia.cargarEventos();
            
           
            for (Evento e : eventosGuardados) {
                if (!DataSystem.listaEventos.contains(e.getNombre())) {
                    DataSystem.listaEventos.add(e.getNombre());
                }
            }
            System.out.println("Sincronización inicial completada.");
        } catch (Exception e) {
            System.err.println("Aún no hay archivos creados o hubo un error: " + e.getMessage());
        }
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