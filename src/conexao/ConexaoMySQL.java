
package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexaoMySQL {
    
    private static final String URL_BANCO = "jdbc:mysql://localhost:3306/despensa";
    private static final String USUARIO = "root";
    private static final String SENHA = "senha123";
    
    
    public static Connection getConexao() throws SQLException, ClassNotFoundException {
        //Faz com que a classe seja carregada pela JVM
        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(URL_BANCO, USUARIO, SENHA);
    }
    
}
