import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Configs.TextFieldFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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

    private boolean verificarNumeroTelefone(TextField boxTelefone){
        int contador = 0;
        for(int i = 0;i<14;i++){
            if(boxTelefone.getText().charAt(i) == ' ' || boxTelefone.getText().charAt(i) == '-' || boxTelefone.getText().charAt(i) == '(' || boxTelefone.getText().charAt(i) == ')'){
            }else{
                contador++;
            }
        }
        if(contador == 11){
            return true;
        }else{
            return false;
        }
    }

    @FXML
    private void cadastrarCliente(ActionEvent event){
        String nome = boxNome.getText();
        String tipo = verificarTipoCliente();
        String telefone = boxTelefone.getText();


        if(b==false){
            if (nome.isEmpty() || telefone.isEmpty() || tipo.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Preencha todos os campos!");
                alert.showAndWait();
            }else{
                if(verificarNumeroTelefone(boxTelefone)){
                    try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Pessoa (pes_nome,pes_telefone,pes_tipo) VALUES(?,?,?)")){
                        preparedStatement.setString(1, nome);
                        preparedStatement.setString(2, telefone);
                        preparedStatement.setString(3, tipo);
        
                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setContentText("Erro ao cadastrar! Provavelmente numero de telefone ja cadastrado");
                        alert.showAndWait();
                    }
                }else{
                    Alert alertTel = new Alert(Alert.AlertType.ERROR);
                    alertTel.setHeaderText(null);
                    alertTel.setContentText("Número de telefone deve conter 11 digitos!");
                    alertTel.showAndWait();
                }   
            }
        }else if(b==true){
            boolean confirmarEdicao = true;

            if(nome.isEmpty()){
                nome = c.getNome();
            }
            if(telefone.isEmpty()){
                telefone = c.getTelefone();
            }else{
                confirmarEdicao = verificarNumeroTelefone(boxTelefone);
            }
            if(tipo.isEmpty()){
                tipo = c.getTipo();
            }

            if(confirmarEdicao){
                try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Pessoa SET "
                + "`pes_nome`=?,"
                + "`pes_telefone`=?,"
                + "`pes_tipo`= ? WHERE pes_id = '"+c.getId()+"'")){
                    preparedStatement.setString(1, nome);
                    preparedStatement.setString(2, telefone);
                    preparedStatement.setString(3, tipo);

                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("ERRO!!");
                }
            }else{
                Alert alertTel = new Alert(Alert.AlertType.ERROR);
                alertTel.setHeaderText(null);
                alertTel.setContentText("Número de telefone deve conter 11 digitos!");
                alertTel.showAndWait();
            }   

        }
        clean(event);
        fecharJanela();



    }

    @FXML
    private void formatarNumCel(KeyEvent event){
        TextFieldFormatter tff = new TextFieldFormatter();
        tff.setMask("(##)#####-####");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(boxTelefone);
        tff.formatter();
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
