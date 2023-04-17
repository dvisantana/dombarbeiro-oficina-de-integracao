import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {

    private static final String URL = "jdbc:mysql://database-dombarbeiro.c6ehfi0kcwnc.sa-east-1.rds.amazonaws.com/mydb";
    private static final String USUARIO = "admin";
    private static final String SENHA = "dombarbeiro123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

}
