package com.ui.dashboard;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class JobFilterApp extends VBox {
    // Fields for storing the selected values
    private String activitySector;
    private String requiredExperience;
    private String studyLevel;
    private String contractType;
    private String remoteWork;
    private String city;
    private String function;

    // UI Components
    private ComboBox<String> sectorCombo;
    private ComboBox<String> experienceCombo;
    private ComboBox<String> studyCombo;
    private ComboBox<String> contractCombo;
    private ComboBox<String> remoteCombo;
    private ComboBox<String> cityCombo;
    private ComboBox<String> functionCombo;
    private ComboBox<String> targetFieldCombo;
    private TextArea resultArea;
    private Button submitButton;

    public JobFilterApp() {
        // Set main container properties
        setSpacing(25);
        setPadding(new Insets(30));
        setStyle("-fx-background-color: #f8f9fa;");

        // Add title
        Label titleLabel = new Label("Job Search Filters");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        titleLabel.setStyle("-fx-text-fill: #2c3e50;");

        // Create main content area
        VBox contentBox = new VBox(20);
        contentBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 3);");
        contentBox.setPadding(new Insets(25));

        // Create two-column layout
        GridPane filterGrid = new GridPane();
        filterGrid.setHgap(20);
        filterGrid.setVgap(20);
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col1.setPercentWidth(50);
        col2.setPercentWidth(50);
        filterGrid.getColumnConstraints().addAll(col1, col2);

        // Initialize ComboBoxes with improved styling
        String comboStyle = "-fx-background-color: white; -fx-border-color: #e0e0e0; " +
                          "-fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 5; " +
                          "-fx-pref-height: 35;";

        sectorCombo = createComboBox("Select Activity Sector", comboStyle,
            "Technology", "Healthcare", "Education", "Finance");
        experienceCombo = createComboBox("Select Required Experience", comboStyle,
            "Entry Level", "1-3 years", "3-5 years", "5+ years");
        studyCombo = createComboBox("Select Study Level", comboStyle,
            "High School", "Bachelor's", "Master's", "PhD");
        contractCombo = createComboBox("Select Contract Type", comboStyle,
            "Full-time", "Part-time", "Contract", "Internship");
        remoteCombo = createComboBox("Select Remote Work Option", comboStyle,
            "Remote", "Hybrid", "On-site");
        cityCombo = createComboBox("Select City", comboStyle,
            "New York", "London", "Paris", "Tokyo");
        functionCombo = createComboBox("Select Function", comboStyle,
            "Developer", "Manager", "Analyst", "Designer");

        // Create styled target field selector
        targetFieldCombo = createComboBox("Select Target Field", 
            "-fx-background-color: white; -fx-border-color: #3498db; " +
            "-fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 5; " +
            "-fx-pref-height: 35; -fx-border-width: 2;",
            "Activity Sector", "Required Experience", "Study Level",
            "Contract Type", "Remote Work", "City", "Function"
        );

        // Add components to grid in two columns
        addToGrid(filterGrid, "Activity Sector", sectorCombo, 0, 0);
        addToGrid(filterGrid, "Required Experience", experienceCombo, 0, 1);
        addToGrid(filterGrid, "Study Level", studyCombo, 0, 2);
        addToGrid(filterGrid, "Contract Type", contractCombo, 0, 3);
        addToGrid(filterGrid, "Remote Work", remoteCombo, 1, 0);
        addToGrid(filterGrid, "City", cityCombo, 1, 1);
        addToGrid(filterGrid, "Function", functionCombo, 1, 2);
        addToGrid(filterGrid, "Target Field", targetFieldCombo, 1, 3);

        // Create action section
        VBox actionSection = new VBox(15);
        actionSection.setAlignment(Pos.CENTER);
        actionSection.setPadding(new Insets(20, 0, 0, 0));

        submitButton = new Button("Apply Filters");
        submitButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; " +
                            "-fx-font-weight: bold; -fx-padding: 10 30; " +
                            "-fx-background-radius: 5; -fx-cursor: hand;");
        submitButton.setOnAction(e -> updateDisplay());

        // Create results section
        VBox resultsSection = new VBox(10);
        resultsSection.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 5; " +
                              "-fx-border-color: #e0e0e0; -fx-border-radius: 5;");
        resultsSection.setPadding(new Insets(20));

        Label resultsLabel = new Label("Filter Results");
        resultsLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        resultsLabel.setStyle("-fx-text-fill: #2c3e50;");

        resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setPrefRowCount(8);
        resultArea.setWrapText(true);
        resultArea.setStyle("-fx-border-color: transparent; -fx-background-color: transparent; " +
                          "-fx-font-size: 13px; -fx-padding: 10;");

        // Assemble the UI
        resultsSection.getChildren().addAll(resultsLabel, resultArea);
        actionSection.getChildren().add(submitButton);
        contentBox.getChildren().addAll(filterGrid, actionSection, resultsSection);
        getChildren().addAll(titleLabel, contentBox);
    }

    private ComboBox<String> createComboBox(String prompt, String style, String... items) {
        ComboBox<String> combo = new ComboBox<>(FXCollections.observableArrayList(items));
        combo.setPromptText(prompt);
        combo.setStyle(style);
        combo.setMaxWidth(Double.MAX_VALUE);
        return combo;
    }

    private void addToGrid(GridPane grid, String labelText, ComboBox<String> combo, int col, int row) {
        VBox container = new VBox(5);
        Label label = new Label(labelText);
        label.setStyle("-fx-font-size: 13px; -fx-text-fill: #566573;");
        container.getChildren().addAll(label, combo);
        grid.add(container, col, row);
    }

    private void updateDisplay() {
        String targetField = targetFieldCombo.getValue();
        if (targetField == null) {
            resultArea.setText("Please select a target field");
            return;
        }

        String selectedValue = switch (targetField) {
            case "Activity Sector" -> activitySector;
            case "Required Experience" -> requiredExperience;
            case "Study Level" -> studyLevel;
            case "Contract Type" -> contractType;
            case "Remote Work" -> remoteWork;
            case "City" -> city;
            case "Function" -> function;
            default -> null;
        };

        String displayText = String.format(
            """
            Selected %s: %s
            
            All Current Filters:
            ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
            Activity Sector: %s
            Required Experience: %s
            Study Level: %s
            Contract Type: %s
            Remote Work: %s
            City: %s
            Function: %s
            """,
            targetField,
            selectedValue,
            activitySector,
            requiredExperience,
            studyLevel,
            contractType,
            remoteWork,
            city,
            function
        );
        
        resultArea.setText(displayText);
    }
}