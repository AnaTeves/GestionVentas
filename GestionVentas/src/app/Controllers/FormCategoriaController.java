package app.Controllers;
import java.io.IOException;

import app.BDD.CategoriaService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;

public class FormCategoriaController {
    @FXML
    private TextField nombreField;
    @FXML
    private TextField descripcionField;
    @FXML
    private StackPane mainContentForm;

    private CategoriaService categorias = new CategoriaService();
    private CategoriaController categoriaController = new CategoriaController();

    @FXML
    public void agregarCategoria() {
        String nombre = nombreField.getText();
        String descripcion = descripcionField.getText();

        // Verifica que todos los campos estén completos
        if (nombre.isEmpty() || descripcion.isEmpty()) {
            categoriaController.mostrarAlerta("Error", "Todos los campos deben estar completos.");
            return;
        }

        // Llama al método para insertar la categoria en la base de datos
        categorias.addCategoria(nombre, descripcion);
        categoriaController.mostrarAlerta("Éxito", "Categoria agregada correctamente.");
    }

    @FXML
    public void cancelar(){
            try {
            Node categoriasView = FXMLLoader.load(getClass().getResource("/resources/CategoriasView.fxml"));
            mainContentForm.getChildren().clear(); // Limpiar contenido actual
            mainContentForm.getChildren().add(categoriasView); // Cargar vista de categorías
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo cargar la vista de categorías");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}