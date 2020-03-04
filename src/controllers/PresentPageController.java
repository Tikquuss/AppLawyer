/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import static main.AppLawyer.stage;
import utilities.ViewDimensionner;
 

/**
 *
 * @author Nyatchou
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

    @FXML
    private JFXButton configData_button;
    
    private Parent clientList, archDocs, home, adminConfig;
    
    public static FXMLLoader homeLoader, clientListLoader, endedFoldersLoader, adminConfigLoader;
    
    public static String pathFolderRoot;

    @FXML
    public void initialize() throws IOException{
        setFolderPathRoot();
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
        configData_button.setOnAction(e -> {
            adminConfigLoader = new FXMLLoader(getClass().getResource("/views/Admin.fxml"));
            try {
                adminConfig = adminConfigLoader.load();
                stage.setScene(new Scene(adminConfig));
            } catch (IOException ex) {
                Logger.getLogger(PresentPageController.class.getName()).log(Level.SEVERE, null, ex);
            }               
        });
    }
    public void setFolderPathRoot() throws FileNotFoundException, IOException{
        BufferedReader in = new BufferedReader(new FileReader(new File("./src/utilities", "cheminDossierRacine")));
        String line = in.readLine();
        pathFolderRoot = line;     
    }

}
