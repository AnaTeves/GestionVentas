package app.Controllers;
import app.Controllers.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

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
            loginStage.initStyle(StageStyle.UNDECORATED);
            loginStage.setScene(new Scene(root));
            loginStage.show();

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            loginStage.setX((screenBounds.getWidth() - loginStage.getWidth()) / 2);
            loginStage.setY((screenBounds.getHeight() - loginStage.getHeight()) / 2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}