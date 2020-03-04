/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import appdatabase.bean.Utilisateur;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.base.ValidatorBase;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.DirectoryChooser;
import static main.AppLawyer.stage;

/**
 * FXML Controller class
 *
 * @author Nyatchou
 */
public class ConfigInitController {

    @FXML
    private TextField folderForClient_textField;
    @FXML
    private Button selectFolder_button;
    @FXML
    private JFXTextField adminName_textField;
    @FXML
    private JFXPasswordField adminPassword_passwordField;
    @FXML
    private JFXPasswordField confirmAdminPassword_passwordField;
    @FXML
    private Button save_button;

    private File selectedFolder;
        
    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        initTextFields();
        setvalidators();
        initButtonsActions();
    }    
    
    public void initTextFields(){
        UnaryOperator<TextFormatter.Change> filter = (TextFormatter.Change t) -> {
            String newText = t.getControlNewText();
            if(newText.matches("^([^\\s].*)*$") && newText.length()< 50) {
                return t ;
            }
            return null ;
        };
        adminName_textField.setTextFormatter(new TextFormatter<>(filter));
        adminPassword_passwordField.setTextFormatter(new TextFormatter<>(filter));
        confirmAdminPassword_passwordField.setTextFormatter(new TextFormatter<>(filter));    
    }
    
    public void setvalidators(){
        ValidatorBase minLenghtValidatorAdminName = new ValidatorBase() {
            @Override
            protected void eval() {
                setMessage("Ce champ doit contenir au moins 8 caractères");
                    if (adminName_textField.getText().length()<8) {
                        hasErrors.set(true);
                    } else hasErrors.set(false);
                }
        };
        ValidatorBase minLenghtValidatorpass = new ValidatorBase(){
            @Override
                protected void eval() {
                    setMessage("Ce champ doit contenir au moins 8 caractères");
                    if (adminPassword_passwordField.getText().length()<8) {
                        hasErrors.set(true);
                    } else hasErrors.set(false);
                }
        };
        
        ValidatorBase passEqualsValidator = new ValidatorBase() {
            @Override
            protected void eval() {
                setMessage("Champs de mot de passe différents");
                if(!adminPassword_passwordField.getText().equals(confirmAdminPassword_passwordField.getText())){
                    hasErrors.set(true);
                } else hasErrors.set(false);               
            }
        };
        
        adminName_textField.getValidators().add(minLenghtValidatorAdminName);
        adminPassword_passwordField.getValidators().add(minLenghtValidatorpass);
        confirmAdminPassword_passwordField.getValidators().add(passEqualsValidator);
        
        adminName_textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!(newValue.equals(oldValue))) {
                adminName_textField.validate();
            }
        });
        adminPassword_passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!(newValue.equals(oldValue))) {
                adminPassword_passwordField.validate();
            }
        });
        confirmAdminPassword_passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!(newValue.equals(oldValue))) {
                confirmAdminPassword_passwordField.validate();
            }
        });
    }
    
    public void initButtonsActions(){
        selectFolder_button.setOnAction(e -> {
            folderForClient_textField.setEditable(true); 
            DirectoryChooser directoryChooser = new DirectoryChooser();
            selectedFolder = directoryChooser.showDialog(null);
            if(selectedFolder != null && selectedFolder.isDirectory()){
                folderForClient_textField.setText(selectedFolder.getAbsolutePath());
                folderForClient_textField.setEditable(false);
            }
            else{
                if(!selectedFolder.isDirectory()){
                    
                }
            }
        });
        
        save_button.setOnAction(e -> {
            try {
                saveConfig();
            } catch (IOException ex) {
                Logger.getLogger(ConfigInitController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    public void setCheminDossierRacine(String path) throws FileNotFoundException{
        PrintWriter pw = new PrintWriter(new File("./src/utilities", "cheminDossierRacine"));
        pw.println(path);
        pw.flush();
    }
    
    public void saveConfig() throws FileNotFoundException, IOException{
        if(this.areAllValidFields()){
            Utilisateur admin = new Utilisateur(adminName_textField.getText(), String.valueOf(adminPassword_passwordField.getText().hashCode()), "Admin");
            admin.save();
            this.setCheminDossierRacine(folderForClient_textField.getText());
            setInitialView();
        }
    }
    public void setInitialView() throws IOException{
        Parent connexionRoot = FXMLLoader.load(getClass().getResource("/views/Connexion.fxml"));
        stage.setScene(new Scene(connexionRoot));
        stage.setMinWidth(1000);
        stage.setMinHeight(800);    
        stage.setResizable(true);
    }
    
    public boolean areAllValidFields(){
        if(selectedFolder == null){
            return false;
        }
        return adminName_textField.validate() && 
                adminPassword_passwordField.validate() && 
                confirmAdminPassword_passwordField.validate() &&
                !folderForClient_textField.getText().equals("");
    }  
}
