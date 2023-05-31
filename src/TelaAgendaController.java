import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class TelaAgendaController {

    @FXML
    private Button botaoSair;

    @FXML
    private FlowPane calendar;

    @FXML
    private Text month;

    @FXML
    private Text year;


// Abertura de Telas
    @FXML
    private void sairApp(ActionEvent event) {
        App.csSairApp();
    }
    
    @FXML
    private void abrirTelaClientes(ActionEvent event) {
        App.abrirClientes();
    }

}
