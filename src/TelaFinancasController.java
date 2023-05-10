import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class TelaFinancasController {

    @FXML
    private TextField boxPesquisar;

    @FXML
    private TableView<?> tabelaUsuario;

    @FXML
    private void abrirTelaAdmin(MouseEvent event) {
        App.abrirAdmin();
    }

    @FXML
    private void abrirTelaAdminUsuario(ActionEvent event){
        App.adminAbrirUsuarios();
    }

    @FXML
    private void sairApp(ActionEvent event) {
        App.csSairApp();
    }

}
