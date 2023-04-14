import java.io.Console;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class TelaAdminController {

    @FXML
    private Button botaoMudarNome;

    @FXML
    private Button botaoSair;

    @FXML
    private Label txtNome;

    @FXML
    public void sairApp(ActionEvent event) {
        App.csSairApp();
    }

}
