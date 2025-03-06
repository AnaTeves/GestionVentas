package app.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import app.Models.Producto;
import app.BDD.InventService;

import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class DetalleProductoController {
    @FXML
    private TextField nombreField;
    @FXML
    private TextField descripcionField;
    @FXML
    private TextField precioField;
    @FXML
    private TextField stockField;
    private Producto producto;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnGuardar;
    @FXML
    private StackPane mainContent;

    InventarioController inventarioController = new InventarioController();
    InventService inventService = new InventService();

    public void setProducto(Producto producto) {
        this.producto = producto;
        cargarDatos();
    }

    private void cargarDatos() {
        nombreField.setText(producto.getNombre());
        descripcionField.setText(producto.getDescripcion());
        precioField.setText(String.valueOf(producto.getPrecio()));
    }

    @FXML
    private void habilitarEdicion() {
        nombreField.setEditable(true);
        descripcionField.setEditable(true);
        precioField.setEditable(true);
        btnModificar.setVisible(false);
        btnGuardar.setVisible(true);
    }

    @FXML
    private void volver() {
        setView("/resources/InventarioView.fxml");
    }

    // Metodo para cargar una vista en el mainContent
    @FXML
    public void setView(String fxmlPath) {
        try {
            Node view = FXMLLoader.load(getClass().getResource(fxmlPath));
            mainContent.getChildren().clear(); // Limpia el contenido actual
            mainContent.getChildren().add(view); // Agrega la nueva vista
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo cargar la vista");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void guardarCambios() {
        producto.setNombre(nombreField.getText());
        producto.setDescripcion(descripcionField.getText());
        producto.setPrecio(Float.parseFloat(precioField.getText()));

        inventService.actualizarProducto(producto); // Actualiza el producto en la base de datos.

        nombreField.setEditable(false);
        descripcionField.setEditable(false);
        precioField.setEditable(false);
        btnModificar.setVisible(true);
        btnGuardar.setVisible(false);
    }
}
