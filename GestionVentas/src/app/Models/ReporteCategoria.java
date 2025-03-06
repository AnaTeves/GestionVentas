package app.Models;

// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.ArrayList;
// import java.util.List;

// import app.BDD.DatabaseConnection;

public class ReporteCategoria {
    String nombre;
    int cantidadVentas;
    
    public ReporteCategoria(String nombre, int cantidadVentas){
        this.nombre = nombre;
        this.cantidadVentas = cantidadVentas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidadVentas() {
        return cantidadVentas;
    }

    public void setCantidadVentas(int cantidadVentas) {
        this.cantidadVentas = cantidadVentas;
    }
}