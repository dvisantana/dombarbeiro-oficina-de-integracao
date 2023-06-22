package src.com.dombarbeiro.Controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import src.com.dombarbeiro.App;

import src.com.dombarbeiro.Configs.ConexaoBD;
import src.com.dombarbeiro.Models.Usuario;
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

    private Usuario usuarioLogin = new Usuario();
    

    private Usuario criaUsuario(String usuario){
        try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Usuario WHERE adm_login = ?"))
        {
            preparedStatement.setString(1, usuario);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Usuario usuarioTeste = new Usuario(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
                return usuarioTeste;
            }
            return null;
        }catch (Exception e) {
            return null;
        }
    }

    public boolean verificarLogin(String usuario, String senha){
        try (Connection connection = ConexaoBD.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Usuario WHERE adm_login = ? AND adm_senha = ?"))
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

    public boolean verificarLoginAdmin(String usuario, String senha){
        try (Connection connection = ConexaoBD.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Usuario WHERE adm_login = ? AND adm_senha = ? AND adm_tipo = 0"))
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
        String login = boxUsuario.getText();
        String senha = boxSenha.getText();

        LoginController loginController = new LoginController();
        usuarioLogin = criaUsuario(login);

        if(loginController.verificarLogin(login,senha)){
            //Admin
            if(usuarioLogin.getTipo() == 0){
                App.csEntrarApp(0);
            }
            //Secretario
            else if(usuarioLogin.getTipo() == 1){
                App.csEntrarApp(1);
                System.out.println("USUARIO!");
            }
            System.out.println("Login realizado!" + usuarioLogin.getTipo());
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
