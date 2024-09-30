package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainController {

    @FXML
    private MenuItem adminOption;
    @FXML
    private MenuItem reporteOption;
    @FXML
    private MenuItem clienteOption;
    @FXML
    private MenuItem ventasOption;
    @FXML
    private MenuItem inventarioOption;
    @FXML
    private Pane contentPane; // Pane donde voy a cargar las vistas
    @FXML
    private MenuItem menuItemAlta;
    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu ventasMenu;
    @FXML
    private Menu reportesMenu;
    @FXML
    private Menu clienteMenu;
    @FXML
    private Menu usuariosMenu;
    @FXML
    private Menu inventarioMenu;

    private Map<String, List<Menu>> accessMap = new HashMap<>();

    public void initialize() {
        // Oculta todos los menús al inicio
        for (Menu menu : menuBar.getMenus()) {
            menu.setVisible(false);
        }

        // Define qué menús puede ver cada rol
        accessMap.put("admin", Arrays.asList(ventasMenu, reportesMenu, clienteMenu, usuariosMenu, inventarioMenu));
        accessMap.put("gerente", Arrays.asList(ventasMenu, reportesMenu));
        accessMap.put("empleado", Arrays.asList(inventarioMenu, clienteMenu, ventasMenu));
    }

    // Este método se llamará desde el LoginController para configurar el menú según el usuario
    public void setUserRole(String username) {
        List<Menu> allowedMenus = accessMap.get(username);
        if (allowedMenus != null) {
            // Itera sobre todos los menús y habilita solo los que el rol puede ver
            for (Menu menu : menuBar.getMenus()) {
                if (allowedMenus.contains(menu)) {
                    menu.setVisible(true);
                } else {
                    menu.setVisible(false);
                }
            }
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

    @FXML
    public void gestionInventario() {
        setView("/resources/InventarioView.fxml"); // Carga la vista de ver inventario
    }

    @FXML
    public void buscarCliente() {
        setView("/resources/ClienteView.fxml");
    }

    @FXML
    public void agregarClientes() {
        setView("/resources/AddClient.fxml");
    }
}