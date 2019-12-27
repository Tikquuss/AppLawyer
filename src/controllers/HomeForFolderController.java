
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
    
    public void add(Parent parent){
      if(!singleFolderContainer.getChildren().contains(parent)){
          singleFolderContainer.getChildren().clear();
          singleFolderContainer.getChildren().add(parent);
          ViewDimensionner.bindSizes((Region)parent, singleFolderContainer);
      }
    }
         
    @FXML
    public void initialize() throws IOException{
             homeCFParent =  FXMLLoader.load(getClass().getResource("../views/HomeClientFolder.fxml"));
             planningParent = FXMLLoader.load(getClass().getResource("../views/Planning.fxml"));
             docParent =FXMLLoader.load(getClass().getResource("../views/Documents.fxml"));
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
