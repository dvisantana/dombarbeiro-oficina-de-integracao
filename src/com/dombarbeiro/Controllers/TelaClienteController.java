package src.com.dombarbeiro.Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.imageio.ImageTranscoder;
import javax.swing.JOptionPane;
import javax.swing.text.Caret;

import src.com.dombarbeiro.App;

import src.com.dombarbeiro.Configs.ConexaoBD;
import src.com.dombarbeiro.Models.Pessoa;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class TelaClienteController implements Initializable{

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
    
    @FXML
    private Button botaoPesquisar;

    @FXML
    private TextField boxPesquisar;

    @FXML
    private ImageView imgAgenda;


    private TelaClienteController telaCadClienteController;

    private static Pessoa userSelec = new Pessoa();
    public static Pessoa getPessoaSelec(){
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
        // imgAgenda.setImage(new Image("icon-agenda.png"));

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
                    if(resultSet.getString(2).toLowerCase().contains((boxPesquisar.getText().toLowerCase()))){
                        ListaPessoa.add(new Pessoa(resultSet.getInt(1),resultSet.getString(2), resultSet.getString(3), resultSet.getString(4)));
                        tabelaPessoa.setItems(ListaPessoa);
                    }
                    // if(boxPesquisar.getText().equalsIgnoreCase(resultSet.getString(2))){
                    //     ListaPessoa.add(new Pessoa(resultSet.getString(2), resultSet.getInt(3), null));
                    //     tabelaPessoa.setItems(ListaPessoa);
                    // }
                }
            }else{
                while(resultSet.next()){
                    ListaPessoa.add(new Pessoa(resultSet.getInt(1),resultSet.getString(2), resultSet.getString(3), resultSet.getString(4)));
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
    }


    @FXML
    private void abrirCadastroPessoa(ActionEvent event){
        editOn = false;
        System.out.println("Oi");
        try {
                Scene sceneTelaCadastroCliente;

                FXMLLoader telaCadastroCliente = new FXMLLoader(getClass().getResource("../Views/telaCadastroCliente.fxml"));
                Parent parentTelaCadastroCliente = telaCadastroCliente.load();
                sceneTelaCadastroCliente = new Scene(parentTelaCadastroCliente);
    
                Stage primaryStage = new Stage();

                primaryStage.initModality(Modality.APPLICATION_MODAL);
                primaryStage.setScene(sceneTelaCadastroCliente);
                primaryStage.initStyle(StageStyle.UTILITY);
                primaryStage.showAndWait();
                carregarTabela(event);
            } catch (Exception e) {
            }
    }

    @FXML
    private void abrirEditarPessoa(ActionEvent event){
        editOn = true;
        if(tabelaPessoaClicked(null) == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Selecione um usuário na tabela para editar");
            alert.showAndWait();
        }else{
            try {
                Scene sceneTelaCadastroCliente;

                FXMLLoader telaCadastroCliente = new FXMLLoader(getClass().getResource("../Views/telaCadastroCliente.fxml"));
                Parent parentTelaCadastroCliente = telaCadastroCliente.load();
                sceneTelaCadastroCliente = new Scene(parentTelaCadastroCliente);
    
                Stage primaryStage = new Stage();

                primaryStage.initModality(Modality.APPLICATION_MODAL);
                primaryStage.setScene(sceneTelaCadastroCliente);
                primaryStage.initStyle(StageStyle.UTILITY);
                primaryStage.showAndWait();
                carregarTabela(event);
            } catch (Exception e) {

            }
        }
            
    }

// Abertura de Telas
    @FXML
    private void sairApp(ActionEvent event) {
        App.csSairApp();
    }

    @FXML
    private void abrirTelaAgenda(ActionEvent event) {
        App.abrirAgenda();
    }

}
