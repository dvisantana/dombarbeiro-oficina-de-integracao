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
import javafx.stage.StageStyle;

public class App extends Application {
    private static Scene sceneTelaLogin, sceneTelaAdmin, sceneTelaAdminUsuario, sceneTelaCadastroUsuario;
    private static Stage primaryStage;


    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader telaLogin = new FXMLLoader(getClass().getResource("telaLogin.fxml"));
        FXMLLoader telaAdmin = new FXMLLoader(getClass().getResource("telaAdmin.fxml"));
        FXMLLoader telaAdminUsuario = new FXMLLoader(getClass().getResource("telaAdminUsuario.fxml"));
        
        
        primaryStage = stage;

        Parent parentTelaLogin = telaLogin.load();
        Parent parentTelaAdmin = telaAdmin.load();
        Parent parentTelaAdminUsuario = telaAdminUsuario.load();
        

        sceneTelaLogin = new Scene(parentTelaLogin);
        sceneTelaAdmin = new Scene(parentTelaAdmin);
        sceneTelaAdminUsuario = new Scene(parentTelaAdminUsuario);
        
                
        primaryStage.setTitle("DomBarbeiro");
        primaryStage.setScene(sceneTelaAdminUsuario);
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

    public static void adminAbrirUsuarios(){
        primaryStage.setScene(sceneTelaAdminUsuario);
    }

    public static void abrirAdmin(){
        primaryStage.setScene(sceneTelaAdmin);
    }

}