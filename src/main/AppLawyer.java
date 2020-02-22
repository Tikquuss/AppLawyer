
package main;

import controllers.HomeForFolderController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class AppLawyer extends Application {

    public static Stage stage = new Stage();
    private FXMLLoader  homeForFolder;
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = new Stage();
        Parent connexionRoot = FXMLLoader.load(getClass().getResource("../views/Connexion.fxml"));
        stage.setScene(new Scene(connexionRoot));
        stage.setMinHeight(790);
        stage.setMinWidth(1000);
        stage.show();
    }
   
    public static void main(String[] args) {
        launch(args);
    }
    
}