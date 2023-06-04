import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class TelaFinancasController implements Initializable{

    @FXML
    private TextField boxValor;

    @FXML
    private TextField boxDescricao;

    @FXML
    private Button botaoCadastrar;

    @FXML
    private Button botaoEditar;

    @FXML
    private ComboBox<String> comboBoxMes;

    @FXML
    private TableView<Financas> tabelaFinancas;

    @FXML
    private TableColumn<Financas, String> dataCol;

    @FXML
    private TableColumn<Financas, String> descricaoCol;

    @FXML
    private TableColumn<Financas, Double> valorCol;

    @FXML
    private TextArea textDespesas;

    @FXML
    private TextArea textGanhos;

    @FXML
    private TextArea textLucros;    

    private ObservableList<Financas> ListaFinancas = FXCollections.observableArrayList();

    private boolean filtroData = false;

    //Assim que abrir a tela fara o que esta dentro dessa funcano "initialize"
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList("Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro");
        comboBoxMes.setItems(list);

        carregarTabela(null);
        calcLucros();
    }
    // ========================================================================


    // ================= FUNCOES =================

    private Double somaGanhos(){

        Double ganho = 0.0;

        //Com filtro de data:
        if(filtroData){
            try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT fin_quantia FROM Finanças WHERE MONTH(fin_data) = ? AND fin_tipo = 0;"))
            {
                int mes = mesFiltroSelecionado();
                preparedStatement.setInt(1,mes);
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
        // Se não precisar filtrar: (normal)
        else{
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

    }

    private void calcLucros(){
        Double lucro = 0.0;
        lucro = somaGanhos()-somaDespesas();
        if(lucro<0){
            textLucros.setStyle("-fx-text-fill: red;");
        }else if(lucro>0){
            textLucros.setStyle("-fx-text-fill: green;");
        }else{
            textLucros.setStyle("-fx-text-fill: black;");
        }
        textLucros.setText("R$ " + lucro.toString());
    }

    private Double somaDespesas(){

        Double despesa = 0.0;

        //Com filtro de data:
        if(filtroData){
            try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT fin_quantia FROM Finanças WHERE MONTH(fin_data) = ? AND fin_tipo = 1;"))
            {
                int mes = mesFiltroSelecionado();
                preparedStatement.setInt(1, mes);
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
        // Se não precisar filtrar: (normal)
        else{
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
    }
  
    private void carregarTabela(ActionEvent event){
        ListaFinancas.clear();

        valorCol.setCellValueFactory(new PropertyValueFactory<>("valor"));
        descricaoCol.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        dataCol.setCellValueFactory(new PropertyValueFactory<>("data"));

        //Com filtro de data:
        if(filtroData){
            int mes = mesFiltroSelecionado();
            try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT fin_descricao,fin_quantia,fin_data,fin_tipo FROM Finanças WHERE MONTH(fin_data) = ?;"))
            {
                preparedStatement.setInt(1, mes);
                ResultSet resultSet = preparedStatement.executeQuery();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
                while(resultSet.next()){
                    ListaFinancas.add(new Financas(resultSet.getString(1), Double.parseDouble(resultSet.getString(2)), LocalDateTime.parse(resultSet.getString(3), formatter), resultSet.getInt(4)));
                    tabelaFinancas.setItems(ListaFinancas);
                }
    
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // Se não precisar filtrar: (normal)
        else{
            try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT fin_descricao,fin_quantia,fin_data,fin_tipo FROM Finanças;"))
            {
                ResultSet resultSet = preparedStatement.executeQuery();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    
                while(resultSet.next()){
                    ListaFinancas.add(new Financas(resultSet.getString(1), Double.parseDouble(resultSet.getString(2)), LocalDateTime.parse(resultSet.getString(3), formatter), resultSet.getInt(4)));
                    tabelaFinancas.setItems(ListaFinancas);
                }
    
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //MUDAR COR DE UMA CELULA DA COLUNA
        valorCol.setCellFactory(col -> new TableCell<>() {
            @Override 
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item,  empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if(tabelaFinancas.getItems().get(getIndex()).getTipo() == 1){
                        setText(item.toString());
                        setStyle("-fx-text-fill: red");
                    }else{
                        setText(item.toString());
                        setStyle("-fx-text-fill: green");
                    }
                }
            }
        });

        calcLucros();
    }

    @FXML
    private void addDespesa(ActionEvent event){
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
    private void addLucro(ActionEvent event){
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

    @FXML
    private void filtrarMesFinancas(ActionEvent event){
        if(comboBoxMes.valueProperty().getValue() == null){
            filtroData = false;
        }else{
            filtroData = true;
        }
        carregarTabela(null);
        System.out.println(mesFiltroSelecionado());
    }

    @FXML
    private void resetarFiltroMesFinancas(ActionEvent event){
        filtroData = false;
        comboBoxMes.valueProperty().set(null);
        carregarTabela(null);
        System.out.println(mesFiltroSelecionado());
    }

    private void clean(){
        boxDescricao.setText("");
        boxValor.setText("");
    }

    private int mesFiltroSelecionado(){
        filtroData = true;
        try {
            switch (comboBoxMes.getValue()){
                case "Janeiro":
                    return 1;
                case "Fevereiro":
                    return 2;
                case "Março":
                    return 3;
                    case "Abril":
                    return 4;
                case "Maio":
                    return 5;
                case "Junho":
                    return 6;
                case "Julho":
                    return 7;
                case "Agosto":
                    return 8;
                case "Setembro":
                    return 9;
                    case "Outubro":
                    return 10;
                case "Novembro":
                    return 11;
                case "Dezembro":
                    return 12;
                default:
                    return 0;                   
            }
        } catch (Exception e) {
            filtroData = false;
            return 0;
        }
        
    }
    // ================= FIM FUNCOES =================


    //Abertura de Telas
    // @FXML
    // private Button botaoRemover;

    // @FXML
    // private TextField boxServicos;

    // @FXML
    // private TableView<?> tabelaServico;

    @FXML
    private void abrirTelaAdmin(MouseEvent event) {
        App.abrirAdmin();
    }

    @FXML
    private void abrirTelaAdminUsuario(ActionEvent event) {
        App.adminAbrirUsuarios();
    }

    @FXML
    private void sairApp(ActionEvent event) {
        App.csSairApp();
    }

    @FXML
    private void abrirTelaServicos(ActionEvent event) {
        App.admAbrirServicos();
    }


}


