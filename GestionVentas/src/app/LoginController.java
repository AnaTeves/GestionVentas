package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private Stage stage;

    // Método para manejar el evento de presionar el botón de Ingresar
    @FXML
    protected void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validación de usuario y contraseña
        if (isValidLogin(username, password)) {
            try {
                // Cargar la vista de menú
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuView.fxml"));
                Parent root = loader.load();

                // Obtener el controlador del menú
                MainController menuController = loader.getController();
                
                // Llamar a un método para habilitar/deshabilitar opciones según el tipo de usuario
                menuController.setUserRole(username);

                // Mostrar el menú
                stage = (Stage) usernameField.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Mostrar mensaje de error si las credenciales son incorrectas
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Login");
            alert.setHeaderText("Usuario o Contraseña incorrectos");
            alert.setContentText("Por favor, intenta de nuevo.");
            alert.showAndWait();
        }
    }

    // Método para validar credenciales (puedes reemplazarlo con una llamada a una base de datos)
    private boolean isValidLogin(String username, String password) {
        // Simulación de validación de usuarios (admin, gerente, repositores)
        if (username.equals("admin") && password.equals("admin")) {
            return true;
        } else if (username.equals("gerente") && password.equals("gerente123")) {
            return true;
        } else if (username.equals("repositor") && password.equals("repositor123")) {
            return true;
        }
        return false;
    }

    public void handleCancel(ActionEvent event) {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }
}
