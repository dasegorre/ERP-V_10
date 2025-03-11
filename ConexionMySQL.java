import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConexionMySQL {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/tiendainformatica?useSSL=false&serverTimezone=UTC";
        String usuario = "root"; // Cambia por tu usuario
        String contraseña = ""; // Cambia por tu contraseña si tienes

        try {
            // Cargar el driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Intentar la conexión
            Connection conexion = DriverManager.getConnection(url, usuario, contraseña);
            System.out.println("✅ Conexión exitosa a la base de datos.");
            
            // Cerrar la conexión
            conexion.close();
        } catch (ClassNotFoundException e) {
            System.out.println("⚠ No se encontró el driver de MySQL.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar con MySQL.");
            e.printStackTrace();
        }
    }
}

