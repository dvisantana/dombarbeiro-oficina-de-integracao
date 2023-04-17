import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    //victor foi bom o cineminha ontem?
    private static Scene sceneTelaLogin, sceneTelaAdmin;
    private static Stage primaryStage;
    // private static TelaAdminController mudar = new TelaAdminController();

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader telaLogin = new FXMLLoader(getClass().getResource("telaLogin.fxml"));
        FXMLLoader telaAdmin = new FXMLLoader(getClass().getResource("telaAdmin.fxml"));

        primaryStage = stage;

        Parent parentTelaLogin = telaLogin.load();
        Parent parentTelaAdmin = telaAdmin.load();

        sceneTelaLogin = new Scene(parentTelaLogin);
        sceneTelaAdmin = new Scene(parentTelaAdmin);
                
        primaryStage.setTitle("DomBarbeiro");
        primaryStage.setScene(sceneTelaLogin);
        primaryStage.show();        
    }

    public static void csEntrarApp(int opc){
        switch(opc){
            case 0:
                primaryStage.setScene(sceneTelaAdmin);
                break;
            // case 1:
            //     primaryStage.setScene(sceneTelaSecretario);
            //     break;
        }
    }

    public static void csSairApp(){
        primaryStage.setScene(sceneTelaLogin);
    }


}