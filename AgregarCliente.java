import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AgregarCliente extends JFrame {

    private JButton botonGuardar, botonVolverAtras;
    private JTextField txtId, txtNombre, txtApellidos, txtNIF, txtTelefono, txtEmail, txtDireccion;
    private JLabel labelId, labelNombre, labelApellidos, labelNif, labelTelefono, labelEmail, labelDireccion;
    private Clientes ventanaClientes;

    public AgregarCliente(Clientes ventanaClientes) {
        this.ventanaClientes = ventanaClientes;
        
        setTitle("Agregar Cliente");
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


        // 📌 Primera fila (ID Cliente, Nombre, Apellidos)
        labelId = new JLabel("ID Cliente:");
        add(labelId);
        txtId = new JTextField();
/*
        txtId.setBackground(Color.GRAY);
*/
        txtId.setEditable(false);
        add(txtId);

        labelNombre = new JLabel("Nombre:");
        add(labelNombre);
        txtNombre = new JTextField();
        add(txtNombre);

        labelApellidos = new JLabel("Apellidos:");
        add(labelApellidos);
        txtApellidos = new JTextField();
        add(txtApellidos);

        // 📌 Segunda fila (NIF, Teléfono, Email)
        labelNif = new JLabel("NIF:");
        add(labelNif);
        txtNIF = new JTextField();
        add(txtNIF);

        labelTelefono = new JLabel("Teléfono:");
        add(labelTelefono);
        txtTelefono = new JTextField();
        add(txtTelefono);

        labelEmail = new JLabel("Email:");
        add(labelEmail);
        txtEmail = new JTextField();
        add(txtEmail);

        // 📌 Tercera fila (Dirección)
        labelDireccion = new JLabel("Dirección:");
        add(labelDireccion);
        txtDireccion = new JTextField();
        add(txtDireccion);

        // 📌 Botones
        botonGuardar = new JButton("Guardar");
        botonGuardar.addActionListener(e -> guardarCliente());
        add(botonGuardar);

        botonVolverAtras = new JButton("Volver");
        botonVolverAtras.addActionListener(e -> dispose());
        add(botonVolverAtras);

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

        labelApellidos.setBounds(margenX + 2 * campoAncho, filaY, 100, campoAlto);
        txtApellidos.setBounds(margenX + 2 * campoAncho + 80, filaY, campoAncho - 90, campoAlto);

        filaY += campoAlto + espacioVertical;

        // 📌 Segunda fila
        labelNif.setBounds(margenX, filaY, 100, campoAlto);
        txtNIF.setBounds(margenX + 80, filaY, campoAncho - 90, campoAlto);

        labelTelefono.setBounds(margenX + campoAncho, filaY, 100, campoAlto);
        txtTelefono.setBounds(margenX + campoAncho + 80, filaY, campoAncho - 90, campoAlto);

        labelEmail.setBounds(margenX + 2 * campoAncho, filaY, 100, campoAlto);
        txtEmail.setBounds(margenX + 2 * campoAncho + 80, filaY, campoAncho - 90, campoAlto);

        filaY += campoAlto + espacioVertical;

        // 📌 Tercera fila (Dirección)
        labelDireccion.setBounds(margenX, filaY, 100, campoAlto);
        txtDireccion.setBounds(margenX + 80, filaY, width - 2 * margenX - 100, campoAlto);

        filaY += campoAlto + 2 * espacioVertical;

        // 📌 Botones
        botonGuardar.setBounds(width - margenX - 120, filaY, 100, 40);
        botonVolverAtras.setBounds(margenX, filaY, 100, 40);
    }

    private void guardarCliente() {
        if (txtNombre.getText().isEmpty() || txtApellidos.getText().isEmpty() ||
            txtNIF.getText().isEmpty() || txtTelefono.getText().isEmpty() ||
            txtEmail.getText().isEmpty() || txtDireccion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiendainformatica", "root", "");
             PreparedStatement ps = con.prepareStatement("INSERT INTO clientes (NOMBRE, APELLIDOS, NIF, TELEFONO, EMAIL, DIRECCION) VALUES (?, ?, ?, ?, ?, ?)");) {
            
            ps.setString(1, txtNombre.getText());
            ps.setString(2, txtApellidos.getText());
            ps.setString(3, txtNIF.getText());
            ps.setString(4, txtTelefono.getText());
            ps.setString(5, txtEmail.getText());
            ps.setString(6, txtDireccion.getText());
            
            int res = ps.executeUpdate();
            if (res > 0) {
                JOptionPane.showMessageDialog(null, "Cliente agregado exitosamente.");
                if (ventanaClientes != null) {
                    ventanaClientes.actualizarListaClientes();
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar el cliente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}





