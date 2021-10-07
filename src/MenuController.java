import Models.BaseMap;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.scene.Node;

import java.io.IOException;

public class MenuController {


    @FXML
    private Button exitBTN;

    @FXML
    private Button optionBTN;

    @FXML
    private Button playBTN;

    @FXML
    void play(ActionEvent event) throws IOException{
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        Parent setupViewParent = null;

        try {
            setupViewParent = FXMLLoader.load(getClass().getResource("View/Waiting.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load the fxml file!");

        }

        Scene setupScene = new Scene(setupViewParent);
        window.setScene(setupScene);
        window.show();

        String serverAdress = "localhost";
        TorpedoClient torpedoClient = new TorpedoClient(serverAdress, window);
    }

    @FXML
    void options(ActionEvent event) {

    }

    @FXML
    void exit(ActionEvent event) {
        Platform.exit();
    }

}
