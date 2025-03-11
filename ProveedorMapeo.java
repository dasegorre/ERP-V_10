import javax.persistence.*;

@Entity
@Table(name = "PROVEEDOR")
public class Proveedor {

    @Id
    @Column(name = "ID_PROVEEDOR", nullable = false, length = 7)
    private String idProveedor;

    @Column(name = "NOMBRE", nullable = false, length = 40)
    private String nombre;

    @Column(name = "CIF", nullable = false, length = 9)
    private String cif;

    @Column(name = "TELEFONO", nullable = false, length = 40)
    private String telefono;

    @Column(name = "DIRECCION", length = 100)
    private String direccion;

    @Column(name = "EMAIL", length = 40)
    private String email;

    // Constructor vacío
    public Proveedor() {}

    // Constructor con todos los campos
    public Proveedor(String idProveedor, String nombre, String cif, String telefono, String direccion, String email) {
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.cif = cif;
        this.telefono = telefono;
        this.direccion = direccion;
        this.email = email;
    }

    // Getters y Setters
    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Proveedor{" +
                "idProveedor='" + idProveedor + '\'' +
                ", nombre='" + nombre + '\'' +
                ", cif='" + cif + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
