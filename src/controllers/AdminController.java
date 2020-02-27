/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import utilities.ViewDimensionner;

/**
 * FXML Controller class
 *
 * @author Utilisateur
 */
public class AdminController{

    @FXML
    private JFXButton documents_button;
    @FXML
    private JFXButton paramDossier_button;
    @FXML
    private AnchorPane containerAdmin_anchorPane;


    private FXMLLoader adminForDocument, adminForFoldersParam;
    private Parent adminForDocParent, adminFoldersParamParent;
            
    @FXML
    public void initialize() throws IOException {
        initLoader();
        this.add(adminForDocParent);
    } 
    
    public void initLoader() throws IOException{
        adminForDocument = new FXMLLoader(getClass().getResource("/views/AdminDocument.fxml"));
        adminForFoldersParam = new FXMLLoader(getClass().getResource("/views/Admin2.fxml"));
        adminForDocParent = adminForDocument.load();
        adminFoldersParamParent = adminForFoldersParam.load();
    }
    
    public void add(Parent parent){
        if(!containerAdmin_anchorPane.getChildren().contains(parent)){
            containerAdmin_anchorPane.getChildren().clear();
            containerAdmin_anchorPane.getChildren().add(parent);
            ViewDimensionner.bindSizes((Region)parent, containerAdmin_anchorPane);
        }
    }
    public void initButtonsActions(){
        documents_button.setOnAction(e -> {
            this.add(adminForDocParent);
        });
        paramDossier_button.setOnAction(e -> {
            this.add(adminFoldersParamParent);
        });
    }
}
