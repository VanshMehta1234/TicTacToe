package org.example.tictactoe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

public class SinglePlayerMode extends Application {
    private boolean isXTurn = true;
    private Button[][] buttons = new Button[3][3];
    private Label statusLabel;
    private boolean isAITurn = false;

    @Override
    public void start(Stage primaryStage) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(15));

        statusLabel = new Label("Player X's Turn");
        statusLabel.setStyle("-fx-font-size: 18");
        vbox.getChildren().add(statusLabel);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new Button("");
                buttons[row][col].setPrefSize(100, 100);
                buttons[row][col].setStyle("-fx-font-size: 24; -fx-background-color: lightblue;");

                final int r = row;
                final int c = col;
                buttons[row][col].setOnAction(e -> handleButtonClick(r, c));

                grid.add(buttons[row][col], col, row);
            }
        }

        vbox.getChildren().add(grid);

        Button resetButton = new Button("Reset Game");
        resetButton.setStyle("-fx-font-size: 16; -fx-background-color: lightcoral;");
        resetButton.setOnAction(e -> resetGame());

        HBox hbox = new HBox(resetButton);
        hbox.setPadding(new Insets(10));
        hbox.setSpacing(10);

        vbox.getChildren().add(hbox);

        Scene scene = new Scene(vbox, 320, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tic-Tac-Toe");
        primaryStage.show();
    }

    private int[][] checkWinner() {
        for (int row = 0; row < 3; row++) {
            if (!buttons[row][0].getText().isEmpty() &&
                    buttons[row][0].getText().equals(buttons[row][1].getText()) &&
                    buttons[row][0].getText().equals(buttons[row][2].getText())) {
                return new int[][] {{row, 0}, {row, 1}, {row, 2}}; // Winning row
            }
        }

        for (int col = 0; col < 3; col++) {
            if (!buttons[0][col].getText().isEmpty() &&
                    buttons[0][col].getText().equals(buttons[1][col].getText()) &&
                    buttons[0][col].getText().equals(buttons[2][col].getText())) {
                return new int[][] {{0, col}, {1, col}, {2, col}}; // Winning column
            }
        }

        if (!buttons[0][0].getText().isEmpty() &&
                buttons[0][0].getText().equals(buttons[1][1].getText()) &&
                buttons[0][0].getText().equals(buttons[2][2].getText())) {
            return new int[][] {{0, 0}, {1, 1}, {2, 2}}; // Main diagonal
        }

        if (!buttons[0][2].getText().isEmpty() &&
                buttons[0][2].getText().equals(buttons[1][1].getText()) &&
                buttons[0][2].getText().equals(buttons[2][0].getText())) {
            return new int[][] {{0, 2}, {1, 1}, {2, 0}}; // Anti-diagonal
        }

        return null;
    }

    private void handleButtonClick(int r, int c) {
        if (buttons[r][c].getText().isEmpty() && !isAITurn) {
            buttons[r][c].setText(isXTurn ? "X" : "O");

            int[][] winningCombination = checkWinner();
            if (winningCombination != null) {
                statusLabel.setText(isXTurn ? "X wins!" : "O wins!");
                highlightWinningCombination(winningCombination);
                disableButtons();
            } else if (isDraw()) {
                statusLabel.setText("It's a draw!");
                disableButtons();
            } else {
                isXTurn = !isXTurn;
                isAITurn = !isAITurn;
                statusLabel.setText(isXTurn ? "Player X's Turn" : "AI's Turn");
                if (isAITurn) {
                    makeAIMove();
                }
            }
        }
    }

    private void makeAIMove() {
        Random random = new Random();
        int aiRow, aiCol;

        do {
            aiRow = random.nextInt(3);
            aiCol = random.nextInt(3);
        } while (!buttons[aiRow][aiCol].getText().isEmpty());

        buttons[aiRow][aiCol].setText("O");

        int[][] winningCombination = checkWinner();
        if (winningCombination != null) {
            statusLabel.setText("O wins!");
            highlightWinningCombination(winningCombination);
            disableButtons();
        } else if (isDraw()) {
            statusLabel.setText("It's a draw!");
            disableButtons();
        } else {
            isXTurn = !isXTurn;
            isAITurn = !isAITurn;
            statusLabel.setText(isXTurn ? "Player X's Turn" : "AI's Turn");
        }
    }

    private void highlightWinningCombination(int[][] winningCombination) {
        for (int[] pos : winningCombination) {
            buttons[pos[0]][pos[1]].setStyle("-fx-background-color: #90EE90; -fx-font-size: 24;");
        }
    }

    private void disableButtons() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setDisable(true);
            }
        }
    }

    private boolean isDraw() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setDisable(false);
                buttons[row][col].setStyle("-fx-font-size: 24; -fx-background-color: lightblue;");
            }
        }
        isXTurn = true;
        isAITurn = false;
        statusLabel.setText("Player X's Turn");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
