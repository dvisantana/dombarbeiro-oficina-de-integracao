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

        ObservableList<String> list = FXCollections.observableArrayList("Cliente","Barbeiro");
        comboBoxTipo.setItems(list);
        
        if(b){
            boxNome.setPromptText(c.getNome());
            boxNome.setFocusTraversable(false); 

            boxTelefone.setPromptText(String.valueOf(c.getTelefone()));
            boxTelefone.setFocusTraversable(false); 
            
            comboBoxTipo.setPromptText(c.getTipo());
            comboBoxTipo.setFocusTraversable(false); 
        }
    }

    private String verificarTipoCliente(){
        try {
            if(comboBoxTipo.getValue().equals("Cliente")){
                return "Cliente";
            }else if(comboBoxTipo.getValue().equals("Barbeiro")){
                return "Barbeiro";
            }else{
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    @FXML
    void cadastrarCliente(ActionEvent event){
        String nome = boxNome.getText();
        String tipo = verificarTipoCliente();
        int telefone = 0;

        try {
            telefone = Integer.parseInt(boxTelefone.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Número de telefone inválido!");
            alert.showAndWait();
        }

        if (nome.isEmpty() || telefone == 0 || tipo.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos!");
            alert.showAndWait();
        } else {
            if(b==false){
                try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Pessoa (pes_nome,pes_telefone,pes_tipo) VALUES(?,?,?)")){
                    preparedStatement.setString(1, nome);
                    preparedStatement.setInt(2, telefone);
                    preparedStatement.setString(3, tipo);
    
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Erro ao cadastrar!");
                    alert.showAndWait();
                }
            }else if(b==true){
                try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Pessoa SET "
                + "`pes_nome`=?,"
                + "`pes_telefone`=?,"
                + "`pes_tipo`= ? WHERE adm_nome = '"+c.getId()+"'")){
                    preparedStatement.setString(1, nome);
                    preparedStatement.setInt(2, telefone);
                    preparedStatement.setString(3, tipo);
    
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("ERRO!!");
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
        boxNome.setText("");
        boxTelefone.setText("");
        comboBoxTipo.setValue("");
    }


}
