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
import javafx.scene.layout.StackPane;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private Stage stage;
    private StackPane stackPane;

    // Método para manejar el evento de presionar el botón de Ingresar
    @FXML
    protected void handleLogin(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Validación de usuario y contraseña
        if (isValidLogin(username, password)) {
            try {
                // Cargar la vista de menú
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/MainView.fxml"));
                Parent root = loader.load();

                // Obtener el controlador del menú
                MainController menuController = loader.getController();
                
                // Llamar a un método para habilitar/deshabilitar opciones según el tipo de usuario
                menuController.setUserRole(username);

                // Mostrar el menú
                stage = (Stage) usernameField.getScene().getWindow(); // Obtengo la ventana actual
                Scene scene = new Scene(root); // Creo una nueva instancia con la escena que deseo mostrar
                stage.setScene(scene);
                stage.setFullScreen(true); // Defino que la ventana sea de pantalla completa
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
        } else if (username.equals("gerente") && password.equals("gerente")) {
            return true;
        } else if (username.equals("empleado") && password.equals("empleado")) {
            return true;
        }
        return false;
    }

    public void handleCancel(ActionEvent event) {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }
}
