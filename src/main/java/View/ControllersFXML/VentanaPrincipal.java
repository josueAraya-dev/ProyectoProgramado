package View.ControllersFXML;

import Model.*;
import View.MainFXML.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.collections.FXCollections;
import java.net.URL;
import java.util.ResourceBundle;

public class VentanaPrincipal implements Initializable {

    @FXML private GridPane gridAsientos;
    @FXML private ComboBox<Evento> comboEventosDisponibles;
    @FXML private ComboBox<String> comboTipoBoleto;
    @FXML private TextField txtNombre;
    @FXML private TextField txtId;
    @FXML private Label lblPrecioBase;
    @FXML private Label lblPrecioFinal;

    private Button[][] matrizAsientos = new Button[10][10];
    private Evento eventoSeleccionado;
    private int filaSeleccionada = -1;
    private int columnaSeleccionada = -1;

    private GestorEventos gestorEventos;
    private GestorVentas gestorVentas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gestorEventos = Contexto.getInstance().getGestorEventos();
        gestorVentas = Contexto.getInstance().getGestorVentas();

        comboTipoBoleto.setItems(FXCollections.observableArrayList("General", "VIP", "Estudiante"));
        comboEventosDisponibles.setItems(FXCollections.observableArrayList(gestorEventos.getEventosCreados()));

        comboEventosDisponibles.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Evento e, boolean empty) {
                super.updateItem(e, empty);
                setText(empty || e == null ? "" : e.getNombre());
            }
        });
        comboEventosDisponibles.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Evento e, boolean empty) {
                super.updateItem(e, empty);
                setText(empty || e == null ? "" : e.getNombre());
            }
        });

        comboEventosDisponibles.setOnAction(e -> cargarEventoSeleccionado());
        comboTipoBoleto.setOnAction(e -> actualizarPrecioFinal());

        crearMatrizAsientos();
    }

    private void cargarEventoSeleccionado() {
        eventoSeleccionado = comboEventosDisponibles.getValue();
        if (eventoSeleccionado == null) return;

        lblPrecioBase.setText("Precio Base: ₡" + String.format("%.2f", eventoSeleccionado.getPrecioBase()));
        filaSeleccionada = -1;
        columnaSeleccionada = -1;
        refrescarMatrizAsientos();
        actualizarPrecioFinal();
    }

    private void crearMatrizAsientos() {
        gridAsientos.getChildren().clear(); 
        for (int f = 0; f < 10; f++) {
            for (int c = 0; c < 10; c++) {
                Button btn = new Button((f + 1) + "-" + (c + 1));
                btn.setPrefSize(45, 45);
                final int fila = f;
                final int col = c;
                btn.setOnAction(e -> seleccionarAsiento(fila, col));
                matrizAsientos[f][c] = btn;
                gridAsientos.add(btn, c, f);
            }
        }
    }

    private void refrescarMatrizAsientos() {
        if (eventoSeleccionado == null) return;
        for (int f = 0; f < 10; f++) {
            for (int c = 0; c < 10; c++) {
                Asiento asiento = eventoSeleccionado.obtenerAsiento(f, c);
                Button btn = matrizAsientos[f][c];
                if (!asiento.estaDisponible()) {
                    btn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                    btn.setDisable(true);
                } else {
                    btn.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                    btn.setDisable(false);
                }
            }
        }
    }

    private void seleccionarAsiento(int fila, int col) {
        if (eventoSeleccionado == null) {
            mostrarAlerta("Aviso", "Primero selecciona un evento.");
            return;
        }
        if (filaSeleccionada != -1) {
            refrescarMatrizAsientos();
        }
        filaSeleccionada = fila;
        columnaSeleccionada = col;
        matrizAsientos[fila][col].setStyle("-fx-background-color: orange; -fx-text-fill: white;");
        actualizarPrecioFinal();
    }

    private void actualizarPrecioFinal() {
        if (eventoSeleccionado == null || comboTipoBoleto.getValue() == null) return;

        String tipo = comboTipoBoleto.getValue();
        // CORRECCIÓN: Usamos un ID genérico que cumpla con la validación de no estar vacío
        Cliente clienteDummy = new Cliente("Sistema", "99999"); 
        Asiento asientoDummy = new Asiento(0, 0);
        Boleto boletoTemporal;

        // Polimorfismo en acción: Creamos el objeto según el tipo
        boletoTemporal = switch (tipo) {
            case "VIP" -> new BoletoVIP(eventoSeleccionado, clienteDummy, asientoDummy);
            case "Estudiante" -> new BoletoEstudiante(eventoSeleccionado, clienteDummy, asientoDummy);
            default -> new BoletoGeneral(eventoSeleccionado, clienteDummy, asientoDummy);
        };

        // Aquí ocurre la magia: No importa qué boleto sea, el modelo sabe su precio
        double precioFinal = boletoTemporal.calcularPrecioFinal();
        
        lblPrecioFinal.setText("Total a Pagar: ₡" + String.format("%.2f", precioFinal));
    }

    @FXML
    private void confirmarCompra() {
        try {
            // 1. Validaciones de campos vacíos
            if (eventoSeleccionado == null || filaSeleccionada == -1 || 
                txtNombre.getText().trim().isEmpty() || txtId.getText().trim().isEmpty() || 
                comboTipoBoleto.getValue() == null) {
                mostrarAlerta("Error", "Complete todos los campos y seleccione su asiento.");
                return;
            }

            String idCli = txtId.getText().trim();
            String nomCli = txtNombre.getText().trim();

            // --- CORRECCIÓN DE EXCEPCIÓN: Validar que el ID sea solo números ---
            if (!idCli.matches("\\d+")) {
                mostrarAlerta("Error de Formato", "La identificación debe contener únicamente números (sin letras ni espacios).");
                return;
            }

            Boleto boleto = gestorVentas.procesarVentaDeBoleto(
                idCli, nomCli, eventoSeleccionado.getIdEvento(),
                filaSeleccionada, columnaSeleccionada, comboTipoBoleto.getValue()
            );

            // Generar el archivo de texto para cumplir con el requerimiento de Facturación
            Contexto.getInstance().getPersistencia().generarTicketTexto(boleto.imprimir(), boleto.getIdBoleto());

            refrescarMatrizAsientos();
            mostrarInfo("¡Compra exitosa!", "Se ha generado un ticket en la carpeta /tickets\n\n" + boleto.imprimir());

            // Limpieza
            filaSeleccionada = -1;
            columnaSeleccionada = -1;
            txtNombre.clear();
            txtId.clear();

        } catch (Exception e) {
            // Este catch general evita que la app se cierre si ocurre algo inesperado
            mostrarAlerta("Error en la compra", e.getMessage());
        }
    }

    @FXML private void switchToAdmin() throws Exception { App.setRoot("Admin"); }
    private void mostrarAlerta(String t, String m) { 
        Alert a = new Alert(Alert.AlertType.ERROR); a.setTitle(t); a.setContentText(m); a.showAndWait(); 
    }
    private void mostrarInfo(String t, String m) { 
        Alert a = new Alert(Alert.AlertType.INFORMATION); a.setTitle(t); a.setContentText(m); a.showAndWait(); 
    }
}