import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private Button botaoEntrar;

    @FXML
    private TextField boxSenha;

    @FXML
    private TextField boxUsuario;

    public boolean verificarLogin(String usuario, String senha){
        try (Connection connection = ConexaoBD.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Admin WHERE adm_login = ? AND adm_senha = ?"))
            {
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, senha);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
    }

    @FXML
    void fazerLogin(ActionEvent event) {
        String usuario = boxUsuario.getText();
        String senha = boxSenha.getText();

        Admin admin = new Admin();
        LoginController loginController = new LoginController();

        if(loginController.verificarLogin(usuario,senha)){
            if(admin.cargo == 0){
                App.csEntrarApp(0);
            }
            System.out.println("Login realizado!");
            // System.out.println("user: " + admin.getUsuario());
            // System.out.println("senha: " + admin.getSenha());
        }else{
            Error();
        }

        limparBox();

    }

    void limparBox(){
        boxUsuario.setText(null);
        boxSenha.setText(null);
    }

    private void Error(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro!");
        alert.setHeaderText("Usuário ou senha estão incorretos");
        alert.showAndWait();
    }

}
