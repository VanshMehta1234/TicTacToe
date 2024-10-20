package org.example.tictactoe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Random;

public class SinglePlayerMode extends Application {
    private Button[][] buttons = new Button[3][3];
    private boolean playerTurn = true; // true if it's the player's turn
    private int playerScore = 0;
    private int aiScore = 0;
    private Label scoreLabel;

    @Override
    public void start(Stage primaryStage) {
        VBox vbox = new VBox(30);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #1A1A1A;");

        scoreLabel = new Label("Score: Player: 0 AI: 0");
        scoreLabel.setFont(Font.font("Futura", 24));
        scoreLabel.setTextFill(Color.LIGHTGRAY);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(15);
        grid.setVgap(15);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new Button("");
                buttons[row][col].setPrefSize(120, 120);
                buttons[row][col].setStyle("-fx-background-color: #333333; -fx-font-size: 36; "
                        + "-fx-text-fill: white; -fx-border-color: #AAAAAA; -fx-border-width: 2;");
                final int r = row;
                final int c = col;
                buttons[row][col].setOnAction(e -> handleButtonClick(r, c));
                grid.add(buttons[row][col], col, row);
            }
        }

        vbox.getChildren().addAll(scoreLabel, grid);

        // Reset Button
        Button resetButton = new Button("Reset Game");
        resetButton.setStyle("-fx-font-size: 16; -fx-background-color: #333333; -fx-text-fill: white; -fx-border-color: #AAAAAA; -fx-border-width: 2;");
        resetButton.setOnAction(e -> resetBoard());

        HBox hbox = new HBox(resetButton);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(10));
        hbox.setSpacing(10);

        vbox.getChildren().add(hbox);

        Scene scene = new Scene(vbox, 500, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tic-Tac-Toe - Single Player");
        primaryStage.show();
    }

    private void handleButtonClick(int r, int c) {
        if (!buttons[r][c].getText().equals("") || !playerTurn) {
            return; // Ignore the click if the button is already pressed or it's not the player's turn
        }

        buttons[r][c].setText("X");
        if (checkForWin("X")) {
            playerScore++;
            updateScore();
            resetBoard();
            return;
        }

        playerTurn = false; // Switch to AI's turn
        aiMove();

        if (checkForWin("O")) {
            aiScore++;
            updateScore();
            resetBoard();
        } else {
            playerTurn = true; // Switch back to player's turn
        }
    }

    private void aiMove() {
        Random rand = new Random();
        int r, c;

        do {
            r = rand.nextInt(3);
            c = rand.nextInt(3);
        } while (!buttons[r][c].getText().equals("")); // Keep trying until it finds an empty spot

        buttons[r][c].setText("O");
    }

    private boolean checkForWin(String symbol) {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if ((buttons[i][0].getText().equals(symbol) && buttons[i][1].getText().equals(symbol) && buttons[i][2].getText().equals(symbol)) ||
                    (buttons[0][i].getText().equals(symbol) && buttons[1][i].getText().equals(symbol) && buttons[2][i].getText().equals(symbol))) {
                return true;
            }
        }
        // Check diagonals
        if ((buttons[0][0].getText().equals(symbol) && buttons[1][1].getText().equals(symbol) && buttons[2][2].getText().equals(symbol)) ||
                (buttons[0][2].getText().equals(symbol) && buttons[1][1].getText().equals(symbol) && buttons[2][0].getText().equals(symbol))) {
            return true;
        }

        return false;
    }

    private void resetBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
            }
        }
        playerTurn = true; // Reset to player's turn
    }

    private void updateScore() {
        scoreLabel.setText("Score: Player: " + playerScore + "  |  AI: " + aiScore);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
