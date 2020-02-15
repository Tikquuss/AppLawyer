/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import appdatabase.bean.Client;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 *
 * @author Utilisateur
 */
public class CreateClientController {
    
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

    @FXML
    private JFXButton saveClient_button;
    
    @FXML
    public void initialize(){
        
        saveClient_button.setOnAction(e -> {
            if(this.checkNoEmptyField()){
               /* Client client = new Client(telephoneClient_textField.getText(),
                                            removeLeadingEmptySpace(emailClient_textField.getText()),
                                            removeLeadingEmptySpace(adresseClient_textField.getText()),
                                            removeLeadingEmptySpace(nomsClient_textField.getText()),
                                            removeLeadingEmptySpace(prenomsClient_textField.getText()),
                                            "cni");
                client.save();*/
                }
        });
        
    }
    
    public boolean checkNoEmptyField(){        
          return 
          !(nomsClient_textField.getText() == "" || prenomsClient_textField.getText() == ""
            || adresseClient_textField.getText() == "" || telephoneClient_textField.getText() == "" || emailClient_textField.getText() == "" );
       }
    
    public String removeLeadingEmptySpace(String s) {
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() > 1 && sb.charAt(0) == ' ') {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }
    
    
}
