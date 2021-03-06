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
import javafx.stage.Modality;
import javafx.stage.Stage;
import appdatabase.bean.*;
import java.awt.Desktop;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import static controllers.CurrentFoldersController.currentFolder;
import java.util.Optional;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableRow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.StageStyle;
import static main.AppLawyer.stage;

/**
 *
 * FXML Controller class
 * @author Nyatchou
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
        this.checkIfDocExist();
        this.initKeyBoardsActions();
        this.hideProperties();
        this.activedoubleclick();
    }
    public void checkIfDocExist(){
        Document.listByDossier(currentFolder).forEach(doc -> {
            File file = new File(doc.getFichier());
            if(!file.exists()){
                  doc.delete();
            }
        });
    }
    
    public void hideProperties(){
        if(currentFolder.getStatut().equals("Archivé")){
            suppDoc_button.setVisible(false);
            newDoc_button.setVisible(false);
        }
    }
    
    public void initButtonsActions()  throws IOException{
        Stage stageNewDoc = new Stage();
        Parent parent = FXMLLoader.load(getClass().getResource("/views/AddDocument.fxml"));
        stageNewDoc.setScene(new Scene(parent));
        stageNewDoc.initStyle(StageStyle.UTILITY);
        stageNewDoc.initModality(Modality.APPLICATION_MODAL); 
        stageNewDoc.initOwner(stage);
        newDoc_button.setOnAction(e -> {
           stageNewDoc.show();
        });
        openDoc_button.setOnAction(event -> {
            openDocAction();
        });
        suppDoc_button.setOnAction(e -> {
            supDocAction();
        });
        
    }
    
    public void activedoubleclick(){
        documents_tableView.setRowFactory(tv -> {
            TableRow<Document> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    openDocAction();
                }
            });
             return row;       
        });
    }
    public void initKeyBoardsActions(){
        documents_tableView.setOnKeyPressed((KeyEvent t) ->{
            KeyCode key=t.getCode();
            if(key == KeyCode.ENTER){
                openDocAction();
            }
            else if(key == KeyCode.DELETE){
                if(currentFolder.getStatut().equals("En cours"))
                     supDocAction();
            }
        });
        
    }
    
    public void supDocAction(){
        Document doc = documents_tableView.getSelectionModel().getSelectedItem();
        if(doc != null){
            Alert dialogConfirm = new Alert(Alert.AlertType.CONFIRMATION);
            ((Stage)dialogConfirm.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/ressources/images/icon_lawyer2.png"));
            dialogConfirm.setTitle("Confirmation de suppression");
            dialogConfirm.setHeaderText("Confirmation de suppression du document");
            dialogConfirm.setContentText("Voulez vous vraiment supprimer ce document ??");
            Optional<ButtonType> answer = dialogConfirm.showAndWait();
            if (answer.get() == ButtonType.OK) {
                  File file = new File(doc.getFichier());
                  if(file.exists()){                       
                      if(file.delete() && doc.delete())
                          documents_tableView.getItems().remove(doc);
                  }                       
                  else{
                      Alert al = new Alert(Alert.AlertType.WARNING);
                      ((Stage)al.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/ressources/images/icon_lawyer2.png"));
                      al.setHeaderText("FICHIER NON EXISTANT");
                      al.setContentText("Ce fichier n'existe plus. Il a pu être supprimé.");
                      al.show();
                  }
            }                
        }
        else{
            displaySelectionError();
        }
    }
    
    public void openDocAction(){
        Document doc = documents_tableView.getSelectionModel().getSelectedItem();
        if(doc != null){
            String absPath = doc.getFichier();
            Desktop desk = Desktop.getDesktop();
            try {
                File file = new File(absPath);
                if(file.exists())
                    desk.open(new File(absPath));
                else{
                    Alert al = new Alert(Alert.AlertType.WARNING);
                    ((Stage)al.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/ressources/images/icon_lawyer2.png"));
                    al.setHeaderText("FICHIER NON EXISTANT");
                    al.setContentText("Ce fichier n'existe plus. Il a pu être supprimé.");
                    al.show();
                }
            } catch (IOException ex) {
                Logger.getLogger(DocumentsController.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
        else{
            displaySelectionError();
        }

    }
    public void manageTable(){
        nomDoc_tableColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        typeDoc_tableColumn.setCellValueFactory(value -> {
           return value.getValue().getType() != null ? new ReadOnlyObjectWrapper<>(value.getValue().getType().getNom()) :  new ReadOnlyObjectWrapper<>("");
                    });
        categDoc_tableColumn.setCellValueFactory(value -> {
           return new ReadOnlyObjectWrapper<String>(value.getValue().getCategorie().getNom());
                    });
        documents_tableView.setItems(FXCollections.observableList(Document.listByDossier(currentFolder)));
    }
    public void updateTabView(){
        documents_tableView.setItems(FXCollections.observableList(Document.listByDossier(currentFolder)));
    }
    
    public void displaySelectionError(){
        Alert al = new Alert(Alert.AlertType.ERROR);
        ((Stage)al.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/ressources/images/icon_lawyer2.png"));
        al.setContentText("Vous n'avez sélectionné aucun document !");
        al.setHeaderText("AUCUN DOCUMENT SELECTIONNE");
        al.show();
    }
}
