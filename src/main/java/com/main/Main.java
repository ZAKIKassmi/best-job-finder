package com.main;

import com.ui.ButtonComponent;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create an instance of ButtonComponent
        ButtonComponent buttonComponent = new ButtonComponent();

        // Set up the Scene
        Scene scene = new Scene(buttonComponent, 300, 250);

        // Set the Stage
        primaryStage.setTitle("JavaFX Button Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Launch the JavaFX Application
        launch(args);
    }
}
