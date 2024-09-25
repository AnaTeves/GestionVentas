package app;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class MainController {

    @FXML
    private MenuItem adminOption;
    @FXML
    private MenuItem vendedorOption;
    @FXML
    private MenuItem repositorOption;

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
}
