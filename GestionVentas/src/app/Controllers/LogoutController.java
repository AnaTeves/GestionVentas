package app.Controllers;
import app.Controllers.SessionManager;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class LogoutController {
    

    public void handleLogout(Stage currentStage){
        SessionManager.clearSession();
        currentStage.close();
        redirectToLogin();
    }

    private void redirectToLogin(){
        // Cargar la vista de Login
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/LoginView.fxml"));
            Parent root = loader.load();

            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(root));
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
