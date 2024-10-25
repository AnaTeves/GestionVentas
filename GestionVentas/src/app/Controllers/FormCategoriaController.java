package app.Controllers;
import app.BDD.CategoriaService;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FormCategoriaController {
    @FXML
    private TextField nombreField;
    @FXML
    private TextField descripcionField;

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
}
