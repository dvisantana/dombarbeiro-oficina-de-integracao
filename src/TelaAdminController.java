import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class TelaAdminController {

    @FXML
    private Button botaoConfiguracoes;

    @FXML
    private Button botaoFinancas;

    @FXML
    private Button botaoSair;

    @FXML
    private Button botaoServicos;

    @FXML
    private Button botaoUsuario;

    // public void cadastrarSecretario(){
    //     try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Admin VALUES(?,?,?)")){
    //         preparedStatement.setString(1, "Victor");
    //         preparedStatement.setString(2, "victor");
    //         preparedStatement.setString(3, "1234");
    //         int linhasAfetadas = preparedStatement.executeUpdate();
    //         System.out.println(linhasAfetadas);
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    // }

    @FXML
    public void abrirTelaAdminUsuario(ActionEvent event){
        App.adminAbrirUsuarios();
    }

    @FXML
    public void sairApp(ActionEvent event) {
        App.csSairApp();
    }

}
