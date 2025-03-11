import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AgregarProveedor extends JFrame {

    private JButton botonGuardar, botonVolverAtras;
    private JTextField txtId, txtNombre, txtTelefono, txtDireccion, txtEmail, txtCIF;
    private JLabel labelId, labelNombre, labelTelefono, labelDireccion, labelEmail, labelCif;
    private Proveedor ventanaProveedor;

    public AgregarProveedor(Proveedor ventanaProveedor) {
        this.ventanaProveedor = ventanaProveedor;
        
        setTitle("Agregar Proveedor");
        setSize(700, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setResizable(true);

        // Ajustar tamaño dinámicamente
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ajustarComponentes();
            }
        });

        // Primera fila (ID_PROVEEDOR, NOMBRE, TELEFONO)
        labelId = new JLabel("ID Proveedor:");
        add(labelId);
        txtId = new JTextField();
        txtId.setEditable(false);
        add(txtId);

        labelNombre = new JLabel("Nombre:");
        add(labelNombre);
        txtNombre = new JTextField();
        add(txtNombre);

        labelTelefono = new JLabel("Teléfono:");
        add(labelTelefono);
        txtTelefono = new JTextField();
        add(txtTelefono);

        // Segunda fila (DIRECCION, EMAIL, CIF)
        labelDireccion = new JLabel("Dirección:");
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

        // Botones
        botonGuardar = new JButton("Guardar");
        botonGuardar.addActionListener(e -> guardarProveedor());
        add(botonGuardar);

        botonVolverAtras = new JButton("Volver");
        botonVolverAtras.addActionListener(e -> dispose());
        add(botonVolverAtras);

        // Ajustar posiciones iniciales
        ajustarComponentes();

        setVisible(true);
    }

    // Posicionar y redimensionar componentes
    private void ajustarComponentes() {
        int width = getWidth();
        int margenX = 20;
        int margenY = 20;
        int campoAncho = (width - 4 * margenX) / 3; // Para 3 columnas
        int campoAlto = 30;
        int espacioVertical = 10;
        int filaY = margenY;

        // Primera fila
        labelId.setBounds(margenX, filaY, 100, campoAlto);
        txtId.setBounds(margenX + 80, filaY, campoAncho - 90, campoAlto);

        labelNombre.setBounds(margenX + campoAncho, filaY, 100, campoAlto);
        txtNombre.setBounds(margenX + campoAncho + 80, filaY, campoAncho - 90, campoAlto);

        labelTelefono.setBounds(margenX + 2 * campoAncho, filaY, 100, campoAlto);
        txtTelefono.setBounds(margenX + 2 * campoAncho + 80, filaY, campoAncho - 90, campoAlto);

        filaY += campoAlto + espacioVertical;

        // Segunda fila
        labelDireccion.setBounds(margenX, filaY, 100, campoAlto);
        txtDireccion.setBounds(margenX + 80, filaY, campoAncho - 90, campoAlto);

        labelEmail.setBounds(margenX + campoAncho, filaY, 100, campoAlto);
        txtEmail.setBounds(margenX + campoAncho + 80, filaY, campoAncho - 90, campoAlto);

        labelCif.setBounds(margenX + 2 * campoAncho, filaY, 100, campoAlto);
        txtCIF.setBounds(margenX + 2 * campoAncho + 80, filaY, campoAncho - 90, campoAlto);

        filaY += campoAlto + 2 * espacioVertical;

        // Botones
        botonGuardar.setBounds(width - margenX - 120, filaY, 100, 40);
        botonVolverAtras.setBounds(margenX, filaY, 100, 40);
    }

    private void guardarProveedor() {
        if (txtNombre.getText().isEmpty() || txtTelefono.getText().isEmpty() ||
            txtCIF.getText().isEmpty() || txtDireccion.getText().isEmpty() ||
            txtEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiendainformatica", "root", "");
             PreparedStatement ps = con.prepareStatement("INSERT INTO proveedor (NOMBRE, TELEFONO, DIRECCION, EMAIL, CIF) VALUES (?, ?, ?, ?, ?)")) {
            
            ps.setString(1, txtNombre.getText());
            ps.setString(2, txtTelefono.getText());
            ps.setString(3, txtDireccion.getText());
            ps.setString(4, txtEmail.getText());
            ps.setString(5, txtCIF.getText());

            int res = ps.executeUpdate();
            if (res > 0) {
                JOptionPane.showMessageDialog(null, "Proveedor agregado exitosamente.");
                if (ventanaProveedor != null) {
                    ventanaProveedor.actualizarListaProveedores();
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar el proveedor.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



