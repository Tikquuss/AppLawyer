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
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import appdatabase.bean.*;
import java.awt.Desktop;
import java.util.ArrayList;
import org.apache.commons.io.IOUtils;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import static controllers.CurrentFoldersController.currentFolder;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

/**
 *
 * @author Utilisateur
 */
public class DocumentsController {
    
    @FXML
    private TableView<Document> documents_tableView;
    
    @FXML
    private TableColumn<Document, String> nomDoc_tableColumn;

    @FXML
    private TableColumn<Document, String> categDoc_tableColumn;

    @FXML
    private TableColumn<Document, String> typeDoc_tableColumn;

    @FXML
    private JFXButton newDoc_button;

    @FXML
    private JFXButton openDoc_button;

    @FXML
    private JFXButton suppDoc_button;
        
    @FXML
    public void initialize() throws IOException{
        this.initButtonsActions();
        this.manageTable();      
    }
    public void initButtonsActions()  throws IOException{
        Stage stageNewDoc = new Stage();
        Parent parent = FXMLLoader.load(getClass().getResource("../views/AddDocument.fxml"));
        stageNewDoc.setScene(new Scene(parent));
        stageNewDoc.setResizable(false);
        stageNewDoc.initModality(Modality.APPLICATION_MODAL);
        newDoc_button.setOnAction(e -> {
           stageNewDoc.show();
        });
        openDoc_button.setOnAction(event -> {
            Document doc = documents_tableView.getSelectionModel().getSelectedItem();
            if(doc != null){
                String absPath = doc.getFichier();
                Desktop desk = Desktop.getDesktop();
                try {
                    desk.open(new File(absPath));
                } catch (IOException ex) {
                    Logger.getLogger(DocumentsController.class.getName()).log(Level.SEVERE, null, ex);
                }   
            }

        });
        suppDoc_button.setOnAction(e -> {
            Document doc = documents_tableView.getSelectionModel().getSelectedItem();
            if(doc != null){
                File file = new File(doc.getFichier());
                if(file.delete() && doc.delete())
                    documents_tableView.getItems().remove(doc);
            }
        });
    }
    
    public void manageTable(){
        nomDoc_tableColumn.setCellValueFactory(new PropertyValueFactory<Document, String>("nom"));
        typeDoc_tableColumn.setCellValueFactory(new PropertyValueFactory<Document, String>("type"));
        categDoc_tableColumn.setCellValueFactory(new PropertyValueFactory<Document, String>("categorie"));
       
        documents_tableView.setItems(FXCollections.observableList(Document.listByDossier(currentFolder)));
    }
    public void updateTabView(){
        documents_tableView.setItems(FXCollections.observableList(Document.listByDossier(currentFolder)));
    }
}
