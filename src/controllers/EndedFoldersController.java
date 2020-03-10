/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import appdatabase.bean.Dossier;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import static controllers.CurrentFoldersController.currentFolder;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import static main.AppLawyer.stage;

/**
 * FXML Controller class
 *
 * @author Nyatchou
 */
public class EndedFoldersController {

    @FXML
    private JFXListView<Dossier> listFolders_listView;
    @FXML
    private JFXButton openFolder_button;
    @FXML
    private JFXButton continueFold_button;

    private FXMLLoader homeForFolder;
    private Parent root;
    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        initListView();
        initButtonsActions();
        activeDoubleClick();
    }    
    
    public void initListView(){
        listFolders_listView.setItems(FXCollections.observableArrayList(Dossier.listByStatus("Archivé")));
    }
    
    public void initButtonsActions(){
        openFolder_button.setOnAction(e -> {
            openFoldAction();
        });
        
        continueFold_button.setOnAction(e -> {
        
        });
    }
    public boolean checkIfFolderExist(Dossier fold){
        File dir = new File(fold.getCheminDossier());
        return dir.exists();         
    }
    public void openFoldAction(){
        Dossier fold   = listFolders_listView.getSelectionModel().getSelectedItem();
            currentFolder = fold;
            if(fold != null){
                if(checkIfFolderExist(fold))
                    try {     
                         makeStageForSingleFolder(fold);

                    } catch (IOException ex) {
                        Logger.getLogger(CurrentFoldersController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                else{
                        displayFolderDontExists();
                }
            }
            else {
                displayNoFolderSelected();
            }
    }
    
    public void makeStageForSingleFolder(Dossier dossier) throws IOException{
        currentFolder = dossier;
        homeForFolder =  new FXMLLoader(getClass().getResource("/views/HomeForFolder.fxml"));
        root = homeForFolder.load();
        stage.setScene(new Scene(root));
        stage.setMinWidth(1100);
        stage.setMinHeight(700);
        /*singleFolderStage = new Stage();
        singleFolderStage.setScene(new Scene(root));
        singleFolderStage.setMinWidth(1100);
        singleFolderStage.setMinHeight(700);
        singleFolderStage.show();*/
    }
    
        
    public void displayFolderDontExists(){
        Alert al = new Alert(Alert.AlertType.WARNING);
        ((Stage)al.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/ressources/images/icon_lawyer2.png"));
        al.setContentText("Le chemin vers le repertoire correspondant à ce dossier n'a pas été retrouvé. Il a peut ëtre été supprimé.");
        al.show();
    }
    
    public void displayNoFolderSelected(){
        Alert al = new Alert(Alert.AlertType.ERROR);
        ((Stage)al.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/ressources/images/icon_lawyer2.png"));
        al.setContentText("Veuillez sélectionner un dossier dans la liste avant de cliquer sur ce bouton !");
        al.show();
    }
    public void activeDoubleClick(){
        listFolders_listView.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2 && event.getTarget() != null)
                openFoldAction();
        });
    }
}
