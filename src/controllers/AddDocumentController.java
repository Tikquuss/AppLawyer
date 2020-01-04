
package controllers;

import appdatabase.bean.CategorieDocument;
import appdatabase.bean.Document;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
    
    private InputStream input = null;
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
                    try {
                            input = new FileInputStream(selectedFile.getAbsolutePath());
                            OutputStream output = new FileOutputStream("F:\\Dossier Client\\"+selectedFile.getName());
                            IOUtils.copy(input, output);
                            Document doc = new Document(selectedFile.getName(), typesDoc_comboBox.getSelectionModel().getSelectedItem(), 
                                    "F:\\Dossier Client\\"+selectedFile.getName(), categoriesDoc_comboBox.getSelectionModel().getSelectedItem());
                            doc.save();
                            ((DocumentsController)docsLoader.getController()).updateTabView();
                        } catch (FileNotFoundException excep) {
                            Logger.getLogger(AddDocumentController.class.getName()).log(Level.SEVERE, null, excep);
                        } catch (IOException exc) {
                            Logger.getLogger(AddDocumentController.class.getName()).log(Level.SEVERE, null, exc);
                        } finally {
                            try {
                                input.close();
                            } catch (IOException ex) {
                                Logger.getLogger(AddDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
        });
    }
}
