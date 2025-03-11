import jakarta.persistence.*;

@Entity
@Table(name = "CLIENTES")
public class Cliente {

    @Id
    @Column(name = "ID_CLIENTE", length = 7, nullable = false)
    private String idCliente;

    @Column(name = "NOMBRE", length = 40, nullable = false)
    private String nombre;

    @Column(name = "APELLIDOS", length = 40, nullable = false)
    private String apellidos;

    @Column(name = "NIF", length = 9, nullable = false, unique = true)
    private String nif;

    @Column(name = "TELEFONO", length = 12, nullable = false)
    private String telefono;

    @Column(name = "EMAIL", length = 40)
    private String email;

    @Column(name = "DIRECCION", length = 100)
    private String direccion;

    // 🔹 Constructor vacío (obligatorio para JPA)
    public Cliente() {}

    // 🔹 Constructor con parámetros
    public Cliente(String idCliente, String nombre, String apellidos, String nif, 
                   String telefono, String email, String direccion) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nif = nif;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
    }

    // 🔹 Getters y Setters
    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
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

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente='" + idCliente + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", nif='" + nif + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
