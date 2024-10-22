package app.Controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Alert;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import java.io.IOException;
import javafx.scene.layout.StackPane;

public class AdminController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private StackPane mainContent;

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
}