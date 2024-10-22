package app.Controllers;
import app.BDD.UserService;

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
import app.Models.Usuario;

import java.io.IOException;

public class UserController {

    @FXML
    private TableView<Usuario> tableView;

    @FXML
    private TableColumn<Usuario, String> nombreCol;

    @FXML
    private TableColumn<Usuario, String> dniCol;

    @FXML
    private TableColumn<Usuario, String> emailCol;

    @FXML
    private TableColumn<Usuario, String> tipoUsuarioCol;

    private ObservableList<Usuario> usuarios = FXCollections.observableArrayList();

    @FXML
    private StackPane mainContent;

    @FXML
    private TextField buscarUser;

    // Creamos una instancia de user service
    private UserService users = new UserService();

    @FXML
    public void initialize() {
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nomYape"));
        dniCol.setCellValueFactory(new PropertyValueFactory<>("dni"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        tipoUsuarioCol.setCellValueFactory(new PropertyValueFactory<>("idPerfil"));

        // Cargar datos desde la base de datos
        cargarDatosDesdeBD();
    }

    // Metodo que llama al metodo loadUsers de la clase UserService y actualiza la tabla
    private void cargarDatosDesdeBD() {
        usuarios = users.loadUsers();
        tableView.setItems(usuarios);
    }

    @FXML
    public void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Metodo que busca usuario por su dni
    @FXML
    public void buscarUsuario() {
        String dni = buscarUser.getText();

        if (dni.isEmpty()) { // Si el campo esta vacio muestra mensaje de error
            System.out.println("Ingrese un DNI para buscar");
            return;
        }
        
        // Llama al método para buscar el usuario en la base de datos
        Usuario usuario = users.searchUser(dni);
        if (usuario != null){ // Si encontramos un usuario
            tableView.getItems().clear(); // Limpiamos la tabla
            tableView.getItems().add(usuario); // Mostramos el usuario
        } else { // Si no encontramos un usuario    
            mostrarAlerta("Error", "Usuario no encontrado");
        }
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
    public void añadirUsuario(){
        setView("/resources/FormUser.fxml");
    }
}
