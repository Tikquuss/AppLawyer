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
    private JFXComboBox<?> categoriesDoc;

    @FXML
    private JFXComboBox<?> typesDoc;

    @FXML
    private JFXButton choiceDocButton;

    @FXML
    private JFXTextField nomDoc;
    
    @FXML
    public void initialize(){
        choiceDocButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(null);
            if(selectedFile != null)
                nomDoc.setText(selectedFile.getName());
        });
    }
    
    
    
}
