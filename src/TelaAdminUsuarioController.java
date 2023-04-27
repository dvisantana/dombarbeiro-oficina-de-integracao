import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TelaAdminUsuarioController {

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
    private TableView<?> tabelaUsuario;

    @FXML
    public void sairApp(ActionEvent event) {
        App.csSairApp();
    }

}
