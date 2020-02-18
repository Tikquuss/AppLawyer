package controllers;

import appdatabase.bean.Dossier;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static main.AppLawyer.stage;
        
public class CurrentFoldersController {

    @FXML
    private JFXListView<Dossier> listFolders_listView;

    @FXML
    private JFXButton openFolder_button;

    @FXML
    private JFXButton newFolder_button;
    
    private Stage newFolderStage, singleFolderStage;
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
    }
        
    public boolean checkIfFolderExist(Dossier fold){
            File dir = new File(fold.getCheminDossier());
            boolean exist = false;
            if(!(exist = dir.exists()))
                dir.mkdirs();
            return exist;         
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
        openFolder_button.setOnAction(event -> {  
            
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
                    Alert al = new Alert(Alert.AlertType.WARNING);
                    al.setContentText("Le chemin vers le repertoire correspondant à ce dossier n'a pas été retrouvé. Il a peut ëtre été supprimé.");
                    al.show();
                }
            }
            else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setContentText("Veuillez sélectionner un dossier dans la liste avant de cliquer sur ce bouton !");
                al.show();
            }
        });
    }
    
    public void makeStageForSingleFolder(Dossier dossier) throws IOException{
        currentFolder = dossier;
        homeForFolder =  new FXMLLoader(getClass().getResource("../views/homeForFolder.fxml"));
        root = homeForFolder.load();
       /* stage.setScene(new Scene(root));
        stage.setMinWidth(1100);
        stage.setMinHeight(700);*/
        singleFolderStage = new Stage();
        singleFolderStage.setScene(new Scene(root));
        singleFolderStage.setMinWidth(1100);
        singleFolderStage.setMinHeight(700);
        singleFolderStage.show();
    }
    
    public void makeStageForCreateFolder() throws IOException{
        newFolderStage = new Stage();
        newFoldLoader = new FXMLLoader(getClass().getResource("../views/CreateFolder.fxml"));
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
    
}
