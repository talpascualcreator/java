import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GestionUsuarios {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/prueva";
        String usuario = "root";
        String contraseña = "";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, usuario, contraseña);

            Scanner scanner = new Scanner(System.in);
            int opcion;
            

            do {
                System.out.println("Seleccione una opción:");
                System.out.println("1. Insertar usuario");
                System.out.println("2. Consultar usuarios");
                System.out.println("3. Actualizar usuario");
                System.out.println("4. Eliminar usuario");
                System.out.println("0. Salir");

                opcion = scanner.nextInt();
                
                switch (opcion) {
                    case 1:
                        // Operación insertar datos
                        System.out.println("Ingrese el nombre de usuario:");
                        String nuevoUsuario = scanner.next();
                        System.out.println("Ingrese la contraseña:");
                        String nuevaContrasena = scanner.next();
                        insertarUsuario(connection, nuevoUsuario, nuevaContrasena);
                        break;
                    case 2:
                        // Operación consultar datos
                        System.out.println("Usuarios en la tabla:");
                        consultarUsuarios(connection);
                        break;
                    case 3:
                        // Operación actualizar datos
                        System.out.println("Ingrese el nombre de usuario a actualizar:");
                        String usuarioActualizar = scanner.next();
                        System.out.println("Ingrese la nueva contraseña:");
                        String nuevaContrasenaUpdate = scanner.next();
                        actualizarUsuario(connection, usuarioActualizar, nuevaContrasenaUpdate);
                        break;
                    case 4:
                        // Operación borrar datos
                        System.out.println("Ingrese el nombre de usuario a eliminar:");
                        String usuarioEliminar = scanner.next();
                        eliminarUsuario(connection, usuarioEliminar);
                        break;
                    case 0:
                        // Salir
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }

            } while (opcion != 0);
            scanner.close(); 
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        
    }

    // ... funciones insertar, consultar, actualizar y eliminar usuarios



    private static void insertarUsuario(Connection connection, String usuario, String contraseña) throws SQLException {
        String sql = "INSERT INTO usuario (usuario, contraseña) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, usuario);
        preparedStatement.setString(2, contraseña);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    private static void consultarUsuarios(Connection connection) throws SQLException {
        String sql = "SELECT usuario, contraseña FROM usuario";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String usuario = resultSet.getString("usuario");
            String contraseña = resultSet.getString("contraseña");
            System.out.println("Usuario: " + usuario + ", Contraseña: " + contraseña);
        }

        resultSet.close();
        preparedStatement.close();
    }

    private static void actualizarUsuario(Connection connection, String usuario, String nuevaContraseña) throws SQLException {
        String sql = "UPDATE usuario SET contraseña = ? WHERE usuario = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, nuevaContraseña);
        preparedStatement.setString(2, usuario);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    private static void eliminarUsuario(Connection connection, String usuario) throws SQLException {
        String sql = "DELETE FROM usuario WHERE usuario = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, usuario);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}