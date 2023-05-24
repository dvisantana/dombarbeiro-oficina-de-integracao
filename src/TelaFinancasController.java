import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class TelaFinancasController implements Initializable{

    @FXML
    private TextField boxDescricao;

    @FXML
    private TextField boxValor;

    @FXML
    private ComboBox<?> comboBoxMes;

    @FXML
    private TableView<Financas> tabelaFinancas;

    @FXML
    private TableColumn<Financas, String> dataCol;

    @FXML
    private TableColumn<Financas, String> descricaoCol;

    @FXML
    private TableColumn<Financas, String> valorCol;

    @FXML
    private TextArea textDespesas;

    @FXML
    private TextArea textGanhos;

    @FXML
    private TextArea textLucros;


    private ObservableList<Financas> ListaFinancas = FXCollections.observableArrayList();

    //Assim que abrir a tela fara o que esta dentro dessa funcano "initialize"
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carregarTabela(null);
        calcLucros();
    }
    // ========================================================================


    // ================= FUNCOES =================

    private Double somaGanhos(){

        Double ganho = 0.0;

        try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT fin_quantia FROM Finanças WHERE fin_tipo=0;"))
        {
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                ganho += resultSet.getDouble(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        textGanhos.setText("R$ " + ganho.toString());
        return ganho;
    }

    private void calcLucros(){
        Double lucro = 0.0;
        lucro = somaGanhos()-somaDespesas();
        textLucros.setText("R$ " + lucro.toString());
    }

    private Double somaDespesas(){

        Double despesa = 0.0;

        try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT fin_quantia FROM Finanças WHERE fin_tipo=1;"))
        {
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                despesa += resultSet.getDouble(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        textDespesas.setText("R$ " + despesa.toString());
        return despesa;
    }

    @FXML

    private Button botaoCadastrar;

    @FXML
    private Button botaoEditar;
  
    private void carregarTabela(ActionEvent event){
        ListaFinancas.clear();

        valorCol.setCellValueFactory(new PropertyValueFactory<>("valor"));
        descricaoCol.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT fin_descricao,fin_quantia,fin_data FROM Finanças;"))
        {
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                ListaFinancas.add(new Financas(resultSet.getString(1), Double.parseDouble(resultSet.getString(2)), resultSet.getString(3)));
                tabelaFinancas.setItems(ListaFinancas);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        calcLucros();
    }

    @FXML
    private void addDespesa(ActionEvent event) {
        String descricao = boxDescricao.getText();
        Double valor = 0.0;
        // Double valor = Double.parseDouble(boxValor.getText());
        
        if (descricao.isEmpty() || boxValor.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos!");
            alert.showAndWait();
        }else{
            valor = Double.parseDouble(boxValor.getText());
            try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Finanças (fin_tipo,fin_quantia,fin_descricao)  VALUES(?,?,?);")){
                preparedStatement.setInt(1, 1);
                preparedStatement.setDouble(2, valor);
                preparedStatement.setString(3, descricao);
    
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Erro ao cadastrar! Provavelmente já existe um usuário com esse login cadastrado");
                alert.showAndWait();
                System.out.println("Erro ao cadastrar! Provavelmente já existe um usuário com esse login cadastrado");
            }
        }
        clean();
        carregarTabela(null);
    }

    @FXML
    private void addLucro(ActionEvent event) {
        String descricao = boxDescricao.getText();
        // Double valor = Double.parseDouble(boxValor.getText());
        Double valor = 0.0;

        if (descricao.isEmpty() || boxValor.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos!");
            alert.showAndWait();
        }else{
            valor = Double.parseDouble(boxValor.getText());
            try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Finanças (fin_tipo,fin_quantia,fin_descricao)  VALUES(?,?,?);")){
                preparedStatement.setInt(1, 0);
                preparedStatement.setDouble(2, valor);
                preparedStatement.setString(3, descricao);
    
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Erro ao cadastrar! Provavelmente já existe um usuário com esse login cadastrado");
                alert.showAndWait();
                System.out.println("Erro ao cadastrar! Provavelmente já existe um usuário com esse login cadastrado");
            }
        }
        clean();
        carregarTabela(null);
    }

    private void clean(){
        boxDescricao.setText("");
        boxValor.setText("");
    }

    // ================= FIM FUNCOES =================


    //Abertura de Telas
    @FXML
    private Button botaoRemover;

    @FXML
    private TextField boxServicos;

    @FXML
    private TextField boxValor;

    @FXML
    private TableView<?> tabelaServico;

    @FXML
    void abrirTelaAdmin(MouseEvent event) {

    }

    @FXML
    void abrirTelaAdminUsuario(ActionEvent event) {

    }

    @FXML
    void sairApp(ActionEvent event) {

    }



    @FXML
    private void abrirTelaServicos(ActionEvent event) {
        App.admAbrirServicos();
    }


}


