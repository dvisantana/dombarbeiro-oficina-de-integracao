import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.Style;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class TelaCadastroAtendimentoController implements Initializable{

    @FXML
    private Button botaoCadastrar;

    @FXML
    private Button botaoLimpar;

    @FXML
    private ColorPicker colorPickerCor;

    @FXML
    private ComboBox<Pessoa> comboBoxCliente;

    @FXML
    private ComboBox<String> comboBoxHorario;

    @FXML
    private ComboBox<Servico> comboBoxServico;

    @FXML
    private DatePicker datePickerData;

    //Horario
    private ObservableList<String> obsListHorario = FXCollections.observableArrayList("18:00","19:00","20:00","21:00","22:00");

    private Atendimento atend = TelaAgendaController.getAtendSelec();
    private boolean b = TelaAgendaController.getBolEdit();
    private static Color colorTag = new Color(0,0,0,0);
    public static Color getColorTag(){
        return colorTag;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        comboBoxHorario.setItems(obsListHorario);
        comboBoxCliente.setItems(allClientes());
        comboBoxServico.setItems(allServicos());

        if(b){
            datePickerData.setValue(atend.getData().toLocalDate());

            comboBoxHorario.setPromptText(atend.getData().toLocalTime().toString());
            comboBoxServico.setPromptText(atend.getServico().getDesc());

            comboBoxCliente.setValue(atend.getCliente());
            comboBoxCliente.setDisable(true);
            comboBoxCliente.setFocusTraversable(false);
        }

    }

    @FXML
    void cadastrarAtendimento(ActionEvent event) {
        colorTag = colorPickerCor.getValue();
        LocalDate data = datePickerData.getValue();
        String horario = comboBoxHorario.getValue();
        Pessoa cliente = comboBoxCliente.getValue();
        Servico servico = comboBoxServico.getValue();


        if(b){
            //Editar
            if(servico==null){
                servico = atend.getServico();
            }
            if(horario==null){
                horario = atend.getData().toLocalTime().toString();
            }
            try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Atendimento SET "
                + "`atd_data`=?,"
                + "`atd_hora`=?,"
                + "`ser_nome`=? WHERE atd_data =? AND pes_id =?  AND atd_hora =?")){

                preparedStatement.setString(1, data.toString());
                preparedStatement.setString(2, horario+":00");
                preparedStatement.setString(3, servico.getDesc());

                preparedStatement.setString(4, atend.getData().toLocalDate().toString());
                preparedStatement.setInt(5, atend.getCliente().getId());
                preparedStatement.setString(6, atend.getData().toLocalTime().toString());

                preparedStatement.executeUpdate();
                fecharJanela();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Erro ao alterar dados!");
                alert.showAndWait();
            }
        }else{
            //Cadastrar
            if(data == null || horario == null || cliente == null || servico == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Preencha todos os campos!");
                alert.showAndWait();
            }else{
                try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Atendimento VALUES(?,?,?,0,?,'admin')")){
                    preparedStatement.setString(1, data.toString());
                    preparedStatement.setInt(2, cliente.getId());
                    preparedStatement.setString(3, horario+":00");
                    preparedStatement.setString(4, servico.getDesc());

                    preparedStatement.executeUpdate();
                    fecharJanela();
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Erro ao cadastrar!");
                    alert.showAndWait();
                }
            }

        }
    }

    private ObservableList<Servico> allServicos(){
        ObservableList<Servico> obsListServicos = FXCollections.observableArrayList();
        try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Servicos")){
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                obsListServicos.add(new Servico(resultSet.getString(1), resultSet.getDouble(2)));
            }
        }catch (SQLException e) {
            e.printStackTrace();        
        }
        return obsListServicos;
    }

    private ObservableList<Pessoa> allClientes(){
        ObservableList<Pessoa> obsListPessoa = FXCollections.observableArrayList();
        try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Pessoa")){
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                obsListPessoa.add(new Pessoa(resultSet.getInt(1),resultSet.getString(2), resultSet.getString(3), resultSet.getString(4)));
            }
        }catch (SQLException e) {
            e.printStackTrace();        
        }
        return obsListPessoa;
    }

    @FXML
    private void fecharJanela(){
        Stage stage = (Stage) comboBoxCliente.getScene().getWindow();
        stage.close();
    }

    @FXML
    void clean(ActionEvent event) {

    }

}
