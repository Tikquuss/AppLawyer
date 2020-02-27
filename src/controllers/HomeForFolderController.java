
package controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import utilities.ViewDimensionner;
import static controllers.CurrentFoldersController.currentFolder;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import static main.AppLawyer.stage;
import static controllers.ConnexionController.presentPageScene;
/**
 *
 * @author Utilisateur
 */
public class HomeForFolderController {
    
    @FXML
    private JFXButton homefolder_button;

    @FXML
    private JFXButton planning_button;

    @FXML
    private JFXButton documents_button;  
    
    @FXML
    private JFXButton backhome_button;

    @FXML
    private AnchorPane singleFolderContainer;
    
    private Parent homeCFParent, planningParent, docParent;
    private FXMLLoader homeClientFolder;
    public static FXMLLoader docsLoader;
    public void add(Parent parent){
      if(!singleFolderContainer.getChildren().contains(parent)){
          singleFolderContainer.getChildren().clear();
          singleFolderContainer.getChildren().add(parent);
          ViewDimensionner.bindSizes((Region)parent, singleFolderContainer);
      }
    }
         
    @FXML
    public void initialize() throws IOException{
             homeClientFolder = new FXMLLoader(getClass().getResource("/views/HomeClientFolder.fxml"));
             homeCFParent = homeClientFolder.load();
             ((HomeClientFolderController)homeClientFolder.getController()).setContent(currentFolder);
             planningParent = FXMLLoader.load(getClass().getResource("/views/Planning.fxml"));
             docsLoader = new FXMLLoader(getClass().getResource("/views/Documents.fxml"));
             docParent =  docsLoader.load();
             this.add(homeCFParent);
             this.initButtonsActions();
    }
    
    public void initButtonsActions(){
          homefolder_button.setOnAction(event -> {
              this.add(homeCFParent);
          });
          planning_button.setOnAction(event -> {
              this.add(planningParent);
          });
          documents_button.setOnAction(event -> {
               this.add(docParent);
          });
          backhome_button.setOnAction((ActionEvent e) -> {
              Alert dialogConfirm = new Alert(Alert.AlertType.CONFIRMATION);
              dialogConfirm.setTitle("Confirmation du retour à l'accueil");
              dialogConfirm.setHeaderText("Confirmation du retour à l'accueil");
              dialogConfirm.setContentText("Voulez vous vraiment retourner à l'accueil ??");
              Optional<ButtonType> answer = dialogConfirm.showAndWait();
              if (answer.get() == ButtonType.OK) {
                    stage.setScene(presentPageScene);
              }
          });
    }    
}
