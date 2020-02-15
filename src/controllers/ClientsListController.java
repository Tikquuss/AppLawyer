/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import appdatabase.bean.Adversaire;
import appdatabase.bean.Client;
import appdatabase.bean.Client;
import appdatabase.bean.Document;
import appdatabase.bean.Dossier;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import static controllers.PresentPageController.homeLoader;
import java.io.File;
/**
 * FXML Controller class
 *
 * @author Utilisateur
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
    private JFXButton edit_button;
    
    
    
    @FXML
    public void initialize() {
       initTable();
    }    
    public void initTable(){
        noms_column.setCellValueFactory(new PropertyValueFactory<Client, String>("nom"));
        prenoms_column.setCellValueFactory(new PropertyValueFactory<Client, String>("prenom"));
        email_column.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
        adresse_column.setCellValueFactory(new PropertyValueFactory<Client, String>("adresse"));
        telephone_column.setCellValueFactory(new PropertyValueFactory<Client, String>("telephone"));
        
        noms_column.setCellFactory(TextFieldTableCell.forTableColumn());
        prenoms_column.setCellFactory(TextFieldTableCell.forTableColumn());
        email_column.setCellFactory(TextFieldTableCell.forTableColumn());
        adresse_column.setCellFactory(TextFieldTableCell.forTableColumn());
        telephone_column.setCellFactory(TextFieldTableCell.forTableColumn());
        
        noms_column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client, String>>() {
        @Override
        public void handle(TableColumn.CellEditEvent<Client, String> event) {
            Client client = ((Client)event.getTableView().getItems().get(event.getTablePosition().getRow()));
            Dossier.listByClient(client).forEach(doc -> {
                String pathDoc = "F:\\Dossiers Clients\\DOSSIER "+doc.getClient().getNom().toUpperCase()+" "+doc.getClient().getPrenom().toUpperCase()+" "+doc.getId()+"\\";
                Document.listByDossier(doc).forEach(a -> {
                a.setFichier(pathDoc+a.getNom());
                a.update();
                });
                ((Client)event.getTableView().getItems().get(event.getTablePosition().getRow())).setNom(removeLeadingEmptySpace(event.getNewValue()));
                ((Client)event.getTableView().getItems().get(event.getTablePosition().getRow())).update();
                ((CurrentFoldersController)homeLoader.getController()).initListView();
                String newPathDoc = "F:\\Dossiers Clients\\DOSSIER "+doc.getClient().getNom().toUpperCase()+" "+doc.getClient().getPrenom().toUpperCase()+" "+doc.getId()+"\\";
                Document.listByDossier(doc).forEach(a -> {
                    a.setFichier(newPathDoc+a.getNom());
                    a.update();
                });
                File file = new File(pathDoc);
                File newFile = new File(newPathDoc);
                file.renameTo(newFile); 
            });
            }
        });
        prenoms_column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client, String>>() {
        @Override
        public void handle(TableColumn.CellEditEvent<Client, String> event) {
            Client client = ((Client)event.getTableView().getItems().get(event.getTablePosition().getRow()));
            Dossier.listByClient(client).forEach(doc -> {
                String pathDoc = "F:\\Dossiers Clients\\DOSSIER "+doc.getClient().getNom().toUpperCase()+" "+doc.getClient().getPrenom().toUpperCase()+" "+doc.getId()+"\\";
                Document.listByDossier(doc).forEach(a -> {
                    a.setFichier(pathDoc+a.getNom());
                    a.update();
                });
                ((Client)event.getTableView().getItems().get(event.getTablePosition().getRow())).setPrenom(removeLeadingEmptySpace(event.getNewValue()));
                ((Client)event.getTableView().getItems().get(event.getTablePosition().getRow())).update();
                ((CurrentFoldersController)homeLoader.getController()).initListView();
                String newPathDoc = "F:\\Dossiers Clients\\DOSSIER "+doc.getClient().getNom().toUpperCase()+" "+doc.getClient().getPrenom().toUpperCase()+" "+doc.getId()+"\\"; 
                Document.listByDossier(doc).forEach(a -> {
                    a.setFichier(newPathDoc+a.getNom());
                    a.update();
                });
                File file = new File(pathDoc);
                File newFile = new File(newPathDoc);
                file.renameTo(newFile);
                });
        }
        });
        email_column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client, String>>() {
        @Override
        public void handle(TableColumn.CellEditEvent<Client, String> event) {
            ((Client)event.getTableView().getItems().get(event.getTablePosition().getRow())).setEmail(event.getNewValue());
            ((Client)event.getTableView().getItems().get(event.getTablePosition().getRow())).update();
            }
        });
        telephone_column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client, String>>() {
        @Override
        public void handle(TableColumn.CellEditEvent<Client, String> event) {
            ((Client)event.getTableView().getItems().get(event.getTablePosition().getRow())).setTelephone(event.getNewValue());
            ((Client)event.getTableView().getItems().get(event.getTablePosition().getRow())).update();
            }
        });
        adresse_column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client, String>>() {
        @Override
        public void handle(TableColumn.CellEditEvent<Client, String> event) {
            ((Client)event.getTableView().getItems().get(event.getTablePosition().getRow())).setAdresse(event.getNewValue());
            ((Client)event.getTableView().getItems().get(event.getTablePosition().getRow())).update();
            ((CurrentFoldersController)homeLoader.getController()).initListView();
            }
        });
        clientsList_tableView.setItems(FXCollections.observableArrayList(Client.all()));
        clientsList_tableView.setEditable(true);

    }
    public String removeLeadingEmptySpace(String s) {
    StringBuilder sb = new StringBuilder(s);
    while (sb.length() > 1 && sb.charAt(0) == ' ') {
        sb.deleteCharAt(0);
    }
    return sb.toString();
    }
    
}
