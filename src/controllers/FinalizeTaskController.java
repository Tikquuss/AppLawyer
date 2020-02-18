/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import appdatabase.bean.Operation;
import com.jfoenix.controls.JFXButton;
import java.util.function.UnaryOperator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import static controllers.PlanningController.tasksPassed;
import static controllers.PlanningController.tasksLater;
import static controllers.TaskToLaterController.stageTask;
import java.time.LocalDateTime;
/**
 * FXML Controller class
 *
 * @author Utilisateur
 */
public class FinalizeTaskController  {

    @FXML
    private TextArea compteRendu_textArea;
    @FXML
    private TextField depenses_textField;
    @FXML
    private Label labelTache_label;
    @FXML
    private JFXButton save_button;

    private Operation op;
    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        
        save_button.setOnAction(event -> {
            op.setEtat("effectuée");
            op.setDateFin(LocalDateTime.now());
            op.setCompteRendu(compteRendu_textArea.getText());
            if(depenses_textField.getText().equals(""))
                op.setDepenses(0);
            else{
                op.setDepenses(Integer.valueOf(depenses_textField.getText()));   
            }
            op.update();
            ((TaskDoneController)tasksPassed.getController()).updateListView(op);
            ((TaskToLaterController)tasksLater.getController()).removeFromListView(op);
             stageTask.close();
        });
        initTextFieldForNumbers();
    }    
    public void initTextFieldForNumbers(){
        UnaryOperator<TextFormatter.Change> filter = (TextFormatter.Change t) -> {
            String newText = t.getControlNewText() ;
            if(newText.matches("-?[0-9]*")) {
                return t ;
            }
            return null ;
        };
        this.depenses_textField.setTextFormatter(new TextFormatter<>(filter));
    }
    public void setContent(Operation operation){
        labelTache_label.setText(operation.getTache());
        this.op = operation;
    }
    

    
    
}
