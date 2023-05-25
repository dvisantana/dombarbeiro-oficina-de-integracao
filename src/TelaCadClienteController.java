import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.imageio.ImageTranscoder;
import javax.swing.JOptionPane;
import javax.swing.text.Caret;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class TelaCadClienteController implements Initializable{

    @FXML
    private Button botaoBuscarPessoa;

    @FXML
    private Button botaoCadastrarPessoa;

    @FXML
    private Button botaoEditarPessoa;

    @FXML
    private Button botaoSair;

    @FXML
    private TableView<Pessoa> tabelaPessoa;

    @FXML
    private TableColumn<Pessoa, String> nomeCol;

    @FXML
    private TableColumn<Pessoa, Integer> telefoneCol;


    private TelaCadClienteController telaCadClienteController;

    private static Pessoa userSelec = new Pessoa(null,null,null);
    
    public static Pessoa getUserSelec(){
        return userSelec;
    }

    private static boolean editOn;
    public static boolean getBolEdit(){
        return editOn;
    }

    private boolean procurarOn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carregarTabela(null);
    }
    
    private ObservableList<Pessoa> ListaPessoa = FXCollections.observableArrayList();

    @FXML
    private void carregarTabela(ActionEvent event){
        ListaPessoa.clear();

        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        telefoneCol.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Pessoa"))
        {
            ResultSet resultSet = preparedStatement.executeQuery();


            if(procurarOn){
                while(resultSet.next()){
                    if(boxPesquisar.getText().equalsIgnoreCase(resultSet.getString(1))){
                        ListaPessoa.add(new Pessoa(resultSet.getString(1), resultSet.getInt(2), null));
                        tabelaPessoa.setItems(ListaPessoa);
                    }
                }
            }else{
                while(resultSet.next()){
                    ListaPessoa.add(new Pessoa(resultSet.getString(1), resultSet.getInt(2), null));
                    tabelaPessoa.setItems(ListaPessoa);
                }
            }

            // while(resultSet.next()){
            //     ListaUsuarios.add(new Usuario(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
            //     tabelaUsuario.setItems(ListaUsuarios);
            // }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void procurarTabela(ActionEvent event){
        if(boxPesquisar.getText().equalsIgnoreCase("")){
            procurarOn = false;
            carregarTabela(event);
        }else{
            procurarOn = true;
            carregarTabela(event);
            boxPesquisar.setText("");
            procurarOn = false;
        }
    }

    @FXML
    public Pessoa tabelaPessoaClicked(MouseEvent e){
        int i = tabelaPessoa.getSelectionModel().getSelectedIndex();
        try {
            Pessoa user = (Pessoa)tabelaPessoa.getItems().get(i);
            userSelec = user;
            return(user);
        } catch (Exception exc) {
            System.out.println("Nenhum Pessoa Selecionado na Tabela");
        }
        return(null);
        // Usuario user = (Usuario)tabelaUsuario.getItems().get(i);
        // userSelec = user;
        // return(user);
    }

    @FXML 
    public void removerPessoa(){
        Pessoa u = tabelaPessoaClicked(null);

        int confirm = JOptionPane.showConfirmDialog(null, "Deseja realmente remover", "Mensagem de Confirmação", JOptionPane.YES_NO_OPTION);
           
            if(confirm == 0){
                try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Pessoa WHERE adm_login=?")){
                    preparedStatement.setString(1, u.getNome());
                    ListaPessoa.remove(u);
                    preparedStatement.executeUpdate();
                    carregarTabela(null);
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Erro ao Remover (CONTATE O ADMINISTRADOR)","Erro",JOptionPane.ERROR_MESSAGE);
                }

            } 
            else{
                JOptionPane.showMessageDialog(null, "Operação Cancelada!","Aviso",JOptionPane.WARNING_MESSAGE);
            }
        
    }

    @FXML
    void abrirCadastroPessoa(ActionEvent event){
        editOn = false;
        try {
                Scene sceneTelaCadastroPessoa;

                FXMLLoader telaCadastroPessoa = new FXMLLoader(getClass().getResource("telaCadastroPessoa.fxml"));
                Parent parentTelaCadastroPessoa = telaCadastroPessoa.load();
                sceneTelaCadastroPessoa = new Scene(parentTelaCadastroPessoa);
    
                Stage primaryStage = new Stage();

                primaryStage.initModality(Modality.APPLICATION_MODAL);
                primaryStage.setScene(sceneTelaCadastroPessoa);
                primaryStage.initStyle(StageStyle.UTILITY);
                primaryStage.showAndWait();
                carregarTabela(event);
            } catch (Exception e) {
    
            }
    }

    @FXML
    void abrirEditarPessoa(ActionEvent event){
        editOn = true;
        if(tabelaPessoaClicked(null) == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Selecione um usuário na tabela para editar");
            alert.showAndWait();
        }else{
            try {
                Scene sceneTelaCadastroPessoa;

                FXMLLoader telaCadastroPessoa = new FXMLLoader(getClass().getResource("telaCadastroPessoa.fxml"));
                Parent parentTelaCadastroPessoa = telaCadastroPessoa.load();
                sceneTelaCadastroPessoa = new Scene(parentTelaCadastroPessoa);
    
                Stage primaryStage = new Stage();

                primaryStage.initModality(Modality.APPLICATION_MODAL);
                primaryStage.setScene(sceneTelaCadastroPessoa);
                primaryStage.initStyle(StageStyle.UTILITY);
                primaryStage.showAndWait();
                carregarTabela(event);
            } catch (Exception e) {

            }
        }
            
    }

    @FXML
    private void abrirTelaAdmin(MouseEvent event) {
        App.abrirAdmin();
    }
    @FXML
    private void abrirTelaServicos(ActionEvent event) {
        App.admAbrirServicos();
    }

    @FXML
    private void abrirTelaFinancas(ActionEvent event){
        App.adminAbrirFinancas();
    }

    @FXML
    private void sairApp(ActionEvent event) {
        App.csSairApp();
    }

}
