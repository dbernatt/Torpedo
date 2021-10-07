package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player extends Thread {

    char mark;
    private Player opponent;
    private Game game;

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public Player(Socket socket, char mark, Game game) {
        this.socket = socket;
        this.mark = mark;
        this.game = game;

        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            sendFleet();

        } catch (IOException e) {
            System.out.println("Player died: " + e);
        }
    }

    public Player getOpponent() {
        return this.opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public void otherPlayerMoved(int row, int column) {

        if(game.getPositionValue(this, row, column) == 0){

            output.println("OPPONENT_MOVED_MISS_" + row + column);//**

        }else if(game.getPositionValue(this,row,column) == 1){
            game.decreaseCurrentFleetSize(this);
            output.println("OPPONENT_MOVED_BANG_" + row + column + game.getCurrentFleetSize(this));//**
        }

        if(game.hasWinner()){
            output.println("DEFEAT");
        }

    }

    private void sendFleet(){
        String result = "";
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(game.isMyFleet(this,i,j)){
                    result = result + i + j + " ";
                }
            }
        }
        output.println(result);
    }


    public void run() {

        String command;
        try {

            if (mark == '1') {
                output.println("Your move...");
            }else{
                output.println("Opponent move...");
            }

            while (true) {
                command = input.readLine();

                if(command.startsWith("FIRE")){

                    int row = Character.getNumericValue(command.charAt(4));
                    int column = Character.getNumericValue(command.charAt(5));

                    if(game.legalMove(row,column,this)){

                        if(game.getPositionValue(this.opponent,row,column) == 0){

                            game.setPositionValue(this.opponent,row,column);//**
                            output.println("VALID_MOVE_MISS_" + row + column);//**

                        }else if(game.getPositionValue(this.opponent,row,column) == 1){

                            game.setPositionValue(this.opponent,row,column);//**
                            output.println("VALID_MOVE_BANG_" + row + column + game.getCurrentFleetSize(this.opponent));//**
                        }

                        if(game.hasWinner()){
                            output.println("VICTORY");
                        }

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Player died: " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Socket closing error!");
            }
        }
    }
}