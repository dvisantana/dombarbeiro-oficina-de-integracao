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
import javafx.scene.control.TableCell;
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


public class TelaAdminUsuarioController implements Initializable{

    @FXML
    private ImageView iconSenha;

    @FXML
    private Button botaoCadastrarUsuario;

    @FXML
    private Button botaoEditarUsuario;

    @FXML
    private Button botaoPesquisar;

    @FXML
    private Button botaoRemoverUsuario;

    @FXML
    private Button botaoSair;

    @FXML
    private TextField boxPesquisar;

    @FXML
    private TableView<Usuario> tabelaUsuario;

    @FXML
    private TableColumn<Usuario, String> nomeCol;

    @FXML
    private TableColumn<Usuario, String> senhaCol;

    @FXML
    private TableColumn<Usuario, String> usuarioCol;
    
    @FXML
    private TableColumn<Usuario, String> tipoCol;

    private static Usuario userSelec = new Usuario();
    
    public static Usuario getUserSelec(){
        return userSelec;
    }

    private static boolean editOn;
    public static boolean getBolEdit(){
        return editOn;
    }

    private boolean procurarOn;

    private ObservableList<Usuario> ListaUsuarios = FXCollections.observableArrayList();

    private boolean mostrarSenha = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carregarTabela(null);
    }
    

    @FXML
    private void carregarTabela(ActionEvent event){
        ListaUsuarios.clear();

        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        usuarioCol.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        senhaCol.setCellValueFactory(new PropertyValueFactory<>("senha"));
        tipoCol.setCellValueFactory(new PropertyValueFactory<>("tipoStr"));


        try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Usuario"))
        {
            ResultSet resultSet = preparedStatement.executeQuery();

            if(procurarOn){
                while(resultSet.next()){
                    String tipoString = "";
                    if(resultSet.getInt(4) == 0){
                        tipoString = "Administrador";
                    }else if(resultSet.getInt(4) == 1){
                        tipoString = "Secretário";
                    }
                    // if(boxPesquisar.getText().equalsIgnoreCase(resultSet.getString(1))){
                    //     ListaUsuarios.add(new Usuario(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4)));
                    //     tabelaUsuario.setItems(ListaUsuarios);
                    // }
                    if(resultSet.getString(1).toLowerCase().contains(boxPesquisar.getText().toLowerCase())){
                        ListaUsuarios.add(new Usuario(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4)));
                        tabelaUsuario.setItems(ListaUsuarios);
                    }
                }
            }else{
                while(resultSet.next()){
                    String tipoString = "";
                    if(resultSet.getInt(4) == 0){
                        tipoString = "Administrador";
                    }else if(resultSet.getInt(4) == 1){
                        tipoString = "Secretário";
                    }
                    ListaUsuarios.add(new Usuario(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4)));
                    tabelaUsuario.setItems(ListaUsuarios);
                }
            }            

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(mostrarSenha){
            Image image = new Image("/imgs/icon-password-off.png");
            iconSenha.setImage(image);
            senhaCol.setCellFactory(param -> new PasswordLabelCell(true));
        }else{
            Image image = new Image("/imgs/icon-password.png");
            iconSenha.setImage(image);
            senhaCol.setCellFactory(param -> new PasswordLabelCell(false));
        }

    }

    @FXML
    private void mostrarSenha(){
        if(!mostrarSenha){
            try {
                Scene sceneTelaAutenticar;
    
                FXMLLoader telaAutenticar = new FXMLLoader(getClass().getResource("telaAutenticar.fxml"));
                Parent parentTelaAutenticar = telaAutenticar.load();
                TelaAutenticarController telaAutenticarController = telaAutenticar.getController();
                sceneTelaAutenticar = new Scene(parentTelaAutenticar);
    
                Stage primaryStage = new Stage();
    
                primaryStage.initModality(Modality.APPLICATION_MODAL);
                primaryStage.setScene(sceneTelaAutenticar);
                primaryStage.initStyle(StageStyle.UTILITY);
                primaryStage.showAndWait();
    
                if(telaAutenticarController.getIsAutenticado()){
                    if(mostrarSenha){
                        mostrarSenha = false;
                        System.out.println("FALSE");
                    }else{
                        mostrarSenha = true;
                        System.out.println("TRUE");
                    }
                }

            } catch (Exception e) {
                System.out.println("ERRO! Não foi possivel abrir a tela Autenticar!");
            }
        }else{
            mostrarSenha = false;
        }
        
        carregarTabela(null);
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
    public Usuario tabelaUsuarioClicked(MouseEvent e){
        int i = tabelaUsuario.getSelectionModel().getSelectedIndex();
        try {
            Usuario user = (Usuario)tabelaUsuario.getItems().get(i);
            userSelec = user;
            return(user);
        } catch (Exception exc) {
            System.out.println("Nenhum Usuario Selecionado na Tabela");
        }
        return(null);
        // Usuario user = (Usuario)tabelaUsuario.getItems().get(i);
        // userSelec = user;
        // return(user);
    }

    @FXML 
    public void removerUsuario(){
        Usuario u = tabelaUsuarioClicked(null);
        if(tabelaUsuarioClicked(null) == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Selecione um usuário na tabela para editar");
            alert.showAndWait();
        }else{
            int confirm = JOptionPane.showConfirmDialog(null, "Deseja realmente remover", "Mensagem de Confirmação", JOptionPane.YES_NO_OPTION);
           
            if(confirm == 0){
                try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Usuario WHERE adm_login=?")){
                    preparedStatement.setString(1, u.getUsuario());
                    ListaUsuarios.remove(u);
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
    }

    @FXML
    void abrirCadastroUsuario(ActionEvent event){
        editOn = false;
        try {
                Scene sceneTelaCadastroUsuario;

                FXMLLoader telaCadastroUsuario = new FXMLLoader(getClass().getResource("telaCadastroUsuario.fxml"));
                Parent parentTelaCadastroUsuario = telaCadastroUsuario.load();
                sceneTelaCadastroUsuario = new Scene(parentTelaCadastroUsuario);
    
                Stage primaryStage = new Stage();

                primaryStage.initModality(Modality.APPLICATION_MODAL);
                primaryStage.setScene(sceneTelaCadastroUsuario);
                primaryStage.initStyle(StageStyle.UTILITY);
                primaryStage.showAndWait();
                carregarTabela(event);
            } catch (Exception e) {
    
            }
    }

    @FXML
    void abrirEditarUsuario(ActionEvent event){
        editOn = true;
        if(tabelaUsuarioClicked(null) == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Selecione um usuário na tabela para editar");
            alert.showAndWait();
        }else{
            try {
                Scene sceneTelaCadastroUsuario;

                FXMLLoader telaCadastroUsuario = new FXMLLoader(getClass().getResource("telaCadastroUsuario.fxml"));
                Parent parentTelaCadastroUsuario = telaCadastroUsuario.load();
                sceneTelaCadastroUsuario = new Scene(parentTelaCadastroUsuario);
    
                Stage primaryStage = new Stage();

                primaryStage.initModality(Modality.APPLICATION_MODAL);
                primaryStage.setScene(sceneTelaCadastroUsuario);
                primaryStage.initStyle(StageStyle.UTILITY);
                primaryStage.showAndWait();
                carregarTabela(event);
            } catch (Exception e) {

            }
        }
            
    }

    //Abertura de Telas
    @FXML
    private void abrirTelaAdmin(MouseEvent event) {
        mostrarSenha = false;
        carregarTabela(null);
        App.abrirAdmin();
    }
    
    @FXML
    private void abrirTelaServicos(ActionEvent event) {
        mostrarSenha = false;
        carregarTabela(null);
        App.admAbrirServicos();
    }

    @FXML
    private void abrirTelaFinancas(ActionEvent event){
        mostrarSenha = false;
        carregarTabela(null);
        App.adminAbrirFinancas();
    }

    @FXML
    private void sairApp(ActionEvent event) {
        mostrarSenha = false;
        carregarTabela(null);
        App.csSairApp();
    }

}
