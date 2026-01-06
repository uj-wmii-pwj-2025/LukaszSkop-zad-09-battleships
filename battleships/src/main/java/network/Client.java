package network;

import game.Board;
import game.EnemyBoard;
import network.Utils;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Client {
    private final String host;
    private final int port;
    private final String mapFile;

    public Client(String host, int port, String mapFile) {
        this.host = host;
        this.port = port;
        this.mapFile = mapFile;
    }

    public void run() {
        Board myBoard;
        try {
            myBoard = new Board(Utils.readMapFile(mapFile));
            myBoard.printBoard();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Socket socket = new Socket(host, port)) {
            System.out.println("Connected to server");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

            Random rand = new Random();
            EnemyBoard enemyBoard = new EnemyBoard();

            String myLastShot = Utils.randomShot(rand);
            String lastMessage = "start;" + myLastShot;
            int retries = 0;


            out.write(lastMessage + "\n");
            System.out.println("Sent: " + lastMessage);
            out.flush();

            while (true) {
                try {
                    String line = in.readLine();
                    if (line == null) break;

                    System.out.println("Received: " + line);
                    retries = 0;


                    if (line.equals("ostatni zatopiony")) {
                        int x = Utils.row(myLastShot.charAt(0));
                        int y = Utils.col(myLastShot.substring(1));
                        enemyBoard.markShot(x, y, "trafiony zatopiony;");

                        System.out.println("Wygrana");
                        System.out.println();
                        enemyBoard.print();
                        System.out.println();
                        myBoard.printBoard();
                        break;
                    }

                    String[] parts = line.split(";");
                    if (parts.length != 2) {
                        out.write(lastMessage + "\n");
                        out.flush();
                        continue;
                    }

                    String enemyResponseToMyShot = parts[0];
                    String enemyShot = parts[1];


                    int myX = Utils.row(myLastShot.charAt(0));
                    int myY = Utils.col(myLastShot.substring(1));
                    enemyBoard.markShot(myX, myY, enemyResponseToMyShot + ";");


                    int enemyX = Utils.row(enemyShot.charAt(0));
                    int enemyY = Utils.col(enemyShot.substring(1));
                    String myResponse = myBoard.shoot(enemyX, enemyY);


                    if (myResponse.equals("ostatni zatopiony")) {
                        out.write("ostatni zatopiony\n");
                        out.flush();
                        System.out.println("Przegrana");
                        System.out.println();
                        enemyBoard.print();
                        System.out.println();
                        myBoard.printBoard();
                        break;
                    }


                    myLastShot = Utils.randomShot(rand);
                    lastMessage = myResponse + myLastShot;

                    out.write(lastMessage + "\n");
                    System.out.println("Sent: " + lastMessage);
                    out.flush();

                } catch (IOException e) {
                    if (++retries >= 3) {
                        System.out.println("Błąd komunikacji");
                        break;
                    }
                    out.write(lastMessage + "\n");
                    out.flush();
                }
            }

        } catch (IOException e) {
            System.out.println("Połączenie przerwane: " + e.getMessage());
        }
    }
}