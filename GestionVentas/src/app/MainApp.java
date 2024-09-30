package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Cargar el archivo FXML
        Parent root = FXMLLoader.load(getClass().getResource("/resources/LoginView.fxml"));

        DatabaseConnection db = new DatabaseConnection();

        // Crear la escena
        Scene scene = new Scene(root);

        scene.getStylesheets().add(getClass().getResource("/resources/styles.css").toExternalForm());

        // Configurar la ventana
        primaryStage.setFullScreen(true);
        primaryStage.setTitle("Gestion de ventas");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}