package src.com.dombarbeiro;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
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

        FXMLLoader telaLogin = new FXMLLoader(getClass().getResource("Views/telaLogin.fxml"));

        // ========= TELAS DE ADMIN ========= //
        FXMLLoader telaAdminUsuario = new FXMLLoader(getClass().getResource("Views/telaAdminUsuario.fxml"));
        FXMLLoader telaFinancas = new FXMLLoader(getClass().getResource("Views/telaFinancas.fxml"));
        FXMLLoader telaServicos = new FXMLLoader(getClass().getResource("Views/telaServicos.fxml"));
        // ========= TELAS DE ADMIN ========= //

        // ========= TELAS DE SECRETARIO ========= //
        FXMLLoader telaClientes = new FXMLLoader(getClass().getResource("Views/telaClientes.fxml"));
        FXMLLoader telaAgenda = new FXMLLoader(getClass().getResource("Views/telaAgenda.fxml"));
        // ========= TELAS DE SECRETARIO ========= //
        
        primaryStage = stage;

        Parent parentTelaLogin = telaLogin.load();
        Parent parentTelaAdminUsuario = telaAdminUsuario.load();
        Parent parentTelaFinancas = telaFinancas.load();
        Parent parentTelaServicos = telaServicos.load();
        Parent parentTelaClientes = telaClientes.load();
        Parent parentTelaAgenda = telaAgenda.load();        

        sceneTelaLogin = new Scene(parentTelaLogin);
        sceneTelaAdminUsuario = new Scene(parentTelaAdminUsuario);
        sceneTelaFinancas = new Scene(parentTelaFinancas);
        sceneTelaServicos = new Scene(parentTelaServicos);
        sceneTelaClientes = new Scene(parentTelaClientes);
        sceneTelaAgenda = new Scene(parentTelaAgenda);
        
        Image image = new Image("src/com/dombarbeiro/imgs/logoImg.png");

        primaryStage.getIcons().add(image);
        primaryStage.setTitle("DomBarbeiro");
        primaryStage.setScene(sceneTelaLogin);
        primaryStage.show();        
    }

    public static void csEntrarApp(int opc){
        switch(opc){
            case 0:
                primaryStage.setScene(sceneTelaAdminUsuario);
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