package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Cargamos el archivo FXML
        Parent root = FXMLLoader.load(getClass().getResource("/resources/LoginView.fxml"));

        DatabaseConnection db = new DatabaseConnection();
        db.getConnection();

        // Creamos la escena
        Scene scene = new Scene(root);

        scene.getStylesheets().add(getClass().getResource("/resources/styles.css").toExternalForm());

        // primaryStage.setFullScreen(true);
        primaryStage.setTitle("Gestion de ventas");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}