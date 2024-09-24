package app;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class UserController {
    @FXML
    private TextField dniField;
    @FXML
    private TextField nombreField;
    @FXML
    private ComboBox<String> perfilComboBox;

    private UserDAO userDAO = new UserDAO();

    @FXML
    public void handleAgregarUsuario(ActionEvent event) {
        String dni = dniField.getText();
        String nombre = nombreField.getText();
        String perfil = perfilComboBox.getValue();
        int idPerfil = obtenerIdPerfil(perfil);

        userDAO.agregarUsuario(dni, nombre, idPerfil);
    }

    private int obtenerIdPerfil(String perfil) {
        if (perfil.equals("admin")) {
            return 1; // ID del perfil admin
        } else if (perfil.equals("usuario")) {
            return 2; // ID del perfil usuario
        }
        return 0; // Valor predeterminado si el perfil no es encontrado
    }
}
