/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import appdatabase.bean.Client;
import appdatabase.bean.Document;
import appdatabase.bean.Dossier;
import appdatabase.bean.Operation;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import static controllers.PresentPageController.homeLoader;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static main.AppLawyer.stage;
/**
 * FXML Controller class
 *
 * @author Nyatchou
 */
public class ClientsListController {

    @FXML
    private TableView<Client> clientsList_tableView;

    @FXML
    private TableColumn<Client, String> noms_column;

    @FXML
    private TableColumn<Client, String> prenoms_column;

    @FXML
    private TableColumn<Client, String> email_column;

    @FXML
    private TableColumn<Client, String> adresse_column;

    @FXML
    private TableColumn<Client, String> telephone_column;

    @FXML
    private TableColumn<Client, String> docStatus_column;

    @FXML
    private JFXTextField search_textField;
    
    @FXML
    private JFXButton modifClient_button;
    
    public static Stage modifClientStage;
    
    private FXMLLoader modifClientLoader;
    private Parent modifClientRoot;
    
    
    public void initialize() {
       initTable();
       search_textField.textProperty().addListener((observable, oldValue, newValue) -> {
            clientsList_tableView.setItems(FXCollections.observableArrayList(Client.filtrer(search_textField.getText())));
       });
       initButtonAction();
       activeDoubleClickOnTableV();
       limitTextEnters();
    }    
    public void initTable(){
        noms_column.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenoms_column.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        email_column.setCellValueFactory(new PropertyValueFactory<>("email"));
        adresse_column.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        telephone_column.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        
        /*noms_column.setCellFactory(TextFieldTableCell.forTableColumn());
        prenoms_column.setCellFactory(TextFieldTableCell.forTableColumn());
        email_column.setCellFactory(TextFieldTableCell.forTableColumn());
        adresse_column.setCellFactory(TextFieldTableCell.forTableColumn());
        telephone_column.setCellFactory(TextFieldTableCell.forTableColumn());
        
        noms_column.setOnEditCommit((TableColumn.CellEditEvent<Client, String> event) -> {
            Client client = ((Client)event.getTableView().getItems().get(event.getTablePosition().getRow()));
            List <Dossier> listDossier = Dossier.listByClient(client);
            client.setNom(removeLeadingEmptySpace(event.getNewValue()));
            client.update();
            for(int ind = 0; ind < listDossier.size(); ind++){
                Dossier doc = listDossier.get(ind);
                String pathDoc0 = "F:\\Dossiers Clients\\DOSSIER "+doc.getClient().getNom().toUpperCase()+" "+doc.getClient().getPrenom().toUpperCase();
                String pathDoc = ind == 0 ? pathDoc0 : pathDoc0 + " " + String.valueOf(ind+1);                

                String newPathDoc0 = "F:\\Dossiers Clients\\DOSSIER "+client.getNom().toUpperCase()+" "+client.getPrenom().toUpperCase(); 
                String newPathDoc = ind == 0 ? newPathDoc0 : newPathDoc0+" "+String.valueOf(ind+1);
                Document.listByDossier(doc).forEach(a -> {
                    a.setFichier(newPathDoc+"\\"+a.getNom());
                    a.update();
                });
                File file = new File(pathDoc);
                File newFile = new File(newPathDoc);
                file.renameTo(newFile);
                doc.setCheminDossier(newPathDoc);
                doc.update();
                ((CurrentFoldersController)homeLoader.getController()).initListView();

            }
        });
        prenoms_column.setOnEditCommit((TableColumn.CellEditEvent<Client, String> event) -> {
            Client client = ((Client)event.getTableView().getItems().get(event.getTablePosition().getRow()));
            List <Dossier> listDossier = Dossier.listByClient(client);
            client.setPrenom(removeLeadingEmptySpace(event.getNewValue()));
            client.update();
            for(int ind = 0; ind < listDossier.size(); ind++){
                Dossier doc = listDossier.get(ind);
                String pathDoc0 = "F:\\Dossiers Clients\\DOSSIER "+doc.getClient().getNom().toUpperCase()+" "+doc.getClient().getPrenom().toUpperCase();
                String pathDoc = ind == 0 ? pathDoc0 : pathDoc0 + " " + String.valueOf(ind+1);

                String newPathDoc0 = "F:\\Dossiers Clients\\DOSSIER "+client.getNom().toUpperCase()+" "+client.getPrenom().toUpperCase();
                String newPathDoc = ind == 0 ? newPathDoc0 : newPathDoc0+" "+String.valueOf(ind+1);
                Document.listByDossier(doc).forEach(a -> {
                    a.setFichier(newPathDoc+"\\"+a.getNom());
                    a.update();
                });
                File file = new File(pathDoc);
                File newFile = new File(newPathDoc);
                file.renameTo(newFile);
                doc.setCheminDossier(newPathDoc);
                doc.update();
                ((CurrentFoldersController)homeLoader.getController()).initListView();
            }
        });
        email_column.setOnEditCommit((TableColumn.CellEditEvent<Client, String> event) -> {
            ((Client)event.getTableView().getItems().get(event.getTablePosition().getRow())).setEmail(event.getNewValue());
            ((Client)event.getTableView().getItems().get(event.getTablePosition().getRow())).update();
        });
        telephone_column.setOnEditCommit((TableColumn.CellEditEvent<Client, String> event) -> {
            ((Client)event.getTableView().getItems().get(event.getTablePosition().getRow())).setTelephone(event.getNewValue());
            ((Client)event.getTableView().getItems().get(event.getTablePosition().getRow())).update();
        });
        adresse_column.setOnEditCommit((TableColumn.CellEditEvent<Client, String> event) -> {
            ((Client)event.getTableView().getItems().get(event.getTablePosition().getRow())).setAdresse(event.getNewValue());
            ((Client)event.getTableView().getItems().get(event.getTablePosition().getRow())).update();
            ((CurrentFoldersController)homeLoader.getController()).initListView();
        });
        clientsList_tableView.setEditable(true);*/
        clientsList_tableView.setItems(FXCollections.observableArrayList(Client.all()));
        

    }
    
    public void initButtonAction(){
        modifClient_button.setOnAction(e -> {          
              openStageModifClient();
        });
    }

    public void openStageModifClient(){
        Client client = clientsList_tableView.getSelectionModel().getSelectedItem();
            if(client != null){
                modifClientStage = new Stage();
                modifClientLoader  = new FXMLLoader(getClass().getResource("/views/ModifClient.fxml"));
                try {
                    modifClientRoot = modifClientLoader.load();
                    ((ModifClientController)modifClientLoader.getController()).setClient(client);
                    modifClientStage.setScene(new Scene(modifClientRoot));
                    modifClientStage.getIcons().add(new Image("/ressources/images/icon_lawyer.png"));
                    modifClientStage.setResizable(false);
                    modifClientStage.initStyle(StageStyle.UTILITY);
                    modifClientStage.initModality(Modality.APPLICATION_MODAL);
                    modifClientStage.initOwner(stage);
                    modifClientStage.show();
                } catch (IOException ex) {
                    Logger.getLogger(ClientsListController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                displaySelectionError();
            }        
    }
    public void displaySelectionError(){
        Alert al = new Alert(Alert.AlertType.ERROR);
        ((Stage)al.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/ressources/images/icon_lawyer2.png"));
        al.setContentText("Vous n'avez sélectionné aucun client !");
        al.setHeaderText("AUCUN CLIENT SELECTIONNE");
        al.show();
    }
    
    public void activeDoubleClickOnTableV(){
        clientsList_tableView.setRowFactory(tv -> {
            TableRow<Client> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    openStageModifClient();
                }
            });
             return row;       
        });
    }
    
        
    public void limitTextEnters(){
            UnaryOperator<TextFormatter.Change> filter = (TextFormatter.Change t) -> {
            String newText = t.getControlNewText();
            if( newText.length()< 40) {
                return t ;
            }
            return null ;
        };
        search_textField.setTextFormatter(new TextFormatter<>(filter));
    }
}
