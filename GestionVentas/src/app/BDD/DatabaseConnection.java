package app.BDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=DB_GESTIONVENTAS;encrypt=false;";
    private static final String USER = "analuzteves"; 
    private static final String PASSWORD = "1234analuz";

    public static Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static void close(Connection conn, PreparedStatement stmt1, PreparedStatement stmt2) {
        try {
            if (stmt1 != null) stmt1.close();
            if (stmt2 != null) stmt2.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}