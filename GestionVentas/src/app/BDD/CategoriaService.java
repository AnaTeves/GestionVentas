package app.BDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import app.Models.Categoria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CategoriaService {
    
    public ObservableList<Categoria> loadCategorias(){
        ObservableList<Categoria> categorias = FXCollections.observableArrayList(); // Creamos una lista observable de categorias
        String query = "SELECT id_categoria, nombre, descripcion, estado FROM CATEGORIA"; // Consulta SQL que selecciona las columnas de la tabla categoria
        // Abrimos la conexion a la base de datos y ejecutamos la consulta
        try(Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);){
            // Recorremos el resultado de la consulta y lo añadimos a la lista observable
            while(rs.next()){
                int id = rs.getInt("id_categoria");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                String estado = rs.getString("estado");

                Categoria categoria = new Categoria(id, nombre, descripcion, estado);
                categorias.add(categoria);
            }    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias; // Retorna la lista
    }

    public void addCategoria(String nombre, String descripcion){
        String sql = "INSERT INTO Categoria(nombre, descripcion) VALUES (?, ?)"; // Consulta SQL para insertar una nueva cateogoria

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);){
                // Asignamos los valores a los parametros de la consulta
                stmt.setString(1, nombre);
                stmt.setString(2, descripcion);
                stmt.executeUpdate(); // Ejecuta la consulta para insertar la nueva categoria en la base de datos
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
