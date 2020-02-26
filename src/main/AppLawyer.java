
package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class AppLawyer extends Application {

    public static Stage stage;
    
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