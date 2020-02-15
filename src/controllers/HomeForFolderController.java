
package controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import utilities.ViewDimensionner;
import static controllers.CurrentFoldersController.currentFolder;


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
             homeClientFolder = new FXMLLoader(getClass().getResource("../views/homeClientFolder.fxml"));
             homeCFParent = homeClientFolder.load();
             ((HomeClientFolderController)homeClientFolder.getController()).setContent(currentFolder);
             planningParent = FXMLLoader.load(getClass().getResource("../views/Planning.fxml"));
             docsLoader = new FXMLLoader(getClass().getResource("../views/Documents.fxml"));
             docParent =  docsLoader.load();
             this.add(homeCFParent);
             this.initActions();
    }
    
    public void initActions(){
          homefolder_button.setOnAction(event -> {
              this.add(homeCFParent);
          });
          planning_button.setOnAction(event -> {
              this.add(planningParent);
          });
          documents_button.setOnAction(event -> {
               this.add(docParent);
          });
    }    
}
