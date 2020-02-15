
package controllers;

import appdatabase.bean.CategorieDocument;
import appdatabase.bean.Document;
import appdatabase.bean.Dossier;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import static controllers.CurrentFoldersController.currentFolder;
import dbsimulator.BeansObjects;
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
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.IOUtils;
import static controllers.HomeForFolderController.docsLoader;
import static controllers.CurrentFoldersController.currentFolder;
import java.util.List;
import javafx.scene.control.Alert;
/**
 *
 * @author Utilisateur
 */
public class AddDocumentController {
    
   
    @FXML
    private JFXComboBox<CategorieDocument> categoriesDoc_comboBox;

    @FXML
    private JFXComboBox<String> typesDoc_comboBox;

    @FXML
    private JFXButton valider_button;

    @FXML
    private JFXTextField choiceDoc_textField;

    @FXML
    private JFXButton choiceDoc_button;
    
    private File selectedFile = null;
    

    @FXML
    public void initialize() throws IOException{
        //String dirName = "F:\\Dossier Client\\";
        //File dir = new File(dirName);
        //boolean isCreated = dir.mkdirs();
        initButtonsActions();
        
        categoriesDoc_comboBox.setItems(FXCollections.observableList(BeansObjects.ctgdocs));
        typesDoc_comboBox.setItems(FXCollections.observableList(FXCollections.observableList(BeansObjects.typesDoc)));
    }
    
    public boolean checkIfNoEmptyFields(){
        return !(categoriesDoc_comboBox.getSelectionModel().getSelectedItem() == null || typesDoc_comboBox.getSelectionModel().getSelectedItem()==null || choiceDoc_textField.getText()=="");
    }
    public String makePathToFolder(Dossier dossier){
        return dossier.getClient().getNom().toUpperCase()+" "+dossier.getClient().getPrenom().toUpperCase()+" "+dossier.getId();
    }
    public void initButtonsActions() throws IOException, FileNotFoundException{
        choiceDoc_button.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            selectedFile = fileChooser.showOpenDialog(null);
            if(selectedFile != null)
                choiceDoc_textField.setText(selectedFile.getName());
        });
        
        valider_button.setOnAction(e -> {
            if(this.checkIfNoEmptyFields())
                if(selectedFile != null){
                    InputStream input = null;
                    OutputStream output = null;
                    try {
                            input = new FileInputStream(selectedFile.getAbsolutePath());
                            String pathDoc = "F:\\Dossiers Clients\\DOSSIER "+makePathToFolder(currentFolder)+"\\"+selectedFile.getName();
                            output = new FileOutputStream(pathDoc);
                            IOUtils.copy(input, output);
                            CategorieDocument cd = new CategorieDocument();
                            cd.save();
                            List<Document> docList = Document.listByDossier(currentFolder);
                            int i = 0;
                            while(i< docList.size() && !docList.get(i).getFichier().equals(pathDoc)){
                                i++;
                            }
                            if(i == docList.size()){
                                Document doc = new Document(selectedFile.getName(), typesDoc_comboBox.getSelectionModel().getSelectedItem(), 
                                pathDoc, cd, currentFolder);
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
                                + "Cliquez tout d'abord sur le bouton choisir  puis sélectionnez le fichier àchoisir à l'aide de la boîte de dialogue qui "
                                + "s'ouvira et enfin validez.");
                        al.setHeaderText("AUCUN FICHIER CHOISI");
                        al.show();
                }
            else{
                        Alert al = new Alert(Alert.AlertType.WARNING);
                        al.setContentText("Remplissez tous les champs s'il vous plait !");
                        al.show();
            }
            
        });
    }
}
