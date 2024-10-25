package app.Controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.Optional;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AdminController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private StackPane mainContent;

    @FXML
    private Button logoutButton;

    private LogoutController logoutController = new LogoutController();

    @FXML
    public void initialize(){
        logoutButton.setOnAction(e -> handleLogout());
    }

    @FXML
    private void setView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node view = loader.load();
            mainBorderPane.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
            // Opcional: Mostrar una alerta si hay un error al cargar la vista
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo cargar la vista");
            alert.setContentText("Verifica que la ruta del archivo FXML sea correcta.");
            alert.showAndWait();
        }
    }
    
    @FXML
    public void handleUsers(){
        setView("/resources/UserView.fxml");
    }

    @FXML
    public void handleInventario(){
        setView("/resources/InventarioView.fxml");
    }

    @FXML
    public void handleCategorias(){
        setView("/resources/CategoriasView.fxml");
    }

    // Método que delega la lógica al LogoutController
    public void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar sesión");
        alert.setHeaderText("¿Estás seguro de que deseas cerrar sesión?");
        alert.setContentText("Selecciona una opción:");

        // Esperar la respuesta del usuario
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Cierra la ventana actual
                Stage currentStage = (Stage) logoutButton.getScene().getWindow();
                logoutController.handleLogout(currentStage);
            }
        });
    }
}