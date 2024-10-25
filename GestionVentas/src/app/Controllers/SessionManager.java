package app.Controllers;
// Clase que maneja la sesion del usuario
public class SessionManager {
    private static String currentUser;

    // Metodo que guarda al usuario que inicio sesion
    public static void setCurrentUser(String user) {
        currentUser = user;
    }

    // Metodo que obtiene al usuario que inicio sesion
    public static String getCurrentUser(){
        return currentUser;
    }

    // Meotodo que limpia el usuario que inicio sesion
    public static void clearSession(){
        currentUser = null;
    }
}
