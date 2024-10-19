package org.example.tictactoe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomePage extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(15));

        Button twoPlayerButton = new Button("Two Player");
        twoPlayerButton.setOnAction(e -> {
            new TwoPlayerMode().start(new Stage());
            primaryStage.close();
        });

        Button singlePlayerButton = new Button("Single Player");
        singlePlayerButton.setOnAction(e -> {
            new SinglePlayerMode().start(new Stage());
            primaryStage.close();
        });

        vbox.getChildren().addAll(twoPlayerButton, singlePlayerButton);

        Scene scene = new Scene(vbox, 200, 150);
        primaryStage.setTitle("Tic-Tac-Toe Home");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
