package org.example.tictactoe;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomePage extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox vbox = new VBox(30); // Increased spacing for better layout
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #1A1A1A;"); // Dark background

        // Title styling
        Text title = new Text("Welcome to Tic-Tac-Toe!");
        title.setFont(Font.font("Futura", 40));
        title.setFill(Color.LIGHTGRAY); // Light gray title

        // Button styling for Single Player
        Button singlePlayerButton = new Button("Single Player");
        singlePlayerButton.setPrefSize(250, 70);
        singlePlayerButton.setStyle("-fx-font-size: 22; -fx-background-color: #333333; "
                + "-fx-text-fill: white; -fx-background-radius: 10; -fx-border-color: #AAAAAA; "
                + "-fx-border-width: 2; -fx-border-radius: 10;");
        singlePlayerButton.setOnAction(e -> {
            SinglePlayerMode singlePlayer = new SinglePlayerMode();
            try {
                singlePlayer.start(new Stage());
                primaryStage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Button styling for Two Player
        Button twoPlayerButton = new Button("Two Player");
        twoPlayerButton.setPrefSize(250, 70);
        twoPlayerButton.setStyle("-fx-font-size: 22; -fx-background-color: #333333; "
                + "-fx-text-fill: white; -fx-background-radius: 10; -fx-border-color: #AAAAAA; "
                + "-fx-border-width: 2; -fx-border-radius: 10;");
        twoPlayerButton.setOnAction(e -> {
            TwoPlayerMode twoPlayer = new TwoPlayerMode();
            try {
                twoPlayer.start(new Stage());
                primaryStage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        vbox.getChildren().addAll(title, singlePlayerButton, twoPlayerButton);

        Scene scene = new Scene(vbox, 500, 600); // Set consistent scene size
        primaryStage.setTitle("Tic-Tac-Toe Home");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
