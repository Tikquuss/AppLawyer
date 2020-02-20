
package main;

import controllers.HomeForFolderController;
import dbsimulator.BeansObjects;
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
    public static Scene presentPageScene;
    private FXMLLoader  homeForFolder;
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        BeansObjects.initObjects();
        stage = new Stage();
        Parent presentPageRoot = FXMLLoader.load(getClass().getResource("../views/PresentPage.fxml"));
        presentPageScene = new Scene(presentPageRoot);
        presentPageScene.setRoot(presentPageRoot);
        stage.setScene(presentPageScene);
        stage.setMinHeight(700);
        stage.setMinWidth(1100);
        stage.show();
    }
   
    public static void main(String[] args) {
        launch(args);
    }
    
}