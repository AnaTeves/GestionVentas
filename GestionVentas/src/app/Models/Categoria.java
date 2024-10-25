package app.Models;

public class Categoria {
    private int id;
    private String nombre;
    private String descripcion;
    private String estado;

    public Categoria(int id,String nombre, String descripcion, String estado){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public String getNombre(){
        return nombre;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public String getEstado(){
        return estado;
    }

    public int getId(){
        return id;
    }

}
