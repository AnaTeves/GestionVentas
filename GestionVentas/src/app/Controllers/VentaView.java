package app.Controllers;

import app.Models.Venta;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import app.BDD.DatabaseConnection;
import app.BDD.VentaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;

public class VentaView {

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

    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField quantityField;


    private final VentaService ventaService = new VentaService();

    public void initialize() {
        // Configuración de las columnas de la tabla
        fechaventaCol.setCellValueFactory(new PropertyValueFactory<>("fechaVenta"));
        totalventaCol.setCellValueFactory(new PropertyValueFactory<>("totalVenta"));
        dniusuarioCol.setCellValueFactory(new PropertyValueFactory<>("dni_usuario"));
        dniclienteCol.setCellValueFactory(new PropertyValueFactory<>("dni_cliente"));

        // Cargar los datos en la tabla
        loadVentas();
    }

    private void loadVentas() {
        ObservableList<Venta> ventas = FXCollections.observableArrayList(ventaService.getAllVentas());
        tableView.setItems(ventas);

        clienteMasCompras();
    }

    public void clienteMasCompras(){
        String query = "SELECT TOP 1 v.id_cliente, c.nomYape, COUNT(v.id_venta) AS cantidad_compras\r\n" + //
                        "FROM VENTA v\r\n" + //
                        "JOIN CLIENTE c ON v.id_cliente = c.id_cliente\r\n" + //
                        "GROUP BY v.id_cliente, c.nomYape\r\n" + //
                        "ORDER BY cantidad_compras DESC;";

        try (Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);){
            while (rs.next()) {
                int id_cliente = rs.getInt("id_cliente");
                String nomYape = rs.getString("nomYape");
                int cantidad_compras = rs.getInt("cantidad_compras");
                
                idField.setText(String.valueOf(id_cliente));
                nameField.setText(nomYape);
                quantityField.setText(String.valueOf(cantidad_compras));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
