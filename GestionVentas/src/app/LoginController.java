package app;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


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
        String dni = dniField.getText().trim();
        String password = passwordField.getText().trim();

        switch (dni) {
            case "vendedor":
                // Caso Vendedor
                if (password.equals("12345678")) {
                    abrirFormularioVendedor(); // Llamada para abrir el formulario de vendedor
                    handleCancel(); // Cierra o resetea el login
                } else {
                    showAlert("Error", "Contraseña incorrecta!");
                    password = ""; // Limpia la contraseña
                    handleCancel();
                }
                break;

            case "admin":
                // Caso Administrador
                if (password.equals("12345678")) { // Cambia "adminpass" por la contraseña que consideres
                    abrirFormularioAdministrador(); // Llamada para abrir el formulario de administrador
                    handleCancel();
                } else {
                    showAlert("Error", "Contraseña incorrecta!");
                    password = ""; // Limpia la contraseña
                    handleCancel();
                }
                break;

            case "gerente":
                // Caso Gerente
                if (password.equals("12345678")) { // Cambia "gerentepass" por la contraseña que consideres
                    abrirFormularioGerente(); // Llamada para abrir el formulario de gerente
                    handleCancel();
                } else {
                    showAlert("Error", "Contraseña incorrecta!");
                    password = ""; // Limpia la contraseña
                    handleCancel();
                }
                break;

            default:
                showAlert("Error", "Tipo de usuario desconocido.");
                handleCancel();
                break;
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

    // Método para abrir el formulario F_administrador.fxml
    private void abrirFormularioAdministrador() {
        try {
            // Cargar el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/administrador/F_administrador.fxml"));
            Parent root = loader.load();
    
            // Crear una nueva escena con el contenido del archivo FXML
            Scene scene = new Scene(root);
    
            // Crear y mostrar la nueva ventana
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Administrador");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo cargar el formulario de administrador.");
        }
    }
    // Método para abrir el formulario F_administrador.fxml
    private void abrirFormularioGerente() {
        try {
            // Cargar el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/gerente/F_gerente.fxml"));
            Parent root = loader.load();
    
            // Crear una nueva escena con el contenido del archivo FXML
            Scene scene = new Scene(root);
    
            // Crear y mostrar la nueva ventana
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Gerente");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo cargar el formulario de administrador.");
        }
    }
    // Método para abrir el formulario F_administrador.fxml
    private void abrirFormularioVendedor() {
        try {
            // Cargar el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/vendedor/F_vendedor.fxml"));
            Parent root = loader.load();
    
            // Crear una nueva escena con el contenido del archivo FXML
            Scene scene = new Scene(root);
    
            // Crear y mostrar la nueva ventana
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Vendedor");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo cargar el formulario de administrador.");
        }
    }
    
    
    

}

   

