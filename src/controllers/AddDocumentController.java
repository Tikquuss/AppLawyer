/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Utilisateur
 */
public class AddDocumentController {
    
   
    @FXML
    private JFXComboBox<?> categoriesDoc_comboBox;

    @FXML
    private JFXComboBox<?> typesDoc_comboBox;

    @FXML
    private JFXButton valider_button;

    @FXML
    private JFXTextField choiceDoc_textField;

    @FXML
    private JFXButton choiceDoc_button;
    @FXML
    
    public void initialize(){
        choiceDoc_button.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(null);
            if(selectedFile != null)
                choiceDoc_textField.setText(selectedFile.getName());
        });
    }
    
    
    
}
