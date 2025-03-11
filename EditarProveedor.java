import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EditarProveedor extends JFrame {

    private JTextField txtId, txtNombre, txtTelefono, txtDireccion, txtEmail, txtCIF;
    private JButton botonGuardar, botonVolverAtras;
    private JLabel labelId, labelNombre, labelTelefono, labelDireccion, labelEmail, labelCif;
    private String idProveedor;
    private Proveedor ventanaProveedor;

    public EditarProveedor(Proveedor ventanaProveedor, String idProveedor) {
        this.ventanaProveedor = ventanaProveedor;
        this.idProveedor = idProveedor;

        setTitle("Ver / Editar Proveedor");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setResizable(true);

        // Agregar ComponentListener para detectar cambios de tamaño
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ajustarComponentes();
            }
        });

        // 📌 Primera fila (ID Proveedor, Nombre, Telefono)
        labelId = new JLabel("ID Proveedor:");
        add(labelId);
        txtId = new JTextField();
        txtId.setEditable(false);  // Solo lectura
        add(txtId);

        labelNombre = new JLabel("Nombre:");
        add(labelNombre);
        txtNombre = new JTextField();
        add(txtNombre);

        labelTelefono = new JLabel("Telefono:");
        add(labelTelefono);
        txtTelefono = new JTextField();
        add(txtTelefono);

        // 📌 Segunda fila (Direccion, Email, CIF)
        labelDireccion = new JLabel("Direccion:");
        add(labelDireccion);
        txtDireccion = new JTextField();
        add(txtDireccion);

        labelEmail = new JLabel("Email:");
        add(labelEmail);
        txtEmail = new JTextField();
        add(txtEmail);

        labelCif = new JLabel("CIF:");
        add(labelCif);
        txtCIF = new JTextField();
        add(txtCIF);

        // 📌 Botones
        botonGuardar = new JButton("Guardar");
        botonGuardar.addActionListener(e -> guardarCambios());
        add(botonGuardar);

        botonVolverAtras = new JButton("Volver");
        botonVolverAtras.addActionListener(e -> dispose());
        add(botonVolverAtras);

        obtenerProveedor();  // Cargar los datos del proveedor

        // Ajustar posiciones iniciales
        ajustarComponentes();

        setVisible(true);
    }

    // 📌 Método para posicionar y redimensionar los componentes
    private void ajustarComponentes() {
        int width = getWidth();
        int height = getHeight();

        int margenX = 20;
        int margenY = 20;
        int campoAncho = (width - 4 * margenX) / 3; // Para 3 columnas
        int campoAlto = 30;
        int espacioVertical = 10;
        int filaY = margenY;

        // 📌 Primera fila
        labelId.setBounds(margenX, filaY, 100, campoAlto);
        txtId.setBounds(margenX + 80, filaY, campoAncho - 90, campoAlto);

        labelNombre.setBounds(margenX + campoAncho, filaY, 100, campoAlto);
        txtNombre.setBounds(margenX + campoAncho + 80, filaY, campoAncho - 90, campoAlto);

        labelTelefono.setBounds(margenX + 2 * campoAncho, filaY, 100, campoAlto);
        txtTelefono.setBounds(margenX + 2 * campoAncho + 80, filaY, campoAncho - 90, campoAlto);

        filaY += campoAlto + espacioVertical;

        // 📌 Segunda fila
        labelDireccion.setBounds(margenX, filaY, 100, campoAlto);
        txtDireccion.setBounds(margenX + 80, filaY, campoAncho - 90, campoAlto);

        labelEmail.setBounds(margenX + campoAncho, filaY, 100, campoAlto);
        txtEmail.setBounds(margenX + campoAncho + 80, filaY, campoAncho - 90, campoAlto);

        labelCif.setBounds(margenX + 2 * campoAncho, filaY, 100, campoAlto);
        txtCIF.setBounds(margenX + 2 * campoAncho + 80, filaY, campoAncho - 90, campoAlto);

        filaY += campoAlto + espacioVertical;

        // 📌 Botones
        botonGuardar.setBounds(width - margenX - 120, filaY, 100, 40);
        botonVolverAtras.setBounds(margenX, filaY, 100, 40);
    }

    // 📌 Método para obtener los datos del proveedor y mostrarlos en los campos
    private void obtenerProveedor() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiendainformatica", "root", "");
             PreparedStatement stmt = con.prepareStatement("SELECT ID_PROVEEDOR, NOMBRE, TELEFONO, DIRECCION, EMAIL, CIF FROM proveedor WHERE ID_PROVEEDOR = ?")) {

            stmt.setString(1, idProveedor);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                txtId.setText(idProveedor);
                txtNombre.setText(rs.getString("NOMBRE"));
                txtTelefono.setText(rs.getString("TELEFONO"));
                txtDireccion.setText(rs.getString("DIRECCION"));
                txtEmail.setText(rs.getString("EMAIL"));
                txtCIF.setText(rs.getString("CIF"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para guardar cambios en el proveedor
    private void guardarCambios() {
        if (txtNombre.getText().isEmpty() || txtTelefono.getText().isEmpty() ||
            txtDireccion.getText().isEmpty() || txtEmail.getText().isEmpty() ||
            txtCIF.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Realizar la actualización de los datos
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiendainformatica", "root", "");
             PreparedStatement stmt = con.prepareStatement("UPDATE proveedor SET NOMBRE = ?, TELEFONO = ?, DIRECCION = ?, EMAIL = ?, CIF = ? WHERE ID_PROVEEDOR = ?")) {

            stmt.setString(1, txtNombre.getText());
            stmt.setString(2, txtTelefono.getText());
            stmt.setString(3, txtDireccion.getText());
            stmt.setString(4, txtEmail.getText());
            stmt.setString(5, txtCIF.getText());
            stmt.setString(6, idProveedor);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Proveedor actualizado correctamente.");
                ventanaProveedor.actualizarListaProveedores(); // Actualiza la lista en la ventana principal
                dispose(); // Cierra la ventana de edición
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar los datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
