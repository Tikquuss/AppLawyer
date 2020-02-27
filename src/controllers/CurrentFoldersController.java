package controllers;

import appdatabase.bean.Dossier;
import appdatabase.bean.Operation;
import appdatabase.bean.Payement;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static main.AppLawyer.stage;
import static controllers.PresentPageController.endedFoldersLoader;
import main.AppLawyer;
        
public class CurrentFoldersController {

    @FXML
    private JFXListView<Dossier> listFolders_listView;

    @FXML
    private JFXButton openFolder_button;

    @FXML
    private JFXButton newFolder_button;
    
    @FXML
    private JFXButton endFolder_button;

    @FXML
    private JFXButton deleteFolder_button;
    
    private Stage  singleFolderStage;
    public static Stage newFolderStage;
    private FXMLLoader homeForFolder, homeClientFolder;
    private Parent root,  newFoldParent, homeClientFolderParent;
    public static Dossier currentFolder;
    public static FXMLLoader newFoldLoader;
    
            
    public void initialize() throws IOException{
        initListView();
        initButtonsActions();
        Dossier.all().forEach( fold -> {
            checkIfFolderExist(fold);
        });
        activeDoubleClick();
    }
        
    public boolean checkIfFolderExist(Dossier fold){
            File dir = new File(fold.getCheminDossier());
            return dir.exists();         
        }
    
    
    public void initButtonsActions() throws IOException{      
        newFolder_button.setOnAction(e -> {
            try {
                //String dirName = "F:\\Dossier Client\\";
                //File dir = new File(dirName);
                //boolean isCreated = dir.mkdirs();
                makeStageForCreateFolder();
            } catch (IOException ex) {
                Logger.getLogger(CurrentFoldersController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        
        
        listFolders_listView.setOnKeyPressed((KeyEvent t)-> {
            KeyCode key=t.getCode();
            if(key == KeyCode.ENTER){
                openFoldAction();
            }         
        });
        
        openFolder_button.setOnAction(event -> {             
            openFoldAction();
        });
        
        deleteFolder_button.setOnAction(e -> {
            Dossier fold   = listFolders_listView.getSelectionModel().getSelectedItem();
            currentFolder = fold;
            if(fold != null){
                if(checkIfFolderExist(fold)){
                    fold.setStatut("Supprimé");
                }
                else{
                    displayFolderDontExists();
                }
                        }
            else{
                displayNoFolderSelected();
            }
        });
        deleteFolder_button.setDisable(true);
        endFolder_button.setOnAction(e -> {
            Dossier fold   = listFolders_listView.getSelectionModel().getSelectedItem();
            currentFolder = fold;
            if(fold != null){
                if(checkIfFolderExist(fold)){
                    Alert dialogConfirm = new Alert(Alert.AlertType.CONFIRMATION);
                    dialogConfirm.setTitle("Confirmation fin du dossier");
                    dialogConfirm.setHeaderText("Confirmation archivage");
                    dialogConfirm.setContentText("Vous êtes sur le point d'archiver ce dossier, voulez-vous continuer ??");
                    Optional<ButtonType> answer = dialogConfirm.showAndWait();
                    if (answer.get() == ButtonType.OK) {
                        if(calcMontantPaiement(fold)<fold.getHonoraires()){
                            Alert dialogConfirmArch = new Alert(Alert.AlertType.CONFIRMATION);
                            dialogConfirmArch.setContentText("Vous n'avez pas été totalement payé, tenez-vous quand même à archiver ce dossier ??");
                            Optional<ButtonType> answerF = dialogConfirmArch.showAndWait();
                            if (answerF.get() == ButtonType.OK) {
                                if(Operation.listByDifEtat("effectuée", currentFolder).isEmpty()){
                                    fold.setStatut("Archivé");
                                    fold.update();
                                    listFolders_listView.getItems().remove(fold);
                                    ((EndedFoldersController)endedFoldersLoader.getController()).initListView();
                                    }
                                else{
                                        displayTasksDontEnded();
                                    }
                                }
                            }
                        else{
                            fold.setStatut("Archivé");
                            fold.update();
                            listFolders_listView.getItems().remove(fold);
                            ((EndedFoldersController)endedFoldersLoader.getController()).initListView();
                        }
                      }
                    }           
                
                else{
                    displayFolderDontExists();
                }
            }      
            else{
                displayNoFolderSelected();
            }
        });
    }
    
    public void makeStageForSingleFolder(Dossier dossier) throws IOException{
        currentFolder = dossier;
        homeForFolder =  new FXMLLoader(getClass().getResource("/views/HomeForFolder.fxml"));
        root = homeForFolder.load();
        stage.setScene(new Scene(root));
        stage.setMinWidth(1100);
        stage.setMinHeight(700);

    }
    
    public void openFoldAction(){
        Dossier fold   = listFolders_listView.getSelectionModel().getSelectedItem();
        currentFolder = fold;
        if(fold != null){
            if(checkIfFolderExist(fold))
                try {     
                     makeStageForSingleFolder(fold);

                } catch (IOException ex) {
                    Logger.getLogger(CurrentFoldersController.class.getName()).log(Level.SEVERE, null, ex);
                }
            else{
                    displayFolderDontExists();
            }
        }
        else {
            displayNoFolderSelected();
        }
    }
    
    public void activeDoubleClick(){
        listFolders_listView.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2 && event.getTarget() != null)
                openFoldAction();
        });
    }
    
    public void makeStageForCreateFolder() throws IOException{
        newFolderStage = new Stage();
        newFoldLoader = new FXMLLoader(getClass().getResource("/views/CreateFolder.fxml"));
        newFoldParent = newFoldLoader.load();
        newFolderStage.setScene(new Scene(newFoldParent));
        newFolderStage.setResizable(false);
        newFolderStage.initModality(Modality.APPLICATION_MODAL);
        newFolderStage.show();
    }
    public void initListView(){
        listFolders_listView.setItems(FXCollections.observableList(Dossier.listByStatus("En cours")));
    }
    public void addToListView(Dossier doc){
        listFolders_listView.getItems().add(doc);
    }
    
    public void displayFolderDontExists(){
        Alert al = new Alert(Alert.AlertType.WARNING);
        al.setContentText("Le chemin vers le repertoire correspondant à ce dossier n'a pas été retrouvé. Il a peut ëtre été supprimé.");
        al.show();
    }
    
    public void displayNoFolderSelected(){
        Alert al = new Alert(Alert.AlertType.ERROR);
        al.setContentText("Veuillez sélectionner un dossier dans la liste avant de cliquer sur ce bouton !");
        al.show();
    }
    public double calcMontantPaiement(Dossier doc){
        double paye = 0;
        List<Payement> payeList = Payement.listByDossier(doc);
        for (int i = 0; i< payeList.size(); i++)
            paye += payeList.get(i).getMontant();         
        return paye;
    }
    
    public void displayTasksDontEnded(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("TACHES NON FINALISEES");
        alert.setContentText("Les tâches programmées n'ont pas toutes été finalisées !");
        alert.show();
    }
    
}
