/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import static controllers.ConnexionController.presentPageScene;
import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import static main.AppLawyer.stage;
import utilities.ViewDimensionner;

/**
 * FXML Controller class
 *
 * @author Nyatchou
 */
public class AdminController{

    @FXML
    private JFXButton documents_button;
    @FXML
    private JFXButton paramDossier_button;
    @FXML
    private AnchorPane containerAdmin_anchorPane;
    @FXML
    private JFXButton backhome_button;
    
    private FXMLLoader adminForDocument, adminForFoldersParam;
    private Parent adminForDocParent, adminFoldersParamParent;
            
    @FXML
    public void initialize() throws IOException {
        initLoader();
        this.add(adminForDocParent);
        initButtonsActions();
    } 
    

    public void initLoader() throws IOException{
        adminForDocument = new FXMLLoader(getClass().getResource("/views/AdminDocument.fxml"));
        adminForFoldersParam = new FXMLLoader(getClass().getResource("/views/Admin2.fxml"));
        adminForDocParent = adminForDocument.load();
        adminFoldersParamParent = adminForFoldersParam.load();
    }
    
    public void add(Parent parent){
        if(!containerAdmin_anchorPane.getChildren().contains(parent)){
            containerAdmin_anchorPane.getChildren().clear();
            containerAdmin_anchorPane.getChildren().add(parent);
            ViewDimensionner.bindSizes((Region)parent, containerAdmin_anchorPane);
        }
    }
    public void initButtonsActions(){
        documents_button.setOnAction(e -> {
            this.add(adminForDocParent);
        });
        paramDossier_button.setOnAction(e -> {
            this.add(adminFoldersParamParent);
        });
        backhome_button.setOnAction((ActionEvent e) -> {
              Alert dialogConfirm = new Alert(Alert.AlertType.CONFIRMATION);
              ((Stage)dialogConfirm.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/ressources/images/icon_lawyer2.png"));
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
