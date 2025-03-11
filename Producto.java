import jakarta.persistence.*;

@Entity
@Table(name = "PRODUCTOS")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COD_PRODUCTO")
    private Integer id;

    @Column(name = "NOMBRE", nullable = false, length = 40)
    private String nombre;

    @Column(name = "PRECIO_COMPRA", nullable = false, precision = 5, scale = 2)
    private Double precioCompra;

    @Column(name = "PVP", nullable = false, precision = 5, scale = 2)
    private Double pvp;

    @ManyToOne
    @JoinColumn(name = "ID_PROVEEDOR", referencedColumnName = "ID_PROVEEDOR", foreignKey = @ForeignKey(name = "fk_proveedor"), onDelete = ReferentialAction.CASCADE, onUpdate = ReferentialAction.CASCADE)
    private Proveedor proveedor;

    @Column(name = "CANTIDAD", columnDefinition = "INT(3)")
    private Integer cantidad;

    // Constructores
    public Producto() {}

    public Producto(String nombre, Double precioCompra, Double pvp, Proveedor proveedor, Integer cantidad) {
        this.nombre = nombre;
        this.precioCompra = precioCompra;
        this.pvp = pvp;
        this.proveedor = proveedor;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(Double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public Double getPvp() {
        return pvp;
    }

    public void setPvp(Double pvp) {
        this.pvp = pvp;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
