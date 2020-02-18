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


public class PlanningController {

    @FXML
    private JFXButton taskToDone_button;

    @FXML
    private JFXButton taskDone_button;

    @FXML
    private AnchorPane planningContainer;

    private Parent tasksLaterParent, tasksPassedParent; 
    
    public static FXMLLoader tasksPassed, tasksLater;
 
    @FXML
    public void initialize() throws IOException {
        tasksPassed = new FXMLLoader(getClass().getResource("../views/TaskDone.fxml"));
        tasksLater = new FXMLLoader(getClass().getResource("../views/TaskToLater.fxml"));
        tasksLaterParent = tasksLater.load();
        tasksPassedParent = tasksPassed.load();
        this.add(tasksLaterParent);
        this.initButtonsActions();
    }    
    
    
    public void add(Parent parent){
        if(!planningContainer.getChildren().contains(parent)){
            planningContainer.getChildren().clear();
            planningContainer.getChildren().add(parent);
            ViewDimensionner.bindSizes((Region)parent, planningContainer);

        }        
    }
    public void initButtonsActions(){
        taskToDone_button.setOnAction(event -> {
            this.add(tasksLaterParent);
        });
        taskDone_button.setOnAction(event -> {
            this.add(tasksPassedParent);
        });
    }
    
}
