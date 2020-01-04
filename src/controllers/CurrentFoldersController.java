package controllers;

import appdatabase.bean.Dossier;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CurrentFoldersController {

    @FXML
    private JFXListView<Dossier> listFolders_listView;

    @FXML
    private JFXButton openFolder_button;

    @FXML
    private JFXButton newFolder_button;
    
    public void initialize() throws IOException{
        Stage stageNewFolder = new Stage();
        Parent newFoldParent = FXMLLoader.load(getClass().getResource("../views/CreateFolder.fxml"));
        stageNewFolder.setScene(new Scene(newFoldParent));
        stageNewFolder.setResizable(false);
        stageNewFolder.initModality(Modality.APPLICATION_MODAL);
        newFolder_button.setOnAction(e -> {
            //String dirName = "F:\\Dossier Client\\";
            //File dir = new File(dirName);
            //boolean isCreated = dir.mkdirs();
            stageNewFolder.show();
        });
    }

}
