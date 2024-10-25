package app.Controllers;
import app.BDD.CategoriaService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import app.Models.Categoria;
import app.Models.Usuario;

import java.io.IOException;

public class CategoriaController {
    @FXML
    private TableView<Categoria> tableView;

    @FXML
    private TableColumn<Categoria, Integer> idCol;

    @FXML
    private TableColumn<Categoria, String> nombreCol;

    @FXML
    private TableColumn<Categoria, String> descCol;

    @FXML
    private TableColumn<Categoria, String> estadoCol;

    private ObservableList<Categoria> categorias = FXCollections.observableArrayList();

    @FXML
    private StackPane mainContent;

    private CategoriaService categoria = new CategoriaService();

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        estadoCol.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Cargar datos desde la base de datos
        cargarDatosDesdeBD();
    }

    private void cargarDatosDesdeBD() {
        categorias = categoria.loadCategorias();
        tableView.setItems(categorias);
    }

    @FXML
    public void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Metodo para cargar una vista en el mainContent
    @FXML
    private void setView(String fxmlPath) {
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

    // Metodo que carga la vista del formulario para añadir un usuario
    @FXML
    public void añadirCategoria(){
        setView("/resources/FormCategoria.fxml");
    }
}
