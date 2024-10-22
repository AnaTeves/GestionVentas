package app.Controllers;
import app.BDD.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class FormUserController {
    @FXML
    private TextField nomYapeField;
    @FXML
    private TextField dniField;
    @FXML
    private TextField emailField;
    @FXML
    private MenuButton perfilMenuButton;

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
        String dni = dniField.getText();
        String email = emailField.getText();
        String perfilDescripcion = perfilMenuButton.getText();
        int idPerfil = users.obtenerIdPerfil(perfilDescripcion);

        // Verifica que todos los campos estén completos
        if (nomYape.isEmpty() || dni.isEmpty() || email.isEmpty() || idPerfil == -1) {
            userController.mostrarAlerta("Error", "Todos los campos deben estar completos.");
            return;
        }

        // Llama al método para insertar el usuario en la base de datos
        users.addUser(nomYape, dni, email, idPerfil);
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
}
