/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import appdatabase.bean.Client;
import appdatabase.bean.Document;
import appdatabase.bean.Dossier;
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
import java.util.List;
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
    private JFXTextField search_textField;
    
    @FXML
    private JFXButton choiceClient_button;
    
    
    
    public void initialize() {
       initTable();
       search_textField.textProperty().addListener((observable, oldValue, newValue) -> {
            clientsList_tableView.setItems(FXCollections.observableArrayList(Client.filtrer(search_textField.getText())));
       });
    }    
    public void initTable(){
        noms_column.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenoms_column.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        email_column.setCellValueFactory(new PropertyValueFactory<>("email"));
        adresse_column.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        telephone_column.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        
        noms_column.setCellFactory(TextFieldTableCell.forTableColumn());
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
                /*Document.listByDossier(doc).forEach(a -> {
                a.setFichier(pathDoc+"\\"+a.getNom());
                a.update();
                });
                ((Client)event.getTableView().getItems().get(event.getTablePosition().getRow())).setNom(removeLeadingEmptySpace(event.getNewValue()));
                ((Client)event.getTableView().getItems().get(event.getTablePosition().getRow())).update();*/
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
                /* Document.listByDossier(doc).forEach(a -> {
                a.setFichier(pathDoc+"\\"+a.getNom());
                a.update();
                });*/
                /*((Client)event.getTableView().getItems().get(event.getTablePosition().getRow())).setPrenom(removeLeadingEmptySpace(event.getNewValue()));
                ((Client)event.getTableView().getItems().get(event.getTablePosition().getRow())).update();*/
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
