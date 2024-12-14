package com.ui;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class ButtonComponent extends StackPane {

    public ButtonComponent() {
        // Create a Button
        Button button = new Button("Click Me");

        // Set up the button's action
        button.setOnAction(event -> System.out.println("Button clicked!"));

        // Add the button to the layout
        this.getChildren().add(button);
    }
}
