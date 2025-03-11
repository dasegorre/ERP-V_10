public class Empleado {
    private int idEmpleado;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;

    // Constructor
    public Empleado(int idEmpleado, String nombre, String apellidos, String email, String telefono) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
    }

    // Getters y Setters
    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // Método toString para mostrar información del empleado
    @Override
    public String toString() {
        return "Empleado [ID_EMPLEADO=" + idEmpleado + ", NOMBRE=" + nombre + ", APELLIDOS=" + apellidos
                + ", EMAIL=" + email + ", TELEFONO=" + telefono + "]";
    }
}
