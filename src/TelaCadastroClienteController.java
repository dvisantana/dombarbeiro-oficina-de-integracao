import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

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

public class TelaCadastroClienteController implements Initializable {

    @FXML
    private Button botaoCadastrar;

    @FXML
    private Button botaoLimpar;

    @FXML
    private TextField boxNome;

    @FXML
    private TextField boxTelefone;

    
    @FXML
    private ComboBox<String> comboBoxTipo;

    Pessoa c = TelaClienteController.getPessoaSelec();
    boolean b = TelaClienteController.getBolEdit();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

       
        
        System.out.println("Inicializar: "+ b);
        if(b){
            boxNome.setPromptText(c.getNome());
            boxNome.setFocusTraversable(false); 
    
            /////VERIFIAR ISSO
            boxTelefone.setPromptText("Cliente");
            boxTelefone.setFocusTraversable(false); 
            
            // comboBoxTipo.setPromptText(u.getTipo());
            // comboBoxTipo.setFocusTraversable(false); 
        }
    }

    @FXML
    void cadastrarCliente(ActionEvent event){
        String nome = boxNome.getText();
        int telefone = Integer. parseInt(boxTelefone. getText());
        String tipo = "Cliente";

        int type = 0;

        // if(comboBoxTipo.getValue().equals("0 - Admin")){
        //     tipo = 0;
        // }else if(comboBoxTipo.getValue().equals("1 - Usuário")){
        //     tipo = 1;
        // }else{
        //     System.out.println("senha");
        // }
        
      

        if (nome.isEmpty() || (telefone != 0)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos!");
            alert.showAndWait();
        } else {
            if(b==false){
                try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Pessoa VALUES(?,?,?)")){
                    preparedStatement.setString(1, nome);
                    preparedStatement.setInt(2, telefone);
                    preparedStatement.setString(3, tipo);
    
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Erro ao cadastrar! Provavelmente já existe um usuário com esse login cadastrado");
                    alert.showAndWait();
                    System.out.println("Erro ao cadastrar! Provavelmente já existe um usuário com esse login cadastrado");
                }
            }else if(b==true){
                try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Pessoa SET "
                + "`adm_nome`=?,"
                + "`adm_telefone`=?,"
                + "`adm_tipo`= ? WHERE adm_nome = '"+c.getNome()+"'")){
                    preparedStatement.setString(1, nome);
                    preparedStatement.setInt(2, telefone);
                    preparedStatement.setString(3, tipo);
    
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    // e.printStackTrace();
                    System.out.println("ERRO AQUI TRUE");
                }
            }
            
            clean(event);
            fecharJanela();
        }

    }


    @FXML
    private void fecharJanela(){
        Stage stage = (Stage) boxNome.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clean(ActionEvent event) {
        boxNome.setText(null);
        boxTelefone.setText(null);
    }


    @FXML
    private void abrirTelaServicos(ActionEvent event) {
        App.admAbrirServicos();
    }


}
