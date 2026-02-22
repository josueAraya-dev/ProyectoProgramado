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

        // Tipos de boleto
        comboTipoBoleto.setItems(FXCollections.observableArrayList("General", "VIP", "Estudiante"));

        // Cargar eventos desde el modelo
        comboEventosDisponibles.setItems(
            FXCollections.observableArrayList(gestorEventos.getEventosCreados())
        );

        // Mostrar nombre del evento en el combo
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

        // Al cambiar evento recargar sala
        comboEventosDisponibles.setOnAction(e -> cargarEventoSeleccionado());

        // Al cambiar tipo de boleto actualizar precio
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
        for (int f = 0; f < 10; f++) {
            for (int c = 0; c < 10; c++) {
                Button btn = new Button((f + 1) + "-" + (c + 1));
                btn.setPrefSize(45, 45);
                btn.setStyle("-fx-background-color: green; -fx-text-fill: white;");
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
        // Deseleccionar anterior
        if (filaSeleccionada != -1) {
            matrizAsientos[filaSeleccionada][columnaSeleccionada]
                .setStyle("-fx-background-color: green; -fx-text-fill: white;");
        }
        filaSeleccionada = fila;
        columnaSeleccionada = col;
        matrizAsientos[fila][col].setStyle("-fx-background-color: orange; -fx-text-fill: white;");
        actualizarPrecioFinal();
    }

    private void actualizarPrecioFinal() {
        if (eventoSeleccionado == null || comboTipoBoleto.getValue() == null) return;

        double precio = eventoSeleccionado.getPrecioBase();
        String tipo = comboTipoBoleto.getValue();

        double precioFinal = switch (tipo) {
            case "VIP" -> precio + 5000;
            case "Estudiante" -> precio - (precio * 0.10);
            default -> precio;
        };

        lblPrecioFinal.setText("Total a Pagar: ₡" + String.format("%.2f", precioFinal));
    }

    @FXML
    private void confirmarCompra() {
        try {
            if (eventoSeleccionado == null) {
                mostrarAlerta("Error", "Selecciona un evento.");
                return;
            }
            if (filaSeleccionada == -1) {
                mostrarAlerta("Error", "Selecciona un asiento.");
                return;
            }
            if (txtNombre.getText().trim().isEmpty() || txtId.getText().trim().isEmpty()) {
                mostrarAlerta("Error", "Ingresa nombre e ID del cliente.");
                return;
            }
            if (comboTipoBoleto.getValue() == null) {
                mostrarAlerta("Error", "Selecciona un tipo de boleto.");
                return;
            }

            Boleto boleto = gestorVentas.procesarVentaDeBoleto(
                txtId.getText().trim(),
                txtNombre.getText().trim(),
                eventoSeleccionado.getIdEvento(),
                filaSeleccionada,
                columnaSeleccionada,
                comboTipoBoleto.getValue()
            );

            // Actualizar vista
            matrizAsientos[filaSeleccionada][columnaSeleccionada]
                .setStyle("-fx-background-color: red; -fx-text-fill: white;");
            matrizAsientos[filaSeleccionada][columnaSeleccionada].setDisable(true);

            mostrarInfo("¡Compra exitosa!", boleto.imprimir());

            filaSeleccionada = -1;
            columnaSeleccionada = -1;
            txtNombre.clear();
            txtId.clear();

        } catch (Exception e) {
            mostrarAlerta("Error en la compra", e.getMessage());
        }
    }

    @FXML
    private void switchToAdmin() throws Exception {
        App.setRoot("Admin");
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarInfo(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}