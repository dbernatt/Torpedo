
package Server;

import Models.BaseMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A server for a network multi-player tic tac toe game. Modified and extended from
 * the class presented in Deitel and Deitel "Java How to Program" book. I made a bunch
 * of enhancements and rewrote large sections of the code. The main change is instead of
 * passing *data* between the client and server, I made a TTTP (tic tac toe protocol)
 * which is totally plain text, so you can test the game with Telnet. The messages of
 * TTTP are:
 *
 *  Client -> Server           Server -> Client
 *  ----------------           ----------------
 *  MOVE <n>  (0 <= n <= 8)    WELCOME <char>  (char in {X, O})
 *  QUIT                       VALID_MOVE
 *                             OTHER_PLAYER_MOVED <n>
 *                             VICTORY
 *                             DEFEAT
 *                             TIE
 *                             MESSAGE <text>
 *
 * A second change is that it allows an unlimited number of pairs of players to play.
 */
public class Server {
    public static void main(String[] args) throws Exception {
        try (ServerSocket listener = new ServerSocket(8901)) {
            System.out.println("Torpedo Server is Running");
            while (true) {

                Game game = new Game();
                Player player1 = new Player(listener.accept(), '1', game);
                System.out.println("Player 1 connected");
                Player player2 = new Player(listener.accept(), '2', game);
                System.out.println("Player 2 connected");
                System.out.println("Players connected");
                player1.setOpponent(player2);
                player2.setOpponent(player1);
                game.setCurrentPlayer(player1);
                player1.start();
                player2.start();
            }
        }
    }
}
