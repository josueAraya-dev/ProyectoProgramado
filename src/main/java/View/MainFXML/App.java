package View.MainFXML;

import Model.Contexto;
import Model.ServicioPersistencia;
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
        cargarDatosDesdeArchivos();
        scene = new Scene(loadFXML("VentanaPrincipal"), 900, 600);
        stage.setTitle("Sistema de Boletos");
        stage.setScene(scene);
        stage.show();
    }

    private void cargarDatosDesdeArchivos() {
        try {
            ServicioPersistencia persistencia = Contexto.getInstance().getPersistencia();

            // Carga en orden estricto: Eventos → Clientes → Ventas
            Contexto.getInstance().getGestorEventos()
                .setEventosCreados(persistencia.cargarEventos());

            Contexto.getInstance().getGestorClientes()
                .setClientesCreados(persistencia.cargarClientes());

            persistencia.cargarVentas(
                Contexto.getInstance().getGestorEventos(),
                Contexto.getInstance().getGestorClientes()
            );

            System.out.println("Datos cargados correctamente.");
        } catch (Exception e) {
            System.err.println("Sin archivos previos o error al cargar: " + e.getMessage());
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