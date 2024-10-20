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
import javafx.stage.Stage;
import java.util.Optional;
import javafx.scene.control.TextInputDialog;

public class TwoPlayerMode extends Application {
    private boolean isXTurn = true;
    private Button[][] buttons = new Button[3][3];
    private Label statusLabel;
    private Label scoreLabel;
    private int playerXScore = 0;
    private int playerOScore = 0;
    private String playerXName = "Player X";
    private String playerOName = "Player O";

    @Override
    public void start(Stage primaryStage) {
        // Ask for Player X's and Player O's names
        TextInputDialog dialogX = new TextInputDialog();
        dialogX.setTitle("Player X Name");
        dialogX.setHeaderText("Enter Player X's name:");
        Optional<String> resultX = dialogX.showAndWait();
        playerXName = resultX.orElse("Player X");

        TextInputDialog dialogO = new TextInputDialog();
        dialogO.setTitle("Player O Name");
        dialogO.setHeaderText("Enter Player O's name:");
        Optional<String> resultO = dialogO.showAndWait();
        playerOName = resultO.orElse("Player O");

        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #1A1A1A;"); // Consistent background color
        vbox.setAlignment(Pos.CENTER); // Center all elements in VBox

        // Styling for status and score labels
        statusLabel = new Label(playerXName + "'s Turn (X)");
        statusLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #E0E0E0;"); // Changed to light gray

        scoreLabel = new Label("Score: "+ playerXName + ": " + playerXScore + "  |  " + playerOName + ": " + playerOScore);
        scoreLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #E0E0E0;"); // Changed to light gray

        // Centering score label
        scoreLabel.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(statusLabel, scoreLabel);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(15);
        grid.setVgap(15);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new Button("");
                buttons[row][col].setPrefSize(120, 120); // Adjusted size for consistency
                buttons[row][col].setStyle("-fx-background-color: #333333; -fx-font-size: 36; "
                        + "-fx-text-fill: white; -fx-border-color: #AAAAAA; -fx-border-width: 2;");

                final int r = row;
                final int c = col;
                buttons[row][col].setOnAction(e -> handleButtonClick(r, c));

                grid.add(buttons[row][col], col, row);
            }
        }

        vbox.getChildren().add(grid);

        // Reset Button
        Button resetButton = new Button("Reset Game");
        resetButton.setStyle("-fx-font-size: 16; -fx-background-color: #333333; -fx-text-fill: white; -fx-border-color: #AAAAAA; -fx-border-width: 2;");
        resetButton.setOnAction(e -> resetGame());

        HBox hbox = new HBox(resetButton);
        hbox.setPadding(new Insets(10));
        hbox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(hbox);

        Scene scene = new Scene(vbox, 500, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tic-Tac-Toe - Two Player");
        primaryStage.show();
    }

    private void handleButtonClick(int r, int c) {
        if (!buttons[r][c].getText().equals("")) {
            return; // Ignore if button already clicked
        }

        buttons[r][c].setText(isXTurn ? "X" : "O");
        if (checkForWin(isXTurn ? "X" : "O")) {
            if (isXTurn) {
                playerXScore++;
            } else {
                playerOScore++;
            }
            updateScore();
            resetBoard();
        } else {
            isXTurn = !isXTurn; // Switch turns
            updateStatus();
        }
    }

    private boolean checkForWin(String symbol) {
        for (int i = 0; i < 3; i++) {
            if ((buttons[i][0].getText().equals(symbol) && buttons[i][1].getText().equals(symbol) && buttons[i][2].getText().equals(symbol)) ||
                    (buttons[0][i].getText().equals(symbol) && buttons[1][i].getText().equals(symbol) && buttons[2][i].getText().equals(symbol))) {
                return true;
            }
        }
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
        isXTurn = true; // Reset turn to Player X
        updateStatus();
    }

    private void resetGame() {
        playerXScore = 0;
        playerOScore = 0;
        updateScore();
        resetBoard();
    }

    private void updateStatus() {
        statusLabel.setText((isXTurn ? playerXName : playerOName) + "'s Turn (" + (isXTurn ? "X" : "O") + ")");
    }

    private void updateScore() {
        scoreLabel.setText("Score: "+ playerXName + ": " + playerXScore + "  |  " + playerOName + ": " + playerOScore);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
