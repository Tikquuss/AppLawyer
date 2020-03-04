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
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import static controllers.PresentPageController.homeLoader;
import static controllers.PresentPageController.clientListLoader;
import static controllers.ClientsListController.modifClientStage;
import java.io.File;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.scene.control.TextFormatter;
import static controllers.PresentPageController.pathFolderRoot;

/**
 *
 * @author Nyatchou
 */
public class ModifClientController {
    
    @FXML
    private JFXButton modifClient_button;

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
    
    private Client client;
    
    @FXML
    public void initialize(){
        initButtonAction();
        initLimitLenghtEmail();
        initTextField();
        setValidators();
        initPhoneNumberField();
    }

    public void setClient(Client client) {
        this.client = client;
        nomsClient_textField.setText(client.getNom());
        prenomsClient_textField.setText(client.getPrenom());
        adresseClient_textField.setText(client.getAdresse());
        telephoneClient_textField.setText(client.getTelephone());
        emailClient_textField.setText(client.getEmail());
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
    
    public void setValidators(){
    
        ValidatorBase requireNom = new RequiredFieldValidator();
        ValidatorBase requirePrenom = new RequiredFieldValidator();
        ValidatorBase requireAddrClient = new RequiredFieldValidator();
        ValidatorBase requireEmail = new RequiredFieldValidator();
        ValidatorBase requireTelephone = new RequiredFieldValidator();

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
        putTextFieldValidator(telephoneClient_textField, requireTelephone);      
 
       }
    public boolean checkIfNoEmptyFields(){
        return 
          (    
            nomsClient_textField.validate() 
            && prenomsClient_textField.validate() 
            && adresseClient_textField.validate()
            && emailClient_textField.validate()
            && telephoneClient_textField.validate()
          );
    }
    
    public void initTextField(){
        UnaryOperator<TextFormatter.Change> filter = (TextFormatter.Change t) -> {
            String newText = t.getControlNewText();
            if(newText.matches("^([^\\s].*)*$") && newText.length()< 50) {
                return t ;
            }
            return null ;
        };
        nomsClient_textField.setTextFormatter(new TextFormatter<>(filter));
        prenomsClient_textField.setTextFormatter(new TextFormatter<>(filter));
        telephoneClient_textField.setTextFormatter(new TextFormatter<>(filter));
        adresseClient_textField.setTextFormatter(new TextFormatter<>(filter));
        emailClient_textField.setTextFormatter(new TextFormatter<>(filter));
    }
    
    public void initLimitLenghtEmail(){
        UnaryOperator<TextFormatter.Change> filter = (TextFormatter.Change t) -> {
            String newText = t.getControlNewText();
            if(newText.length()< 100) {
                return t ;
            }
            return null ;
        };
        emailClient_textField.setTextFormatter(new TextFormatter<>(filter));
    }
    
    public void initPhoneNumberField(){
        UnaryOperator<TextFormatter.Change> filter = (TextFormatter.Change t) -> {
            String newText = t.getControlNewText() ;
            if(newText.matches("^[0-9]*") && newText.length()< 18) {
                return t ;
            }
            return null ;
        };
        telephoneClient_textField.setTextFormatter(new TextFormatter<>(filter));
    }
    
    public void initButtonAction(){
        modifClient_button.setOnAction(e -> {
            if(checkIfNoEmptyFields()){
                String noms = removeLeadingEmptySpace(nomsClient_textField.getText());
                String prenoms = removeLeadingEmptySpace(prenomsClient_textField.getText());
                if(!client.getNom().equals(noms)){
                    List <Dossier> listDossier = Dossier.listByClient(client);
                    client.setNom(noms);
                    for(int ind = 0; ind < listDossier.size(); ind++){
                        Dossier doc = listDossier.get(ind);
                        String pathDoc0 = pathFolderRoot+ "\\DOSSIER "+doc.getClient().getNom().toUpperCase()+" "+doc.getClient().getPrenom().toUpperCase();
                        String pathDoc = ind == 0 ? pathDoc0 : pathDoc0 + " " + String.valueOf(ind+1);                

                        String newPathDoc0 = pathFolderRoot+"\\DOSSIER "+client.getNom().toUpperCase()+" "+client.getPrenom().toUpperCase(); 
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
                    }
                }
                if(!client.getPrenom().equals(prenoms)){
                    List <Dossier> listDossier = Dossier.listByClient(client);
                    client.setPrenom(prenoms);
                    for(int ind = 0; ind < listDossier.size(); ind++){
                        Dossier doc = listDossier.get(ind);
                        String pathDoc0 = pathFolderRoot+"\\DOSSIER "+doc.getClient().getNom().toUpperCase()+" "+doc.getClient().getPrenom().toUpperCase();
                        String pathDoc = ind == 0 ? pathDoc0 : pathDoc0 + " " + String.valueOf(ind+1);
                        /* Document.listByDossier(doc).forEach(a -> {
                        a.setFichier(pathDoc+"\\"+a.getNom());
                        a.update();
                        });*/
                        /*((Client)event.getTableView().getItems().get(event.getTablePosition().getRow())).setPrenom(removeLeadingEmptySpace(event.getNewValue()));
                        ((Client)event.getTableView().getItems().get(event.getTablePosition().getRow())).update();*/
                        String newPathDoc0 = pathFolderRoot+"\\DOSSIER "+client.getNom().toUpperCase()+" "+client.getPrenom().toUpperCase();
                        String newPathDoc = ind == 0 ? newPathDoc0 : newPathDoc0+" "+String.valueOf(ind+1);
                        Document.listByDossier(doc).forEach(a -> {
                            a.setFichier(newPathDoc+"\\"+a.getNom());
                            a.update();
                            System.out.println(a.getFichier());
                        });
                        File file = new File(pathDoc);
                        File newFile = new File(newPathDoc);
                        System.out.println(newPathDoc);
                        file.renameTo(newFile);
                        doc.setCheminDossier(newPathDoc);
                        doc.update();
                    }
                }
                if(!client.getAdresse().equals(removeLeadingEmptySpace(adresseClient_textField.getText()))){
                    client.setAdresse(removeLeadingEmptySpace(adresseClient_textField.getText()));
                }
                if(!client.getEmail().equals(emailClient_textField.getText())){
                    client.setEmail(emailClient_textField.getText());
                }
                if(!client.getTelephone().equals(removeLeadingEmptySpace(telephoneClient_textField.getText()))){
                    client.setTelephone(removeLeadingEmptySpace(telephoneClient_textField.getText()));
                }
            client.update(); 
            ((CurrentFoldersController)homeLoader.getController()).initListView();
            ((ClientsListController)clientListLoader.getController()).initTable();
             modifClientStage.close();
            }
            
        });
    } 
    public String removeLeadingEmptySpace(String s) {
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() > 1 && sb.charAt(sb.length()-1) == ' ') {
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }
}
