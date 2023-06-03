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
    private static Scene sceneTelaLogin, sceneTelaAdmin, sceneTelaAdminUsuario, sceneTelaFinancas,sceneTelaServicos, sceneTelaClientes, sceneTelaAgenda;
    private static Stage primaryStage;


    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{

        FXMLLoader telaLogin = new FXMLLoader(getClass().getResource("telaLogin.fxml"));

        // ========= TELAS DE ADMIN ========= //
        FXMLLoader telaAdmin = new FXMLLoader(getClass().getResource("telaAdmin.fxml"));
        FXMLLoader telaAdminUsuario = new FXMLLoader(getClass().getResource("telaAdminUsuario.fxml"));
        FXMLLoader telaFinancas = new FXMLLoader(getClass().getResource("telaFinancas.fxml"));
        FXMLLoader telaServicos = new FXMLLoader(getClass().getResource("telaServicos.fxml"));
        // ========= TELAS DE ADMIN ========= //

        // ========= TELAS DE SECRETARIO ========= //
        FXMLLoader telaClientes = new FXMLLoader(getClass().getResource("telaClientes.fxml"));
        FXMLLoader telaAgenda = new FXMLLoader(getClass().getResource("telaAgenda.fxml"));
        // ========= TELAS DE SECRETARIO ========= //
        
        primaryStage = stage;

        Parent parentTelaLogin = telaLogin.load();
        Parent parentTelaAdmin = telaAdmin.load();
        Parent parentTelaAdminUsuario = telaAdminUsuario.load();
        Parent parentTelaFinancas = telaFinancas.load();
        Parent parentTelaServicos = telaServicos.load();
        Parent parentTelaClientes = telaClientes.load();
        Parent parentTelaAgenda = telaAgenda.load();        

        sceneTelaLogin = new Scene(parentTelaLogin);
        sceneTelaAdmin = new Scene(parentTelaAdmin);
        sceneTelaAdminUsuario = new Scene(parentTelaAdminUsuario);
        sceneTelaFinancas = new Scene(parentTelaFinancas);
        sceneTelaServicos = new Scene(parentTelaServicos);
        sceneTelaClientes = new Scene(parentTelaClientes);
        sceneTelaAgenda = new Scene(parentTelaAgenda);
        
        primaryStage.setTitle("DomBarbeiro");
        primaryStage.setScene(sceneTelaFinancas);
        primaryStage.show();        
    }

    public static void csEntrarApp(int opc){
        switch(opc){
            case 0:
                primaryStage.setScene(sceneTelaAdmin);
                break;
            case 1:
                primaryStage.setScene(sceneTelaAgenda);
                break;
        }
    }

    public static void csSairApp(){
        primaryStage.setScene(sceneTelaLogin);
    }

    public static void adminAbrirUsuarios(){
        primaryStage.setScene(sceneTelaAdminUsuario);
    }

    public static void adminAbrirFinancas(){
        primaryStage.setScene(sceneTelaFinancas);
    }

    public static void abrirAdmin(){
        primaryStage.setScene(sceneTelaAdmin);
    }
    public static void admAbrirServicos(){
        primaryStage.setScene(sceneTelaServicos);
    }
    
    // ========= TELAS DE SECRETARIO ========= //
    public static void abrirAgenda(){
        primaryStage.setScene(sceneTelaAgenda);
    }
    public static void abrirClientes(){
        primaryStage.setScene(sceneTelaClientes);
    }
    // ========= TELAS DE SECRETARIO ========= //

}