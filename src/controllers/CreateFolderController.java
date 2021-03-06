/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import appdatabase.bean.Adversaire;
import appdatabase.bean.Client;
import appdatabase.bean.Dossier;
import appdatabase.bean.Juridiction;
import appdatabase.bean.QualiteAvocat;
import appdatabase.bean.TypeAffaire;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import java.io.File;
import java.util.function.UnaryOperator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TextFormatter;
import static controllers.PresentPageController.homeLoader;
import static controllers.PresentPageController.clientListLoader;
import static controllers.CurrentFoldersController.newFolderStage;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import static controllers.PresentPageController.pathFolderRoot;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * FXML Controller class
 *
 * @author Nyatchou
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
    private Parent rootClientList;
    private FXMLLoader clientList2Loader;
    private ArrayList <JFXTextField> textFieldsList ;
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
        setValidators();
        initTextField();
        initPhoneNumberField();
        initLimitLenghtEmail();
    }  

    public void initTextFieldsList(){
        textFieldsList = new ArrayList<>();
        textFieldsList.add(nomsClient_textField);
        textFieldsList.add(prenomsClient_textField);
        textFieldsList.add(adresseClient_textField);
        textFieldsList.add(telephoneClient_textField);
        textFieldsList.add(emailClient_textField);
        textFieldsList.add(nomAdvers_textField);
        textFieldsList.add(honoraires_textField);
        textFieldsList.add(provisions_textField);   
    }
    
    public void initButtonsActions() throws IOException{        
        clientListStage = new Stage();
        clientListStage.initModality(Modality.APPLICATION_MODAL);
        clientListStage.initStyle(StageStyle.UTILITY);
        clientList2Loader = new FXMLLoader(getClass().getResource("/views/ClientsList2.fxml"));
        rootClientList = clientList2Loader.load();
        clientListStage.setScene(new Scene(rootClientList));
        clientListStage.initOwner(newFolderStage);
        save_button.setOnAction(e -> {
            if(this.checkNoEmptyField()){
                Client client = this.clientChoice == null ? new Client(removeLeadingEmptySpace(telephoneClient_textField.getText()),
                                           removeLeadingEmptySpace(emailClient_textField.getText()),
                                           removeLeadingEmptySpace(adresseClient_textField.getText()),
                                           removeLeadingEmptySpace(nomsClient_textField.getText()),
                                           removeLeadingEmptySpace(prenomsClient_textField.getText()),
                                            "cni") : this.clientChoice;
                Dossier doc = new Dossier();
                int nbDoc = Dossier.listByClient(client) == null ? 0 : Dossier.listByClient(client).size();
                String dirName0 = pathFolderRoot+"\\DOSSIER "+removeLeadingEmptySpace(nomsClient_textField.getText().toUpperCase())+" "+
                        removeLeadingEmptySpace(prenomsClient_textField.getText().toUpperCase());
                String dirName = nbDoc == 0 ? dirName0 : dirName0+" "+String.valueOf(nbDoc+1);
                File dir = new File(dirName);
                boolean isCreated = dir.mkdirs();
                client.save();
                Adversaire adv  = new Adversaire(removeLeadingEmptySpace(nomAdvers_textField.getText()), "", "cni");
                adv.save();
                doc.setCheminDossier(dirName);
                doc.setClient(client);
                doc.setAdversaire(adv);
                doc.setJuridiction(juridiction_comboBox.getSelectionModel().getSelectedItem());
                doc.setQualite(qualiteAvocat_comboBox.getSelectionModel().getSelectedItem());
                doc.setTypeAffaire(typeAffaire_comboBox.getSelectionModel().getSelectedItem());
                doc.setProvisions(Long.valueOf(provisions_textField.getText()));
                doc.setHonoraires(Long.valueOf(honoraires_textField.getText()));
                doc.setStatut("En cours");
                doc.save();
                ((CurrentFoldersController)homeLoader.getController()).addToListView(doc);      
                ((ClientsListController)clientListLoader.getController()).initTable();
                ((ClientsList2Controller)clientList2Loader.getController()).initTable();
                newFolderStage.close();
            }    
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/ressources/images/icon_lawyer2.png"));
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
        qualiteAvocat_comboBox.setItems(FXCollections.observableList(QualiteAvocat.allQualities()));
        typeAffaire_comboBox.setItems(FXCollections.observableList(TypeAffaire.allTypesAff()));
        juridiction_comboBox.setItems(FXCollections.observableList(Juridiction.allJuridictions()));
    }
    
    public void putTextFieldNoEmptyValidator(JFXTextField field, ValidatorBase validator){
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
    
        
        String combo1 = qualiteAvocat_comboBox.getSelectionModel().getSelectedItem();
        String combo2 = typeAffaire_comboBox.getSelectionModel().getSelectedItem();
        String combo3 = juridiction_comboBox.getSelectionModel().getSelectedItem();
         
        boolean areValids = combo1 != null && combo2 != null && combo3 != null;
        
       for (JFXTextField textField : textFieldsList)
           areValids = areValids && textField.validate();
        return areValids;
               
       }
    
    public void setValidators(){
        initTextFieldsList();
        ArrayList <ValidatorBase> validatorsList = new ArrayList<>();
        textFieldsList.forEach((textField) -> {
            putTextFieldNoEmptyValidator(textField, new RequiredFieldValidator());
        });

        ValidatorBase emailValidator = new ValidatorBase() {
                @Override
                protected void eval() {
                    setMessage("Adresse email invalide");
                    if (!isValidEmailAddress(emailClient_textField.getText())) {
                        hasErrors.set(true);
                    } else hasErrors.set(false);
                }
            };
        emailClient_textField.getValidators().add(emailValidator);

        emailClient_textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!(newValue.equals(oldValue))) {
                emailClient_textField.validate();
            }
        });

    }
    public void initTextField(){
        UnaryOperator<TextFormatter.Change> filter = (TextFormatter.Change t) -> {
            String newText = t.getControlNewText();
            if(newText.matches("^([^\\s].*)*$") && newText.length()< 50) {
                return t ;
            }
            return null ;
        };
        nomAdvers_textField.setTextFormatter(new TextFormatter<>(filter));
        nomsClient_textField.setTextFormatter(new TextFormatter<>(filter));
        prenomsClient_textField.setTextFormatter(new TextFormatter<>(filter));
        adresseClient_textField.setTextFormatter(new TextFormatter<>(filter));
    }
    
    public void initLimitLenghtEmail(){
        UnaryOperator<TextFormatter.Change> filter = (TextFormatter.Change t) -> {
            String newText = t.getControlNewText();
            if(newText.matches("^([^\\s].*)*$") && newText.length()< 100) {
                return t ;
            }
            return null ;
        };
        emailClient_textField.setTextFormatter(new TextFormatter<>(filter));
    }
    
    public void initPhoneNumberField(){
        UnaryOperator<TextFormatter.Change> filter = (TextFormatter.Change t) -> {
            String newText = t.getControlNewText() ;
            if(newText.matches("[0-9]*") && newText.length()< 18) {
                return t ;
            }
            return null ;
        };
        telephoneClient_textField.setTextFormatter(new TextFormatter<>(filter));
    }
    
    public void initTextFieldForNumbers(){
        UnaryOperator<TextFormatter.Change> filter = (TextFormatter.Change t) -> {
            String newText = t.getControlNewText() ;
            if(newText.matches("^([1-9][0-9]*)*") && newText.length() <= 13) {
                return t ;
            }
            return null ;
        };
        
        UnaryOperator<TextFormatter.Change> filterProv = (TextFormatter.Change t) -> {
            String newText = t.getControlNewText() ;
            if(newText.matches("([1-9][0-9]*)*|[0]") && newText.length() <= 13) {
                return t ;
            }
            return null ;
        };    
     honoraires_textField.setTextFormatter(new TextFormatter<>(filter));
     provisions_textField.setTextFormatter(new TextFormatter<>(filterProv));
    }
    
    public String removeLeadingEmptySpace(String s) {
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() > 1 && sb.charAt(sb.length()-1) == ' ') {
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }
    
    public void clearAllTextFields(){
        textFieldsList.forEach((textField) -> {
            textField.setText("");
        });
    }
}
