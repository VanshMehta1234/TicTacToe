package org.example.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class TicTacToe extends Application {
    private boolean isXTurn = true;
    private Button[][] buttons = new Button[3][3];
    private Label statusLabel;
    @Override
    public void start(Stage primaryStage) {
        VBox vbox = new VBox();
        statusLabel = new Label("X's Turn");
        vbox.getChildren().add(statusLabel);

        // Create a GridPane to hold the 3x3 board
        GridPane grid = new GridPane();

        // Initialize the 3x3 board with buttons
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new Button("");
                buttons[row][col].setPrefSize(100, 100);  // Set button size

                // Handle button click events
                final int r = row;
                final int c = col;
                buttons[row][col].setOnAction(e -> handleButtonClick(r, c));

                // Add button to the grid
                grid.add(buttons[row][col], col, row);
            }
        }

        // Add the grid to the VBox
        vbox.getChildren().add(grid);

        // Set up the scene and stage
        Scene scene = new Scene(vbox, 300, 350); // Adjusted height to accommodate the label
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tic-Tac-Toe");
        primaryStage.show();
    }

    private void handleButtonClick(int r, int c) {
    }

    public static void main(String[] args) {
        launch(args);
    }
}