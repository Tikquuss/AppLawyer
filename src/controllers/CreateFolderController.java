/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import dbsimulator.BeansObjects;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import utilities.GestionSpinner;

/**
 * FXML Controller class
 *
 * @author Utilisateur
 */
public class CreateFolderController {

    @FXML
    private JFXButton save_button;
    @FXML
    private TextField nomAdvers_textField;
    @FXML
    private TextField honoraires_textField;
    @FXML
    private JFXComboBox<String> qualiteAvocat_comboBox;
    @FXML
    private JFXComboBox<String> typeAffaire_comboBox;
    @FXML
    private JFXComboBox<String> juridiction_comboBox;
    @FXML
    private TextField provisions_textField;
    @FXML
    private TextField nomsClient_textField;
    @FXML
    private TextField prenomsClient_textField;
    @FXML
    private TextField adresseClient_textField;
    @FXML
    private TextField telephoneClient_textField;
    @FXML
    private TextField emailClient_textField;

    /**
     * Initializes the controller class.
     */

    public void initialize() {

        initComboBox();
        initTextFieldForNumbers();
        checkEmailIsValid();
        save_button.setOnAction(e -> {
            if(this.checkNoEmptyField()){
                String dirName = "F:\\Dossier Client\\Dossier "+nomsClient_textField.getText().toUpperCase();
                File dir = new File(dirName);
                boolean isCreated = dir.mkdirs();
                
            }               
        });
       
    }    
    
    public void initComboBox(){
        qualiteAvocat_comboBox.setItems(FXCollections.observableList(BeansObjects.qualiteAvo));
        typeAffaire_comboBox.setItems(FXCollections.observableList(BeansObjects.typeAff));
        juridiction_comboBox.setItems(FXCollections.observableList(BeansObjects.juridictions));
    }
    
    public boolean checkNoEmptyField(){
          String combo1 = qualiteAvocat_comboBox.getSelectionModel().getSelectedItem();
          String combo2 = typeAffaire_comboBox.getSelectionModel().getSelectedItem();
          String combo3 = juridiction_comboBox.getSelectionModel().getSelectedItem();
         
          return 
          !(nomAdvers_textField.getText() == "" || nomsClient_textField.getText() == "" || prenomsClient_textField.getText() == ""
            || adresseClient_textField.getText() == "" || telephoneClient_textField.getText() == "" || emailClient_textField.getText() == "" 
                  || honoraires_textField.getText() == "" || provisions_textField.getText() == "" || combo1 == null || combo2 == null || combo3 == null);
          
          
       }
    
    public void initTextFieldForNumbers(){
        UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>() {
            @Override
            public TextFormatter.Change apply(TextFormatter.Change t) {
                String newText = t.getControlNewText() ;
                if(newText.matches("-?[0-9]*")) {
                    return t ;
                }
                return null ;
            }
        };
     honoraires_textField.setTextFormatter(new TextFormatter<>(filter));
     provisions_textField.setTextFormatter(new TextFormatter<>(filter));
    }
    public void checkEmailIsValid(){
        
    }
}
