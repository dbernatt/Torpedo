
package Server;

import Models.BaseMap;
import javafx.application.Platform;

public class Game {

    private BaseMap p1Base;
    private BaseMap p2Base;

    private Player currentPlayer;

    private int p1fleetSize;
    private int p2fleetSize;

    public Game(){
        p1Base = new BaseMap(10);
        p2Base = new BaseMap(10);
        p1fleetSize = p1Base.getFleetSize();
        p2fleetSize = p2Base.getFleetSize();
    }

    public boolean hasWinner() {
        return p1fleetSize == 0 || p2fleetSize == 0;
    }

    public boolean legalMove(int row, int column, Player player) {
        System.out.println("Current player : " + currentPlayer.mark);
        if (player == currentPlayer) {
            if (player.mark == '1') {
                if (p2Base.getFieldValue(row, column) == 0 || p2Base.getFieldValue(row, column) == 1) {
                    currentPlayer = currentPlayer.getOpponent();
                    currentPlayer.otherPlayerMoved(row, column);
                    return true;
                }
            } else {
                if (p1Base.getFieldValue(row, column) == 0 || p1Base.getFieldValue(row, column) == 1) {
                    currentPlayer = currentPlayer.getOpponent();
                    currentPlayer.otherPlayerMoved(row, column);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isMyFleet(Player player, int row, int column){
        if(player.mark == '1'){
            if(p1Base.getFieldValue(row, column) == 1){
                return true;
            }
        }else{
            if(p2Base.getFieldValue(row, column) == 1){
                return true;
            }
        }
        return false;
    }

    public int getPositionValue(Player player, int row, int column){
        if(player.mark == '1'){
            return p1Base.getFieldValue(row, column);
        }else{
            return p2Base.getFieldValue(row, column);
        }
    }

    public void setPositionValue(Player player,int row, int column){
        if(player.mark == '1'){
            p1Base.setFieldValue(row,column,2);
        }else{
            p2Base.setFieldValue(row,column,2);
        }
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public int getCurrentFleetSize(Player player){
        if(player.mark == '1'){
            return p1fleetSize;
        }else{
            return p2fleetSize;
        }
    }

    public void decreaseCurrentFleetSize(Player player){
        if(player.mark == '1'){
            --this.p1fleetSize;
        }else{
            --this.p2fleetSize;
        }
    }

}
