/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import appdatabase.bean.Dossier;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Utilisateur
 */
public class EndedFoldersController {

    @FXML
    private JFXListView<Dossier> listFolders_listView;
    @FXML
    private JFXButton openFolder_button;
    @FXML
    private JFXButton continueFold_button;

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        initListView();
    }    
    
    public void initListView(){
        listFolders_listView.setItems(FXCollections.observableArrayList(Dossier.listByStatus("Archivé")));
    }
    
    public void initButtonsActions(){
        openFolder_button.setOnAction(e -> {
            
        });
        
        continueFold_button.setOnAction(e -> {
        
        });
    }
    
}
