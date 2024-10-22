package org.example.tictactoe;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer {
    private static final int PORT = 12345;
    private static String[][] board = new String[3][3]; // 3x3 board for Tic-Tac-Toe
    private static ArrayList<PlayerHandler> players = new ArrayList<>(); // List of connected players

    public static void main(String[] args) {
        System.out.println("Tic-Tac-Toe Server started on port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept(); // Accept new connections
                System.out.println("Player connected: " + (players.size() + 1));
                PlayerHandler playerHandler = new PlayerHandler(clientSocket, players.size() + 1);
                players.add(playerHandler);
                new Thread(playerHandler).start(); // Start a new thread for each player
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class PlayerHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private int playerId;

        public PlayerHandler(Socket socket, int playerId) {
            this.socket = socket;
            this.playerId = playerId;
            initializeBoard(); // Initialize the board for a new game
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out.println("SYMBOL:" + (playerId == 1 ? "X" : "O")); // Assign symbol
                if (players.size() == 2) {
                    out.println("START"); // Notify both players when the game starts
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void initializeBoard() {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    board[i][j] = null; // Initialize the board to null
                }
            }
        }

        @Override
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Received move from player: " + message);
                    handleMove(message); // Process player's move
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close(); // Close socket on exit
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleMove(String message) {
            // Split the message properly to get the action and its details
            String[] parts = message.split(":");
            if (parts.length != 2 || !parts[0].equals("MOVE")) {
                System.out.println("Invalid message format: " + message);
                return; // Early exit if the format is not as expected
            }

            String[] moveDetails = parts[1].split(",");
            if (moveDetails.length != 3) {
                System.out.println("Invalid move details: " + parts[1]);
                return; // Early exit if the move details are not correct
            }

            int row;
            int col;
            String symbol = moveDetails[2];

            try {
                row = Integer.parseInt(moveDetails[0]);
                col = Integer.parseInt(moveDetails[1]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format: " + e.getMessage());
                return; // Early exit if parsing fails
            }

            if (board[row][col] == null) { // Check if the move is valid
                board[row][col] = symbol; // Place the symbol on the board
                broadcastMove(row, col, symbol); // Broadcast the move to all players
                if (checkForWin(symbol)) {
                    broadcastMessage("WIN:" + symbol); // Notify win
                } else if (isBoardFull()) {
                    broadcastMessage("TIE"); // Notify tie
                }
            } else {
                System.out.println("Invalid move attempted: " + row + "," + col);
            }
        }


        private void broadcastMove(int row, int col, String symbol) {
            for (PlayerHandler player : players) {
                player.out.println("MOVE:" + row + "," + col + "," + symbol); // Notify players of the move
            }
        }

        private void broadcastMessage(String message) {
            for (PlayerHandler player : players) {
                player.out.println(message); // Send message to all players
            }
        }

        private boolean checkForWin(String symbol) {
            // Check rows, columns, and diagonals for a win
            for (int i = 0; i < 3; i++) {
                if ((board[i][0] != null && board[i][0].equals(symbol) &&
                        board[i][1] != null && board[i][1].equals(symbol) &&
                        board[i][2] != null && board[i][2].equals(symbol)) ||
                        (board[0][i] != null && board[0][i].equals(symbol) &&
                                board[1][i] != null && board[1][i].equals(symbol) &&
                                board[2][i] != null && board[2][i].equals(symbol))) {
                    return true; // Found a winning row or column
                }
            }
            // Check diagonals
            return (board[0][0] != null && board[0][0].equals(symbol) &&
                    board[1][1] != null && board[1][1].equals(symbol) &&
                    board[2][2] != null && board[2][2].equals(symbol)) ||
                    (board[0][2] != null && board[0][2].equals(symbol) &&
                            board[1][1] != null && board[1][1].equals(symbol) &&
                            board[2][0] != null && board[2][0].equals(symbol));
        }

        private boolean isBoardFull() {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == null) {
                        return false; // Found an empty space
                    }
                }
            }
            return true; // No empty spaces found, board is full
        }
    }
}
