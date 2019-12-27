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
    
    @FXML
    public void initialize() throws IOException{
        clientList = FXMLLoader.load(getClass().getResource("../views/ClientsList.fxml"));
        home = FXMLLoader.load(getClass().getResource("../views/CurrentFolders.fxml"));
        initActions();
    }
    
    public void add(Parent parent){
        if(!content.getChildren().contains(parent)){
            content.getChildren().clear();
            content.getChildren().add(parent);
            ViewDimensionner.bindSizes((Region)parent, content);
        }
    }
    
    public void initActions(){
        home_button.setOnAction( e -> {
            this.add(home);
        });
        clientsList_button.setOnAction( e -> {
            this.add(clientList);
        });
        archDoc_button.setOnAction( e -> {
            
        });
        
    }
    
}
