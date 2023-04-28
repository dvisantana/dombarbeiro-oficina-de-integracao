import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.imageio.ImageTranscoder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class TelaAdminUsuarioController implements Initializable{

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carregarTabela(null);
    }
    

    public void carregarTabela(ActionEvent event){
        ObservableList<Usuario> ListaUsuarios = FXCollections.observableArrayList();

        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        usuarioCol.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        senhaCol.setCellValueFactory(new PropertyValueFactory<>("senha"));

        try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Usuario"))
        {
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                ListaUsuarios.add(new Usuario(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
                tabelaUsuario.setItems(ListaUsuarios);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirTelaAdmin(){
        App.abrirAdmin();
    }

    @FXML
    public void sairApp(ActionEvent event) {
        App.csSairApp();
    }

}
