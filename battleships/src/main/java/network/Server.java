package network;

import game.Board;
import game.EnemyBoard;
import network.Utils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Server {
    private final int PORT;
    private final String mapFile;

    public Server(int port, String mapFile) {
        PORT = port;
        this.mapFile = mapFile;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepted connection from " + clientSocket.getInetAddress().getHostName());

            clientSocket.setSoTimeout(1000);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8));

            Board myBoard = new Board(Utils.readMapFile(mapFile));
            myBoard.printBoard();

            Random rand = new Random();
            EnemyBoard enemyBoard = new EnemyBoard();

            String lastMessage = null;
            String myLastShot = null;
            int retries = 0;

            while (true) {
                try {
                    String line = in.readLine();
                    if (line == null) break;

                    System.out.println("Received: " + line);
                    retries = 0;


                    if (line.equals("ostatni zatopiony")) {
                        if (myLastShot != null) {
                            int x = Utils.row(myLastShot.charAt(0));
                            int y = Utils.col(myLastShot.substring(1));
                            enemyBoard.markShot(x, y, "trafiony zatopiony;");
                        }

                        System.out.println("Wygrana");
                        System.out.println();
                        enemyBoard.print();
                        System.out.println();
                        myBoard.printBoard();
                        break;
                    }

                    String[] parts = line.split(";");
                    if (parts.length != 2) {
                        if (lastMessage != null) {
                            out.write(lastMessage + "\n");
                            out.flush();
                        }
                        continue;
                    }

                    String enemyResponseToMyShot = parts[0];
                    String enemyShot = parts[1];


                    if (!enemyResponseToMyShot.equals("start") && myLastShot != null) {
                        int myX = Utils.row(myLastShot.charAt(0));
                        int myY = Utils.col(myLastShot.substring(1));
                        enemyBoard.markShot(myX, myY, enemyResponseToMyShot + ";");
                    }


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
                    if (lastMessage != null) {
                        out.write(lastMessage + "\n");
                        out.flush();
                    }
                }
            }

            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}