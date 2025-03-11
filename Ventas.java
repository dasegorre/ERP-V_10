import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


// Clase para la nueva ventana
public class Ventas extends JFrame implements ActionListener {

    private JTable tablaVentas;
    private DefaultTableModel modeloTabla;
    private JButton boton1;

    public Ventas() {
        setTitle("Ventas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana
        setLocationRelativeTo(null); // Centrar en la pantalla después de definir el tamaño
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout()); // Usar BorderLayout para distribuir componentes


        // 📌 Panel de contenido con Layout
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);


        // 📌 Definir los nombres de las columnas
        String[] columnas = {"COD_FACTURA", "FECHA", "IMPORTE", "ID_CLIENTE", "ID_EMPLEADO"};

        // 📌 Crear el DefaultTableModel para manejar dinámicamente los datos
        modeloTabla = new DefaultTableModel(columnas, 0); //cero filas al inciio
        tablaVentas = new JTable(modeloTabla);
        tablaVentas.setFillsViewportHeight(true); //crear la tabla con el modelo
        tablaVentas.setRowHeight(30);  // Cambia la altura de todas las filas a 30 píxeles

        //Ajustamos el tamaño de las columnas
        tablaVentas.getColumnModel().getColumn(0).setPreferredWidth(50);  // COD_FACTURA más pequeño
        tablaVentas.getColumnModel().getColumn(1).setPreferredWidth(150); // FECHA más ancho
        tablaVentas.getColumnModel().getColumn(2).setPreferredWidth(100); // IMPORTE
        tablaVentas.getColumnModel().getColumn(3).setPreferredWidth(100); // ID_CLIENTE
        tablaVentas.getColumnModel().getColumn(4).setPreferredWidth(100); // ID_EMPLEADO

        // 📌 Añadir la tabla al panel dentro de un JScrollPane
        JScrollPane scrollPane = new JScrollPane(tablaVentas);
        panel.add(scrollPane, BorderLayout.CENTER);

        //panel para el boton
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Alinear a la izquierda
        boton1 = new JButton("Agregar Venta");
        boton1.setPreferredSize(new Dimension(150, 30));
        boton1.addActionListener(this);
        panelBoton.add(boton1);
        add(panelBoton,BorderLayout.SOUTH);

        // 📌 Llenar la tabla con datos de la base de datos
        obtenerVentas();  

        // 📌 Añadir el panel al JFrame
        add(panel);

        // Mostrar la ventana
        setVisible(true);

    }

     // Método para obtener los datos de los clientes desde la base de datos
     private void obtenerVentas() {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;   

        try {
            // 🔗 Conectar a la base de datos
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiendainformatica", "root", "");
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT COD_FACTURA, FECHA, IMPORTE, ID_CLIENTE, ID_EMPLEADO FROM ventas");

            // 🔄 Limpiar modelo antes de llenarlo (para evitar duplicados)
            modeloTabla.setRowCount(0);

            // 📥 Llenar la tabla con datos
            while (rs.next()) {
                Object[] fila = {
                    rs.getString("COD_FACTURA"),
                    rs.getString("FECHA"),
                    rs.getString("IMPORTE"),
                    rs.getString("ID_CLIENTE"),
                    rs.getString("ID_EMPLEADO")
                };
                modeloTabla.addRow(fila);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 🔴 Cerrar la conexión correctamente
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Ventas();  // Crear y mostrar la ventana de Clientes
    }
}