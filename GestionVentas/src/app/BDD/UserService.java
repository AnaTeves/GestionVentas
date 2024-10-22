package app.BDD;
import app.Models.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
// Clase que maneja la interaccion con la base de datos
public class UserService {
    
    // Metodo que carga todos los usuarios desde la base de datos
    public ObservableList<Usuario> loadUsers(){
        ObservableList<Usuario> usuarios = FXCollections.observableArrayList(); // Creamos una lista observable de usuarios
        String query = "SELECT nombreyape, DNI, email, id_perfil FROM USUARIO"; // Consulta SQL que selecciona las columnas de la tabla usuarios
        // Abrimos la conexion a la base de datos y ejecutamos la consulta
        try(Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);){
            // Recorremos el resultado de la consulta y lo añadimos a la lista observable
            while(rs.next()){
                String nomYape = rs.getString("nombreyape");
                String dni = rs.getString("DNI");
                String email = rs.getString("email");
                int idPerfil = rs.getInt("id_perfil");

                Usuario user = new Usuario(nomYape, dni, email, idPerfil);
                usuarios.add(user);
            }    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios; // Retorna la lista
    }

    // Meotdo que busca a un ususario por su DNI
    public Usuario searchUser(String dni){
        String query = "SELECT * FROM USUARIO WHERE DNI = ?"; // Consulta SQL para buscar usuario por su DNI
        Usuario usuario = null;

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);){

            stmt.setString(1, dni); // Asigna el valor dni al parametro de la consulta
            ResultSet rs = stmt.executeQuery();
            // Si encuentra un resultado, extrae los datos y crea un objeto Usuario
            if(rs.next()){
                String nomYape = rs.getString("nombreyape");
                String documento = rs.getString("DNI");
                String email = rs.getString("email");
                int idPerfil = rs.getInt("id_perfil");  

                usuario = new Usuario(nomYape, documento, email, idPerfil);
            }

        } catch (SQLException e) {
            e.printStackTrace();            
        }
        return usuario;
    }

    // Metodo para agregar un usuario a la base de datos
    public void addUser(String nomYape, String dni, String email, int idPerfil){
        String sql = "INSERT INTO Usuario(nombreyape, DNI, email, id_perfil) VALUES (?, ?, ?, ?)"; // Consulta SQL para insertar un nuevo usuario

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);){
                // Asignamos los valores a los parametros de la consulta
                stmt.setString(1, nomYape);
                stmt.setString(2, dni);
                stmt.setString(3, email);
                stmt.setInt(4, idPerfil);
                stmt.executeUpdate(); // Ejecuta la consulta para insertar al nuevo usuario en la base de datos
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int obtenerIdPerfil(String perfilDescripcion) {
        String sql = "SELECT id_perfil FROM PERFIL WHERE descripcion = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, perfilDescripcion);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                return resultSet.getInt("id_perfil");
            } 

        } catch (SQLException e) {
            return -1; // En caso de error en la consulta
        }

        return -1;
    }
}
