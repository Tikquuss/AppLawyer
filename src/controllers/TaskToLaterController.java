
package controllers;

import appdatabase.bean.Operation;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTimePicker;
import static controllers.PlanningController.tasksPassed;
import static controllers.CurrentFoldersController.currentFolder;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utilities.NotifExample;


public class TaskToLaterController  {

    @FXML
    private JFXListView<Operation> listeTaches_listView;
    @FXML
    private JFXButton initTache_button;
    @FXML
    private JFXButton finaliseTache_button;
    @FXML
    private JFXButton deleteTache_button;
    @FXML
    private JFXButton programNewTache_button;
    @FXML
    private TextField labelNewTache_textField;
    @FXML
    private JFXDatePicker dateNewTache_datePicker;
    @FXML
    private JFXTimePicker heureNewTache_timePicker;
    @FXML
    private JFXButton valider_button;
    
    public static Stage stageTask;
    private Parent finalizeTaskParent;
    private FXMLLoader finalizeTaskLoader;

    @FXML
    public void initialize() throws IOException {
        finalizeTaskLoader = new FXMLLoader(getClass().getResource("../views/FinalizeTask.fxml"));
        finalizeTaskParent = finalizeTaskLoader.load();
        initButtonsActions();
        initListView();
    }   

    
    public void initListView(){
        listeTaches_listView.setItems(FXCollections.observableList(Operation.listByDifEtat("effectuée", currentFolder)));
    }
    
    public void removeFromListView(Operation op){
        listeTaches_listView.getItems().remove(op);
    }
    
    public boolean checkNoEmptyField(){
        return !(labelNewTache_textField.getText().equals("") || dateNewTache_datePicker.getValue() == null || heureNewTache_timePicker.getValue() == null);
    }
    public void initButtonsActions(){
        stageTask = new Stage();
        stageTask.setScene(new Scene(finalizeTaskParent));
        stageTask.setResizable(false);
        stageTask.initModality(Modality.APPLICATION_MODAL);
        valider_button.setOnAction(event -> {
            if(this.checkNoEmptyField()){
                LocalDateTime date = LocalDateTime.of(dateNewTache_datePicker.getValue(), heureNewTache_timePicker.getValue());
                if(date.isAfter(LocalDateTime.now())){
                    Operation op = new Operation(labelNewTache_textField.getText(), date, currentFolder);
                    op.save();
                    listeTaches_listView.getItems().add(op);
                    NotifExample.setAlarmTask(date, labelNewTache_textField.getText());
                }
                else{
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setContentText("Vous ne pouvez pas programmer une tâche à une date antérieure à l'heure actuelle !");
                    al.setHeaderText("ERREUR DE CREATION DE TACHE");
                    al.show();
                }
                //listeTaches_listView.setItems(FXCollections.observableList(Operation.listByDifEtat("effectuée")));
            }
            else{
                Alert al = new Alert(Alert.AlertType.WARNING);
                    al.setContentText("Veuillez remplir ces 03 champs s'il vous plait");
                    al.setHeaderText("CHAMPS VIDES");
                    al.show();
            }
        });
        
        initTache_button.setOnAction(event -> {
            Operation op = listeTaches_listView.getSelectionModel().getSelectedItem();

            if(op != null ){
                if("En cours".equals(op.getEtat())){
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setContentText("Cette tâche a déjà débutée !");
                    al.setHeaderText("OPERAION DEJA EN COURS");
                    al.show();
                }
                else{
                    int indOp = listeTaches_listView.getItems().indexOf(op);
                    op.setEtat("En cours");
                    op.setDateDebut(LocalDateTime.now());
                    op.update();                
                    listeTaches_listView.getItems().set(indOp, op);
                }
            }
            else{
                displaySelectionError();
            }
            //listeTaches_listView.setItems(FXCollections.observableArrayList(Operation.listByDifEtat("effectuée")));
           });
        
        finaliseTache_button.setOnAction((ActionEvent event) -> {
            Operation op = listeTaches_listView.getSelectionModel().getSelectedItem();
            if(op != null){
                if( op.getEtat().equals("En cours")){
                ((FinalizeTaskController)finalizeTaskLoader.getController()).setContent(op);
                stageTask.show();
                }
                else{
                        Alert al = new Alert(Alert.AlertType.WARNING);
                        al.setContentText("Vous ne pouvez pas finaliser une tâche qui n'a pas encore débuté !");
                        al.setHeaderText("ERREUR");
                        al.show();
                }
            }

            else{
                displaySelectionError();
            }         
            //listeTaches_listView.setItems(FXCollections.observableList(Operation.listByDifEtat("effectuée")));            
        });
        
        deleteTache_button.setOnAction(event -> {
             Operation op = listeTaches_listView.getSelectionModel().getSelectedItem();
             if(op != null){
                listeTaches_listView.getItems().remove(op);
                op.delete();
            }
             else{
                 displaySelectionError();
             }
             
             //listeTaches_listView.setItems(FXCollections.observableList(Operation.listByDifEtat("effectuée")));
        });
 
    }
    public void displaySelectionError(){
        Alert al = new Alert(Alert.AlertType.ERROR);
        al.setContentText("Vous n'avez sélectionné aucune tâche !");
        al.setHeaderText("AUCUNE TACHE SELECTIONNEE");
        al.show();
    }
    
}
