
package controllers;

import appdatabase.bean.Operation;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTimePicker;
import static controllers.CurrentFoldersController.currentFolder;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static main.AppLawyer.stage;
import utilities.NotifExample;

/**
 ** FXML Controller class
 *
 * @author Nyatchou
 */
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
    @FXML
    private GridPane createTask_gridPane;
    
    public static Stage stageTask;
    private Parent finalizeTaskParent;
    private FXMLLoader finalizeTaskLoader;

    @FXML
    public void initialize() throws IOException {
        
        initButtonsActions();
        initListView();
        initKeyBoardsActions();
    }   

    
    public void initListView(){
        listeTaches_listView.setItems(FXCollections.observableList(Operation.listByDifEtat("effectuée", currentFolder)));
    }
    
    public void initTextField(){
        UnaryOperator<TextFormatter.Change> filter = (TextFormatter.Change t) -> {
            String newText = t.getControlNewText();
            if(newText.matches("^([^\\s].*)*$") && newText.length()< 80) {
                return t ;
            }
            return null ;
        };
        labelNewTache_textField.setTextFormatter(new TextFormatter<>(filter));
    }
    
    public void removeFromListView(Operation op){
        listeTaches_listView.getItems().remove(op);
    }
    
    public boolean checkNoEmptyField(){
        return !(labelNewTache_textField.getText().equals("") || dateNewTache_datePicker.getValue() == null || heureNewTache_timePicker.getValue() == null);
    }
    public void createTaskAction(){
        if(this.checkNoEmptyField()){
            LocalDateTime date = LocalDateTime.of(dateNewTache_datePicker.getValue(), heureNewTache_timePicker.getValue());
            if(date.isAfter(LocalDateTime.now())){
                Operation op = new Operation(labelNewTache_textField.getText(), date, currentFolder);
                op.save();
                listeTaches_listView.getItems().add(op);              
                NotifExample.setAlarmTask(date, op);
                NotifExample.setAlarmTaskOneDay(date, op);
            }
            else{
                Alert al = new Alert(Alert.AlertType.ERROR);
                ((Stage)al.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/ressources/images/icon_lawyer2.png"));
                al.setContentText("Vous ne pouvez pas programmer une tâche à une date antérieure à l'heure actuelle !");
                al.setHeaderText("ERREUR DE CREATION DE TACHE");
                al.show();
            }
            //listeTaches_listView.setItems(FXCollections.observableList(Operation.listByDifEtat("effectuée")));
        }
        else{
            Alert al = new Alert(Alert.AlertType.WARNING);
            ((Stage)al.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/ressources/images/icon_lawyer2.png"));
            al.setContentText("Veuillez remplir ces 03 champs s'il vous plait");
            al.setHeaderText("CHAMPS VIDES");
            al.show();
        }
    }
    public void initTaskAction(){
        Operation op = listeTaches_listView.getSelectionModel().getSelectedItem();
        if(op != null ){
            if("En cours".equals(op.getEtat())){
                Alert al = new Alert(Alert.AlertType.ERROR);
                ((Stage)al.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/ressources/images/icon_lawyer2.png"));
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
    }
    public void finalizeTaskAction(){
        Operation op = listeTaches_listView.getSelectionModel().getSelectedItem();
        if(op != null){
            if( op.getEtat().equals("En cours")){
            ((FinalizeTaskController)finalizeTaskLoader.getController()).setContent(op);
            stageTask.show();
            }
            else{
                    Alert al = new Alert(Alert.AlertType.WARNING);
                    ((Stage)al.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/ressources/images/icon_lawyer2.png"));
                    al.setContentText("Vous ne pouvez pas finaliser une tâche qui n'a pas encore débuté !");
                    al.setHeaderText("ERREUR");
                    al.show();
            }
        }

        else{
            displaySelectionError();
        }  
    }
    public void deleteTaskAction(){
        Operation op = listeTaches_listView.getSelectionModel().getSelectedItem();
        if(op != null){
            Alert dialogConfirm = new Alert(Alert.AlertType.CONFIRMATION);
            ((Stage)dialogConfirm.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/ressources/images/icon_lawyer2.png"));
            dialogConfirm.setTitle("Confirmation d'annulation");
            dialogConfirm.setHeaderText("Confirmation d' annulation de tache");
            dialogConfirm.setContentText("Tenez vous vraiment à annuler cette tâche ??");
            Optional<ButtonType> answer = dialogConfirm.showAndWait();
            if (answer.get() == ButtonType.OK) {
                listeTaches_listView.getItems().remove(op);
                op.delete();
              }
            }           
        else{
             displaySelectionError();
        }
    }
    public void initButtonsActions() throws IOException{
        finalizeTaskLoader = new FXMLLoader(getClass().getResource("/views/FinalizeTask.fxml"));
        finalizeTaskParent = finalizeTaskLoader.load();
        stageTask = new Stage();
        stageTask.setScene(new Scene(finalizeTaskParent));
        stageTask.initStyle(StageStyle.UTILITY);
        stageTask.initOwner(stage);
        stageTask.initModality(Modality.APPLICATION_MODAL);
        valider_button.setOnAction(event -> {
            createTaskAction();
        });
        
        initTache_button.setOnAction(event -> {
            initTaskAction();
            //listeTaches_listView.setItems(FXCollections.observableArrayList(Operation.listByDifEtat("effectuée")));
           });
        
        finaliseTache_button.setOnAction((ActionEvent event) -> {
            finalizeTaskAction();
            //listeTaches_listView.setItems(FXCollections.observableList(Operation.listByDifEtat("effectuée")));            
        });
        
        deleteTache_button.setOnAction(event -> {
            deleteTaskAction();
             //listeTaches_listView.setItems(FXCollections.observableList(Operation.listByDifEtat("effectuée")));
        });
    }
    
    public void initKeyBoardsActions(){
        createTask_gridPane.setOnKeyPressed((KeyEvent t) -> {
            KeyCode key = t.getCode();
            if(key == KeyCode.ENTER){
                createTaskAction();
            }
        });
        listeTaches_listView.setOnKeyPressed((KeyEvent t) -> {
            KeyCode key = t.getCode();
            if(key == KeyCode.DELETE){
                deleteTaskAction();
            }
        });
    }
    
    public void displaySelectionError(){
        Alert al = new Alert(Alert.AlertType.ERROR);
        ((Stage)al.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/ressources/images/icon_lawyer2.png"));
        al.setContentText("Vous n'avez sélectionné aucune tâche !");
        al.setHeaderText("AUCUNE TACHE SELECTIONNEE");
        al.show();
    }
    
}
