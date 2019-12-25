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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import utilities.ViewDimensionner;


public class PlanningController {

    @FXML
    private AnchorPane planningContainer;

    @FXML
    private JFXButton toDone;

    @FXML
    private JFXButton done;

    private Parent taskLaterParent, taskPassedParent; 
    @FXML
    public void initialize() throws IOException {
        taskLaterParent = FXMLLoader.load(getClass().getResource("../views/TaskToLater.fxml"));
        taskPassedParent = FXMLLoader.load(getClass().getResource("../views/TaskDone.fxml"));
        this.initActions();
    }    
    
    
    public void add(Parent parent){
        if(!planningContainer.getChildren().contains(parent)){
            planningContainer.getChildren().clear();
            planningContainer.getChildren().add(parent);
            ViewDimensionner.bindSizes((Region)parent, planningContainer);

        }        
    }
    public void initActions(){
        toDone.setOnAction(event -> {
            this.add(taskLaterParent);
        });
        done.setOnAction(event -> {
            this.add(taskPassedParent);
        });
    }
    
}
