package app;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAO {
    private DatabaseConnection dbConnection;

    public UserDAO() {
        dbConnection = new DatabaseConnection();
    }

    public void agregarUsuario(String dni, String nombre, int idPerfil) {
        String query = "INSERT INTO usuarios (dni, nombre, id_perfil) VALUES (?, ?, ?)";
        try (Connection connection = dbConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query)) {
            
            stmt.setString(1, dni);
            stmt.setString(2, nombre);
            stmt.setInt(3, idPerfil);
            
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Un nuevo usuario fue insertado con éxito.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Puedes agregar más métodos aquí para actualizar, eliminar, etc.
}
