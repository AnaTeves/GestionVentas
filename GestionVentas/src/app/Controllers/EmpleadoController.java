package app.Controllers;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Map;

import app.BDD.VentaService;
import app.Models.Venta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

// Controlador del empleado que hereda del controlador de metodos comunes
public class EmpleadoController extends ComunesController {
    // Tabla y columnas de la tabla
    @FXML
    private TableView<Venta> tableView;
    @FXML
    private TableColumn<Venta, Timestamp> fechaventaCol;
    @FXML
    private TableColumn<Venta, Float> totalventaCol;
    @FXML
    private TableColumn<Venta, String> dniusuarioCol;
    @FXML
    private TableColumn<Venta, String> dniclienteCol;
    // Controlar las fechas de los reportes
    @FXML
    private DatePicker datePickerInicio;
    @FXML
    private DatePicker datePickerFin;

    private VentaService ventaService = new VentaService(); // Instanciamos la clase que interactua con la base de datos de las ventas

    @FXML
    private StackPane mainContent;
    @FXML
    private BorderPane mainBorderPane;

    @FXML GridPane gridPane;
    private Node vistaInicial;

    @FXML
    public void initialize(){
        super.initialize();
        vistaInicial = mainContent; // Asigno la vista inicial que contiene los reportes
        // Configuracion de las columnas de la tabla
        fechaventaCol.setCellValueFactory(new PropertyValueFactory<>("fechaVenta"));
        totalventaCol.setCellValueFactory(new PropertyValueFactory<>("totalVenta"));
        dniusuarioCol.setCellValueFactory(new PropertyValueFactory<>("dni_usuario"));
        dniclienteCol.setCellValueFactory(new PropertyValueFactory<>("dni_cliente"));

        // Cargar los datos en la tabla
        loadVentas();
    }

    // Metodo que carga la tabla con los datos almacenados en la base de datos
    private void loadVentas() {
        ObservableList<Venta> ventas = FXCollections.observableArrayList(ventaService.getAllVentas());
        tableView.setItems(ventas);
    }

    
    @FXML
    public void calcularReporteVentas() {
        LocalDate fechaInicio = datePickerInicio.getValue();
        LocalDate fechaFin = datePickerFin.getValue();

        if (fechaInicio != null && fechaFin != null && !fechaInicio.isAfter(fechaFin)) {
            try {
                Map<String, Object> reporte = ventaService.obtenerReporteVentas(fechaInicio, fechaFin);
                if (reporte != null) {
                    int totalVentas = (int) reporte.get("total_ventas");
                    double montoTotal = (double) reporte.get("monto_total");

                    mostrarReporte(totalVentas, montoTotal);
                } else {
                    mostrarReporte(0, 0.0);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                mostrarAlertaError("Error al calcular el reporte", "Ha ocurrido un error al obtener los datos.");
            }
        } else {
            mostrarAlertaError("Fechas inválidas", "Por favor, selecciona un rango de fechas válido.");
        }
    }

    private void mostrarReporte(int totalVentas, double montoTotal) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Reporte de Ventas");
        alert.setHeaderText("Resumen del rango seleccionado");
        alert.setContentText(
                "Total de Ventas: " + totalVentas + "\n" +
                "Monto Generado: $" + String.format("%.2f", montoTotal)
        );
        alert.showAndWait();
    }

    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Metodo para cerrar la sesion
    @FXML
    public void cerrarSesion(){
        handleLogout();
    }

    // Metodo que carga la vista del inventario
    @FXML
    public void handleInventario(){
        setView("/resources/InventarioView.fxml");
    }

    // Metodo que carga la vista de los clientes
    @FXML
    public void handleClientes(){
        setView("/resources/ClientesView.fxml");
    }

    // Metodo que carga la vista de las ventas
    @FXML
    public void handleVentas(){
        setView("/resources/FormVenta.fxml");
    }

    // Metodo que carga la vista de los reportes 
    @FXML
    public void handleReportes(){
        mainBorderPane.setCenter(vistaInicial);
    }
}