import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AgregarEmpleados extends JFrame {

    private JButton botonGuardar, botonVolverAtras;
    private JTextField txtNombre, txtApellidos, txtTelefono, txtEmail;
    private JLabel labelNombre, labelApellidos, labelTelefono, labelEmail;
    private Empleados ventanaEmpleados;

    public AgregarEmpleados(Empleados ventanaEmpleados) {
        this.ventanaEmpleados = ventanaEmpleados;
        
        setTitle("Agregar Empleado");
        setSize(700, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setResizable(true);

        // 📌 Agregar ComponentListener para ajustar tamaños dinámicamente
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ajustarComponentes();
            }
        });

        // 📌 Primera fila (NOMBRE, APELLIDOS, TELEFONO)
        labelNombre = new JLabel("Nombre:");
        add(labelNombre);
        txtNombre = new JTextField();
        add(txtNombre);

        labelApellidos = new JLabel("Apellidos:");
        add(labelApellidos);
        txtApellidos = new JTextField();
        add(txtApellidos);

        labelTelefono = new JLabel("Teléfono:");
        add(labelTelefono);
        txtTelefono = new JTextField();
        add(txtTelefono);

        // 📌 Segunda fila (EMAIL)
        labelEmail = new JLabel("Email:");
        add(labelEmail);
        txtEmail = new JTextField();
        add(txtEmail);

        // 📌 Botones
        botonGuardar = new JButton("Guardar");
        botonGuardar.addActionListener(e -> guardarEmpleado());
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
        int margenX = 20;
        int margenY = 20;
        int campoAncho = (width - 4 * margenX) / 3; // Para 3 columnas
        int campoAlto = 30;
        int espacioVertical = 10;
        int filaY = margenY;

        // 📌 Primera fila (NOMBRE, APELLIDOS, TELEFONO)
        labelNombre.setBounds(margenX, filaY, 100, campoAlto);
        txtNombre.setBounds(margenX + 80, filaY, campoAncho - 90, campoAlto);

        labelApellidos.setBounds(margenX + campoAncho, filaY, 100, campoAlto);
        txtApellidos.setBounds(margenX + campoAncho + 80, filaY, campoAncho - 90, campoAlto);

        labelTelefono.setBounds(margenX + 2 * campoAncho, filaY, 100, campoAlto);
        txtTelefono.setBounds(margenX + 2 * campoAncho + 80, filaY, campoAncho - 90, campoAlto);

        filaY += campoAlto + espacioVertical;

        // 📌 Segunda fila (EMAIL)
        labelEmail.setBounds(margenX, filaY, 100, campoAlto);
        txtEmail.setBounds(margenX + 80, filaY, campoAncho - 90, campoAlto);

        filaY += campoAlto + 2 * espacioVertical;

        // 📌 Botones
        botonGuardar.setBounds(width - margenX - 120, filaY, 100, 40);
        botonVolverAtras.setBounds(margenX, filaY, 100, 40);
    }

    private void guardarEmpleado() {
        if (txtNombre.getText().isEmpty() || txtApellidos.getText().isEmpty() ||
            txtTelefono.getText().isEmpty() || txtEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiendainformatica", "root", "");
             PreparedStatement ps = con.prepareStatement("INSERT INTO empleados (NOMBRE, APELLIDOS, TELEFONO, EMAIL) VALUES (?, ?, ?, ?)")) {
            
            ps.setString(1, txtNombre.getText());
            ps.setString(2, txtApellidos.getText());
            ps.setString(3, txtTelefono.getText());
            ps.setString(4, txtEmail.getText());

            int res = ps.executeUpdate();
            if (res > 0) {
                JOptionPane.showMessageDialog(null, "Empleado agregado exitosamente.");
                if (ventanaEmpleados != null) {
                    ventanaEmpleados.actualizarListaEmpleados(); // Actualizar la lista de empleados
                }
                dispose(); // Cerrar la ventana de agregar
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar el empleado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



