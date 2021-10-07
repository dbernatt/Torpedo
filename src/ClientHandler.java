import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;



public class ClientHandler extends Thread {

    private String startText;

    private Button[][] mapBtn1;
    private Button[][] mapBtn2;

    private BufferedReader input;
    private PrintWriter output;
    private Label messageLabel;
    private Label homefleetSizeLabel;
    private Label enemyfleetSizeLabel;

    public ClientHandler(Button[][] mapBtn1, Button[][] mapBtn2, BufferedReader input, PrintWriter output, Label messageLabel, Label homefleetSizeLabel, Label enemyfleetSizeLabel){
        this.mapBtn1 = mapBtn1;
        this.mapBtn2 = mapBtn2;
        this.input = input;
        this.output = output;
        this.messageLabel = messageLabel;
        this.homefleetSizeLabel = homefleetSizeLabel;
        this.enemyfleetSizeLabel = enemyfleetSizeLabel;
    }

    public void run(){

        startText = null;
        try {
            startText = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                messageLabel.setText(startText);
            }
        });


        while(true){

            String response;
            int row;
            int column;

            try {
                response = input.readLine();


                if(response.startsWith("VALID_MOVE_BANG_")){

                    System.out.println(response);
                    System.out.println(response.substring(response.length() - 1));

                    System.out.println("Bang! Opponent turn!");
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            messageLabel.setText("Bang! Opponent turn!");
                            homefleetSizeLabel.setText(response.substring(response.length() - 1));
                        }
                    });

                    row = Character.getNumericValue(response.charAt(response.length() - 3));
                    column = Character.getNumericValue(response.charAt(response.length() - 2));

                    mapBtn2[row][column].setStyle("-fx-background-color: Red");

                } else if(response.startsWith("VALID_MOVE_MISS_")){

                    System.out.println("Missed! Oppenent turn!");
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            messageLabel.setText("Missed! Oppenent turn!");
                        }
                    });

                    row = Character.getNumericValue(response.charAt(response.length() - 2));
                    column = Character.getNumericValue(response.charAt(response.length() - 1));

                    mapBtn2[row][column].setStyle("-fx-background-color: DarkTurquoise");

                }else if(response.startsWith("OPPONENT_MOVED_BANG_")){
                    System.out.println(response);
                    System.out.println(response.substring(response.length() - 1));
                    System.out.println("Bang! Opponent turn!");
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            messageLabel.setText("Bang! Opponent turn!");
                            enemyfleetSizeLabel.setText(response.substring(response.length() - 1));
                        }
                    });

                    row = Character.getNumericValue(response.charAt(response.length() - 3));
                    column = Character.getNumericValue(response.charAt(response.length() - 2));

                    mapBtn1[row][column].setStyle("-fx-background-color: Red");

                }else if(response.startsWith("OPPONENT_MOVED_MISS_")){

                    System.out.println("Missed! Your turn!");
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            messageLabel.setText("Missed! Your turn!");
                        }
                    });

                    row = Character.getNumericValue(response.charAt(response.length() - 2));
                    column = Character.getNumericValue(response.charAt(response.length() - 1));

                    mapBtn1[row][column].setStyle("-fx-background-color: Black");

                }else if(response.startsWith("VICTORY")){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            messageLabel.setText("You Win!");
                        }
                    });
                    return;
                }else if (response.startsWith("DEFEAT")){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            messageLabel.setText("You lose!");
                        }
                    });
                    return;
                }
            } catch (IOException e){
                e.printStackTrace();
            }

        }

    }
}
