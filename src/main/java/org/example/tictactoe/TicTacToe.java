package org.example.tictactoe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TicTacToe extends Application {
    private boolean isXTurn = true;
    private Button[][] buttons = new Button[3][3];
    private Label statusLabel;

    @Override
    public void start(Stage primaryStage) {
        VBox vbox = new VBox();
        statusLabel = new Label("X's Turn");
        vbox.getChildren().add(statusLabel);

        GridPane grid = new GridPane();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new Button("");
                buttons[row][col].setPrefSize(100, 100);

                final int r = row;
                final int c = col;
                buttons[row][col].setOnAction(e -> handleButtonClick(r, c));

                grid.add(buttons[row][col], col, row);
            }
        }

        vbox.getChildren().add(grid);
        Scene scene = new Scene(vbox, 300, 350);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tic-Tac-Toe");
        primaryStage.show();
    }

    private void handleButtonClick(int r, int c) {
        if (buttons[r][c].getText().isEmpty()) {
            buttons[r][c].setText(isXTurn ? "X" : "O");
        }

        if (checkWinner()) {
            statusLabel.setText(isXTurn ? "X" : "O" + " wins!");
            disableButtons();
        } else if (isDraw()) {
            statusLabel.setText("It's a draw!");
            disableButtons();
        } else {
            isXTurn = !isXTurn;
            statusLabel.setText(isXTurn ? "X's Turn" : "O's Turn");
        }
    }

    // Disable all buttons after the game ends
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

    private boolean checkWinner() {
        for (int row = 0; row < 3; row++) {
            if (!buttons[row][0].getText().isEmpty() &&
                    buttons[row][0].getText().equals(buttons[row][1].getText()) &&
                    buttons[row][0].getText().equals(buttons[row][2].getText())) {
                return true;
            }
        }

        for (int col = 0; col < 3; col++) {
            if (!buttons[0][col].getText().isEmpty() &&
                    buttons[0][col].getText().equals(buttons[1][col].getText()) &&
                    buttons[0][col].getText().equals(buttons[2][col].getText())) {
                return true;
            }
        }

        if (!buttons[0][0].getText().isEmpty() &&
                buttons[0][0].getText().equals(buttons[1][1].getText()) &&
                buttons[0][0].getText().equals(buttons[2][2].getText())) {
            return true;
        }

        if (!buttons[0][2].getText().isEmpty() &&
                buttons[0][2].getText().equals(buttons[1][1].getText()) &&
                buttons[0][2].getText().equals(buttons[2][0].getText())) {
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
