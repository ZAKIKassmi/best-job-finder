package com.ui.dashboard;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class DashboardApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create main border pane
        BorderPane root = new BorderPane();
        
        // Create sidebar
        VBox sidebar = createSidebar();
        root.setLeft(sidebar);
        
        // Create header
        HBox header = createHeader();
        root.setTop(header);
        
        // Create main content area
        ScrollPane mainContent = createMainContent();
        root.setCenter(mainContent);
        
        // Set scene
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setTitle("Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private VBox createSidebar() {
        VBox sidebar = new VBox();
        sidebar.setPrefWidth(250);
        sidebar.setStyle("-fx-background-color: #2c3e50;");
        sidebar.setPadding(new Insets(10));
        sidebar.setSpacing(10);
        
        // Logo area
        Label logo = new Label("Dashboard");
        logo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        logo.setTextFill(Color.WHITE);
        logo.setPadding(new Insets(20));
        
        // Navigation buttons
        Button[] navButtons = {
            createNavButton("Dashboard", true),
            createNavButton("Analytics"),
            createNavButton("Reports"),
            createNavButton("Users"),
            createNavButton("Settings")
        };
        
        sidebar.getChildren().add(logo);
        sidebar.getChildren().addAll(navButtons);
        
        return sidebar;
    }
    
    private Button createNavButton(String text) {
        return createNavButton(text, false);
    }
    
    private Button createNavButton(String text, boolean isActive) {
        Button button = new Button(text);
        button.setPrefWidth(230);
        button.setPrefHeight(40);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setPadding(new Insets(10, 20, 10, 20));
        
        if (isActive) {
            button.setStyle(
                "-fx-background-color: #34495e;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-border-radius: 5;" +
                "-fx-background-radius: 5"
            );
        } else {
            button.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: #ecf0f1;" +
                "-fx-font-size: 14px;" +
                "-fx-border-radius: 5;" +
                "-fx-background-radius: 5"
            );
        }
        
        // Hover effect
        button.setOnMouseEntered(e -> {
            if (!isActive) {
                button.setStyle(
                    "-fx-background-color: #34495e;" +
                    "-fx-text-fill: white;" +
                    "-fx-font-size: 14px;" +
                    "-fx-border-radius: 5;" +
                    "-fx-background-radius: 5"
                );
            }
        });
        
        button.setOnMouseExited(e -> {
            if (!isActive) {
                button.setStyle(
                    "-fx-background-color: transparent;" +
                    "-fx-text-fill: #ecf0f1;" +
                    "-fx-font-size: 14px;" +
                    "-fx-border-radius: 5;" +
                    "-fx-background-radius: 5"
                );
            }
        });
        
        return button;
    }
    
    private HBox createHeader() {
        HBox header = new HBox();
        header.setStyle("-fx-background-color: white; -fx-border-color: #ecf0f1; -fx-border-width: 0 0 1 0;");
        header.setPadding(new Insets(15, 20, 15, 20));
        header.setAlignment(Pos.CENTER_RIGHT);
        
        // Add header content (e.g., search bar, notifications, profile)
        Button profileBtn = new Button("John Doe");
        profileBtn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #2c3e50;" +
            "-fx-font-size: 14px"
        );
        
        header.getChildren().add(profileBtn);
        
        return header;
    }
    
    private ScrollPane createMainContent() {
        ScrollPane scrollPane = new ScrollPane();
        VBox content = new VBox();
        content.setSpacing(20);
        content.setPadding(new Insets(20));
        
        // Add dashboard cards
        GridPane dashboardGrid = new GridPane();
        dashboardGrid.setHgap(20);
        dashboardGrid.setVgap(20);
        
        // Create dashboard cards
        dashboardGrid.add(createCard("Total Users", "1,234"), 0, 0);
        dashboardGrid.add(createCard("Revenue", "$45,678"), 1, 0);
        dashboardGrid.add(createCard("Active Sessions", "456"), 2, 0);
        dashboardGrid.add(createCard("Conversion Rate", "12.3%"), 3, 0);
        
        // Add chart placeholders
        VBox chart1 = createChartPlaceholder("Weekly Revenue");
        VBox chart2 = createChartPlaceholder("User Activity");
        
        content.getChildren().addAll(dashboardGrid, chart1, chart2);
        scrollPane.setContent(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #f8f9fa;");
        
        return scrollPane;
    }
    
    private VBox createCard(String title, String value) {
        VBox card = new VBox();
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 3);"
        );
        card.setPadding(new Insets(20));
        card.setPrefWidth(250);
        
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 14px;");
        
        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-text-fill: #2c3e50; -fx-font-size: 24px; -fx-font-weight: bold;");
        valueLabel.setPadding(new Insets(10, 0, 0, 0));
        
        card.getChildren().addAll(titleLabel, valueLabel);
        
        return card;
    }
    
    private VBox createChartPlaceholder(String title) {
        VBox container = new VBox();
        container.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 3);"
        );
        container.setPadding(new Insets(20));
        container.setSpacing(15);
        
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: #2c3e50; -fx-font-size: 18px; -fx-font-weight: bold;");
        
        Rectangle chartPlaceholder = new Rectangle(0, 0, 1100, 300);
        chartPlaceholder.setFill(Color.web("#f8f9fa"));
        
        container.getChildren().addAll(titleLabel, chartPlaceholder);
        
        return container;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
