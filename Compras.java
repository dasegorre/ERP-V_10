import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


// Clase para la nueva ventana
public class Compras extends JFrame implements ActionListener {

    private JTable tablaCompras;
    private DefaultTableModel modeloTabla;
    private JButton boton1;

    public Compras() {
        setTitle("Compras");
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
        String[] columnas = {"COD_FACTURA", "FECHA", "IMPORTE", "ID_PROVEEDOR", "ID_EMPLEADO"};

        // 📌 Crear el DefaultTableModel para manejar dinámicamente los datos
        modeloTabla = new DefaultTableModel(columnas, 0); //cero filas al inciio
        tablaCompras = new JTable(modeloTabla);
        tablaCompras.setFillsViewportHeight(true); //crear la tabla con el modelo
        tablaCompras.setRowHeight(30);  // Cambia la altura de todas las filas a 30 píxeles

        //Ajustamos el tamaño de las columnas
        tablaCompras.getColumnModel().getColumn(0).setPreferredWidth(50);  // COD_PRODUCTIO más pequeño
        tablaCompras.getColumnModel().getColumn(1).setPreferredWidth(150); // FECHA más ancho
        tablaCompras.getColumnModel().getColumn(2).setPreferredWidth(100); // IMPORTE
        tablaCompras.getColumnModel().getColumn(3).setPreferredWidth(100); // ID_PROVEEDOR
        tablaCompras.getColumnModel().getColumn(4).setPreferredWidth(100); // ID_EMPLEADO

        // 📌 Añadir la tabla al panel dentro de un JScrollPane
        JScrollPane scrollPane = new JScrollPane(tablaCompras);
        panel.add(scrollPane, BorderLayout.CENTER);

        //panel para el boton
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Alinear a la izquierda
        boton1 = new JButton("Agregar Compra");
        boton1.setPreferredSize(new Dimension(150, 30));
        boton1.addActionListener(this);
        panelBoton.add(boton1);
        add(panelBoton,BorderLayout.SOUTH);

        // 📌 Llenar la tabla con datos de la base de datos
        obtenerCompras();  

        // 📌 Añadir el panel al JFrame
        add(panel);

        // Mostrar la ventana
        setVisible(true);

    }

     // Método para obtener los datos de los clientes desde la base de datos
     private void obtenerCompras() {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;   

        try {
            // 🔗 Conectar a la base de datos
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tiendainformatica", "root", "");
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT COD_PRODUCTO, FECHA, IMPORTE, ID_PROVEEDOR, ID_EMPLEADO FROM compras");

            // 🔄 Limpiar modelo antes de llenarlo (para evitar duplicados)
            modeloTabla.setRowCount(0);

            // 📥 Llenar la tabla con datos
            while (rs.next()) {
                Object[] fila = {
                    rs.getString("COD_PRODUCTO"),
                    rs.getString("FECHA"),
                    rs.getString("IMPORTE"),
                    rs.getString("ID_PROVEEDOR"),
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
        new Compras();  // Crear y mostrar la ventana de Clientes
    }
}