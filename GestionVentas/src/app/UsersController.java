package app;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.control.MenuItem;

public class UsersController {
    @FXML
    private TextField nomYapeField;
    @FXML
    private TextField dniField;
    @FXML
    private TextField emailField;
    @FXML
    private MenuButton perfilMenuButton;

    @FXML
    public void initialize() {
        perfilSelection(); // Llama a perfilSelection en la inicialización
    }

    private void perfilSelection() {
        for (MenuItem item : perfilMenuButton.getItems()) {
            item.setOnAction(event -> {
                String perfilSeleccionado = item.getText();
                perfilMenuButton.setText(perfilSeleccionado);
            });
        }
    }

    private int obtenerIdPerfil(String perfilDescripcion) {
        // En un escenario real, deberías consultar la base de datos para el ID del perfil.
        // Aquí hay una versión simplificada utilizando valores codificados.
        switch (perfilDescripcion) {
            case "Administrador":
                return 1; // Suponiendo que el ID para "Administrador" es 1
            case "Vendedor":
                return 2; // Suponiendo que el ID para "Vendedor" es 2
            case "Repositor":
                return 3; // Suponiendo que el ID para "Repositor" es 3
            default:
                return -1; // Perfil inválido
        }
    }

    @FXML
    public void agregarUsuario() {
        String nomYape = nomYapeField.getText();
        String dni = dniField.getText();
        String email = emailField.getText();
        String perfilDescripcion = perfilMenuButton.getText();
        int idPerfil = obtenerIdPerfil(perfilDescripcion);

        // Verifica que todos los campos estén completos
        if (nomYape.isEmpty() || dni.isEmpty() || email.isEmpty() || idPerfil == -1) {
            mostrarAlerta("Error", "Todos los campos deben estar completos.");
            return;
        }

        // Llama al método para insertar el usuario en la base de datos
        try {
            insertarUsuarioEnBaseDeDatos(nomYape, dni, email, idPerfil);
            mostrarAlerta("Éxito", "Usuario agregado correctamente.");
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudo agregar el usuario: " + e.getMessage());
        }
    }

    private void insertarUsuarioEnBaseDeDatos(String nomYape, String dni, String email, int idPerfil) throws SQLException {
        String sql = "INSERT INTO Usuario (nomYape, dni, email, id_perfil) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nomYape);
            preparedStatement.setString(2, dni);
            preparedStatement.setString(3, email);
            preparedStatement.setInt(4, idPerfil);
            preparedStatement.executeUpdate();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
