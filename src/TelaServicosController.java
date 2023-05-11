import java.net.URL;
import java.util.ResourceBundle;

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
