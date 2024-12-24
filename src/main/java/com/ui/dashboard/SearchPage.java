package com.ui.dashboard;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SearchPage extends ScrollPane {
    
    public SearchPage() {
        VBox content = new VBox();
        content.setPadding(new Insets(20));
        content.setSpacing(20);
        content.setStyle("-fx-background-color: #f8f9fa;");
        
        // Search controls
        Label title = new Label("Search Jobs");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        HBox searchBox = new HBox(10);
        searchBox.setPadding(new Insets(20, 0, 20, 0));
        
        TextField searchField = new TextField();
        searchField.setPromptText("Search jobs...");
        searchField.setPrefWidth(300);
        
        Button searchButton = createActionButton("Search", "#2980b9");
        
        searchBox.getChildren().addAll(searchField, searchButton);
        
        // Filters
        VBox filtersSection = createSection("Filters");
        GridPane filters = new GridPane();
        filters.setHgap(10);
        filters.setVgap(10);
        
        ComboBox<String> websiteFilter = new ComboBox<>();
        websiteFilter.getItems().addAll("All Websites", "Rekrute", "Emploi", "M-Jobs");
        websiteFilter.setValue("All Websites");
        
        ComboBox<String> dateFilter = new ComboBox<>();
        dateFilter.getItems().addAll("All Time", "Today", "Last Week", "Last Month");
        dateFilter.setValue("All Time");
        
        filters.add(new Label("Website:"), 0, 0);
        filters.add(websiteFilter, 1, 0);
        filters.add(new Label("Date:"), 0, 1);
        filters.add(dateFilter, 1, 1);
        
        filtersSection.getChildren().add(filters);
        
        // Results
        TableView<Object> resultsTable = new TableView<>();
        resultsTable.setPrefHeight(500);
        
        TableColumn<Object, String> titleCol = new TableColumn<>("Title");
        TableColumn<Object, String> websiteCol = new TableColumn<>("Website");
        TableColumn<Object, String> dateCol = new TableColumn<>("Date");
        
        resultsTable.getColumns().addAll(titleCol, websiteCol, dateCol);
        
        content.getChildren().addAll(title, searchBox, filtersSection, resultsTable);
        
        setContent(content);
        setFitToWidth(true);
        setStyle("-fx-background-color: #f8f9fa;");
    }
    
    private VBox createSection(String title) {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(15));
        section.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 3);"
        );
        
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        section.getChildren().add(titleLabel);
        return section;
    }
    
    private Button createActionButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle(
            "-fx-background-color: " + color + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-padding: 10 20;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5"
        );
        return button;
    }
}