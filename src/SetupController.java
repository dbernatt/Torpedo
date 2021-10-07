import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;



public class SetupController {

    private boolean opponent = false;

    @FXML
    public void startButton(ActionEvent event){

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

//        Game client = new TicTacToeClient(serverAddress);

//        while(!this.opponent);

//        GameController gameController = new GameController(1, window);
    }

    @FXML
    private void joinButton(ActionEvent event){

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

//        this.opponent = true;

//        GameController gameController = new GameController(2, window);
    }
}
