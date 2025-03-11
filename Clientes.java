import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Clientes extends JFrame {

    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField campoBusqueda;
    private JButton botonBuscar, botonAgregar, botonEliminar, botonEditar;
    private JComboBox<String> comboCriterio;

    public Clientes() {
        setTitle("Clientes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // 📌 Panel de búsqueda con JComboBox
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setBackground(Color.WHITE);

        // Opciones de búsqueda
        String[] criterios = {"ID_CLIENTE", "NOMBRE", "APELLIDOS", "NIF", "TELEFONO"};
        comboCriterio = new JComboBox<>(criterios);

        campoBusqueda = new JTextField(15);
        botonBuscar = new JButton("Buscar");
        botonBuscar.addActionListener(e -> buscarCliente());

        panelBusqueda.add(new JLabel("Buscar por:"));
        panelBusqueda.add(comboCriterio);
        panelBusqueda.add(campoBusqueda);
        panelBusqueda.add(botonBuscar);
        panel.add(panelBusqueda, BorderLayout.NORTH);

        // 📌 Definir los nombres de las columnas
        String[] columnas = {"ID_CLIENTE", "NOMBRE", "APELLIDOS", "NIF", "TELEFONO"};

        // 📌 Crear el modelo de la tabla
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaClientes = new JTable(modeloTabla);
        tablaClientes.setFillsViewportHeight(true);
        tablaClientes.setRowHeight(30);

        tablaClientes.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaClientes.getColumnModel().getColumn(1).setPreferredWidth(150);
        tablaClientes.getColumnModel().getColumn(2).setPreferredWidth(200);
        tablaClientes.getColumnModel().getColumn(3).setPreferredWidth(100);
        tablaClientes.getColumnModel().getColumn(4).setPreferredWidth(100);

        // 📌 Colores alternados para las filas
        tablaClientes.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                cell.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
                if (isSelected) {
                    cell.setBackground(Color.CYAN);
                }
                return cell;
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        panel.add(scrollPane, BorderLayout.CENTER);

        // 📌 Panel con botones
        JPanel panelBotonIzquierda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botonAgregar = new JButton("Agregar Cliente");
        botonAgregar.setPreferredSize(new Dimension(150, 30));
        botonAgregar.addActionListener(e -> new AgregarCliente(this)); // Pasamos "this" para actualizar después
        panelBotonIzquierda.add(botonAgregar);

        // Añadir botón Eliminar Cliente
        botonEliminar = new JButton("Eliminar Cliente");
        botonEliminar.setPreferredSize(new Dimension(150, 30));
        botonEliminar.addActionListener(e -> eliminarClienteSeleccionado()); // Método para eliminar cliente
        botonEliminar.setEnabled(false);  // Inicialmente desactivado
        panelBotonIzquierda.add(botonEliminar);

        // Panel para "Editar Cliente" a la derecha
        JPanel panelBotonDerecha = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botonEditar = new JButton("Ver / Editar Cliente");
        botonEditar.setPreferredSize(new Dimension(150, 30));
        botonEditar.setEnabled(false);  // Se habilitará cuando se seleccione una fila
        botonEditar.addActionListener(e -> editarClienteSeleccionado());
        panelBotonDerecha.add(botonEditar);

        // Añadir ambos paneles al panel principal
        JPanel panelBotones = new JPanel(new BorderLayout());
        panelBotones.add(panelBotonIzquierda, BorderLayout.WEST);
        panelBotones.add(panelBotonDerecha, BorderLayout.EAST);
        add(panelBotones, BorderLayout.SOUTH);

        // 📌 Añadir filtro dinámico a la tabla
        sorter = new TableRowSorter<>(modeloTabla);
        tablaClientes.setRowSorter(sorter);

        // 📌 Selección de cliente de la tabla
        tablaClientes.getSelectionModel().addListSelectionListener(e -> habilitarBotonesSiFilaSeleccionada());

        // 📌 Detectar clic en cualquier parte de la ventana
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Deseleccionamos la fila cuando hacemos clic fuera de la tabla
                tablaClientes.clearSelection();
                deshabilitarBotones();  // Desactivar botones si no hay fila seleccionada
            }
        });

        obtenerClientes(); // Cargar clientes al inicio
        add(panel);
        setVisible(true);
    }

    // 📌 Método para habilitar los botones "Editar" y "Eliminar" si se selecciona una fila
    private void habilitarBotonesSiFilaSeleccionada() {
        if (tablaClientes.getSelectedRow() != -1) {
            botonEditar.setEnabled(true);
            botonEliminar.setEnabled(true);  // Habilitar también el botón Eliminar
        } else {
            deshabilitarBotones();  // Desactivar los botones si no hay fila seleccionada
        }
    }

    // 📌 Método para deshabilitar los botones
    private void deshabilitarBotones() {
        botonEditar.setEnabled(false);
        botonEliminar.setEnabled(false);
    }

    // 📌 Método para abrir la ventana de edición
    private void editarClienteSeleccionado() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        String idCliente = modeloTabla.getValueAt(filaSeleccionada, 0).toString();
        new EditarCliente(this, idCliente); // Abrir la ventana de edición pasando el ID del cliente
    }

    // 📌 Método para eliminar el cliente seleccionado
    private void eliminarClienteSeleccionado() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        if (filaSeleccionada != -1) {
            String idCliente = modeloTabla.getValueAt(filaSeleccionada, 0).toString();

            // Mostrar mensaje de confirmación
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de que deseas eliminar este cliente?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                eliminarCliente(idCliente);
            }
        }
    }

    // Método para eliminar el cliente de la base de datos
    private void eliminarCliente(String idCliente) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiendainformatica", "root", "");
             PreparedStatement ps = con.prepareStatement("DELETE FROM clientes WHERE ID_CLIENTE = ?")) {

            ps.setString(1, idCliente);
            int res = ps.executeUpdate();

            if (res > 0) {
                JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.");
                obtenerClientes(); // Actualizar la lista de clientes
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el cliente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para actualizar la tabla después de agregar un cliente
    public void actualizarListaClientes() {
        obtenerClientes();
    }

    // 📌 Método para filtrar la tabla
    private void buscarCliente() {
        String texto = campoBusqueda.getText().trim();
        int columna = comboCriterio.getSelectedIndex(); // Índice de la columna según JComboBox

        if (texto.isEmpty()) {
            sorter.setRowFilter(null); // Mostrar todo si no hay filtro
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, columna)); // Filtro en la columna seleccionada
        }
    }

    private void obtenerClientes() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiendainformatica", "root", "");
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT ID_CLIENTE, NOMBRE, APELLIDOS, NIF, TELEFONO FROM clientes")) {

            modeloTabla.setRowCount(0); // Limpiar antes de actualizar

            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                        rs.getString("ID_CLIENTE"),
                        rs.getString("NOMBRE"),
                        rs.getString("APELLIDOS"),
                        rs.getString("NIF"),
                        rs.getString("TELEFONO")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Clientes();
    }
}










