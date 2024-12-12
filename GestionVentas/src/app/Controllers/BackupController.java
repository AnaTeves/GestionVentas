package app.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class BackupController {

    @FXML
    private TextField backupNameField;

    @FXML
    private TextField backupLocationField;

    // Método para seleccionar la ubicación del backup
    @FXML
    private void selectLocation() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Backup Files", "*.bak"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            backupLocationField.setText(file.getAbsolutePath());
        }
    }

    // Método para realizar el backup
    @FXML
    private void performBackup() {
        String backupName = backupNameField.getText();
        String backupLocation = backupLocationField.getText();

        if (backupName.isEmpty() || backupLocation.isEmpty()) {
            showAlert("Error", "Por favor, ingrese el nombre y la ubicación del backup.", AlertType.ERROR);
            return;
        }

        // Definir la consulta SQL para el backup
        String sql = "BACKUP DATABASE [DB_GESTIONVENTAS] "
                   + "TO DISK = '" + backupLocation + "' "
                   + "WITH FORMAT, MEDIANAME = '" + backupName + "', NAME = 'Full Backup of DB_GESTIONVENTAS'";

        // Configuración de la conexión a la base de datos
        String url = "jdbc:sqlserver://localhost:1433;databaseName=DB_GESTIONVENTAS;encrypt=false;";
        String username = "analuzteves";
        String password = "1234analuz";

        // Ejecutar el backup
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            showAlert("Éxito", "El backup se realizó correctamente.", AlertType.INFORMATION);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Hubo un error al realizar el backup.", AlertType.ERROR);
        }
    }

    // Método para mostrar alertas
    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
