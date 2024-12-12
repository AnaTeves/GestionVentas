package app.BDD;
import app.Models.Perfil;
import app.Models.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

// Clase que maneja la interaccion con la base de datos
public class UserService {

    // Método para validar usuario y devolver la descripción del perfil
    public String validarUsuario(String dni, String contraseña) {
        String perfilDescripcion = null;

        String query = """
            SELECT PERFIL.descripcion 
            FROM USUARIO 
            JOIN PERFIL ON USUARIO.id_perfil = PERFIL.id_perfil 
            WHERE USUARIO.email = ? AND USUARIO.contraseña = ?
        """;

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, dni);
            String hashedPassword = encriptarContraseña(contraseña);
            statement.setString(2, hashedPassword);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                perfilDescripcion = resultSet.getString("descripcion"); 
                return perfilDescripcion;
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return perfilDescripcion;
    }

    // Metodo que carga todos los usuarios desde la base de datos
    public ObservableList<Usuario> loadUsers(){
        ObservableList<Usuario> usuarios = FXCollections.observableArrayList(); // Creamos una lista observable de usuarios
        String query = "SELECT nombreyape, DNI, email, id_perfil, estado FROM USUARIO"; // Consulta SQL que selecciona las columnas de la tabla usuarios
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
                String estado = rs.getString("estado");

                Usuario user = new Usuario(nomYape, dni, email, idPerfil, estado);
                usuarios.add(user);
            }    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios; // Retorna la lista
    }

    public List<String> loadUsersName(){
        List<String> usuarios = new ArrayList<>(); // Creamos una lista de usuarios
        String query = "SELECT nombreyape FROM USUARIO"; // Consulta SQL que selecciona las columnas de la tabla usuarios
        // Abrimos la conexion a la base de datos y ejecutamos la consulta
        try(Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);){
            // Recorremos el resultado de la consulta y lo agregamos a la lista
            while(rs.next()){
                String nomYape = rs.getString("nombreyape");
                usuarios.add(nomYape);
            }    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    // Metodo que busca a un ususario por su DNI
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
                String estado = rs.getString("estado"); 

                usuario = new Usuario(nomYape, documento, email, idPerfil, estado);
            }

        } catch (SQLException e) {
            e.printStackTrace();            
        }
        return usuario;
    }

    // Metodo para agregar un usuario a la base de datos
    public void addUser(String nomYape, String dni, String email, int idPerfil, String contraseña){
        String sql = "INSERT INTO Usuario(nombreyape, DNI, email, id_perfil, estado, contraseña) VALUES (?, ?, ?, ?, ?, ?)"; // Consulta SQL para insertar un nuevo usuario

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);){
                // Asignamos los valores a los parametros de la consulta
                stmt.setString(1, nomYape);
                stmt.setString(2, dni);
                stmt.setString(3, email);
                stmt.setInt(4, idPerfil);
                stmt.setString(5, "Activo");

                String hashedPassword = encriptarContraseña(contraseña);
                stmt.setString(6, hashedPassword);
                stmt.executeUpdate(); // Ejecuta la consulta para insertar al nuevo usuario en la base de datos
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String encriptarContraseña(String contraseña) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(contraseña.getBytes(StandardCharsets.UTF_8));
            
            // Convertir el hash en una representación hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
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

    public void updateUsuario(Usuario usuario) {
        String query = "UPDATE USUARIO SET estado = ? WHERE DNI = ?";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, usuario.getEstado());
            preparedStatement.setString(2, usuario.getDni());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Perfil obtenerPerfilUsuario(String dniUsuario) throws SQLException {
        String query = "SELECT PERFIL.id_perfil, PERFIL.descripcion FROM USUARIO " +
                        "JOIN PERFIL ON USUARIO.id_perfil = PERFIL.id_perfil " + 
                        "WHERE USUARIO.DNI = ?";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, dniUsuario);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id_perfil");
                String descripcion = resultSet.getString("descripcion");
                return new Perfil(id, descripcion);
            }
        }
        return null; // Si no se encuentra el perfil, retorna null o lanza una excepción
    }
}