package app.Controllers;

import javafx.fxml.FXML;

import java.time.LocalDate;
import java.util.Map;

import app.BDD.CategoriaService;
import app.BDD.VentaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

// Controlador del administrador que hereda del controlador de los metodos comunes
public class AdminController extends ComunesController {

    @FXML
    private ComboBox<String> mesComboBox;
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private BarChart<String, Number> barChartMes;
    @FXML
    private BarChart<String, Number> ventasPorDiaChart; 
    @FXML
    private LineChart<String, Number> ventasLineChart;
    @FXML
    private PieChart pieChart;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    private VentaService ventaService = new VentaService();
    @FXML
    private BorderPane mainBorderPane;
    @FXML GridPane gridPane;
    private Node vistaInicial;
    @FXML
    private StackPane mainContent;
    private CategoriaService categoriaService = new CategoriaService();
    

    @FXML
    public void initialize(){
        super.initialize();
        vistaInicial = mainContent;
        cargarMeses();
        cargarGraficoCategorias();
        cargarVentasPorDia();
        cargarProductosVendidos();

        
        
    }

    public void cargarMeses(){
        mesComboBox.setItems(FXCollections.observableArrayList(
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        ));

        System.out.println("Meses cargados: " + mesComboBox.getItems());

        // Mostrar datos iniciales (ingresos mensuales)
        cargarDatosMensuales();

        // Evento al seleccionar un mes
        mesComboBox.setOnAction(event -> {
            String mesSeleccionado = mesComboBox.getSelectionModel().getSelectedItem();
            if (mesSeleccionado != null) {
                cargarDatosSemanalPorMes(mesSeleccionado);
            }
        });
    }

    public void cargarGraficoCategorias(LocalDate fromDate, LocalDate toDate) {

        ObservableList<PieChart.Data> data;

        data = categoriaService.obtenerVentasPorCategoriaFiltradas(fromDate, toDate);

    // Actualizar el gráfico
        pieChart.setData(data);
    }    

    public void cargarGraficoCategorias() {

        ObservableList<PieChart.Data> data;
        // Cargo todos los datos de mi base de datos
        data = categoriaService.obtenerVentasPorCategoria();
        pieChart.setData(data);
    }

    public void cargarVentasPorDia() {

        ventasLineChart.setTitle("Ventas por Día de la Semana");
        yAxis.setLabel("Cantidad de Ventas");
        

        Map<String, Integer> ventasPorDia = ventaService.obtenerVentasPorDiaDeLaSemana();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Ventas");

        for (Map.Entry<String, Integer> entry : ventasPorDia.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        ventasLineChart.getData().add(series);
    }

    public void cargarProductosVendidos(){
        barChart.setData(ventaService.obtenerProductosVendidos());

        // Rotar las etiquetas del eje X para que no se sobrepongan
        CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();
        xAxis.setTickLabelRotation(45); // Gira las etiquetas 45 grados
    }

    private void cargarDatosMensuales() {
        barChartMes.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Ingresos Mensuales");

        // Obtener datos de ingresos mensuales (de tu servicio)
        VentaService.obtenerIngresosMensuales().forEach((mes, total) -> {
            series.getData().add(new XYChart.Data<>(mes, total));
        });

        barChartMes.getData().add(series);
    }

    private void cargarDatosSemanalPorMes(String mesSeleccionado) {
        barChartMes.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Ingresos semanales de " + mesSeleccionado);

        // Obtener datos de ingresos semanales por mes (de tu servicio)
        VentaService.obtenerIngresosSemanalesPorMes(mesSeleccionado).forEach((semana, total) -> {
            series.getData().add(new XYChart.Data<>(semana, total));
        });

        barChartMes.getData().add(series);
    }

    // Metodo que carga la vista de los usuarios
    @FXML
    public void handleUsers(){
        setView("/resources/UserView.fxml");
    }

    // Metodo que carga la vista del inventario
    @FXML
    public void handleInventario(){
        setView("/resources/InventarioView.fxml");
    }

    // Metodo que carga la vista de las categorias
    @FXML
    public void handleCategorias(){
        setView("/resources/CategoriasView.fxml");
    }

    // Metodo que carga la vista de las ventas
    @FXML
    public void handleVentas(){
        setView("/resources/VentasView.fxml");
    }

    // Metodo que carga la vista de las ventas
    @FXML
    public void handleReportes(){
        mainBorderPane.setCenter(vistaInicial);
    }

    @FXML
    public void openBackupForm(){
        setView("/resources/BackupForm.fxml");
    }

    @FXML
    public void cerrarSesion(){
        handleLogout();
    }
}