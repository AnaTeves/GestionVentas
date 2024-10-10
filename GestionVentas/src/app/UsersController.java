package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.sql.Statement;

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
    private TextField buscarUser;

    @FXML
    private TableView<Usuario> tableView;

    @FXML
    private TableColumn<Usuario, String> nombreCol;

    @FXML
    private TableColumn<Usuario, String> dniCol;

    @FXML
    private TableColumn<Usuario, String> emailCol;

    @FXML
    private TableColumn<Usuario, String> tipoUsuarioCol;

    private ObservableList<Usuario> usuarios = FXCollections.observableArrayList();

    @FXML
    public void buscarUsuario() {
        String dni = buscarUser.getText();

        if (dni.isEmpty()) {
            // Si el campo está vacío, tal vez quieras mostrar un mensaje de error
            System.out.println("Ingrese un DNI para buscar");
            return;
        }
        
        // Realiza la consulta a la base de datos para buscar el usuario
        String query = "SELECT * FROM USUARIO WHERE DNI = ?";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            
            // Limpia la tabla antes de mostrar los resultados
            tableView.getItems().clear();
            
            if (rs.next()) {

                String nombreYApellido = rs.getString("nombreyape");
                String documento = rs.getString("DNI");
                String email = rs.getString("email");
                int tipoUsuario = rs.getInt("id_perfil");

                Usuario usuario = new Usuario(nombreYApellido, documento, email, tipoUsuario);        
                // Añadir el usuario a la tabla
                tableView.getItems().add(usuario);
            } else {
                System.out.println("No se encontró el usuario con el DNI proporcionado");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void inicio() {
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nomYape"));
        dniCol.setCellValueFactory(new PropertyValueFactory<>("dni"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        tipoUsuarioCol.setCellValueFactory(new PropertyValueFactory<>("idPerfil"));

        // Cargar datos desde la base de datos
        cargarDatosDesdeBD();
        tableView.setItems(usuarios);
    }

    private void cargarDatosDesdeBD() {
        Connection conn = DatabaseConnection.getConnection(); 
        String query = "SELECT nombreyape, DNI, email, id_perfil FROM USUARIO";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String nombreYApellido = rs.getString("nombreyape");
                String documento = rs.getString("DNI");
                String email = rs.getString("email");
                int tipoUsuario = rs.getInt("id_perfil");

                System.out.println("Nombre: " + nombreYApellido + ", DNI: " + documento + ", Email: " + email + ", Tipo Usuario: " + tipoUsuario);

                Usuario usuario = new Usuario(nombreYApellido, documento, email, tipoUsuario);
                usuarios.add(usuario);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        inicio();
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
        String sql = "SELECT id_perfil FROM PERFIL WHERE descripcion = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, perfilDescripcion);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                return resultSet.getInt("id_perfil");
            } else {
                mostrarAlerta("Error", "Perfil no encontrado.");
                return -1; // Perfil inválido
            }
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudo obtener el perfil: " + e.getMessage());
            return -1; // En caso de error en la consulta
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
        String sql = "INSERT INTO Usuario (nombreyape, DNI, email, id_perfil) VALUES (?, ?, ?, ?)";

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
