/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import appdatabase.bean.Document;
import appdatabase.bean.Operation;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import dbsimulator.BeansObjects;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import static controllers.CurrentFoldersController.currentFolder;
import javafx.beans.property.Property;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Utilisateur
 */
public class TaskDoneController{
    
    @FXML
    private TableView<Operation> listeOperation_tableView;
    
    @FXML
    private TableColumn<Operation, String> tache_tableColumn;

    @FXML
    private TableColumn<Operation, String> date_tableColumn;

    @FXML
    private TableColumn<Operation, String> dateInit_tableColumn;

    @FXML
    private TableColumn<Operation, String> dateFin_tableColumn;
    @FXML
    private JFXButton voirPlus_button;

    public static Stage editTaskStage;
    
    private FXMLLoader editTaskLoader;

    public TaskDoneController() {
    }
    @FXML
    public void initialize() throws IOException {
        editTaskStage = new Stage();
        editTaskLoader = new FXMLLoader(getClass().getResource("../views/EditTask.fxml"));
        editTaskStage.setScene(new Scene(editTaskLoader.load()));
        editTaskStage.setResizable(false);
        editTaskStage.initModality(Modality.APPLICATION_MODAL);
        manageTable();
        voirPlus_button.setOnAction(event -> {
                Operation op = listeOperation_tableView.getSelectionModel().getSelectedItem();
                if (op != null){                   
                    ((EditTaskController)editTaskLoader.getController()).setContent(op);
                    ((EditTaskController)editTaskLoader.getController()).initView();
                    editTaskStage.show();
                }
                else{
                    displaySelectionError();
                }
        });  
    }   
    public void manageTable(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        tache_tableColumn.setCellValueFactory(new PropertyValueFactory<Operation, String>("tache"));
        date_tableColumn.setCellValueFactory(value -> new ReadOnlyObjectWrapper<String>(value.getValue().getDate().format(formatter)));
        dateInit_tableColumn.setCellValueFactory(value -> new ReadOnlyObjectWrapper<String>(value.getValue().getDateDebut().format(formatter)));
        dateFin_tableColumn.setCellValueFactory(value -> new ReadOnlyObjectWrapper<String>(value.getValue().getDateFin().format(formatter)));
        listeOperation_tableView.setItems(FXCollections.observableList(Operation.listByEtat("effectuée", currentFolder)));

    }
    
    public void updateListView(Operation op){
        listeOperation_tableView.getItems().add(op);       
    }
    
    public void displaySelectionError(){
        Alert al = new Alert(Alert.AlertType.ERROR);
        al.setContentText("Vous n'avez sélectionné aucune tâche!");
        al.setHeaderText("AUCUNE TACHE SELECTIONNEE");
        al.show();
    }
}
