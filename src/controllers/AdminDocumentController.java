/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import appdatabase.bean.CategorieDocument;
import appdatabase.bean.TypeDocument;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Utilisateur
 */
public class AdminDocumentController {

    @FXML
    private TableView<TypeDocument> typeDoc_tableView;
    @FXML
    private JFXTextField typeDoc_textField;
    @FXML
    private JFXComboBox<CategorieDocument> categDoc_comboBox;
    @FXML
    private JFXButton saveTypeDoc_button;
    @FXML
    private JFXListView<CategorieDocument> categDoc_listView;
    @FXML
    private JFXTextField categDoc_textField;
    @FXML
    private JFXButton saveCategDoc_button;
    @FXML
    private TableColumn<TypeDocument, String> categDoc_column;

    @FXML
    private TableColumn<TypeDocument, String> typeDoc_column;

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
         initListView();
         initTableView();
         initButtonsActions();
         initKeyBoardsActions();
    }    
    
    public void initTableView(){
        categDoc_column.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        typeDoc_column.setCellValueFactory(new PropertyValueFactory<>("nom"));
        typeDoc_tableView.setItems(FXCollections.observableArrayList(TypeDocument.all()));
    }
    
    public void initListView(){
        categDoc_listView.setItems(FXCollections.observableArrayList(CategorieDocument.all()));
    }
    
    public void initButtonsActions(){
        
        saveCategDoc_button.setOnAction(e -> {
            String nomCategDoc = categDoc_textField.getText();
            if(!nomCategDoc.equals("")){
                 CategorieDocument cd = new CategorieDocument(categDoc_textField.getText());
                 cd.save();
                 categDoc_listView.getItems().add(cd);
            }
        });
        saveTypeDoc_button.setOnAction(e -> {
            String nomTypeDoc = typeDoc_textField.getText();
            if(!nomTypeDoc.equals("")){
                CategorieDocument cd = categDoc_comboBox.getSelectionModel().getSelectedItem();
                if(cd != null){
                    TypeDocument td  = new TypeDocument(nomTypeDoc, cd);
                    td.save();
                    typeDoc_tableView.getItems().add(td);
                }
            }
        });
    }
    
    public void initKeyBoardsActions(){
        categDoc_listView.setOnKeyPressed((KeyEvent event) -> {
            KeyCode key = event.getCode();
            if(key == KeyCode.DELETE){
                CategorieDocument cd = categDoc_listView.getSelectionModel().getSelectedItem();
                if(cd != null){
                    categDoc_listView.getItems().remove(cd);
                    cd.delete();
                        }
                };           
        });
        typeDoc_tableView.setOnKeyPressed((KeyEvent event) -> {
            KeyCode key = event.getCode();
            if(key == KeyCode.DELETE){
                TypeDocument td = typeDoc_tableView.getSelectionModel().getSelectedItem();
                if(td != null){
                    typeDoc_tableView.getItems().remove(td);
                    td.delete();
                        }
                }; 
        });
    }  
    
}

