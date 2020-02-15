/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import appdatabase.bean.Adversaire;
import appdatabase.bean.Client;
import appdatabase.bean.Dossier;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.validation.RequiredFieldValidator;
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
import static controllers.PresentPageController.homeLoader;
import java.time.LocalDate;
import java.util.List;

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

                Dossier doc = new Dossier();
                doc.save();
                String dirName = "F:\\Dossiers Clients\\DOSSIER "+removeLeadingEmptySpace(nomsClient_textField.getText().toUpperCase())+" "+removeLeadingEmptySpace(prenomsClient_textField.getText().toUpperCase())+" "+doc.getId();
                File dir = new File(dirName);
                boolean idCreated = dir.mkdirs();
                Client client = new Client(telephoneClient_textField.getText(),
                                            removeLeadingEmptySpace(emailClient_textField.getText()),
                                            removeLeadingEmptySpace(adresseClient_textField.getText()),
                                            removeLeadingEmptySpace(nomsClient_textField.getText()),
                                            removeLeadingEmptySpace(prenomsClient_textField.getText()),
                                            "cni");
                client.save();
                Adversaire adv  = new Adversaire(removeLeadingEmptySpace(nomAdvers_textField.getText()), "", "cni");
                adv.save();
                doc.setClient(client);
                doc.setAdversaire(adv);
                doc.setJuridiction(juridiction_comboBox.getSelectionModel().getSelectedItem());
                doc.setQualite(qualiteAvocat_comboBox.getSelectionModel().getSelectedItem());
                doc.setTypeAffaire(typeAffaire_comboBox.getSelectionModel().getSelectedItem());
                doc.setProvisions(Integer.valueOf(provisions_textField.getText()));
                doc.setHonoraires(Integer.valueOf(honoraires_textField.getText()));
                doc.setStatut("En cours");
                doc.update();
                ((CurrentFoldersController)homeLoader.getController()).addToListView(doc);
                
            }               
        });
       
    }  
    public boolean checkIfIsInClientList(){
        boolean isInClientList = false;
        int index = 0;
        List <Client> listeClient = Client.all();
        while(index < listeClient.size() &&  
                !(listeClient.get(index).getNom().equals(removeLeadingEmptySpace(nomsClient_textField.getText())) && 
                  listeClient.get(index).getPrenom().equals(removeLeadingEmptySpace(prenomsClient_textField.getText())))){
            index++;
        }
        if(index != listeClient.size()){
            isInClientList = true;
            System.out.println(true);
        }
        return isInClientList;
    }
    
    
    public String removeLeadingEmptySpace(String s) {
    StringBuilder sb = new StringBuilder(s);
    while (sb.length() > 1 && sb.charAt(0) == ' ') {
        sb.deleteCharAt(0);
    }
    return sb.toString();
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
