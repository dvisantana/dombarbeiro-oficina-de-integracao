import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.table.TableColumn;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class TelaServicosController implements Initializable {

    @FXML
    private Button botaoCadastrar;

    @FXML
    private Button botaoEditar;

    @FXML
    private Button botaoRemover;

    @FXML
    private TextField boxServicos;

    @FXML
    private TextField boxValor;

    @FXML
    private TableView<?> tabelaServico;

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

    public void carregarTabela(ActionEvent event){
        ListaServicos.clear();

        desc.setCellValueFactory(new PropertyValueFactory<>("Descrição"));
        valor.setCellValueFactory(new PropertyValueFactory<>("Valor"));

        try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Servico"))
        {
            ResultSet resultSet = preparedStatement.executeQuery();


            if(procurarOn){
                while(resultSet.next()){
                    if(boxPesquisar.getText().equalsIgnoreCase(resultSet.getString(1))){
                        ListaServico.add(new Servico(resultSet.getString(1), resultSet.getString(2)));
                        tabelaServico.setItems(ListaServico);
                    }
                }
            }else{
                while(resultSet.next()){
                    ListaServico.add(new Servico(resultSet.getString(1), resultSet.getString(2)));
                    tabelaServico.setItems(ListaServico);
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
        
        Servico s = servicoSelec;
        String servico = boxservico.getText();
        Double valor = boxvalor.getText();

        int tipo = 0;

        // if(comboBoxTipo.getValue().equals("0 - Admin")){
        //     tipo = 0;
        // }else if(comboBoxTipo.getValue().equals("1 - Usuário")){
        //     tipo = 1;
        // }else{
        //     System.out.println("valor");
        // }
        
        try {
            if(comboBoxTipo.getValue().equals("0 - Admin")){
                tipo = 0;
            }else if(comboBoxTipo.getValue().equals("1 - Usuário")){
                tipo = 1;
            }else{
                tipo = 3;
            }
        } catch (Exception e) {
            tipo = 3;
            System.out.println("Erro");
        }

        if (nome.isEmpty() || servico.isEmpty() || valor.isEmpty() || tipo == 3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos!");
            alert.showAndWait();
        } else {
            if(b==false){
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
            }else if(b==true){
                try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Servico SET "
                + "`adm_nome`=?,"
                + "`adm_login`=?,"
                + "`adm_valor`=?,"
                + "`adm_tipo`= ? WHERE adm_login = '"+s.getServico()+"'")){
                    preparedStatement.setString(1, s.getServico());
                    preparedStatement.setString(2, valor);
    
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
