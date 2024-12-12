package app.Controllers;
import app.BDD.CategoriaService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import app.Models.Categoria;
import java.io.IOException;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;

public class CategoriaController {
    @FXML
    private TableView<Categoria> tableView;
    @FXML
    private TableColumn<Categoria, Integer> idCol;
    @FXML
    private TableColumn<Categoria, String> nombreCol;
    @FXML
    private TableColumn<Categoria, String> descCol;
    @FXML
    private TableColumn<Categoria, String> estadoCol;
    private ObservableList<Categoria> categorias = FXCollections.observableArrayList();
    @FXML
    private StackPane mainContent;
    @FXML
    private TextField buscarCategoria;
    private CategoriaService categoriaService = new CategoriaService();
    @FXML
    private VBox vbox;
    CustomAlert customAlert = new CustomAlert();

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        estadoCol.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Creo una nueva columna para manejar la activacion y desactivacion de las categorias
        TableColumn<Categoria, String> actionCol = new TableColumn<>("Acciones");
        actionCol.setCellFactory(new Callback<TableColumn<Categoria, String>, TableCell<Categoria, String>>() {
            @Override
            public TableCell<Categoria, String> call(final TableColumn<Categoria, String> param) {
                final TableCell<Categoria, String> cell = new TableCell<Categoria, String>() {
                    private final Button btn = new Button("Activar/Desactivar"); // Definicion del boton

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Categoria categoria = getTableView().getItems().get(getIndex());
                            cambiarEstadoCategoria(categoria);
                        });
                    }
                    // Actualiza el contenido de la columna
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) { // Verifica si la tabla esta vacia
                            setGraphic(null);
                        } else { // Muestra un boton de activacion o desactivacion
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        });
        tableView.getColumns().add(actionCol); // Agrega la nueva columna
        tableView.prefWidthProperty().bind(vbox.widthProperty());

        // Cargar datos desde la base de datos
        cargarDatosDesdeBD();
    }

    private void cargarDatosDesdeBD() {
        categorias = categoriaService.loadCategorias();
      // Ajusta el ancho de la tabla al ancho del VBox
        tableView.setItems(categorias);
    }

    // Método que cambia el estado de la categoria
    private void cambiarEstadoCategoria(Categoria categoria) {
        String nuevaCategoria = categoria.getEstado().equals("activa") ? "inactiva" : "activa";
        categoria.setEstado(nuevaCategoria);
        // Actualiza en la base de datos
        categoriaService.updateCategoria(categoria);
        // Vuelve a cargar los datos para reflejar el cambio en la tabla
        cargarDatosDesdeBD();
    }

    @FXML
    public void mostrarAlerta(String titulo, String mensaje) {
        // Alert alert = new Alert(AlertType.INFORMATION);
        // alert.setTitle(titulo);
        // alert.setHeaderText(null);
        // alert.setContentText(mensaje);
        // alert.showAndWait();
        customAlert.mostrarAlertaPersonalizada(titulo, mensaje);
    }

    // Metodo para cargar una vista en el mainContent
    @FXML
    public void setView(String fxmlPath) {
        try {
            Node view = FXMLLoader.load(getClass().getResource(fxmlPath));
            mainContent.getChildren().clear(); // Limpia el contenido actual
            mainContent.getChildren().add(view); // Agrega la nueva vista
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo cargar la vista");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    // Metodo que carga la vista del formulario para añadir una categoria
    @FXML
    public void añadirCategoria(){
        setView("/resources/FormCategoria.fxml");
    }

    // Metodo que permite buscar una categoria
    @FXML
    public void buscarCategoria() {
        String name = buscarCategoria.getText();
        if(name.isEmpty()) {
            customAlert.mostrarAlertaPersonalizada("Error", "Ingrese el nombre de una categoria.");
            return;
        }
        // Llamamos al metodo que busca la categoria en la base de datos
        Categoria categoria = categoriaService.searchCategory(name);
        if(categoria != null) {
            tableView.getItems().clear(); // Limpiamos la tabla
            tableView.getItems().add(categoria); // Mostramos la categoria
        } else {
            customAlert.mostrarAlertaPersonalizada("Error", "Categoria no encontrada.");
        }
    }

    // Metodo que recarga a la vista inicial de la gestion de categorias
    @FXML
    public void recarga() {
        setView("/resources/CategoriasView.fxml");
    }
}
