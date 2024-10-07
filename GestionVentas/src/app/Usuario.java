package app;

public class Usuario {
    private String nomYape;
    private String dni;
    private String email;
    private int idPerfil;

    public Usuario(String nomYape, String dni, String email, int idPerfil){
        this.nomYape = nomYape;
        this.dni = dni;
        this.email = email;
        this.idPerfil = idPerfil;
    }

    public String getNomYape() {
        return nomYape;
    }

    public String getDni() {
        return dni;
    }

    public String getEmail() {
        return email;
    }  

    public int getIdPerfil() {
        return idPerfil;
    }
}
