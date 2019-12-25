/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Utilisateur
 */
public class DocumentsController {
    
    @FXML
    private JFXButton newDoc;

    @FXML
    private JFXButton openDoc;

    @FXML
    private JFXButton suppDoc;
        
    
    
    @FXML
    public void initialize() throws IOException{
        Stage stageNewDoc = new Stage();
        Parent parent = FXMLLoader.load(getClass().getResource("../views/AddDocument.fxml"));

        stageNewDoc.setScene(new Scene(parent));
        stageNewDoc.setResizable(false);
        stageNewDoc.initModality(Modality.APPLICATION_MODAL);
        newDoc.setOnAction(e -> {
           stageNewDoc.show();
        });
    }
}