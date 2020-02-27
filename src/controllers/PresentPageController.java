/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import utilities.ViewDimensionner;

/**
 *
 * @author Utilisateur
 */
public class PresentPageController {
    
    @FXML
    private AnchorPane content;

    @FXML
    private JFXButton home_button;

    @FXML
    private JFXButton clientsList_button;

    @FXML
    private JFXButton archDoc_button;
    
    private Parent clientList, archDocs, home;
    
    public static FXMLLoader homeLoader, clientListLoader, endedFoldersLoader;
    
    @FXML
    public void initialize() throws IOException{
        clientListLoader = new FXMLLoader(getClass().getResource("/views/ClientsList.fxml"));
        homeLoader = new FXMLLoader(getClass().getResource("/views/CurrentFolders.fxml"));
        endedFoldersLoader = new FXMLLoader(getClass().getResource("/views/EndedFolders.fxml"));
        home = homeLoader.load();
        clientList = clientListLoader.load();
        archDocs = endedFoldersLoader.load();
        this.add(home);
        initButtonsActions();
        
    }
    
    public void add(Parent parent){
        if(!content.getChildren().contains(parent)){
            content.getChildren().clear();
            content.getChildren().add(parent);
            ViewDimensionner.bindSizes((Region)parent, content);
        }
    }
    
    public void initButtonsActions(){
        home_button.setOnAction( e -> {
            this.add(home);
        });
        clientsList_button.setOnAction( e -> {
            this.add(clientList);
        });
        archDoc_button.setOnAction( e -> {
            this.add(archDocs);
        });
        
    }
    
}
