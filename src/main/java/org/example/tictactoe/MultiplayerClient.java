package org.example.tictactoe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MultiplayerClient extends Application {
    private String symbol;
    private boolean isMyTurn = false;
    private Button[][] buttons = new Button[3][3];
    private PrintWriter out;
    private BufferedReader in;
    private Label statusLabel;

    @Override
    public void start(Stage primaryStage) {
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        statusLabel = new Label("Connecting to server...");
        vbox.getChildren().add(statusLabel);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new Button("");
                buttons[row][col].setPrefSize(100, 100);
                final int r = row;
                final int c = col;
                buttons[row][col].setOnAction(e -> handleMove(r, c));
                grid.add(buttons[row][col], col, row);
            }
        }

        vbox.getChildren().add(grid);

        Scene scene = new Scene(vbox, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tic-Tac-Toe - Multiplayer");
        primaryStage.show();

        connectToServer();
    }

    private void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Connected to server.");
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println("Received: " + message);
                        String finalMessage = message;
                        Platform.runLater(() -> handleServerMessage(finalMessage));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleServerMessage(String message) {
        if (message.startsWith("SYMBOL:")) {
            symbol = message.substring(7);
            isMyTurn = symbol.equals("X");
            updateStatusLabel();
        } else if (message.startsWith("MOVE:")) {
            String[] move = message.substring(5).split(",");
            int row = Integer.parseInt(move[0]);
            int col = Integer.parseInt(move[1]);
            String symbol = move[2];
            buttons[row][col].setText(symbol);
            isMyTurn = !this.symbol.equals(symbol); // Toggle turn based on the move
            updateStatusLabel();
        } else if (message.startsWith("WIN:")) {
            String winner = message.substring(4);
            statusLabel.setText(winner.equals(symbol) ? "You Win!" : "You Lose!");
        } else if (message.equals("TIE")) {
            statusLabel.setText("It's a Tie!");
        } else if (message.equals("START")) {
            updateStatusLabel(); // Update status label for start
        }
    }

    private void updateStatusLabel() {
        statusLabel.setText(isMyTurn ? "Your Turn" : "Opponent's Turn");
    }

    private void handleMove(int row, int col) {
        if (!isMyTurn || !buttons[row][col].getText().equals("")) {
            System.out.println("Invalid move attempted.");
            return;
        }

        buttons[row][col].setText(symbol);
        System.out.println("Sending move: " + row + "," + col);
        out.println("MOVE:" + row + "," + col + "," + symbol);
        isMyTurn = false; // Set to false after sending move
        updateStatusLabel();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
