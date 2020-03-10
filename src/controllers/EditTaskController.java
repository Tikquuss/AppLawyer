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
import static controllers.TaskDoneController.editTaskStage;
import static controllers.CurrentFoldersController.currentFolder;
import java.time.format.DateTimeFormatter;
/**
 * FXML Controller class
 *
 * @author Nyatchou
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
            this.operation.setCompteRendu(compteRendu_textArea.getText());
            this.operation.setDepenses(Long.valueOf(depenses_textField.getText()));
            this.operation.update();
            editTaskStage.close();
        });     
        initTextFieldForNumbers();
        disableFields();
    }    
    
    public void initTextFieldForNumbers(){
        UnaryOperator<TextFormatter.Change> filter = (TextFormatter.Change t) -> {
            String newText = t.getControlNewText() ;
            if(newText.matches("-?[0-9]*") && newText.length() <= 13) {
                return t ;
            }
            return null ;
        };
        this.depenses_textField.setTextFormatter(new TextFormatter<>(filter));
    }
    public void setContent(Operation operation){
        this.operation = operation;
    }
    
    public void initView(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy à HH:mm");
        this.labelTache_label.setText(operation.getTache());
        this.compteRendu_textArea.setText(operation.getCompteRendu());
        this.dateHeureDebut_label.setText(operation.getDateDebut().format(formatter));
        this.depenses_textField.setText(String.valueOf((int)operation.getDepenses()));
        this.dateHeureFin_label.setText(operation.getDateFin().format(formatter));
        this.datePrevue_label.setText(operation.getDate().format(formatter));
    }
    
    public void disableFields(){
        if(currentFolder.getStatut().equals("Archivé")){
            this.depenses_textField.setDisable(true);
            this.compteRendu_textArea.setDisable(true);
            modifier_button.setVisible(false);
        }
    }
}
