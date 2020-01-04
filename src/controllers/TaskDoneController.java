/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import appdatabase.bean.Operation;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import dbsimulator.BeansObjects;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Utilisateur
 */
public class TaskDoneController{

    @FXML
    private JFXListView<Operation> listOperations_listView;
    @FXML
    private JFXButton voirPlus_button;

    private FXMLLoader editTaskLoader;

    public TaskDoneController() {
    }
    @FXML
    public void initialize() throws IOException {
        Stage editTaskStage = new Stage();
        editTaskLoader = new FXMLLoader(getClass().getResource("../views/EditTask.fxml"));
        editTaskStage.setScene(new Scene(editTaskLoader.load()));
        editTaskStage.setResizable(false);
        editTaskStage.initModality(Modality.APPLICATION_MODAL);
        listOperations_listView.setItems(FXCollections.observableList(Operation.listByEtat("effectuée")));
        voirPlus_button.setOnAction(event -> {
                Operation op = listOperations_listView.getSelectionModel().getSelectedItem();
                if (op != null){                   
                    ((EditTaskController)editTaskLoader.getController()).setContent(op);
                    editTaskStage.show();
                }
        });
    }    
    
    public void updateListView(Operation op){
        listOperations_listView.getItems().add(op);       
    }
}
