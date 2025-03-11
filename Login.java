import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.awt.Color;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;



public class Login extends JFrame {


    public Login() {
        // Configurar la ventana principal
        setTitle("Login");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana
        setLayout(null); // Evita problemas con FlowLayout
        getContentPane().setBackground(Color.WHITE);
        setResizable(false);

        // Cargar imagen correctamente
        ImageIcon imagenOriginal = new ImageIcon(getClass().getResource("/imagen.jpg")); // 📌 Asegurar ruta correcta dentro del JAR
        int anchoMaximo = 100; // Tamaño deseado
        int altoMaximo = 100;

        // Redimensionar la imagen
        Image imagenEscalada = imagenOriginal.getImage().getScaledInstance(anchoMaximo, altoMaximo, Image.SCALE_SMOOTH);
        ImageIcon imagen = new ImageIcon(imagenEscalada);

        // Crear un JLabel con la imagen escalada
        JLabel labelImagen = new JLabel(imagen);
        labelImagen.setBounds(360, 150, anchoMaximo, altoMaximo); // Ajustar tamaño y posición
        add(labelImagen);
        
        // Mostrar ventana
        setVisible(true);

        // Crear label y campo usuario
        JLabel Usuario = new JLabel("Usuario");
        Usuario.setBounds(280,240,100,30);
        add(Usuario);

        JTextField Usuario1 = new JTextField("");
        Usuario1.setBounds(330,250,150,20);
        add(Usuario1);

        // Crear label y campo contraseña
        JLabel Contraseña = new JLabel("Contraseña");
        Contraseña.setBounds(255,295,100,30);
        add(Contraseña);

        JPasswordField Contraseña1 = new JPasswordField("");
        Contraseña1.setBounds(330,300,150,20);
        add(Contraseña1);

        // Crear un botón
        JButton botonLogin = new JButton("Login");
        botonLogin.setBounds(360, 355, 80, 30); // Ubicación y tamaño del botón manualmente
        add(botonLogin);

        // Acción del botón
        botonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = Usuario1.getText();
                String contraseña = new String(Contraseña1.getPassword());

                // Verificar usuario y contraseña
                if (usuario.equals("admin") && contraseña.equals("123456")) {
                    dispose(); // Cierra la ventana de login
                    new Home(); // Abre la nueva ventana de Home
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Llamamos a revalidate() y repaint() para forzar la actualización de la interfaz
        revalidate();
        repaint();

        setVisible(true);
    }



    public static void main(String[] args) {
        new Login(); // Inicia la ventana principal

    }
}


