/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import appdatabase.bean.Adversaire;
import appdatabase.bean.Client;
import appdatabase.bean.Dossier;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;

import dbsimulator.BeansObjects;
import java.io.File;
import java.util.function.UnaryOperator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TextFormatter;
import static controllers.PresentPageController.homeLoader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author Utilisateur
 */
public class CreateFolderController {

    @FXML
    private JFXButton save_button;
    @FXML
    private JFXTextField nomAdvers_textField;
    @FXML
    private JFXTextField honoraires_textField;
    @FXML
    private JFXComboBox<String> qualiteAvocat_comboBox;
    @FXML
    private JFXComboBox<String> typeAffaire_comboBox;
    @FXML
    private JFXComboBox<String> juridiction_comboBox;
    @FXML
    private JFXTextField provisions_textField;
    @FXML
    private JFXTextField nomsClient_textField;
    @FXML
    private JFXTextField prenomsClient_textField;
    @FXML
    private JFXTextField adresseClient_textField;
    @FXML
    private JFXTextField telephoneClient_textField;
    @FXML
    private JFXTextField emailClient_textField;
    @FXML
    private JFXButton choiceClient_button;
    
    
    private Client clientChoice;
    public static Stage clientListStage;


    /**
     * Initializes the controller class.
     * @param client
     */

    public void setClientChoice(Client client){
        this.clientChoice = client;
        nomsClient_textField.setText(client.getNom());
        prenomsClient_textField.setText(client.getPrenom());
        adresseClient_textField.setText(client.getAdresse());
        telephoneClient_textField.setText(client.getTelephone());
        emailClient_textField.setText(client.getEmail());
        
        nomsClient_textField.setDisable(true);
        prenomsClient_textField.setDisable(true);
        adresseClient_textField.setDisable(true);
        telephoneClient_textField.setDisable(true);
        emailClient_textField.setDisable(true);
    }
    public void initialize() throws IOException {
        initComboBox();
        initTextFieldForNumbers();
        initButtonsActions();
        checkNoEmptyField();
        initTextField();
        initPhoneNumberField();
    }  

    
    public void initButtonsActions() throws IOException{
        clientListStage = new Stage();
        Parent rootClientList = FXMLLoader.load(getClass().getResource("../views/ClientsList2.fxml"));
        clientListStage.setResizable(false);
        clientListStage.initModality(Modality.APPLICATION_MODAL);
        clientListStage.setScene(new Scene(rootClientList));
        save_button.setOnAction(e -> {
            if(this.checkNoEmptyField()){
                Client client = this.clientChoice == null ? new Client(telephoneClient_textField.getText(),
                                           emailClient_textField.getText(),
                                           adresseClient_textField.getText(),
                                           nomsClient_textField.getText(),
                                           prenomsClient_textField.getText(),
                                            "cni") : this.clientChoice;
                Dossier doc = new Dossier();
                int nbDoc = Dossier.listByClient(client) == null ? 0 : Dossier.listByClient(client).size();
                String dirName0 = "F:\\Dossiers Clients\\DOSSIER "+nomsClient_textField.getText().toUpperCase()+" "+prenomsClient_textField.getText().toUpperCase();
                String dirName = nbDoc == 0 ? dirName0 : dirName0+" "+String.valueOf(nbDoc+1);
                File dir = new File(dirName);
                boolean isCreated = dir.mkdirs();
                client.save();
                Adversaire adv  = new Adversaire(nomAdvers_textField.getText(), "", "cni");
                adv.save();
                doc.setCheminDossier(dirName);
                doc.setClient(client);
                doc.setAdversaire(adv);
                doc.setJuridiction(juridiction_comboBox.getSelectionModel().getSelectedItem());
                doc.setQualite(qualiteAvocat_comboBox.getSelectionModel().getSelectedItem());
                doc.setTypeAffaire(typeAffaire_comboBox.getSelectionModel().getSelectedItem());
                doc.setProvisions(Integer.valueOf(provisions_textField.getText()));
                doc.setHonoraires(Integer.valueOf(honoraires_textField.getText()));
                doc.setStatut("En cours");
                doc.save();
                ((CurrentFoldersController)homeLoader.getController()).addToListView(doc);               
            }    
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("ERREUR");
                alert.setContentText("Vérifiez que vous avez remplis tous les champs et que ceux-ci sont tous valides.");
                alert.show();
            }
        });
        choiceClient_button.setOnAction(e -> {
            clientListStage.show();
        });
    }
    
    public boolean checkIfIsInClientList(Client client){
        List <Client> listeClient = Client.all();
        return listeClient.contains(client);       
    }
    

    public void initComboBox(){
        qualiteAvocat_comboBox.setItems(FXCollections.observableList(BeansObjects.qualiteAvo));
        typeAffaire_comboBox.setItems(FXCollections.observableList(BeansObjects.typeAff));
        juridiction_comboBox.setItems(FXCollections.observableList(BeansObjects.juridictions));
    }
    
    public void putTextFieldValidator(JFXTextField field, ValidatorBase validator){
        validator.setMessage("Ce champ ne doit pas être vide");
        field.getValidators().add(validator);
        /*field.focusedProperty().addListener((o, oldVal, newVal) -> {
        if (!newVal)
            field.validate();
             });*/
        field.textProperty().addListener((o, oldVal, newVal) -> {
        if (!(newVal.equals(oldVal)))
                field.validate();
             });
    }
     public boolean isValidEmailAddress(String email){
        String regex = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(email);
        return m.matches();
    }
    
    public boolean checkNoEmptyField(){
    
        ValidatorBase requireNom = new RequiredFieldValidator();
        ValidatorBase requirePrenom = new RequiredFieldValidator();
        ValidatorBase requireAddrClient = new RequiredFieldValidator();
        ValidatorBase requireHonoraires = new RequiredFieldValidator();
        ValidatorBase requireEmail = new RequiredFieldValidator();
        ValidatorBase requireTelephone = new RequiredFieldValidator();
        ValidatorBase requireProvisions = new RequiredFieldValidator();
        ValidatorBase requireNomAdv = new RequiredFieldValidator();
        ValidatorBase requireQualiteAvocat = new RequiredFieldValidator();
        ValidatorBase requireTypAff = new RequiredFieldValidator();
        ValidatorBase requireJuridiction = new RequiredFieldValidator();
        ValidatorBase emailValidator = new ValidatorBase() {
                @Override
                protected void eval() {
                    setMessage("Adresse email invalide");
                    if (!isValidEmailAddress(emailClient_textField.getText())) {
                        hasErrors.set(true);
                    } else hasErrors.set(false);
                }
            };
        requireEmail.setMessage("Ce champ ne doit pas être vide");
        emailClient_textField.getValidators().addAll(requireEmail,emailValidator);

       /* emailClient_textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                emailClient_textField.validate();
            }
        });*/
        emailClient_textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!(newValue.equals(oldValue))) {
                emailClient_textField.validate();
            }
        });
        putTextFieldValidator(nomsClient_textField, requireNom);
        putTextFieldValidator(prenomsClient_textField, requirePrenom);
        putTextFieldValidator(adresseClient_textField, requireAddrClient);
        putTextFieldValidator(honoraires_textField, requireHonoraires);
        putTextFieldValidator(telephoneClient_textField, requireTelephone);      
        putTextFieldValidator(provisions_textField, requireProvisions);
        putTextFieldValidator(nomAdvers_textField, requireNomAdv);
        
        String combo1 = qualiteAvocat_comboBox.getSelectionModel().getSelectedItem();
        String combo2 = typeAffaire_comboBox.getSelectionModel().getSelectedItem();
        String combo3 = juridiction_comboBox.getSelectionModel().getSelectedItem();
         
        return 
          (    
               combo1 != null
            && combo2 != null
            && combo3 != null
            && nomsClient_textField.validate() 
            && prenomsClient_textField.validate() 
            && adresseClient_textField.validate()
            && honoraires_textField.validate()
            && provisions_textField.validate()
            && nomAdvers_textField.validate()
            && emailClient_textField.validate()
            && telephoneClient_textField.validate()
          );
       }
    
    public void initTextField(){
        UnaryOperator<TextFormatter.Change> filter = (TextFormatter.Change t) -> {
            String newText = t.getControlNewText() ;
            if(newText.matches("-?([^\\s].*)*$")) {
                return t ;
            }
            return null ;
        };
        nomAdvers_textField.setTextFormatter(new TextFormatter<>(filter));
        nomsClient_textField.setTextFormatter(new TextFormatter<>(filter));
        prenomsClient_textField.setTextFormatter(new TextFormatter<>(filter));
        telephoneClient_textField.setTextFormatter(new TextFormatter<>(filter));
        adresseClient_textField.setTextFormatter(new TextFormatter<>(filter));
        emailClient_textField.setTextFormatter(new TextFormatter<>(filter));
    }
    
    public void initPhoneNumberField(){
        UnaryOperator<TextFormatter.Change> filter = (TextFormatter.Change t) -> {
            String newText = t.getControlNewText() ;
            if(newText.matches("-?[0-9]*")) {
                return t ;
            }
            return null ;
        };
        telephoneClient_textField.setTextFormatter(new TextFormatter<>(filter));
    }
    
    public void initTextFieldForNumbers(){
        UnaryOperator<TextFormatter.Change> filter = (TextFormatter.Change t) -> {
            String newText = t.getControlNewText() ;
            if(newText.matches("-?([1-9][0-9]*)*")) {
                return t ;
            }
            return null ;
        };
     honoraires_textField.setTextFormatter(new TextFormatter<>(filter));
     provisions_textField.setTextFormatter(new TextFormatter<>(filter));
    }

}
