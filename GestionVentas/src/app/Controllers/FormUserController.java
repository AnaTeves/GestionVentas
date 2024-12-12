package app.Controllers;
import java.io.IOException;

import app.BDD.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class FormUserController {
    @FXML
    private TextField nomYapeField;
    @FXML
    private TextField dniField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField contraseñaField;
    @FXML
    private MenuButton perfilMenuButton;
    @FXML
    private StackPane mainContentForm;

    // Creamos una instancia de user service
    private UserService users = new UserService();
    // Creamos una instancia de user controller
    private UserController userController = new UserController();

    @FXML
    public void initialize() {
        perfilSelection();
    }
    
    // Metodo que agrega un nuevo usuario
    @FXML
    public void agregarUsuario() {
        String nomYape = nomYapeField.getText();
        String contraseña = contraseñaField.getText();
        String dni = dniField.getText();
        String email = emailField.getText();
        String perfilDescripcion = perfilMenuButton.getText();
        int idPerfil = users.obtenerIdPerfil(perfilDescripcion);

        // Verifica que todos los campos estén completos
        if (nomYape.isEmpty() || dni.isEmpty() || email.isEmpty() || contraseña.isEmpty() ||idPerfil == -1) {
            userController.mostrarAlerta("Error", "Todos los campos deben estar completos.");
            return;
        }

        // Llama al método para insertar el usuario en la base de datos
        users.addUser(nomYape, dni, email, idPerfil, contraseña);
        userController.mostrarAlerta("Éxito", "Usuario agregado correctamente.");
    }
    
    private void perfilSelection() {
        for (MenuItem item : perfilMenuButton.getItems()) {
            item.setOnAction(event -> {
                String perfilSeleccionado = item.getText();
                perfilMenuButton.setText(perfilSeleccionado);
            });
        }
    }

    @FXML
    public void cancelar(){
            try {
            Node usuarioview = FXMLLoader.load(getClass().getResource("/resources/UserView.fxml"));
            mainContentForm.getChildren().clear(); // Limpiar contenido actual
            mainContentForm.getChildren().add(usuarioview); // Cargar vista de categorías
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo cargar la vista de usuarios");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    
}