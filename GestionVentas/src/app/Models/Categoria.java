package app.Models;

public class Categoria {
    private int id;
    private String nombre;
    private String descripcion;
    private String estado;
    // private int idCategoria;

    public Categoria(int id,String nombre, String descripcion, String estado){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public String getEstado(){
        return estado;
    }

    public void setEstado(String estado){
        this.estado = estado;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
}
