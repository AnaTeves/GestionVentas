package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import java.io.IOException;

public class MainController {

    @FXML
    private MenuItem adminOption;
    @FXML
    private MenuItem vendedorOption;
    @FXML
    private MenuItem repositorOption;
    @FXML
    private Pane contentPane; // Pane donde voy a cargar las vistas
    @FXML
    private MenuItem menuItemAlta;

    // Este método se llamará desde el LoginController para configurar el menú según el usuario
    public void setUserRole(String username) {
        // Deshabilita todas las opciones por defecto
        adminOption.setDisable(true);
        vendedorOption.setDisable(true);
        repositorOption.setDisable(true);

        // Habilita las opciones correspondientes según el rol del usuario
        if (username.equals("admin")) {
            adminOption.setDisable(false);
        } else if (username.equals("gerente")) {
            vendedorOption.setDisable(false);
        } else if (username.equals("repositor")) {
            repositorOption.setDisable(false);
        }
    }

    // Método para cargar una vista en el contentPane
    private void setView(String fxmlPath) {
        try {
            Node view = FXMLLoader.load(getClass().getResource(fxmlPath));
            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Vincula cada MenuItem a una vista específica
    @FXML
    public void altaUsuarios() {
        setView("/resources/AddUser.fxml"); // Carga la vista de dar de alta usuarios
    }

    @FXML
    public void verUsuarios() {
        setView("/resources/UsersView.fxml"); // Carga la vista de ver usuarios
    }
}
