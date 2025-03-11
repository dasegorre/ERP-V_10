import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Productos extends JFrame {

    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField campoBusqueda;
    private JButton botonBuscar, botonAgregar, botonEliminar, botonEditar;
    private JComboBox<String> comboCriterio;

    public Productos() {
        setTitle("Productos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setBackground(Color.WHITE);

        String[] criterios = {"COD_PRODUCTO", "NOMBRE", "PRECIO_COMPRA", "ID_PROVEEDOR"};
        comboCriterio = new JComboBox<>(criterios);

        campoBusqueda = new JTextField(15);
        botonBuscar = new JButton("Buscar");
        botonBuscar.addActionListener(e -> buscarProductos());

        panelBusqueda.add(new JLabel("Buscar por:"));
        panelBusqueda.add(comboCriterio);
        panelBusqueda.add(campoBusqueda);
        panelBusqueda.add(botonBuscar);
        panel.add(panelBusqueda, BorderLayout.NORTH);

        // Definir columnas de la tabla
        String[] columnas = {"COD_PRODUCTO", "NOMBRE", "PRECIO_COMPRA", "ID_PROVEEDOR"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setFillsViewportHeight(true);
        tablaProductos.setRowHeight(30);

        // Ajustar el ancho de las columnas
        tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaProductos.getColumnModel().getColumn(1).setPreferredWidth(150);
        tablaProductos.getColumnModel().getColumn(2).setPreferredWidth(100);
        tablaProductos.getColumnModel().getColumn(3).setPreferredWidth(100);

        // Colores alternados para filas
        tablaProductos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel con botones
        JPanel panelBotonIzquierda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botonAgregar = new JButton("Agregar Producto");
        botonAgregar.setPreferredSize(new Dimension(150, 30));
        botonAgregar.addActionListener(e -> new AgregarProductos(this));
        panelBotonIzquierda.add(botonAgregar);

        // Botón Eliminar Producto
        botonEliminar = new JButton("Eliminar Producto");
        botonEliminar.setPreferredSize(new Dimension(150, 30));
        botonEliminar.addActionListener(e -> eliminarProductoSeleccionado());
        botonEliminar.setEnabled(false);
        panelBotonIzquierda.add(botonEliminar);

        // Panel botón Editar
        JPanel panelBotonDerecha = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botonEditar = new JButton("Ver / Editar Producto");
        botonEditar.setPreferredSize(new Dimension(150, 30));
        botonEditar.setEnabled(false); // Inicialmente deshabilitado
        botonEditar.addActionListener(e -> editarProductoSeleccionado());
        panelBotonDerecha.add(botonEditar);

        // Añadir ambos paneles al panel principal
        JPanel panelBotones = new JPanel(new BorderLayout());
        panelBotones.add(panelBotonIzquierda, BorderLayout.WEST);
        panelBotones.add(panelBotonDerecha, BorderLayout.EAST);
        add(panelBotones, BorderLayout.SOUTH);

        // Añadir filtro dinámico a la tabla
        sorter = new TableRowSorter<>(modeloTabla);
        tablaProductos.setRowSorter(sorter);

        // Manejo de selección de tabla para activar botones
        tablaProductos.getSelectionModel().addListSelectionListener(e -> habilitarBotonesSiFilaSeleccionada());

        // Deseleccionar cuando se hace clic fuera de la tabla
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                tablaProductos.clearSelection();
                deshabilitarBotones();
            }
        });

        obtenerProductos();
        add(panel);
        setVisible(true);
    }

    // Método para habilitar botones cuando hay fila seleccionada
    private void habilitarBotonesSiFilaSeleccionada() {
        if (tablaProductos.getSelectedRow() != -1) {
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
    private void editarProductoSeleccionado() {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada != -1) {
            String codProducto = modeloTabla.getValueAt(filaSeleccionada, 0).toString();
            new EditarProductos(this, codProducto);  // Abre la ventana de edición
        }
    }

    // Método para eliminar producto seleccionado
    private void eliminarProductoSeleccionado() {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada != -1) {
            String codProducto = modeloTabla.getValueAt(filaSeleccionada, 0).toString();

            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de que deseas eliminar este producto?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                eliminarProducto(codProducto);
            }
        }
    }

    // Método para eliminar producto de la base de datos
    private void eliminarProducto(String codProducto) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiendainformatica", "root", "");
             PreparedStatement ps = con.prepareStatement("DELETE FROM productos WHERE COD_PRODUCTO = ?")) {

            ps.setString(1, codProducto);
            int res = ps.executeUpdate();

            if (res > 0) {
                JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
                obtenerProductos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el producto.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para actualizar la lista de productos
    public void actualizarListaProductos() {
        obtenerProductos();
    }

    // Método para buscar productos en la tabla
    private void buscarProductos() {
        String texto = campoBusqueda.getText().trim();
        int columna = comboCriterio.getSelectedIndex();

        if (texto.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, columna));
        }
    }

    // Método para obtener los productos desde la base de datos
    private void obtenerProductos() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiendainformatica", "root", "");
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COD_PRODUCTO, NOMBRE, PRECIO_COMPRA, ID_PROVEEDOR FROM productos")) {

            modeloTabla.setRowCount(0);

            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                        rs.getString("COD_PRODUCTO"),
                        rs.getString("NOMBRE"),
                        rs.getString("PRECIO_COMPRA"),
                        rs.getString("ID_PROVEEDOR")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Productos();
    }
}







