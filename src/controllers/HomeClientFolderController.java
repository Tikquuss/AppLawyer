/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import appdatabase.bean.Dossier;
import appdatabase.bean.Payement;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.time.LocalDate;
import java.time.LocalTime;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import static controllers.CurrentFoldersController.currentFolder;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.function.UnaryOperator;
import javafx.scene.control.Alert;
import javafx.scene.control.TextFormatter;

/**
 * FXML Controller class
 *
 * @author Utilisateur
 */
public class HomeClientFolderController {

    @FXML
    private Label namesClient_label;
    @FXML
    private Label surnamesClient_label;
    @FXML
    private Label nameAdv_label;
    @FXML
    private Label dureeDossier_label;
    @FXML
    private Label provisions_label;
    @FXML
    private Label dateOpenFolder_label;
    @FXML
    private Label typeAff_label;
    @FXML
    private Label honoraires_label;
    @FXML
    private Label qualite_label;
    @FXML
    private Label juridiction_label;
    @FXML
    private JFXDatePicker datePaiement_datePicker;

    @FXML
    private JFXTimePicker heurePaiement_timePicker;

    @FXML
    private JFXTextField montantPaiement_textField;

    @FXML
    private JFXButton savePaiement_button;
    @FXML
    private TableView<Payement> paiements_tableView;
    @FXML
    private TableColumn<Payement, Integer> numero_tableColumn;
    @FXML
    private TableColumn<Payement, LocalDate> date_tableColumn;
    @FXML
    private TableColumn<Payement, LocalTime> heure_tableColumn;
    @FXML
    private TableColumn<Payement, Integer> montant_tableColumn;

    private Dossier dossier;

   
    @FXML
    public void initialize() {
        initTable();
        //initView();
        initButtonsActions();
        initTextFieldForNumbers();
    }    
    
    public void initTable(){
        numero_tableColumn.setCellValueFactory(new PropertyValueFactory<>("numero"));
        date_tableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        heure_tableColumn.setCellValueFactory(new PropertyValueFactory<>("heure"));
        montant_tableColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
        paiements_tableView.setItems(FXCollections.observableList(Payement.listByDossier(currentFolder)));
    }
    public void initView(){
        namesClient_label.setText(dossier.getClient().getNom().toUpperCase());
        surnamesClient_label.setText(dossier.getClient().getPrenom().toUpperCase());
        nameAdv_label.setText(dossier.getAdversaire().getNom().toUpperCase());
        provisions_label.setText(String.valueOf((int)dossier.getProvisions())+" FCFA");
        dateOpenFolder_label.setText(String.valueOf(dossier.getDateOuverture()));
        typeAff_label.setText(dossier.getTypeAffaire());
        honoraires_label.setText(String.valueOf((int)dossier.getHonoraires())+" FCFA");
        qualite_label.setText(dossier.getQualite());
        juridiction_label.setText(dossier.getJuridiction());
    }
    public void setContent(Dossier dossier){
        this.dossier = dossier;
        initView();
    }
    
    public boolean checkIfNoEmptyField(){
        return (datePaiement_datePicker.getValue()!= null && heurePaiement_timePicker.getValue() != null && !montantPaiement_textField.getText().equals(""));
    }
    
    public void initButtonsActions(){
        savePaiement_button.setOnAction(e -> {
            if(checkIfNoEmptyField()){
                if(LocalDateTime.of(datePaiement_datePicker.getValue(), heurePaiement_timePicker.getValue()).isAfter(LocalDateTime.now())){
                    
                }
                else{
                    Payement paye = new Payement(Payement.listByDossier(currentFolder).size()+1, Integer.valueOf(montantPaiement_textField.getText()), 
                    currentFolder , datePaiement_datePicker.getValue(), heurePaiement_timePicker.getValue() );
                    paye.save();
                    paiements_tableView.getItems().add(paye);
                }
            }
            else {
                    Alert al = new Alert(Alert.AlertType.WARNING);
                    al.setContentText("Veuillez remplir ces 03 champs s'il vous plait");
                    al.setHeaderText("CHAMPS VIDES");
                    al.show();
            }      
        });
    }
    public void initTextFieldForNumbers(){
        UnaryOperator<TextFormatter.Change> filter = (TextFormatter.Change t) -> {
            String newText = t.getControlNewText() ;
            if(newText.matches("-?[0-9]*")) {
                return t ;
            }
            return null ;            
        };
        montantPaiement_textField.setTextFormatter(new TextFormatter<>(filter));
    }

}
