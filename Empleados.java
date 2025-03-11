import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.*;

public class Empleados extends JFrame {

    private JTable tablaEmpleados;
    private DefaultTableModel modeloTabla;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField campoBusqueda;
    private JButton botonBuscar, botonAgregar;
    private JComboBox<String> comboCriterio;

    public Empleados() {
        setTitle("Empleados");
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
        String[] criterios = {"ID_EMPLEADO", "NOMBRE", "APELLIDOS", "EMAIL", "TELEFONO"};
        comboCriterio = new JComboBox<>(criterios);

        campoBusqueda = new JTextField(15);
        botonBuscar = new JButton("Buscar");
        botonBuscar.addActionListener(e -> buscarEmpleados());

        panelBusqueda.add(new JLabel("Buscar por:"));
        panelBusqueda.add(comboCriterio);
        panelBusqueda.add(campoBusqueda);
        panelBusqueda.add(botonBuscar);
        panel.add(panelBusqueda, BorderLayout.NORTH);

        // 📌 Definir los nombres de las columnas
        String[] columnas = {"ID_EMPLEADO", "NOMBRE", "APELLIDOS"};

        // 📌 Crear el modelo de la tabla
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaEmpleados = new JTable(modeloTabla);
        tablaEmpleados.setFillsViewportHeight(true);
        tablaEmpleados.setRowHeight(30);

        // 📌 Colores alternados para las filas
        tablaEmpleados.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

        JScrollPane scrollPane = new JScrollPane(tablaEmpleados);
        panel.add(scrollPane, BorderLayout.CENTER);

        // 📌 Panel con botón
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botonAgregar = new JButton("Agregar Empleado");
        botonAgregar.setPreferredSize(new Dimension(150, 30));
        botonAgregar.addActionListener(e -> {
            // Al presionar "Agregar Empleado", se abre la ventana AgregarEmpleados
            AgregarEmpleados ventanaAgregar = new AgregarEmpleados(this);
            ventanaAgregar.setVisible(true);
        });
        panelBoton.add(botonAgregar);
        panel.add(panelBoton, BorderLayout.SOUTH);

        // 📌 Añadir filtro dinámico a la tabla
        sorter = new TableRowSorter<>(modeloTabla);
        tablaEmpleados.setRowSorter(sorter);

        obtenerEmpleados();
        add(panel);
        setVisible(true);
    }

    // 📌 Método para filtrar la tabla
    private void buscarEmpleados() {
        String texto = campoBusqueda.getText().trim();
        int columna = comboCriterio.getSelectedIndex();

        if (texto.isEmpty()) {
            sorter.setRowFilter(null); 
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, columna)); 
        }
    }

    private void obtenerEmpleados() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiendainformatica", "root", "");
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT ID_EMPLEADO, NOMBRE, APELLIDOS, EMAIL, TELEFONO FROM empleados")) {

            modeloTabla.setRowCount(0); // Limpiar antes de actualizar

            while (rs.next()) {
                modeloTabla.addRow(new Object[] {
                    rs.getString("ID_EMPLEADO"),
                    rs.getString("NOMBRE"),
                    rs.getString("APELLIDOS"),
                    rs.getString("EMAIL"),
                    rs.getString("TELEFONO")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarListaEmpleados() {
        obtenerEmpleados();
    }

    public static void main(String[] args) {
        new Empleados();
    }
}
