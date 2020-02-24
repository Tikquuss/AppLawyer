/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import appdatabase.bean.Client;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import static controllers.CurrentFoldersController.newFoldLoader;
import static controllers.CreateFolderController.clientListStage;
import java.util.function.UnaryOperator;
import javafx.event.ActionEvent;
import javafx.scene.control.TextFormatter;
/**
 *
 * @author Utilisateur
 */
public class ClientsList2Controller {
    
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
    private JFXButton choiceClient_button;
    
    @FXML
    private JFXTextField search_textField;
    
    
    
    public void initialize() {
       initTable();
       initActions();
       limitTextEnters();
    }    
    public void initTable(){
        noms_column.setCellValueFactory(new PropertyValueFactory<Client, String>("nom"));
        prenoms_column.setCellValueFactory(new PropertyValueFactory<Client, String>("prenom"));
        email_column.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
        adresse_column.setCellValueFactory(new PropertyValueFactory<Client, String>("adresse"));
        telephone_column.setCellValueFactory(new PropertyValueFactory<Client, String>("telephone"));
        
        clientsList_tableView.setItems(FXCollections.observableArrayList(Client.all()));

    }
    
    public void initActions(){
        choiceClient_button.setOnAction((ActionEvent e) -> {
            Client client = clientsList_tableView.getSelectionModel().getSelectedItem();
            if(client != null){
                ((CreateFolderController)newFoldLoader.getController()).setClientChoice(client);    
                clientListStage.close();
            }
        });
        search_textField.textProperty().addListener((observable, oldValue, newValue) -> {
            clientsList_tableView.setItems(FXCollections.observableArrayList(Client.filtrer(search_textField.getText())));
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
