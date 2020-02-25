/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import static main.AppLawyer.stage;

/**
 *
 * @author Utilisateur
 */
public class ConnexionController {
    
    @FXML
    private JFXTextField username_textField;

    @FXML
    private JFXButton connect_button;

    @FXML
    private JFXPasswordField password_textField;
    
    @FXML
    private AnchorPane form_anchorPane;

    
    public static Scene presentPageScene;
    private Parent presentPageRoot;

    
    @FXML
    public void initialize(){
       initButtonsActions();
       initTextField();
    }
    
    public void initButtonsActions(){
        connect_button.setOnAction(e -> {
                loginAction();
        });
        
        form_anchorPane.setOnKeyPressed((KeyEvent t) ->{
            KeyCode key=t.getCode();
            if(key == KeyCode.ENTER){
                loginAction();
                }     
        });
    }
    
    public void loginAction(){
        String username = username_textField.getText();
        String password = password_textField.getText();
        if(username.equals("") || password.equals(""))
            displayEmptyFieldsErrors();
        else{
            if(!login())
                displayConnexionError();
            else{
                try {
                    setInitialView();
                } catch (IOException ex) {
                    Logger.getLogger(ConnexionController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void initTextField(){
        UnaryOperator<TextFormatter.Change> filter = (TextFormatter.Change t) -> {
            String newText = t.getControlNewText();
            if(newText.matches("^([^\\s].*)*$") && newText.length()< 50) {
                return t ;
            }
            return null ;
            };
        username_textField.setTextFormatter(new TextFormatter<>(filter));
        password_textField.setTextFormatter(new TextFormatter<>(filter));
    }
    
    public void displayEmptyFieldsErrors(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("CHAMPS VIDES");
        alert.setContentText("Remplissez les tous les champs s'il vous plaît !");
        alert.show();
    }
    
    public void displayConnexionError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("CHAMPS INCORRECTS");
        alert.setContentText("Nom d'utilisateur et/ou mot de passe incorrect(s) !");
        alert.show();
    }
    
    public boolean login(){
        return true;
    }
    
    public void setInitialView() throws IOException{

        presentPageRoot = FXMLLoader.load(getClass().getResource("../views/PresentPage.fxml"));
        presentPageScene = new Scene(presentPageRoot);
        stage.setScene(presentPageScene);
        stage.setMinWidth(1000);
        stage.setMinHeight(800);        
    }
}
