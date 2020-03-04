
package controllers;

import appdatabase.bean.CategorieDocument;
import appdatabase.bean.Client;
import appdatabase.bean.Document;
import appdatabase.bean.Dossier;
import appdatabase.bean.TypeDocument;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import org.apache.commons.io.IOUtils;
import static controllers.HomeForFolderController.docsLoader;
import static controllers.CurrentFoldersController.currentFolder;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
/**
 *
 * @author Nyatchou
 */
public class AddDocumentController {
    
   
    @FXML
    private JFXComboBox<CategorieDocument> categoriesDoc_comboBox;

    @FXML
    private JFXComboBox<TypeDocument> typesDoc_comboBox;

    @FXML
    private JFXButton valider_button;

    @FXML
    private JFXTextField choiceDoc_textField;

    @FXML
    private JFXButton choiceDoc_button;
    
    private File selectedFile = null;
    
    private final int MAX_SIZE_FORFILE=31457280;
    

    @FXML
    public void initialize() throws IOException{

        initButtonsActions();
        initComboBox();
    }
    
    public boolean checkIfNoEmptyFields(){
        CategorieDocument cd = categoriesDoc_comboBox.getSelectionModel().getSelectedItem();
        if(cd == null || choiceDoc_textField.getText().equals(""))
            return false;
        List <TypeDocument> tdList = TypeDocument.listByCategorie(cd);
        if(tdList.isEmpty())
            return true;
        return typesDoc_comboBox.getSelectionModel().getSelectedItem() != null;
    }
    public String makePathToFolder(Dossier dossier){
        Client cl = dossier.getClient();
        int nb = Dossier.listByClient(cl).size();
        String path0 = dossier.getClient().getNom().toUpperCase()+" "+dossier.getClient().getPrenom().toUpperCase();
        return nb==1 ? path0 : path0+" "+String.valueOf(nb);
    }
    
    public void initComboBox(){              
        categoriesDoc_comboBox.setItems(FXCollections.observableList(CategorieDocument.all()));
        categoriesDoc_comboBox.valueProperty().addListener((ObservableValue<? extends CategorieDocument> observable, CategorieDocument oldValue, CategorieDocument newValue) -> {
            if(newValue != oldValue){
                List <TypeDocument> listTypesDocuments = TypeDocument.listByCategorie(newValue);
                if(!listTypesDocuments.isEmpty()){
                    typesDoc_comboBox.setDisable(false);
                    typesDoc_comboBox.setItems(FXCollections.observableArrayList(listTypesDocuments));
                }
                else {            
                    typesDoc_comboBox.setDisable(true);
                }
            }
        });
        typesDoc_comboBox.setDisable(true);
    }
    
    public void initButtonsActions() throws IOException, FileNotFoundException{
        choiceDoc_button.setOnAction(e -> {
            choiceDoc_textField.setEditable(true);
            FileChooser fileChooser = new FileChooser();
            selectedFile = fileChooser.showOpenDialog(null);
            if(selectedFile != null && selectedFile.length()<MAX_SIZE_FORFILE){
                choiceDoc_textField.setText(selectedFile.getName());
                choiceDoc_textField.setEditable(false);
            }

            else if(selectedFile.length()>=MAX_SIZE_FORFILE){
                Alert al = new Alert(Alert.AlertType.WARNING);
                al.setHeaderText("TAILLE DE FICHIER TROP ELEVEE");
                al.setContentText("Vous ne pouvez pas choisir un fichier de taille supérieure à 30 Mo !");
                al.show();
            }
        });
        
        valider_button.setOnAction((ActionEvent e) -> {
            if (this.checkIfNoEmptyFields()) {
                if(selectedFile != null){
                    InputStream input = null;
                    OutputStream output = null;
                    try {
                        input = new FileInputStream(selectedFile.getAbsolutePath());
                        String pathDoc = currentFolder.getCheminDossier()+"\\"+selectedFile.getName();
                        output = new FileOutputStream(pathDoc);
                        IOUtils.copy(input, output);
                        List<Document> docList = Document.listByDossier(currentFolder);
                        int i = 0;
                        while(i< docList.size() && !docList.get(i).getFichier().equals(pathDoc)){
                            i++;
                        }
                        if(i == docList.size()){
                            Document doc = new Document(selectedFile.getName(),
                                    typesDoc_comboBox.isDisabled() ? null : typesDoc_comboBox.getSelectionModel().getSelectedItem(),
                                    pathDoc, categoriesDoc_comboBox.getSelectionModel().getSelectedItem(), currentFolder);
                            doc.save();
                            ((DocumentsController)docsLoader.getController()).updateTabView();
                        }
                        
                    } catch (FileNotFoundException excep) {
                        Logger.getLogger(AddDocumentController.class.getName()).log(Level.SEVERE, null, excep);
                    } catch (IOException exc) {
                        Logger.getLogger(AddDocumentController.class.getName()).log(Level.SEVERE, null, exc);
                    } finally {
                        try {
                            input.close();
                            output.close();
                            
                        } catch (IOException ex) {
                            Logger.getLogger(AddDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setContentText("Veuillez choisir un fichier s'il vous plait ! "
                            + "Cliquez tout d'abord sur le bouton choisir  puis sélectionnez le fichier à choisir à l'aide de la boîte de dialogue qui "
                            + "s'ouvira et enfin validez.");
                    al.setHeaderText("AUCUN FICHIER CHOISI");
                    al.show();
                }
            } else {
                Alert al = new Alert(Alert.AlertType.WARNING);
                al.setContentText("Remplissez tous les champs s'il vous plait !");
                al.show();
            }
        });
    }
}
