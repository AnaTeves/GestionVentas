package app;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class LoginController {
    @FXML
    private ImageView logoView;

    @FXML
    private TextField dniField;

    @FXML
    private PasswordField passwordField;

    @FXML HBox logoLabelContainer;

    // Método que se ejecuta al hacer clic en el botón "Ingresar"
    @FXML
    private void handleLogin() {
        String dni = dniField.getText();
        String password = passwordField.getText();

        // Validar los datos de inicio de sesión
        if (dni.isEmpty() || password.isEmpty()) {
            showAlert("Error", "DNI y contraseña no pueden estar vacíos.");
        } else {
            // Aquí puedes agregar la lógica de autenticación
            if (dni.equals("12345678") && password.equals("admin")) {
                showAlert("Éxito", "Inicio de sesión exitoso.");
            } else {
                showAlert("Error", "DNI o contraseña incorrectos.");
            }
        }
    }

    // Método que se ejecuta al hacer clic en el botón "Cancelar"
    @FXML
    private void handleCancel() {
        // Limpiar los campos de texto
        dniField.clear();
        passwordField.clear();
    }

    // Método para mostrar una alerta en pantalla
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void initialize(){
        Image logo = new Image("/resources/files/logoblanco.png");
        logoView.setImage(logo);
    }
}
