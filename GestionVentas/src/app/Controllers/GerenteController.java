package app.Controllers;
import java.util.List;
import app.BDD.VentaService;
import app.BDD.CategoriaService;
import app.BDD.DatabaseConnection;
// import app.BDD.UserService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import java.time.LocalDate;
import java.time.LocalTime;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javafx.collections.FXCollections;
import javafx.scene.control.Label;

public class GerenteController extends ComunesController {

    private CategoriaService categoriaService = new CategoriaService();
    private VentaService ventaService = new VentaService();
    @FXML
    private BarChart<String, Number> ventasPorDiaChart; 
    @FXML
    private LineChart<String, Number> ventasLineChart;
    @FXML
    private PieChart pieChart;
    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private DatePicker datePickerFrom;

    @FXML
    private DatePicker datePickerTo;

    @FXML
    private StackPane mainContent;
    @FXML
    private BorderPane mainBorderPane;

    @FXML GridPane gridPane;
    private Node vistaInicial;

    // NEW REPORTES 2.0 - Reportes mas vendidos
    @FXML
    private ComboBox<String> comboReporte;

    @FXML
    private DatePicker fechaInicio, fechaFin;

    @FXML
    private PieChart graficoReporte;

    // Reportes resumen de ingresos
    @FXML
    private ComboBox<String> comboBoxOpciones;
    
    @FXML Label labelMontoTotal, labelNumeroVentas;

    // FIN NEW

    @FXML
    private ComboBox<String> vendedorComboBox;
    // private UserService userService = new UserService();

    @FXML
    public void initialize(){
        super.initialize();
        vistaInicial = mainContent;
        
        // Cargo por defecto todos los datos de mi base de datos
        // cargarGraficoCategorias();
        // datePickerFrom.setOnAction(event -> filtrarDatos());
        // datePickerTo.setOnAction(event -> filtrarDatos());

        // cargarVentasPorDia();
        // cargarProductosVendidos();

        // List<String> usuarios = userService.loadUsersName();
        // vendedorComboBox.getItems().clear();
        // vendedorComboBox.getItems().addAll(usuarios);
        // // Establecer el manejador de eventos para cuando el usuario seleccione una opción
        // vendedorComboBox.setOnAction(event -> {
        //     String userSeleccionado = vendedorComboBox.getSelectionModel().getSelectedItem();
        //     if (userSeleccionado != null) {
        //     actualizarVentasPorDia(userSeleccionado);
        //     }
        // });

        // Combo box para seleccionar el tipo de reporte
        comboReporte.setItems(FXCollections.observableArrayList("Productos más vendidos", "Categorías más vendidas"));

        comboBoxOpciones.setItems(FXCollections.observableArrayList(
            "Dia actual",
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre",
            "2023", "2024"
        ));

        comboBoxOpciones.setOnAction(event -> manejarSeleccion(comboBoxOpciones.getValue()));
    }

    // Metodo que carga la vista del inventario
    @FXML
    public void handleInventario(){
        setView("/resources/InventarioView.fxml");
    }

    @FXML
    public void handleUsers(){
        setView("/resources/UserView.fxml");
    }

    // Metodo que carga la vista de las categorias
    @FXML
    public void handleCategorias(){
        setView("/resources/CategoriasView.fxml");
    }

    // Metodo que carga la vista de los clientes
    @FXML
    public void handleClientes(){
        setView("/resources/ClientesView.fxml");
    }

    // Metodo que carga la vista de las ventas
    @FXML
    public void handleVentas(){
        setView("/resources/VentasView.fxml");
    }

    @FXML
    public void handleReportes(){
        mainBorderPane.setCenter(vistaInicial);
    }

    @FXML
    public void cerrarSesion(){
        handleLogout();
    }

    // Metodo que genera el reporte de los mas vendidos (categorias o productos)
    @FXML
    private void generarReporte(){
        String opcionSeleccionada = comboReporte.getValue();
        LocalDate inicio = fechaInicio.getValue();
        LocalDate fin = fechaFin.getValue();

        if (opcionSeleccionada == null || inicio == null || fin == null) {
            showAlert("Error", "Debe completar todos los campos para generar el reporte.");
            return;
        }

        if (opcionSeleccionada.equals("Productos más vendidos")) {
            generarGraficoProductos(inicio, fin);
        } else if (opcionSeleccionada.equals("Categorías más vendidas")) {
            generarGraficoCategorias(inicio, fin);
        }
    }

    private void generarGraficoProductos(LocalDate inicio, LocalDate fin) {
        // try{
        //     ObservableList<PieChart.Data> data = ventaService.obtenerProductosMasVendidos(inicio, fin);
        //     graficoReporte.setData(data);
        //     // graficoReporte.setTitle("Productos mas vendidos");
        // } catch(SQLException e){
        //     e.printStackTrace();
        // }
        try{
            ObservableList<PieChart.Data> data = ventaService.obtenerProductosMasVendidos(inicio, fin);
            graficoReporte.setData(data);
            // graficoReporte.setTitle("Categorias mas vendidas");

            double sum = 0;
            for (PieChart.Data d : data) {
                sum += d.getPieValue();
            }
                
            for (PieChart.Data d : data) {
                double porcentaje = (d.getPieValue() / sum) * 100;
                d.setName(d.getName() + " (" + String.format("%.2f", porcentaje) + "%)");
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void generarGraficoCategorias(LocalDate inicio, LocalDate fin) {
            ObservableList<PieChart.Data> data = categoriaService.obtenerVentasPorCategoriaFiltradas(inicio, fin);
            graficoReporte.setData(data);
            // graficoReporte.setTitle("Categorias mas vendidas");

            double sum = 0;
            for (PieChart.Data d : data) {
                sum += d.getPieValue();
            }
            
            for (PieChart.Data d : data) {
                double porcentaje = (d.getPieValue() / sum) * 100;
                d.setName(d.getName() + " (" + String.format("%.2f", porcentaje) + "%)");
            }
    }

    private void manejarSeleccion(String seleccion) {
        LocalDate inicio, fin;

        if("Dia actual".equals(seleccion)) {
            inicio = LocalDate.now();
            fin = inicio;
        } else if (esMes(seleccion)) {
            int mes = obtenerNumeroMes(seleccion);
            inicio = LocalDate.of(LocalDate.now().getYear(), mes, 1);
            fin = inicio.withDayOfMonth(inicio.lengthOfMonth());
        } else if (esAnio(seleccion)) {
            int anio = Integer.parseInt(seleccion);
            inicio = LocalDate.of(anio, 1, 1);
            fin = LocalDate.of(anio, 12, 31);
        } else {
            return;
        }

        calcularReporteVentas(inicio, fin);
    }

    private boolean esMes(String seleccion) {
        return List.of("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre").contains(seleccion);
    }

    private int obtenerNumeroMes(String mes) {
        return List.of("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre").indexOf(mes) + 1;
    }

    private boolean esAnio(String seleccion) {
        return seleccion.matches("\\d{4}");
    }

    public void calcularReporteVentas(LocalDate inicio, LocalDate fin) {
        String query = """
            SELECT COUNT(*) AS cantidad_ventas, SUM(total_venta) AS total_venta
            FROM VENTA
            WHERE fecha_venta BETWEEN ? AND ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setTimestamp(1, Timestamp.valueOf(inicio.atStartOfDay()));
                stmt.setTimestamp(2, Timestamp.valueOf(fin.atTime(LocalTime.MAX)));

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int totalVentas = rs.getInt("cantidad_ventas");
                    double montoTotal = rs.getDouble("total_venta");

                    labelNumeroVentas.setText("Numero de ventas: " + totalVentas);
                    labelMontoTotal.setText("Monto total: $" + montoTotal);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showAlert(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
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

    public void filtrarDatos() {
        // Obtener las fechas de los DatePicker
        LocalDate fromDateLocal = datePickerFrom.getValue();
        LocalDate toDateLocal = datePickerTo.getValue();

        // Validar las fechas
        if (fromDateLocal == null || toDateLocal == null) {
            System.out.println("Por favor, seleccione ambas fechas para filtrar.");
            return;
        }
        if (fromDateLocal.isAfter(toDateLocal)) {
            System.out.println("La fecha inicial no puede ser posterior a la fecha final.");
            return;
        }

        // // Convertir a java.sql.Date
        // Date fromDate = Date.valueOf(fromDateLocal);
        // Date toDate = Date.valueOf(toDateLocal);

        // Cargar el gráfico con los datos filtrados
        cargarGraficoCategorias(fromDateLocal, toDateLocal);
    }

    // public void cargarVentasPorDia() {

    //     ventasLineChart.setTitle("Ventas por Día de la Semana");
    //     yAxis.setLabel("Cantidad de Ventas");
        

    //     Map<String, Integer> ventasPorDia = ventaService.obtenerVentasPorDiaDeLaSemana();

    //     XYChart.Series<String, Number> series = new XYChart.Series<>();
    //     series.setName("Ventas");

    //     for (Map.Entry<String, Integer> entry : ventasPorDia.entrySet()) {
    //         series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
    //     }

    //     ventasLineChart.getData().add(series);
    // }

    
    // public void actualizarVentasPorDia(String user) {
    //     ventasLineChart.setTitle("Ventas de " + user);
    //     yAxis.setLabel("Cantidad de Ventas");
    
    //     // Obtener las ventas filtradas por el vendedor seleccionado
    //     Map<String, Integer> ventasPorUser = ventaService.obtenerVentasPorVendedor(user);
    
    //     XYChart.Series<String, Number> series = new XYChart.Series<>();
    //     series.setName("Ventas");
    
    //     // Agregar las ventas filtradas al gráfico
    //     for (Map.Entry<String, Integer> entry : ventasPorUser.entrySet()) {
    //         series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
    //     }
    
    //     ventasLineChart.getData().clear();  // Limpiar el gráfico antes de agregar nuevas series
    //     ventasLineChart.getData().add(series);
    // }

    // public void cargarProductosVendidos(){
    //     barChart.setData(ventaService.obtenerProductosVendidos());

    //     // Rotar las etiquetas del eje X para que no se sobrepongan
    //     CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();
    //     xAxis.setTickLabelRotation(45); // Gira las etiquetas 45 grados
    // }
}