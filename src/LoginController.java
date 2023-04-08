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

    @FXML
    void fazerLogin(ActionEvent event) {
        String usuario = boxUsuario.getText();
        String senha = boxSenha.getText();

        Admin admin = new Admin();

        if(usuario.equals(admin.getUsuario()) && senha.equals(admin.getSenha())){
            System.out.println("Login realizado!");
            System.out.println("user: " + admin.getUsuario());
            System.out.println("senha: " + admin.getSenha());
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
