/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import appdatabase.bean.Document;
import appdatabase.bean.Operation;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import static controllers.CurrentFoldersController.currentFolder;
import javafx.scene.control.Alert;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
        initEditTaskStage();
        manageTable();
        voirPlus_button.setOnAction(event -> {
            openTasksDetails();
        });
        listeOperation_tableView.setOnKeyPressed((KeyEvent t) ->{
            KeyCode key=t.getCode();
            if(key == KeyCode.ENTER){
                    openTasksDetails();
                }
            });
        activeDoubleClickOnTableV();
        }   
    
    public void initEditTaskStage() throws IOException{
        editTaskStage = new Stage();
        editTaskLoader = new FXMLLoader(getClass().getResource("../views/EditTask.fxml"));
        editTaskStage.setScene(new Scene(editTaskLoader.load()));
        editTaskStage.setResizable(false);
        editTaskStage.initModality(Modality.APPLICATION_MODAL);
    }
    
    public void manageTable(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        tache_tableColumn.setCellValueFactory(new PropertyValueFactory<Operation, String>("tache"));
        date_tableColumn.setCellValueFactory(value -> new ReadOnlyObjectWrapper<String>(value.getValue().getDate().format(formatter)));
        dateInit_tableColumn.setCellValueFactory(value -> new ReadOnlyObjectWrapper<>(value.getValue().getDateDebut().format(formatter)));
        dateFin_tableColumn.setCellValueFactory(value -> new ReadOnlyObjectWrapper<>(value.getValue().getDateFin().format(formatter)));
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
    
    
    public void openTasksDetails(){
        Operation op = listeOperation_tableView.getSelectionModel().getSelectedItem();
        if (op != null){                   
            ((EditTaskController)editTaskLoader.getController()).setContent(op);
            ((EditTaskController)editTaskLoader.getController()).initView();
            editTaskStage.show();
        }
        else{
            displaySelectionError();
        }
    }
    
        
    public void activeDoubleClickOnTableV(){
        listeOperation_tableView.setRowFactory(tv -> {
            TableRow<Operation> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    openTasksDetails();
                }
            });
             return row;       
        });
    }
}
