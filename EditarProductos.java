import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EditarProductos extends JFrame {

    private JButton botonGuardar, botonVolverAtras;
    private JTextField txtCod, txtNombre, txtPrecioCompra, txtIdProveedor, txtCantidad, txtPvp;
    private JLabel labelCod, labelNombre, labelPrecioCompra, labelIdProveedor, labelCantidad, labelPvp;
    private Productos ventanaProductos;
    private String codProducto;

    public EditarProductos(Productos ventanaProductos, String codProducto) {
        this.ventanaProductos = ventanaProductos;
        this.codProducto = codProducto;

        setTitle("Editar Producto");
        setSize(700, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setResizable(true);

        // Ajustar componentes dinámicamente al redimensionar la ventana
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ajustarComponentes();
            }
        });

        // Primera fila (COD_PRODUCTO, NOMBRE, PRECIO_COMPRA)
        labelCod = new JLabel("Cod Producto:");
        add(labelCod);
        txtCod = new JTextField();
        txtCod.setEditable(false);
        add(txtCod);

        labelNombre = new JLabel("Nombre:");
        add(labelNombre);
        txtNombre = new JTextField();
        add(txtNombre);

        labelPrecioCompra = new JLabel("Precio Compra:");
        add(labelPrecioCompra);
        txtPrecioCompra = new JTextField();
        add(txtPrecioCompra);

        // Segunda fila (ID_PROVEEDOR, CANTIDAD, PVP)
        labelIdProveedor = new JLabel("ID Proveedor:");
        add(labelIdProveedor);
        txtIdProveedor = new JTextField();
        add(txtIdProveedor);

        labelCantidad = new JLabel("Cantidad:");
        add(labelCantidad);
        txtCantidad = new JTextField();
        add(txtCantidad);

        labelPvp = new JLabel("PVP:");
        add(labelPvp);
        txtPvp = new JTextField();
        add(txtPvp);

        // Botones
        botonGuardar = new JButton("Guardar");
        botonGuardar.addActionListener(e -> actualizarProducto());
        add(botonGuardar);

        botonVolverAtras = new JButton("Volver");
        botonVolverAtras.addActionListener(e -> dispose());
        add(botonVolverAtras);

        // Ajustar posiciones iniciales
        ajustarComponentes();

        // Cargar datos del producto
        cargarDatosProducto();

        setVisible(true);
    }

    private void ajustarComponentes() {
        int width = getWidth();
        int margenX = 20;
        int margenY = 20;
        int campoAncho = (width - 4 * margenX) / 3; // Para 3 columnas
        int campoAlto = 30;
        int espacioVertical = 10;
        int filaY = margenY;

        // Primera fila
        labelCod.setBounds(margenX, filaY, 100, campoAlto);
        txtCod.setBounds(margenX + 80, filaY, campoAncho - 90, campoAlto);

        labelNombre.setBounds(margenX + campoAncho, filaY, 100, campoAlto);
        txtNombre.setBounds(margenX + campoAncho + 80, filaY, campoAncho - 90, campoAlto);

        labelPrecioCompra.setBounds(margenX + 2 * campoAncho, filaY, 120, campoAlto);
        txtPrecioCompra.setBounds(margenX + 2 * campoAncho + 100, filaY, campoAncho - 110, campoAlto);

        filaY += campoAlto + espacioVertical;

        // Segunda fila
        labelIdProveedor.setBounds(margenX, filaY, 100, campoAlto);
        txtIdProveedor.setBounds(margenX + 80, filaY, campoAncho - 90, campoAlto);

        labelCantidad.setBounds(margenX + campoAncho, filaY, 100, campoAlto);
        txtCantidad.setBounds(margenX + campoAncho + 80, filaY, campoAncho - 90, campoAlto);

        labelPvp.setBounds(margenX + 2 * campoAncho, filaY, 100, campoAlto);
        txtPvp.setBounds(margenX + 2 * campoAncho + 80, filaY, campoAncho - 90, campoAlto);

        filaY += campoAlto + 2 * espacioVertical;

        // Botones
        botonGuardar.setBounds(width - margenX - 120, filaY, 100, 40);
        botonVolverAtras.setBounds(margenX, filaY, 100, 40);
    }

    private void cargarDatosProducto() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiendainformatica", "root", "");
             PreparedStatement ps = con.prepareStatement("SELECT * FROM productos WHERE COD_PRODUCTO = ?")) {

            ps.setString(1, codProducto);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                txtCod.setText(rs.getString("COD_PRODUCTO"));
                txtNombre.setText(rs.getString("NOMBRE"));
                txtPrecioCompra.setText(rs.getString("PRECIO_COMPRA"));
                txtIdProveedor.setText(rs.getString("ID_PROVEEDOR"));
                txtCantidad.setText(rs.getString("CANTIDAD"));
                txtPvp.setText(rs.getString("PVP"));
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos del producto.");
                dispose();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void actualizarProducto() {
        if (txtNombre.getText().isEmpty() || txtPrecioCompra.getText().isEmpty() ||
            txtIdProveedor.getText().isEmpty() || txtCantidad.getText().isEmpty() ||
            txtPvp.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiendainformatica", "root", "");
             PreparedStatement ps = con.prepareStatement("UPDATE productos SET NOMBRE=?, PRECIO_COMPRA=?, ID_PROVEEDOR=?, CANTIDAD=?, PVP=? WHERE COD_PRODUCTO=?")) {
            
            ps.setString(1, txtNombre.getText());
            ps.setString(2, txtPrecioCompra.getText());
            ps.setString(3, txtIdProveedor.getText());
            ps.setString(4, txtCantidad.getText());
            ps.setString(5, txtPvp.getText());
            ps.setString(6, codProducto);

            int res = ps.executeUpdate();
            if (res > 0) {
                JOptionPane.showMessageDialog(null, "Producto actualizado exitosamente.");
                if (ventanaProductos != null) {
                    ventanaProductos.actualizarListaProductos();
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar el producto.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

