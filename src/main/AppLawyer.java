
package main;

import appdatabase.bean.Utilisateur;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;



public class AppLawyer extends Application {

    public static Stage stage;
    private Parent initRoot;
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = new Stage();
        //Parent connexionRoot = FXMLLoader.load(getClass().getResource("/views/Connexion.fxml"));
        if(Utilisateur.all().isEmpty()){
            initRoot = FXMLLoader.load(getClass().getResource("/views/ConfigInit.fxml"));
            stage.setMinHeight(600);
            stage.setMinWidth(900);
            stage.setResizable(false);
        }
        else{
            initRoot = FXMLLoader.load(getClass().getResource("/views/Connexion.fxml"));
            stage.setMinHeight(790);
            stage.setMinWidth(1000);
        }
        stage.setScene(new Scene(initRoot));
        stage.getIcons().add(new Image("/ressources/images/icon_lawyer.png"));
        stage.show();
    }
   
    public static void main(String[] args) {
        launch(args);
    }
}