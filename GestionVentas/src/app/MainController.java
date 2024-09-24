package app;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class MainController {

    @FXML
    private MenuItem adminOption;
    @FXML
    private MenuItem gerenteOption;
    @FXML
    private MenuItem repositorOption;

    // Este método se llamará desde el LoginController para configurar el menú según el usuario
    public void setUserRole(String username) {
        // Deshabilitar todas las opciones por defecto
        adminOption.setDisable(true);
        gerenteOption.setDisable(true);
        repositorOption.setDisable(true);

        // Habilitar las opciones correspondientes según el rol del usuario
        if (username.equals("admin")) {
            adminOption.setDisable(false);
        } else if (username.equals("gerente")) {
            gerenteOption.setDisable(false);
        } else if (username.equals("repositor")) {
            repositorOption.setDisable(false);
        }
    }
}
