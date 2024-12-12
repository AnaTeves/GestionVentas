package app.Controllers;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CustomAlert {

    public void mostrarAlertaPersonalizada(String titulo, String mensaje) {
        // Crear un nuevo Stage (ventana)
        Stage alertaStage = new Stage();
        alertaStage.initModality(Modality.APPLICATION_MODAL); // Hace que sea modal
        alertaStage.initStyle(StageStyle.UNDECORATED); // Elimina el borde superior
        alertaStage.setTitle(titulo);

        // Contenido de la alerta
        Label mensajeLabel = new Label(mensaje);
        mensajeLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #333;");

        Button cerrarButton = new Button("Aceptar");
        cerrarButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 10;");
        cerrarButton.setOnAction(e -> alertaStage.close());

        VBox layout = new VBox(10, mensajeLabel, cerrarButton);
        layout.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 20; -fx-border-color: #4CAF50; -fx-border-width: 2;");
        layout.setAlignment(javafx.geometry.Pos.CENTER);

        // Configurar la escena
        Scene escena = new Scene(layout, 300, 150);
        alertaStage.setScene(escena);

        // Mostrar la alerta
        alertaStage.showAndWait();
    }
}