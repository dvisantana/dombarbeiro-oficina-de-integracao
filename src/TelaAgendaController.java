import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.beans.EventHandler;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.Period;
import java.util.*;

import javax.swing.JOptionPane;

public class TelaAgendaController implements Initializable {

    LocalDateTime dateFocus;
    LocalDateTime today;

    @FXML
    private CheckBox checkBoxConfirmado;
    
    @FXML
    private Text textCardBarbeiro;

    @FXML
    private Text textCardCliente;

    @FXML
    private Text textCardData;

    @FXML
    private Text textCardHora;

    @FXML
    private Text textCardServico;

    @FXML
    private Text textCardTitulo;


    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;

    @FXML
    private Pane cardInfos;
    
    @FXML
    private Pane cardAgendamentos;
    
    @FXML
    private VBox xBoxAgendamentos;

    private static boolean editOn;
    public static boolean getBolEdit(){
        return editOn;
    }
    private static Atendimento atendSelec = new Atendimento();
    public static Atendimento getAtendSelec(){
        return atendSelec;
    }
    private Color colorTag = TelaCadastroAtendimentoController.getColorTag();
    private TelaFinancasController telaFinancasController = new TelaFinancasController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = LocalDateTime.now();
        today = LocalDateTime.now();
        drawCalendar();
    }

    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }
    

    public void verfificarData(int dia){
        LocalDate data;
        data = dateFocus.withDayOfMonth(dia).toLocalDate();
        System.out.println(data);
    }

    private void drawCalendar(){
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        //List of activities for a given month
        // Map<Integer, List<CalendarActivity>> calendarActivityMap = getCalendarActivitiesMonth(dateFocus);
        List<Atendimento> atendimentosMap = getAtendimentos();

        
        int monthMaxDate = dateFocus.getMonth().maxLength();
        //Check for leap year
        if(dateFocus.getYear() % 4 != 0 && monthMaxDate == 29){
            monthMaxDate = 28;
        }
        int dateOffset = LocalDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1,0,0,0,0).getDayOfWeek().getValue();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.rgb(173, 173, 173));
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth =(calendarWidth/7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight/6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);
                stackPane.setOpacity(0.30);
                
                int calculatedDate = (j+1)+(7*i);
                LocalDateTime dateRectangle = dateFocus;
                if(calculatedDate > dateOffset){
                    int currentDate = calculatedDate - dateOffset;
                    if(currentDate <= monthMaxDate){
                        Text date = new Text(String.valueOf(currentDate));
                        double textTranslationY = - (rectangleHeight / 2) * 0.75;
                        date.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(date);
                        stackPane.setOpacity(1);

                        stackPane.setOnMouseClicked(mouseEvent ->{
                            if(mouseEvent.getClickCount() == 2){
                                verfificarData(currentDate);
                            }
                        });

                        stackPane.setOnMouseEntered(mouseEvent -> {
                            rectangle.setStroke(Color.BLACK);
                        });

                        stackPane.setOnMouseExited(mouseEvent -> {
                            if(today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate){
                                rectangle.setStroke(Color.BLACK);
                            }else{
                                rectangle.setStroke(Color.rgb(173, 173, 173));
                            }
                        });

                        List<Atendimento> calendarActivities = new ArrayList<>();
                        for(int cont = 0;cont<atendimentosMap.size();cont++){
                            if(atendimentosMap.get(cont).getData().getDayOfMonth() == currentDate && atendimentosMap.get(cont).getData().getMonth() == dateFocus.getMonth() && atendimentosMap.get(cont).getData().getYear() == dateFocus.getYear()){
                                calendarActivities.add(atendimentosMap.get(cont));
                            }
                        }

                        if(!calendarActivities.isEmpty()){
                            createCalendarActivity(calendarActivities.size(),calendarActivities, rectangleHeight, rectangleWidth, stackPane);
                        }
                        dateRectangle = dateFocus.withDayOfMonth(currentDate);
                    }
                    //DIA ATUAL
                    if(today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate){
                        rectangle.setStroke(Color.BLACK);
                        rectangle.setFill(Color.LIGHTGRAY);
                        stackPane.setStyle("-fx-font-weight: bold");
                    }
                    if(dateRectangle.isBefore(today) ){
                        stackPane.setOpacity(0.30);
                    }

                }
                calendar.getChildren().add(stackPane);
            }
        }
    }

    private void carregarCardAgendamentos(List<Atendimento> agendamentos){
        xBoxAgendamentos.getChildren().clear();

        VBox calendarActivityBox = new VBox();
        for(int i = 0; i < agendamentos.size(); i++){
            VBox agendamentosBox = new VBox();
            Text text = new Text(agendamentos.get(i).getData().toLocalTime() + " - " + agendamentos.get(i).getCliente().getNome());
            text.setTextAlignment(TextAlignment.LEFT);
            text.setStyle("-fx-font: 20 system;");
            if(verificarConfirmacao()){
                text.setStyle("-fx-strikethrough: true;");
            }
            agendamentosBox.getChildren().add(text);

            int k = i;
            text.setOnMouseClicked(mouseEvent -> {
                if(mouseEvent.getClickCount() == 2){
                    atendSelec = agendamentos.get(k);
                    carregarCardInfos(agendamentos.get(k),text);
                    cardInfos.setVisible(true);
                }
                System.out.println(text.getText());
            });
            agendamentosBox.setStyle("-fx-background-color: #34abeb;-fx-background-radius: 15px; -fx-padding: 10px;");
            agendamentosBox.setPrefHeight(45);
            agendamentosBox.setPrefWidth(330);
            agendamentosBox.setAlignment(Pos.CENTER_LEFT);
            calendarActivityBox.getChildren().add(agendamentosBox);
        }

        // calendarActivityBox.setMaxWidth(xBoxAgendamentos.getWidth() * 0.9);
        // calendarActivityBox.setMaxHeight(xBoxAgendamentos.getHeight() * 0.25);
        // calendarActivityBox.setAlignment(Pos.CENTER);
        calendarActivityBox.setSpacing(10);
        calendarActivityBox.setPrefWidth(330);
        xBoxAgendamentos.getChildren().add(calendarActivityBox);
    }

    private void createCalendarActivity(int teste,List<Atendimento> calendarActivities, double rectangleHeight, double rectangleWidth, StackPane stackPane) {

        VBox calendarActivityBox = new VBox();
        for (int k = 0; k < calendarActivities.size(); k++) {
            atendSelec = calendarActivities.get(k);
            if(k >= 2) {
                Text moreActivities = new Text("...");
                calendarActivityBox.getChildren().add(moreActivities);
                moreActivities.setOnMouseClicked(mouseEvent -> {
                    //On Text clicked
                    if(mouseEvent.getClickCount() == 2){
                        carregarCardAgendamentos(calendarActivities);
                        cardAgendamentos.setVisible(true);
                    }
                    System.out.println(moreActivities.getText());
                });
                break;
            }
            // Text text = new Text(calendarActivities.get(k).getServico() + ", " + calendarActivities.get(k).getData().toLocalTime());
            Text text = new Text(calendarActivities.get(k).getData().toLocalTime() + " - " + calendarActivities.get(k).getCliente().getNome());
            text.setTextAlignment(TextAlignment.CENTER);
            if(verificarConfirmacao()){
                text.setStyle("-fx-strikethrough: true;");
            }
            calendarActivityBox.getChildren().add(text);

            int i = k;

            text.setOnMouseClicked(mouseEvent -> {
                //On Text clicked
                if(mouseEvent.getClickCount() == 2){
                    atendSelec = calendarActivities.get(i);
                    fecharCardAgendamento(mouseEvent);
                    fecharCardInfo(mouseEvent);
                    carregarCardInfos(calendarActivities.get(i),text);
                    cardInfos.setVisible(true);
                }
                System.out.println(text.getText());
            });
        }
        calendarActivityBox.setTranslateY((rectangleHeight / 2)*0.01);
        calendarActivityBox.setMaxWidth(rectangleWidth * 0.9);
        calendarActivityBox.setMaxHeight(rectangleHeight * 0.25);
        calendarActivityBox.setAlignment(Pos.CENTER);
        // calendarActivityBox.setStyle("-fx-background-color:#34abeb; -fx-background-radius: 10px; -fx-padding: 3px; -fx-strikethrough: true;");
        if(verificarConfirmacao()){
            calendarActivityBox.setStyle("-fx-background-color:"+"#469e49"+"; -fx-background-radius: 10px; -fx-padding: 3px;");
        }else{
            calendarActivityBox.setStyle("-fx-background-color:"+"#34abeb"+"; -fx-background-radius: 10px; -fx-padding: 3px;");
        }
        
        stackPane.getChildren().add(calendarActivityBox);
    }

    @FXML
    private void fecharCardInfo(MouseEvent event){
        cardInfos.setVisible(false);
    }
    @FXML
    private void fecharCardAgendamento(MouseEvent event){
        cardAgendamentos.setVisible(false);
    }

    private void carregarCardInfos(Atendimento atendimentoSelec, Text text){
        String dataFormatada = atendimentoSelec.getData().toLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));

        textCardTitulo.setText(text.getText());
        // textCardBarbeiro.setText(Integer.toString(atendimentoSelec.getCliente()));
        textCardCliente.setText(atendimentoSelec.getCliente().nome);
        textCardData.setText(dataFormatada);
        textCardHora.setText(Integer.toString(atendimentoSelec.getData().getHour()) + "h");
        textCardServico.setText(atendimentoSelec.getServico().getDesc());

        if(verificarConfirmacao()){
            checkBoxConfirmado.setSelected(true);
        }else{
            checkBoxConfirmado.setSelected(false);
        }

    }

    private Map<Integer, List<CalendarActivity>> createCalendarMap(List<CalendarActivity> calendarActivities) {
        Map<Integer, List<CalendarActivity>> calendarActivityMap = new HashMap<>();

        for (CalendarActivity activity: calendarActivities) {
            int activityDate = activity.getDate().getDayOfMonth();
            if(!calendarActivityMap.containsKey(activityDate)){
                calendarActivityMap.put(activityDate, List.of(activity));
            } else {
                List<CalendarActivity> OldListByDate = calendarActivityMap.get(activityDate);

                List<CalendarActivity> newList = new ArrayList<>(OldListByDate);
                newList.add(activity);
                calendarActivityMap.put(activityDate, newList);
            }
        }
        return  calendarActivityMap;
    }

    private Map<Integer, List<CalendarActivity>> getCalendarActivitiesMonth(LocalDateTime dateFocus) {
        List<CalendarActivity> calendarActivities = new ArrayList<>();
        int year = dateFocus.getYear();
        int month = dateFocus.getMonth().getValue();

        LocalDateTime time = LocalDateTime.of(year, month, 5, 16,0,0,0);
        calendarActivities.add(new CalendarActivity(time, "Hans", 111111));

        return createCalendarMap(calendarActivities);
    }

    private List<Atendimento> getAtendimentos(){
        List<Atendimento> listaAtendimentos = new ArrayList<>();

        try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Atendimento a,Pessoa p WHERE p.pes_id = a.pes_id;"))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                while(resultSet.next()){
                    String data = resultSet.getString(1)+" ";
                    String hora = resultSet.getString(3);
                    listaAtendimentos.add(new Atendimento(LocalDateTime.parse(data+hora, formatter), resultSet.getInt(2), resultSet.getString(8),resultSet.getString(5)));
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaAtendimentos;
    }
    
    @FXML
    private void abrirTelaCadastroAtendimento(ActionEvent event){
        editOn = false;
        try {
            Scene sceneTelaCadastroAtendimento;

            FXMLLoader telaCadastroAtendimento = new FXMLLoader(getClass().getResource("telaCadastroAtendimento.fxml"));
            Parent parentTelaCadastroAtendimento = telaCadastroAtendimento.load();
            sceneTelaCadastroAtendimento = new Scene(parentTelaCadastroAtendimento);

            Stage primaryStage = new Stage();

            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.setScene(sceneTelaCadastroAtendimento);
            primaryStage.initStyle(StageStyle.UTILITY);
            primaryStage.showAndWait();
            
            fecharCardInfo(null);
            calendar.getChildren().clear();
            drawCalendar();
        } catch (Exception e) {
        }
    }
    
    @FXML
    private void abrirTelaEditarAtendimento(ActionEvent event){
        editOn = true;
        try {
            Scene sceneTelaCadastroAtendimento;

            FXMLLoader telaCadastroAtendimento = new FXMLLoader(getClass().getResource("telaCadastroAtendimento.fxml"));
            Parent parentTelaCadastroAtendimento = telaCadastroAtendimento.load();
            sceneTelaCadastroAtendimento = new Scene(parentTelaCadastroAtendimento);

            Stage primaryStage = new Stage();

            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.setScene(sceneTelaCadastroAtendimento);
            primaryStage.initStyle(StageStyle.UTILITY);
            primaryStage.showAndWait();

            fecharCardInfo(null);
            calendar.getChildren().clear();
            drawCalendar();
        } catch (Exception e) {
        }
    }

    @FXML
    private void removerAtendimento(ActionEvent event){
            int confirm = JOptionPane.showConfirmDialog(null, "Deseja realmente remover", "Mensagem de Confirmação", JOptionPane.YES_NO_OPTION);
            if(confirm == 0){
                try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Atendimento WHERE atd_data=? AND pes_id =? AND atd_hora=?")){
                    preparedStatement.setString(1, atendSelec.getData().toLocalDate().toString());
                    preparedStatement.setInt(2, atendSelec.getCliente().getId());
                    preparedStatement.setString(3, atendSelec.getData().toLocalTime().toString());
                    preparedStatement.executeUpdate();

                    fecharCardInfo(null);
                    calendar.getChildren().clear();
                    drawCalendar(); 
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Erro ao Remover (CONTATE O ADMINISTRADOR)","Erro",JOptionPane.ERROR_MESSAGE);
                }
            } 
            else{
                JOptionPane.showMessageDialog(null, "Operação Cancelada!","Aviso",JOptionPane.WARNING_MESSAGE);
            }
    }

    @FXML
    private void confirmarAtendimento(ActionEvent event){
        if(checkBoxConfirmado.isSelected()){
            if(verificarConfirmacao()){
                System.out.println("Ja esta confirmado!");
            }else{
                try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Atendimento SET "
                    + "`atd_confirmado`=? WHERE atd_data =? AND pes_id =?  AND atd_hora =?")){

                    preparedStatement.setInt(1, 1);

                    preparedStatement.setString(2, atendSelec.getData().toLocalDate().toString());
                    preparedStatement.setInt(3, atendSelec.getCliente().getId());
                    preparedStatement.setString(4, atendSelec.getData().toLocalTime().toString());

                    preparedStatement.executeUpdate();

                    telaFinancasController.addLucro(atendSelec);
                    fecharCardInfo(null);
                    calendar.getChildren().clear();
                    drawCalendar();
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Erro ao confirmar!");
                    alert.showAndWait();
                }
            }

        }else{
            if(!verificarConfirmacao()){
                System.out.println("Ja esta SEM confirmação!");
            }else{
                try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Atendimento SET "
                    + "`atd_confirmado`=? WHERE atd_data =? AND pes_id =?  AND atd_hora =?")){

                    preparedStatement.setInt(1, 0);

                    preparedStatement.setString(2, atendSelec.getData().toLocalDate().toString());
                    preparedStatement.setInt(3, atendSelec.getCliente().getId());
                    preparedStatement.setString(4, atendSelec.getData().toLocalTime().toString());

                    preparedStatement.executeUpdate();

                    telaFinancasController.addDespesa(atendSelec);
                    fecharCardInfo(null);
                    calendar.getChildren().clear();
                    drawCalendar();
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Erro ao desconfirmar!");
                    alert.showAndWait();
                }
            }
        }
    }

    private boolean verificarConfirmacao(){
        int opc = 0;
        try (Connection connection = ConexaoBD.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT atd_confirmado FROM Atendimento WHERE atd_data=? AND pes_id =? AND atd_hora=?"))
        {
            preparedStatement.setString(1, atendSelec.getData().toLocalDate().toString());
            preparedStatement.setInt(2, atendSelec.getCliente().getId());
            preparedStatement.setString(3, atendSelec.getData().toLocalTime().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                opc = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(opc == 1){
            return true;
        }else{
            return false;
        }
    }

    

    @FXML
    private void sairApp(ActionEvent event) {
        App.csSairApp();
    }
    
    @FXML
    private void abrirTelaClientes(ActionEvent event) {
        App.abrirClientes();
    }

}

