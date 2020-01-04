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

import java.io.*;

public class AppLawyer extends Application {

    private FXMLLoader  homeForFolder;

    @Override
    public void start(Stage primaryStage) throws Exception{
        BeansObjects.initObjects();
        homeForFolder =  new FXMLLoader(getClass().getResource("./views/homeForFolder.fxml"));
        Parent root = homeForFolder.load();
        Parent presentPageRoot = FXMLLoader.load(getClass().getResource("./views/PresentPage.fxml"));
        Parent homeCF = FXMLLoader.load(getClass().getResource("./views/homeClientFolder.fxml"));
        ((HomeForFolderController)homeForFolder.getController()).add(homeCF);
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinHeight(700);
        primaryStage.setMinWidth(1100);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}