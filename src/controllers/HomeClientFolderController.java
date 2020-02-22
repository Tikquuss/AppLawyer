/*
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
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
    private TableColumn<Payement, LocalDate> date_tableColumn;
    @FXML
    private TableColumn<Payement, LocalTime> heure_tableColumn;
    @FXML
    private TableColumn<Payement, Long> montant_tableColumn;
    
    @FXML
    private Label reste_label;

    private Dossier dossier;
    private double resteapayer;
   
    @FXML
    public void initialize() {
        initTable();
        //initView();
        initButtonsActions();
        initTextFieldForNumbers();
        initReste();
    }    
    
    public void initReste(){
        long toBuy = (long)(currentFolder.getHonoraires() - calcMontantPaiementActu(0));
        reste_label.setText(String.valueOf(toBuy)+" FCFA");
    }
    public void initTable(){
        date_tableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        heure_tableColumn.setCellValueFactory(new PropertyValueFactory<>("heure"));
        montant_tableColumn.setCellValueFactory(value -> new ReadOnlyObjectWrapper<Long>((long)value.getValue().getMontant()));
        paiements_tableView.setItems(FXCollections.observableList(Payement.listByDossier(currentFolder)));
    }
    public void initView(){
        namesClient_label.setText(dossier.getClient().getNom().toUpperCase());
        surnamesClient_label.setText(dossier.getClient().getPrenom().toUpperCase());
        nameAdv_label.setText(dossier.getAdversaire().getNom().toUpperCase());
        provisions_label.setText(String.valueOf((long)dossier.getProvisions())+" FCFA");
        dateOpenFolder_label.setText(String.valueOf(dossier.getDateOuverture()));
        typeAff_label.setText(dossier.getTypeAffaire());
        honoraires_label.setText(String.valueOf((long)dossier.getHonoraires())+" FCFA");
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
                        Alert al = new Alert(Alert.AlertType.WARNING);
                        al.setContentText("Vous ne pouvez pas enregistrer un paiement à une date ultérieure à la date actuelle.");
                        al.setHeaderText("ERREUR DATE");
                        al.show();
                }
                else{
                    if(calcMontantPaiementActu(Long.valueOf(montantPaiement_textField.getText())) > currentFolder.getHonoraires()){
                        Alert al = new Alert(Alert.AlertType.WARNING);
                        al.setContentText("La valeur totale des paiements n'est pas censée dépasser celle des honoraires.");
                        al.setHeaderText("VALEUR DE CHAMP ERRONEE");
                        al.show();
                    }
                    else{
                        Payement paye = new Payement(Long.valueOf(montantPaiement_textField.getText()), 
                        currentFolder , datePaiement_datePicker.getValue(), heurePaiement_timePicker.getValue() );
                        resteapayer = currentFolder.getHonoraires() - calcMontantPaiementActu(Long.valueOf(montantPaiement_textField.getText()));
                        reste_label.setText(String.valueOf((long)resteapayer)+" FCFA");
                        paye.save();
                        paiements_tableView.getItems().add(paye);
                    }                 
                }
            }
            else {
                    Alert al = new Alert(Alert.AlertType.WARNING);
                    al.setContentText("Veuillez remplir ces 03 champs s'il vous plait");
                    al.setHeaderText("CHAMPS VIDES");
                    al.show();
            }      
        });
        paiements_tableView.setOnKeyPressed((KeyEvent t)-> {
            KeyCode key=t.getCode();
            Payement paye = paiements_tableView.getSelectionModel().getSelectedItem();
            if(paye != null){      
                if (key==KeyCode.DELETE){
                    Alert dialogConfirm = new Alert(Alert.AlertType.CONFIRMATION);
                    dialogConfirm.setTitle("Confirmation suppression");
                    dialogConfirm.setHeaderText("Confirmation suppression");
                    dialogConfirm.setContentText("Voulez vous vraiment supprimer ce paiement de la liste ??");
                    Optional<ButtonType> answer = dialogConfirm.showAndWait();
                    if (answer.get() == ButtonType.OK) {
                            paiements_tableView.getItems().remove(paye);
                            resteapayer = currentFolder.getHonoraires() - calcMontantPaiementActu(0) + paye.getMontant();
                            reste_label.setText(String.valueOf((long)resteapayer) +" FCFA");
                            paye.delete();
                      }
                }
            }
        });
    }
    public void initTextFieldForNumbers(){
        UnaryOperator<TextFormatter.Change> filter = (TextFormatter.Change t) -> {
            String newText = t.getControlNewText() ;
            if(newText.matches("([1-9][0-9]*)*") && newText.length() <= 13 ) {
                return t ;
            }
            return null ;            
        };
        montantPaiement_textField.setTextFormatter(new TextFormatter<>(filter));
    }
    
    public double calcMontantPaiementActu(double nveauMontant){
        double paye = 0;
        List<Payement> payeList = Payement.listByDossier(currentFolder);
        for (int i = 0; i< payeList.size(); i++)
            paye += payeList.get(i).getMontant();         
        return paye+nveauMontant;
    }

}
