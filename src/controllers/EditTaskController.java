/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import appdatabase.bean.Operation;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import utilities.GestionSpinner;
import static controllers.TaskDoneController.editTaskStage;
/**
 * FXML Controller class
 *
 * @author Utilisateur
 */
public class EditTaskController {

    @FXML
    private TextArea compteRendu_textArea;
    @FXML
    private TextField depenses_textField;
    @FXML
    private Label dateHeureDebut_label;
    @FXML
    private JFXButton modifier_button;
    @FXML
    private Label datePrevue_label;
    @FXML
    private Label dateHeureFin_label;
    @FXML
    private Label labelTache_label;
    
    private Operation operation;

    @FXML
    public void initialize() {
        
        modifier_button.setOnAction(event -> {
            editTaskStage.close();
        });     
        initTextFieldForNumbers();
    }    
    
    public void initTextFieldForNumbers(){
        UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>() {
            @Override
            public TextFormatter.Change apply(TextFormatter.Change t) {
                String newText = t.getControlNewText() ;
                if(newText.matches("-?[0-9]*")) {
                    return t ;
                }
                return null ;
            }
        };
        this.depenses_textField.setTextFormatter(new TextFormatter<>(filter));
    }
    public void setContent(Operation operation){
        this.operation = operation;
    }
    
    public void initView(){
        this.labelTache_label.setText(operation.getTache());
        this.compteRendu_textArea.setText(operation.getCompteRendu());
        this.dateHeureDebut_label.setText(operation.dateFormatter(operation.getDateDebut()));
        this.depenses_textField.setText(String.valueOf((int)operation.getDepenses()));
        this.dateHeureFin_label.setText(operation.dateFormatter(operation.getDateFin()));
        this.datePrevue_label.setText(operation.dateFormatter(operation.getDate()));
    }
}
