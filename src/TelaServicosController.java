import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class TelaServicosController implements Initializable {

    @FXML
    private Button botaoCadastrar;

    @FXML
    private Button botaoEditar;

    @FXML
    private Button botaoRemover;

    @FXML
    private TextField boxDesc;

    @FXML
    private TextField boxValor;

    @FXML
    private TableColumn<Servico, Double> tableValor;

    @FXML
    private TableView<Servico> tabelaServico;

    @FXML
    private TableColumn<Servico, String> tableDesc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    private ObservableList<Servico> ListaServicos = FXCollections.observableArrayList();
    private static Servico servicoSelec = new Servico(null,null);
   
    
    
    // private TableColumn<Servico, String> desc;
    // private TableColumn<Servico, Double> valor;

    public Servico tabelaServicoClicked(MouseEvent e){
        int i = tabelaServico.getSelectionModel().getSelectedIndex();
        try {
            Servico servico = (Servico)tabelaServico.getItems().get(i);
            servicoSelec = servico;
            return(servico);
        } catch (Exception exc) {
            System.out.println("Nenhum Servico Selecionado na Tabela");
        }
        return(null);
        // Servico servico = (Servico)tabelaServico.getItems().get(i);
        // servicoSelec = servico;
        // return(servico);
    }
    private boolean procurarOn;
    public void carregarTabela(ActionEvent event){
        ListaServicos.clear();
        Servico s = new Servico(null, null);
        tableDesc.setCellValueFactory(new PropertyValueFactory<>("servico"));
        tableValor.setCellValueFactory(new PropertyValueFactory<>("valor"));

        try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Servico"))
        {
            ResultSet resultSet = preparedStatement.executeQuery();


            if(procurarOn){
                while(resultSet.next()){
                    ListaServicos.add(new Servico(resultSet.getString(1), resultSet.getDouble(2)));
                    tabelaServico.setItems(ListaServicos);
                }
            }

            // while(resultSet.next()){
            //     ListaServico.add(new Servico(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
            //     tabelaServico.setItems(ListaServico);
            // }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void abrirTelaAdmin(MouseEvent event) {

    }

    @FXML
    void abrirTelaAdminServico(ActionEvent event) {

    }

    @FXML
    void sairApp(ActionEvent event) {

    }
    @FXML
    private void abrirTelaServicos(ActionEvent event) {
        App.admAbrirServicos();
    }

   
    
   

    @FXML
    void cadastrarServico(ActionEvent event){
        
       
        String servico = boxDesc.getText();
        //verificar
        Double valor = Double.valueOf(boxValor.getText());

        if (servico.isEmpty() || valor.isNaN() ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos!");
            alert.showAndWait();
        } else {
           
                try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Servico VALUES(?,?,?,?)")){
                    preparedStatement.setString(1, servico);
                    preparedStatement.setDouble(2, valor);
    
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Erro ao cadastrar! Provavelmente já existe esse serviço cadastrado");
                    alert.showAndWait();
                    System.out.println("Erro ao cadastrar! Provavelmente já existe esse serviço cadastrado");
                }
            
                //EDITAR O SERVICO
                // try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Servico SET "
                // + "`ser_nome`=?,"
                // + "`ser_preco`=?"
                // + "WHERE adm_login = '"+s.getServico()+"'")){
                //     preparedStatement.setString(1, s.getServico());
                //     preparedStatement.setString(2, valor);
    
                //     preparedStatement.executeUpdate();
                // } catch (SQLException e) {
                //     // e.printStackTrace();
                //     System.out.println("ERRO AQUI TRUE");
                // }
            }   
            clean(event);
    }
    
    @FXML
    private void clean(ActionEvent event) {
        boxDesc.setText(null);
        boxValor.setText(null);;
    }

 @FXML 
    public void removerServico(){
        Servico s = tabelaServicoClicked(null);

        int confirm = JOptionPane.showConfirmDialog(null, "Deseja realmente remover", "Mensagem de Confirmação", JOptionPane.YES_NO_OPTION);
           
            if(confirm == 0){
                try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Servico WHERE adm_login=?")){
                    preparedStatement.setString(1, s.getDesc());
                    ListaServicos.remove(s);
                    preparedStatement.executeUpdate();
                    carregarTabela(null);
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Erro ao Remover ","Erro",JOptionPane.ERROR_MESSAGE);
                }

            } 
            else{
                JOptionPane.showMessageDialog(null, "Operação Cancelada!","Aviso",JOptionPane.WARNING_MESSAGE);
            }
        
    }

}
