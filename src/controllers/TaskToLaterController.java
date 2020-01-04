
package controllers;

import appdatabase.bean.Operation;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTimePicker;
import static controllers.PlanningController.tasksPassed;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import utilities.ConcatList;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;


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
    
    private Parent finalizeTaskParent;
    private FXMLLoader finalizeTaskLoader;

    @FXML
    public void initialize() throws IOException {
        finalizeTaskLoader = new FXMLLoader(getClass().getResource("../views/FinalizeTask.fxml"));
        finalizeTaskParent = finalizeTaskLoader.load();
        initButtonsActions();
        initListView();
        System.out.println(Operation.all());
    }   

    
    public void initListView(){
        listeTaches_listView.setItems(FXCollections.observableList(Operation.listByDifEtat("effectuée")));
    }
    
    public void removeFromListView(Operation op){
        listeTaches_listView.getItems().remove(op);
    }
    
    public boolean checkNoEmptyField(){
        return !(labelNewTache_textField.getText() == "" || dateNewTache_datePicker.getValue() == null || heureNewTache_timePicker.getValue() == null);
    }
    public void initButtonsActions(){
        Stage stageTask = new Stage();
        stageTask.setScene(new Scene(finalizeTaskParent));
        stageTask.setResizable(false);
        stageTask.initModality(Modality.APPLICATION_MODAL);
        valider_button.setOnAction(event -> {
            if(this.checkNoEmptyField()){
                Operation op = new Operation(labelNewTache_textField.getText(), LocalDateTime.of(dateNewTache_datePicker.getValue(), heureNewTache_timePicker.getValue()));
                op.save();
                listeTaches_listView.getItems().add(op);
                //listeTaches_listView.setItems(FXCollections.observableList(Operation.listByDifEtat("effectuée")));
            }
        });
        
        initTache_button.setOnAction(event -> {
            Operation op = listeTaches_listView.getSelectionModel().getSelectedItem();
            int indOp = listeTaches_listView.getItems().indexOf(op);

            if(op != null){
                  op.setEtat("En cours");
                  op.setDateDebut(LocalDateTime.now());
                  op.update();
            }
            listeTaches_listView.getItems().set(indOp, op);
            //listeTaches_listView.setItems(FXCollections.observableArrayList(Operation.listByDifEtat("effectuée")));
           });
        
        finaliseTache_button.setOnAction(event -> {
            Operation op = listeTaches_listView.getSelectionModel().getSelectedItem();
            System.out.println(op);
            int indOp = listeTaches_listView.getItems().indexOf(op);
            if(op != null && op.getEtat()=="En cours"){
                ((FinalizeTaskController)finalizeTaskLoader.getController()).setContent(op);
                 stageTask.show();         
            }
            listeTaches_listView.getItems().set(indOp, op);
            //listeTaches_listView.setItems(FXCollections.observableList(Operation.listByDifEtat("effectuée")));            
        });
        
        deleteTache_button.setOnAction(event -> {
             Operation op = listeTaches_listView.getSelectionModel().getSelectedItem();
             if(op != null){
                op.delete();
            }
             listeTaches_listView.getItems().remove(op);
             //listeTaches_listView.setItems(FXCollections.observableList(Operation.listByDifEtat("effectuée")));
        });
 
    }
    
}
