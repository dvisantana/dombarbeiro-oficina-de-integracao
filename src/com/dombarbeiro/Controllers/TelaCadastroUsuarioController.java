package src.com.dombarbeiro.Controllers;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import src.com.dombarbeiro.App;

import src.com.dombarbeiro.Configs.ConexaoBD;
import src.com.dombarbeiro.Models.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TelaCadastroUsuarioController implements Initializable {

    @FXML
    private Button botaoCadastrar;

    @FXML
    private Button botaoLimpar;

    @FXML
    private TextField boxNome;

    @FXML
    private TextField boxSenha;

    @FXML
    private TextField boxUser;

    @FXML
    private ComboBox<String> comboBoxTipo;

    Usuario u = TelaAdminUsuarioController.getUserSelec();
    boolean b = TelaAdminUsuarioController.getBolEdit();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<String> list = FXCollections.observableArrayList("0 - Admin","1 - Secretário");
        comboBoxTipo.setItems(list);
        
        System.out.println("Inicializar: "+ b);
        if(b){
            boxNome.setPromptText(u.getNome());
            boxNome.setFocusTraversable(false); 
    
            boxUser.setText(u.getUsuario());
            boxUser.setEditable(false);
            boxUser.setFocusTraversable(false); 
    
            boxSenha.setPromptText(u.getSenha());
            boxSenha.setFocusTraversable(false); 
            
            // comboBoxTipo.setPromptText(u.getTipo());
            // comboBoxTipo.setFocusTraversable(false); 
        }
    }

    @FXML
    void cadastrarUsuario(ActionEvent event){
        String nome = boxNome.getText();
        String usuario = boxUser.getText();
        String senha = boxSenha.getText();
        int tipo = 0;
        
        try {
            if(comboBoxTipo.getValue().equals("0 - Admin")){
                tipo = 0;
            }else if(comboBoxTipo.getValue().equals("1 - Secretário")){
                tipo = 1;
            }else{
                tipo = 3;
            }
        } catch (Exception e) {
            tipo = 3;
            System.out.println("Erro");
        }

        if(b==false){
            if (nome.isEmpty() || usuario.isEmpty() || senha.isEmpty() || tipo == 3) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Preencha todos os campos!");
                alert.showAndWait();
            }else{
                try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Usuario VALUES(?,?,?,?)")){
                    preparedStatement.setString(1, nome);
                    preparedStatement.setString(2, usuario);
                    preparedStatement.setString(3, senha);
                    preparedStatement.setInt(4, tipo);
    
                    preparedStatement.executeUpdate();
                    clean(event);
                    fecharJanela();
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Erro ao cadastrar! Provavelmente já existe um usuário com esse login cadastrado");
                    alert.showAndWait();
                    System.out.println("Erro ao cadastrar! Provavelmente já existe um usuário com esse login cadastrado");
                }
            }
        }else if(b==true){
            if (nome.isEmpty() && senha.isEmpty() && tipo == 3) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Preencha os campos que deseja alterar!");
                alert.showAndWait();
            }else{
                if(nome.isEmpty()){
                    nome = u.getNome();
                }
                if(senha.isEmpty()){
                    senha = u.getSenha();
                }
                if(tipo == 3){
                    tipo = u.getTipo();
                }
    
                try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Usuario SET "
                + "`adm_nome`=?,"
                + "`adm_login`=?,"
                + "`adm_senha`=?,"
                + "`adm_tipo`= ? WHERE adm_login = '"+u.getUsuario()+"'")){
                    preparedStatement.setString(1, nome);
                    preparedStatement.setString(2, u.getUsuario());
                    preparedStatement.setString(3, senha);
                    preparedStatement.setInt(4, tipo);
    
                    preparedStatement.executeUpdate();
                    clean(event);
                    fecharJanela();
                } catch (SQLException e) {
                    // e.printStackTrace();
                    System.out.println("ERRO AQUI");
                }
            }
        }
    }


    @FXML
    private void fecharJanela(){
        Stage stage = (Stage) boxNome.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clean(ActionEvent event) {
        boxNome.setText("");
        if(b==false){
            boxUser.setText("");
        }
        boxSenha.setText("");
        comboBoxTipo.setValue("");
    }


    @FXML
    private void abrirTelaServicos(ActionEvent event) {
        App.admAbrirServicos();
    }


}
