import Models.BaseMap;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.scene.Node;

public class TorpedoClient extends SetupController{

    private Stage window;

    private ClientHandler clientHandler;
    private static int PORT = 8901;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private Button[][] map1Btn;
    private Button[][] map2Btn;
    private Button exittoMenubtn;

    private Label messageLabel;
    private Label homefleetLabel;
    private Label enemyfleetLabel;
    private Label homefleetSizeLabel;
    private Label enemyfleetSizeLabel;

    public TorpedoClient(String serverAddress, Stage window) throws IOException{

        this.window = window;

        socket = new Socket(serverAddress, PORT);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        String allfleet = this.getFleet();

        AnchorPane root = new AnchorPane();

        GridPane map1 = new GridPane();
        GridPane map2 = new GridPane();

        map1.setLayoutX(40);
        map1.setLayoutY(14);
        map2.setLayoutX(666);
        map2.setLayoutY(14);

        map1.setMinWidth(10);
        map1.setMinHeight(10);
        map2.setMinWidth(10);
        map2.setMinHeight(10);

        map1.setPrefWidth(600);
        map1.setPrefHeight(600);
        map2.setPrefWidth(600);
        map2.setPrefHeight(600);

        map1Btn = new Button[10][10];
        map2Btn = new Button[10][10];

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                map1Btn[i][j] = new Button();
                map1Btn[i][j].setMaxWidth(60);
                map1Btn[i][j].setMaxHeight(60);
                map1Btn[i][j].setMnemonicParsing(false);
                map1Btn[i][j].setPrefHeight(60);
                map1Btn[i][j].setPrefHeight(60);
                map1Btn[i][j].setId(new Integer(i).toString() + new Integer(j).toString());

                if(isFleet(allfleet,i,j)){
                    map1Btn[i][j].setStyle("-fx-background-color: Green");
                }

                map1.add(map1Btn[i][j],j,i);

                map2Btn[i][j] = new Button();
                map2Btn[i][j].setMaxWidth(60);
                map2Btn[i][j].setMaxHeight(60);
                map2Btn[i][j].setMnemonicParsing(false);
                map2Btn[i][j].setOnAction(this::fire);
                map2Btn[i][j].setPrefHeight(60);
                map2Btn[i][j].setPrefHeight(60);
                map2Btn[i][j].setId(new Integer(i).toString() + new Integer(j).toString());

                map2.add(map2Btn[i][j], j, i);
            }
        }

        for (int row = 0 ; row < 10 ; row++ ){
            RowConstraints rc = new RowConstraints();
            rc.setMaxHeight(100);
            rc.setMinHeight(10);
            rc.setPrefHeight(100);
            rc.setVgrow(Priority.SOMETIMES);
            map1.getRowConstraints().add(rc);
            map2.getRowConstraints().add(rc);
        }

        for (int col = 0 ; col < 10; col++ ) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setMaxWidth(100);
            cc.setMinWidth(10);
            cc.setPrefWidth(10);
            cc.setHgrow(Priority.SOMETIMES);
            map1.getColumnConstraints().add(cc);
            map2.getColumnConstraints().add(cc);
        }
        messageLabel = new Label("");
        messageLabel.setLayoutX(367);
        messageLabel.setLayoutY(635);
        messageLabel.setPrefHeight(20);
        messageLabel.setPrefWidth(537);
        messageLabel.setAlignment(Pos.CENTER);
        messageLabel.setFont(new Font(26));

        homefleetLabel = new Label("Home Fleet");
        enemyfleetLabel = new Label("Enemy Fleet");

        homefleetLabel.setLayoutX(252);
        homefleetLabel.setLayoutY(655);
        homefleetLabel.setAlignment(Pos.CENTER);
        homefleetLabel.prefHeight(35);
        homefleetLabel.setPrefWidth(124);
        homefleetLabel.setFont(new Font(24));

        enemyfleetLabel.setLayoutX(904);
        enemyfleetLabel.setLayoutY(655);
        enemyfleetLabel.setAlignment(Pos.CENTER);
        enemyfleetLabel.setFont(new Font(24));


        enemyfleetSizeLabel = new Label("10");
        enemyfleetSizeLabel.setLayoutX(250);
        enemyfleetSizeLabel.setLayoutY(690);
        enemyfleetSizeLabel.prefHeight(35);
        enemyfleetSizeLabel.prefWidth(124);
        enemyfleetSizeLabel.setAlignment(Pos.CENTER);
        enemyfleetSizeLabel.setFont(new Font(24));

        homefleetSizeLabel = new Label("10");
        homefleetSizeLabel.setLayoutX(905);
        homefleetSizeLabel.setLayoutY(690);
        homefleetSizeLabel.prefHeight(35);
        homefleetSizeLabel.prefWidth(124);
        homefleetSizeLabel.setAlignment(Pos.CENTER);
        homefleetSizeLabel.setFont(new Font(24));

        exittoMenubtn = new Button("EXIT TO MENU");
        exittoMenubtn.setLayoutX(570);
        exittoMenubtn.setLayoutY(725);
        exittoMenubtn.setMnemonicParsing(false);
        exittoMenubtn.prefHeight(35);
        exittoMenubtn.prefWidth(192);

        exittoMenubtn.setOnAction(this::exittoMenu);

        clientHandler = new ClientHandler(map1Btn,map2Btn,in,out,messageLabel,homefleetSizeLabel, enemyfleetSizeLabel);
        clientHandler.start();

        root.getChildren().addAll(map1, map2, messageLabel, homefleetLabel, enemyfleetLabel, homefleetSizeLabel, enemyfleetSizeLabel, exittoMenubtn);

        Scene gameScene= new Scene(root, 1280,800);

        window.setScene(gameScene);
        window.show();
    }

    private String getFleet(){
        String result = null;
        try {
            result = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private boolean isFleet(String allFleet, int row, int column){
        if(allFleet.contains(Integer.toString(row) + Integer.toString(column))){
            return true;
        }
        return false;
    }


    public void fire(ActionEvent event) {
        Button btn = (Button) event.getSource();

        int row = Character.getNumericValue(btn.getId().charAt(0));
        int column = Character.getNumericValue(btn.getId().charAt(1));

        String position = Integer.toString(row) + Integer.toString(column);

        out.println("FIRE" + position);
    }

    public void exittoMenu(ActionEvent event){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("View/MenuView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene mainScene = new Scene(root, 800, 600);

        window.setScene(mainScene);
        window.show();
    }
}
