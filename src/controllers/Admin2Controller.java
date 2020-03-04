/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import appdatabase.bean.Juridiction;
import appdatabase.bean.QualiteAvocat;
import appdatabase.bean.TypeAffaire;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.util.function.UnaryOperator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Nyatchou
 */
public class Admin2Controller {

    @FXML
    private JFXTextField addJuridictions_textField;
    @FXML
    private JFXButton addJuridiction_button;
    @FXML
    private JFXListView<Juridiction> juridictions_listView;
    @FXML
    private JFXTextField addTypesAffaires_textField;
    @FXML
    private JFXButton addTypesAffaires_button;
    @FXML
    private JFXListView<TypeAffaire> typesAffaires_listView;
    @FXML
    private JFXTextField addQualitesAvocat_textField;
    @FXML
    private JFXButton addQualite_button;
    @FXML
    private JFXListView<QualiteAvocat> qualites_listView;

    @FXML
    public void initialize() {
        this.initButtonsActions();
        this.initListsViews();
        this.initTextFields();
        this.initKeyBoardsActions();
    } 
    
    public void addTypeAffAction(){
        if(!addTypesAffaires_textField.getText().equals("")){
                TypeAffaire typAff = new TypeAffaire(addTypesAffaires_textField.getText());
                typAff.save();
                typesAffaires_listView.getItems().add(typAff);
            }
    }
    public void addJurAction(){
        if(!addJuridictions_textField.getText().equals("")){
                Juridiction jur = new Juridiction(addJuridictions_textField.getText());
                jur.save();
                juridictions_listView.getItems().add(jur);
            }
    }
    public void addQualAction(){
        if(!addQualitesAvocat_textField.getText().equals("")){
                QualiteAvocat qualiteAvocat = new QualiteAvocat(addQualitesAvocat_textField.getText());
                qualiteAvocat.save();
                qualites_listView.getItems().add(qualiteAvocat);
            }
    }
    public void initButtonsActions(){
        addTypesAffaires_button.setOnAction(e -> {
            addTypeAffAction();
        });
        addJuridiction_button.setOnAction(e -> {
            addJurAction();
        });
        addQualite_button.setOnAction(e -> {
            addQualAction();
        });
        
    }
    
    public void initListsViews(){      
        juridictions_listView.setItems(FXCollections.observableArrayList(Juridiction.all()));
        typesAffaires_listView.setItems(FXCollections.observableArrayList(TypeAffaire.all()));
        qualites_listView.setItems(FXCollections.observableArrayList(QualiteAvocat.all()));
    }
    
    public void initTextFields(){
        UnaryOperator<TextFormatter.Change> filter = (TextFormatter.Change t) -> {
            String newText = t.getControlNewText();
            if(newText.matches("^([^\\s].*)*$") && newText.length()< 120) {
                return t ;
            }
            return null ;
        };
        addJuridictions_textField.setTextFormatter(new TextFormatter<>(filter));
        addQualitesAvocat_textField.setTextFormatter(new TextFormatter<>(filter));
        addTypesAffaires_textField.setTextFormatter(new TextFormatter<>(filter));
    
    }
    
    public void initKeyBoardsActions(){
        addJuridictions_textField.setOnKeyPressed((KeyEvent t) -> {
            KeyCode key=t.getCode();
            if(key == KeyCode.ENTER){
                addJurAction();
            }
        });
        addQualitesAvocat_textField.setOnKeyPressed((KeyEvent t) -> {
            KeyCode key=t.getCode();
                if(key == KeyCode.ENTER){
                  addQualAction();
            }
        });
        addTypesAffaires_textField.setOnKeyPressed((KeyEvent t) -> {
            KeyCode key=t.getCode();
                if(key == KeyCode.ENTER){
                    addTypeAffAction();
            }
        });
        juridictions_listView.setOnKeyPressed((KeyEvent t)->{
            KeyCode key=t.getCode();
                if(key == KeyCode.DELETE){
                    Juridiction jur = juridictions_listView.getSelectionModel().getSelectedItem();
                    if(jur != null){
                        juridictions_listView.getItems().remove(jur);
                        jur.delete();
                    }
            }
        });
        qualites_listView.setOnKeyPressed((KeyEvent t)->{
            KeyCode key=t.getCode();
                if(key == KeyCode.DELETE){
                    QualiteAvocat qual = qualites_listView.getSelectionModel().getSelectedItem();
                    if(qual != null){
                        qualites_listView.getItems().remove(qual);
                        qual.delete();
                    }
            }
        });
        typesAffaires_listView.setOnKeyPressed((KeyEvent t)->{
            KeyCode key=t.getCode();
                if(key == KeyCode.DELETE){
                    TypeAffaire ta = typesAffaires_listView.getSelectionModel().getSelectedItem();
                    if(ta != null){
                        typesAffaires_listView.getItems().remove(ta);
                        ta.delete();
                    }
            }
        });
    }
    
}
