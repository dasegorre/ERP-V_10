import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EditarCliente extends JFrame {

    private JTextField txtId, txtNombre, txtApellidos, txtNIF, txtTelefono, txtEmail, txtDireccion;
    private JButton botonGuardar, botonVolverAtras;
    private JLabel labelId, labelNombre, labelApellidos, labelNif, labelTelefono, labelEmail, labelDireccion;
    private String idCliente;
    private Clientes ventanaClientes;

    public EditarCliente(Clientes ventanaClientes, String idCliente) {
        this.ventanaClientes = ventanaClientes;
        this.idCliente = idCliente;

        setTitle("Ver / Editar Cliente");
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
        txtId.setEditable(false);  // Solo lectura
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
        botonGuardar.addActionListener(e -> guardarCambios());
        add(botonGuardar);

        botonVolverAtras = new JButton("Volver");
        botonVolverAtras.addActionListener(e -> dispose());
        add(botonVolverAtras);

        obtenerCliente();  // Cargar los datos del cliente

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

    // 📌 Método para obtener los datos del cliente y mostrarlos en los campos
    private void obtenerCliente() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiendainformatica", "root", "");
             PreparedStatement stmt = con.prepareStatement("SELECT NOMBRE, APELLIDOS, NIF, TELEFONO, EMAIL, DIRECCION FROM clientes WHERE ID_CLIENTE = ?")) {

            stmt.setString(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                txtId.setText(idCliente);
                txtNombre.setText(rs.getString("NOMBRE"));
                txtApellidos.setText(rs.getString("APELLIDOS"));
                txtNIF.setText(rs.getString("NIF"));
                txtTelefono.setText(rs.getString("TELEFONO"));
                txtEmail.setText(rs.getString("EMAIL"));
                txtDireccion.setText(rs.getString("DIRECCION"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para guardar cambios en el cliente
    private void guardarCambios() {
        // Validar si los campos son correctos antes de realizar el update
        if (txtNombre.getText().isEmpty() || txtApellidos.getText().isEmpty() ||
            txtNIF.getText().isEmpty() || txtTelefono.getText().isEmpty() ||
            txtEmail.getText().isEmpty() || txtDireccion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Realizar la actualización de los datos
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiendainformatica", "root", "");
             PreparedStatement stmt = con.prepareStatement("UPDATE clientes SET NOMBRE = ?, APELLIDOS = ?, NIF = ?, TELEFONO = ?, EMAIL = ?, DIRECCION = ? WHERE ID_CLIENTE = ?")) {

            stmt.setString(1, txtNombre.getText());
            stmt.setString(2, txtApellidos.getText());
            stmt.setString(3, txtNIF.getText());
            stmt.setString(4, txtTelefono.getText());
            stmt.setString(5, txtEmail.getText());
            stmt.setString(6, txtDireccion.getText());
            stmt.setString(7, idCliente);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.");
                ventanaClientes.actualizarListaClientes(); // Actualiza la lista de clientes en la ventana principal
                dispose(); // Cierra la ventana de edición
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar los datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

