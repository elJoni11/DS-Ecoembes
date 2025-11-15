package Ecoembes.entity;

public class Empleado {
	
    private String id;
    private String nombre;
    private String email;
    private String password;

    /** Constructor para crear/inicializar un Empleado **/
    public Empleado(String id, String nombre, String email, String password) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    // Constructor sin ID (Útil antes de ser guardado en base de datos)
    public Empleado(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }
    
    // Getters 
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

 
    public String getPassword() {
        return password;
    }

    // Setters 
    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Método toString
    @Override
    public String toString() {
        return "Empleado{" +
               "id=" + id +
               ", nombre='" + nombre + '\'' +
               ", email='" + email + '\'' +
               ", password='[OCULTO]'" + // Se oculta la contraseña por razones de seguridad.
               '}';
    }
}
