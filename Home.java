import javax.swing.*;
import java.awt.*;

public class Home extends JFrame {
    public Home() {
        setTitle("Home");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana
        setLocationRelativeTo(null); // Centrar en la pantalla después de definir el tamaño
        getContentPane().setBackground(Color.CYAN);
        setLayout(new BorderLayout()); // 🔹 Se añade layout principal

        // 📌 Etiqueta principal
        JLabel label = new JLabel("INFORMATICA GRUPO 23", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        add(label, BorderLayout.NORTH);

        // 📌 Panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(2, 3, 15, 15)); // 2 filas, 3 columnas
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Espaciado
        panelBotones.setBackground(Color.CYAN);



        // 📌 Crear los botones
        JButton botonClientes = new JButton("CLIENTES");
        JButton botonProveedor = new JButton("PROVEEDOR");
        JButton botonCompras = new JButton("COMPRAS");
        JButton botonVentas = new JButton("VENTAS");
        JButton botonProductos = new JButton("PRODUCTOS");
        JButton botonEmpleados = new JButton("EMPLEADOS");
        JButton botonSalir = new JButton("SALIR");

        // 📌 Ajustar el tamaño de los botones
        botonClientes.setPreferredSize(new Dimension(100, 40)); // Ajusta el tamaño
        botonProveedor.setPreferredSize(new Dimension(100, 40));
        botonCompras.setPreferredSize(new Dimension(100, 40));
        botonVentas.setPreferredSize(new Dimension(100, 40));
        botonProductos.setPreferredSize(new Dimension(100, 40));
        botonEmpleados.setPreferredSize(new Dimension(100, 40));
        botonSalir.setPreferredSize(new Dimension(100, 40));

        // 📌 Agregar los botones al panel
        panelBotones.add(botonClientes);
        panelBotones.add(botonProveedor);
        panelBotones.add(botonCompras);
        panelBotones.add(botonVentas);
        panelBotones.add(botonProductos);
        panelBotones.add(botonEmpleados);

        // 📌 Agregar el panel de botones a la ventana
        add(panelBotones, BorderLayout.CENTER);
        add(botonSalir, BorderLayout.SOUTH); // Botón salir en la parte inferior

        // 📌 Acción del botón Clientes
        botonClientes.addActionListener(e -> abrirVentanaClientes());

        botonProveedor.addActionListener(e -> abrirVentanaProveedor());

        botonCompras.addActionListener(e -> abrirVentanaCompras());

        botonVentas.addActionListener(e -> abrirVentanaVentas());

        botonProductos.addActionListener(e -> abrirVentanaProductos());


        botonEmpleados.addActionListener(e -> abrirVentanaEmpleados());



        // 📌 Acción del botón Salir
        botonSalir.addActionListener(e -> {
            dispose(); // Cierra la ventana actual
            new Login(); // Abre la ventana de login
        });

        // Mostrar la ventana
        setVisible(true);
    }

    // 📌 Método corregido para abrir la ventana de Clientes
    private void abrirVentanaClientes() {
    // Crear y mostrar la ventana de Clientes
    new Clientes();  // Esto abrirá la ventana Clientes correctamente.
   }

    // 📌 Método corregido para abrir la ventana de Proveedor
    private void abrirVentanaProveedor() {
      // Crear y mostrar la ventana de Clientes
      new Proveedor();  // Esto abrirá la ventana Proveedor correctamente.
     }

    // 📌 Método corregido para abrir la ventana de Compras
    private void abrirVentanaCompras() {
      // Crear y mostrar la ventana de Clientes
      new Compras();  // Esto abrirá la ventana Compras correctamente.
     }

    // 📌 Método corregido para abrir la ventana de Ventas
    private void abrirVentanaVentas() {
      // Crear y mostrar la ventana de Clientes
      new Ventas();  // Esto abrirá la ventana Ventas correctamente.
     }

    // 📌 Método corregido para abrir la ventana de Productos
    private void abrirVentanaProductos() {
      // Crear y mostrar la ventana de Clientes
      new Productos();  // Esto abrirá la ventana  correctamente.
     }

    // 📌 Método corregido para abrir la ventana de Empleados
    private void abrirVentanaEmpleados() {
      // Crear y mostrar la ventana de Clientes
      new Empleados();  // Esto abrirá la ventana Clientes correctamente.
     }

    public static void main(String[] args) {
        new Home();
    }
}
