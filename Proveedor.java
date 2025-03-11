import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Proveedor extends JFrame {

    private JTable tablaProveedor;
    private DefaultTableModel modeloTabla;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField campoBusqueda;
    private JButton botonBuscar, botonAgregar, botonEliminar, botonEditar;
    private JComboBox<String> comboCriterio;

    public Proveedor() {
        setTitle("Proveedor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setBackground(Color.WHITE);

        String[] criterios = {"ID_PROVEEDOR", "NOMBRE", "CIF", "TELEFONO"};
        comboCriterio = new JComboBox<>(criterios);

        campoBusqueda = new JTextField(15);
        botonBuscar = new JButton("Buscar");
        botonBuscar.addActionListener(e -> buscarProveedor());

        panelBusqueda.add(new JLabel("Buscar por:"));
        panelBusqueda.add(comboCriterio);
        panelBusqueda.add(campoBusqueda);
        panelBusqueda.add(botonBuscar);
        panel.add(panelBusqueda, BorderLayout.NORTH);

        // Definir columnas de la tabla
        String[] columnas = {"ID_PROVEEDOR", "NOMBRE", "CIF", "TELEFONO"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaProveedor = new JTable(modeloTabla);
        tablaProveedor.setFillsViewportHeight(true);
        tablaProveedor.setRowHeight(30);

        // Ajustar el ancho de las columnas
        tablaProveedor.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaProveedor.getColumnModel().getColumn(1).setPreferredWidth(150);
        tablaProveedor.getColumnModel().getColumn(2).setPreferredWidth(200);
        tablaProveedor.getColumnModel().getColumn(3).setPreferredWidth(100);

        // Colores alternados para filas
        tablaProveedor.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

        JScrollPane scrollPane = new JScrollPane(tablaProveedor);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel con botones
        JPanel panelBotonIzquierda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botonAgregar = new JButton("Agregar Proveedor");
        botonAgregar.setPreferredSize(new Dimension(150, 30));
        botonAgregar.addActionListener(e -> new AgregarProveedor(this));
        panelBotonIzquierda.add(botonAgregar);

        // Botón Eliminar Proveedor
        botonEliminar = new JButton("Eliminar Proveedor");
        botonEliminar.setPreferredSize(new Dimension(150, 30));
        botonEliminar.addActionListener(e -> eliminarProveedorSeleccionado());
        botonEliminar.setEnabled(false);
        panelBotonIzquierda.add(botonEliminar);

        // Panel botón Editar
        JPanel panelBotonDerecha = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botonEditar = new JButton("Ver / Editar Proveedor");
        botonEditar.setPreferredSize(new Dimension(150, 30));
        botonEditar.setEnabled(false);
        botonEditar.addActionListener(e -> editarProveedorSeleccionado());
        panelBotonDerecha.add(botonEditar);

        // Añadir ambos paneles al panel principal
        JPanel panelBotones = new JPanel(new BorderLayout());
        panelBotones.add(panelBotonIzquierda, BorderLayout.WEST);
        panelBotones.add(panelBotonDerecha, BorderLayout.EAST);
        add(panelBotones, BorderLayout.SOUTH);

        // Añadir filtro dinámico a la tabla
        sorter = new TableRowSorter<>(modeloTabla);
        tablaProveedor.setRowSorter(sorter);

        // Manejo de selección de tabla para activar botones
        tablaProveedor.getSelectionModel().addListSelectionListener(e -> habilitarBotonesSiFilaSeleccionada());

        // Deseleccionar cuando se hace clic fuera
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                tablaProveedor.clearSelection();
                deshabilitarBotones();
            }
        });

        obtenerProveedores();
        add(panel);
        setVisible(true);
    }

    // Método para habilitar botones cuando hay fila seleccionada
    private void habilitarBotonesSiFilaSeleccionada() {
        if (tablaProveedor.getSelectedRow() != -1) {
            botonEditar.setEnabled(true);
            botonEliminar.setEnabled(true);
        } else {
            deshabilitarBotones();
        }
    }

    // Método para deshabilitar botones
    private void deshabilitarBotones() {
        botonEditar.setEnabled(false);
        botonEliminar.setEnabled(false);
    }

    // Método para abrir ventana de edición
    private void editarProveedorSeleccionado() {
        int filaSeleccionada = tablaProveedor.getSelectedRow();
        if (filaSeleccionada != -1) {
            String idProveedor = modeloTabla.getValueAt(filaSeleccionada, 0).toString();
            new EditarProveedor(this, idProveedor);
        }
    }

    // Método para eliminar proveedor seleccionado
    private void eliminarProveedorSeleccionado() {
        int filaSeleccionada = tablaProveedor.getSelectedRow();
        if (filaSeleccionada != -1) {
            String idProveedor = modeloTabla.getValueAt(filaSeleccionada, 0).toString();

            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de que deseas eliminar este proveedor?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                eliminarProveedor(idProveedor);
            }
        }
    }

    // Método para eliminar proveedor de la base de datos
    private void eliminarProveedor(String idProveedor) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiendainformatica", "root", "");
             PreparedStatement ps = con.prepareStatement("DELETE FROM proveedor WHERE ID_PROVEEDOR = ?")) {

            ps.setString(1, idProveedor);
            int res = ps.executeUpdate();

            if (res > 0) {
                JOptionPane.showMessageDialog(this, "Proveedor eliminado correctamente.");
                obtenerProveedores();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el proveedor.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para actualizar la tabla después de agregar un proveedor
    public void actualizarListaProveedores() {
        obtenerProveedores();
    }

    // Método para buscar en la tabla
    private void buscarProveedor() {
        String texto = campoBusqueda.getText().trim();
        int columna = comboCriterio.getSelectedIndex();

        if (texto.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, columna));
        }
    }

    // Método para obtener proveedores de la base de datos
    private void obtenerProveedores() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiendainformatica", "root", "");
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT ID_PROVEEDOR, NOMBRE, CIF, TELEFONO FROM proveedor")) {

            modeloTabla.setRowCount(0);

            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                        rs.getString("ID_PROVEEDOR"),
                        rs.getString("NOMBRE"),
                        rs.getString("CIF"),
                        rs.getString("TELEFONO")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Proveedor();
    }
}

