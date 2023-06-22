package src.com.dombarbeiro.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaAutenticarController {

    @FXML
    private Button botaoAutenticar;

    @FXML
    private TextField boxLogin;

    @FXML
    private PasswordField boxSenha;

    private boolean isAutenticado;

    public boolean getIsAutenticado(){
        return isAutenticado;
    }

    LoginController loginController = new LoginController();

    TelaAdminUsuarioController telaAdminUsuarioController = new TelaAdminUsuarioController();

    @FXML
    void autenticarAdmin(ActionEvent event) {
        boolean autenticacao = loginController.verificarLoginAdmin(boxLogin.getText(),boxSenha.getText());
        if(autenticacao){
            clean();
            isAutenticado = true;
            fecharJanela();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Admin não autenticado!");
            alert.showAndWait();
            clean();
        }
    }

    @FXML
    private void fecharJanela(){
        Stage stage = (Stage) boxLogin.getScene().getWindow();
        stage.close();
    }

    private void clean() {
        boxLogin.setText("");
        boxSenha.setText("");
    }


} 
