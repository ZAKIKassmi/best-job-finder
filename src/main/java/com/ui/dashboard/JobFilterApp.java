package com.ui.dashboard;

import java.util.ArrayList;
import java.util.HashMap;

import com.ai.HashMapData;
import com.ai.Prediction;
import com.db.DatabaseServices;
import com.main.TestJob;

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
import javafx.util.StringConverter;

public class JobFilterApp extends VBox {

    private static final HashMap<String, Integer> targetIndices = new HashMap<>() {{
        put("Activity Sector", 0);
        put("Required Experience", 1);
        put("Study Level", 2);
        put("Contract Type", 3);
        put("Remote Work", 4);
        put("City", 5);
    }};

    // Fields for storing the selected keys
    private String activitySectorKey;
    private String requiredExperienceKey;
    private String studyLevelKey;
    private String contractTypeKey;
    private String remoteWorkKey;
    private String cityKey;
    
    // HashMaps from your second file
    private static final HashMap<String, String> sectorMap = HashMapData.sectorMap;
    private static final HashMap<String, String> experienceMap = HashMapData.experienceMap;
    private static final HashMap<String, String> studyMap = HashMapData.studyMap;
    private static final HashMap<String, String> contractMap = HashMapData.contractMap;
    private static final HashMap<String, String> remoteMap = HashMapData.remoteMap;
    private static final HashMap<String, String> cityMap = HashMapData.cityMap;

    // UI Components using HashMap.Entry to store both key and value
    private ComboBox<HashMap.Entry<String, String>> sectorCombo;
    private ComboBox<HashMap.Entry<String, String>> experienceCombo;
    private ComboBox<HashMap.Entry<String, String>> studyCombo;
    private ComboBox<HashMap.Entry<String, String>> contractCombo;
    private ComboBox<HashMap.Entry<String, String>> remoteCombo;
    private ComboBox<HashMap.Entry<String, String>> cityCombo;
    private ComboBox<String> targetFieldCombo;
    private TextArea resultArea;
    private Button submitButton;

    public JobFilterApp() {
        // Basic setup remains the same
        setSpacing(25);
        setPadding(new Insets(30));
        setStyle("-fx-background-color: #f8f9fa;");

        Label titleLabel = new Label("Job Search Filters");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        titleLabel.setStyle("-fx-text-fill: #2c3e50;");

        VBox contentBox = new VBox(20);
        contentBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; " +
                          "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 3);");
        contentBox.setPadding(new Insets(25));

        GridPane filterGrid = createFilterGrid();

        // Initialize ComboBoxes with HashMap entries
        String comboStyle = "-fx-background-color: white; -fx-border-color: #e0e0e0; " +
                          "-fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 5; " +
                          "-fx-pref-height: 35;";

        sectorCombo = createMapComboBox("Select Activity Sector", comboStyle, sectorMap);
        experienceCombo = createMapComboBox("Select Required Experience", comboStyle, experienceMap);
        studyCombo = createMapComboBox("Select Study Level", comboStyle, studyMap);
        contractCombo = createMapComboBox("Select Contract Type", comboStyle, contractMap);
        remoteCombo = createMapComboBox("Select Remote Work Option", comboStyle, remoteMap);
        cityCombo = createMapComboBox("Select City", comboStyle, cityMap);

        // Setup change listeners to store selected keys
        sectorCombo.setOnAction(e -> activitySectorKey = getSelectedKey(sectorCombo));
        experienceCombo.setOnAction(e -> requiredExperienceKey = getSelectedKey(experienceCombo));
        studyCombo.setOnAction(e -> studyLevelKey = getSelectedKey(studyCombo));
        contractCombo.setOnAction(e -> contractTypeKey = getSelectedKey(contractCombo));
        remoteCombo.setOnAction(e -> remoteWorkKey = getSelectedKey(remoteCombo));
        cityCombo.setOnAction(e -> cityKey = getSelectedKey(cityCombo));

        // Create target field selector
        targetFieldCombo = new ComboBox<>(FXCollections.observableArrayList(
            "Activity Sector", "Required Experience", "Study Level",
            "Contract Type", "Remote Work", "City"
        ));
        targetFieldCombo.setStyle("-fx-background-color: white; -fx-border-color: #3498db; " +
                                "-fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 5; " +
                                "-fx-pref-height: 35; -fx-border-width: 2;");
        targetFieldCombo.setPromptText("Select Target Field");

        // Add components to grid
        addToGrid(filterGrid, "Activity Sector", sectorCombo, 0, 0);
        addToGrid(filterGrid, "Required Experience", experienceCombo, 0, 1);
        addToGrid(filterGrid, "Study Level", studyCombo, 0, 2);
        addToGrid(filterGrid, "Contract Type", contractCombo, 1, 0);
        addToGrid(filterGrid, "Remote Work", remoteCombo, 1, 1);
        addToGrid(filterGrid, "City", cityCombo, 1, 2);
        addToGrid(filterGrid, "Target Field", targetFieldCombo, 1, 3);

        // Create action and results sections
        VBox actionSection = createActionSection();
        VBox resultsSection = createResultsSection();

        // Assemble the UI
        contentBox.getChildren().addAll(filterGrid, actionSection, resultsSection);
        getChildren().addAll(titleLabel, contentBox);
    }

    private ComboBox<HashMap.Entry<String, String>> createMapComboBox(
            String prompt, String style, HashMap<String, String> map) {
        ComboBox<HashMap.Entry<String, String>> combo = new ComboBox<>(
            FXCollections.observableArrayList(map.entrySet())
        );
        combo.setPromptText(prompt);
        combo.setStyle(style);
        combo.setMaxWidth(Double.MAX_VALUE);
        
        // Set up custom string converter to display only the values
        combo.setConverter(new StringConverter<>() {
            @Override
            public String toString(HashMap.Entry<String, String> entry) {
                return entry == null ? "" : entry.getValue();
            }

            @Override
            public HashMap.Entry<String, String> fromString(String string) {
                return null; // Not needed for this implementation
            }
        });
        
        return combo;
    }

    private String getSelectedKey(ComboBox<HashMap.Entry<String, String>> combo) {
        HashMap.Entry<String, String> selected = combo.getValue();
        return selected != null ? selected.getKey() : null;
    }

    private String getSelectedValue(ComboBox<HashMap.Entry<String, String>> combo) {
        HashMap.Entry<String, String> selected = combo.getValue();
        return selected != null ? selected.getValue() : null;
    }

    private String getPrediction(int targetIndex) {
        ArrayList<TestJob> jobs = DatabaseServices.getAllJobs();
        if(jobs == null){
            resultArea.setText("No job opportunities were found at this time. Please double check the database");
            return null;
        }

        Prediction predictor = new Prediction(targetIndex);
        for (TestJob job : jobs) {
            if (job.getCity() == null || job.getCity().isEmpty() ||
                job.getActivitySector() == null || job.getActivitySector().isEmpty() || 
                job.getRequiredExperience() == null || job.getRequiredExperience().isEmpty() || 
                job.getStudyLevel() == null || job.getStudyLevel().isEmpty() || 
                job.getContractType() == null || job.getContractType().isEmpty() || 
                job.getRemoteWork() == null || job.getRemoteWork().isEmpty()) {
                continue;
            }
            predictor.addTrainingData(job);
        }

        try {
            predictor.trainModel();
            // We need to exclude the target field from the inputs
            return switch (targetIndex) {
                case 0 -> predictor.predictActivitySector(
                        experienceKey(), studyLevelKey(), 
                        contractTypeKey(), remoteWorkKey(), cityKey()
                );
                case 1 -> predictor.predictExperience(
                        activitySectorKey(), studyLevelKey(),
                        contractTypeKey(), remoteWorkKey(), cityKey()
                );
                case 2 -> predictor.predictStudyLevel(
                        activitySectorKey(), experienceKey(),
                        contractTypeKey(), remoteWorkKey(), cityKey()
                );
                case 3 -> predictor.predictContractType(
                        activitySectorKey(), experienceKey(),
                        studyLevelKey(), remoteWorkKey(), cityKey()
                );
                case 4 -> predictor.predictRemoteWork(
                        activitySectorKey(), experienceKey(),
                        studyLevelKey(), contractTypeKey(), cityKey()
                );
                case 5 -> predictor.predictCity(
                        activitySectorKey(), experienceKey(),
                        studyLevelKey(), contractTypeKey(), remoteWorkKey()
                );
                default -> "Unknown target";
            }; 
        } catch (Exception e) {
            return "Prediction error: " + e.getMessage();
        }
    }
    private String activitySectorKey() {
        return activitySectorKey != null ? activitySectorKey : "";
    }

    private String experienceKey() {
        return requiredExperienceKey != null ? requiredExperienceKey : "";
    }

    private String studyLevelKey() {
        return studyLevelKey != null ? studyLevelKey : "";
    }

    private String contractTypeKey() {
        return contractTypeKey != null ? contractTypeKey : "";
    }

    private String remoteWorkKey() {
        return remoteWorkKey != null ? remoteWorkKey : "";
    }

    private String cityKey() {
        return cityKey != null ? cityKey : "";
    }

    private String getValueForPrediction(int targetIndex, String predictionKey) {
        if (predictionKey == null) return null;
        return switch (targetIndex) {
            case 0 -> sectorMap.get(predictionKey);
            case 1 -> experienceMap.get(predictionKey);
            case 2 -> studyMap.get(predictionKey);
            case 3 -> contractMap.get(predictionKey);
            case 4 -> remoteMap.get(predictionKey);
            case 5 -> cityMap.get(predictionKey);
            default -> null;
        };
    }

    private void updateDisplay() {
        String targetField = targetFieldCombo.getValue();
        if (targetField == null) {
            resultArea.setText("Please select a target field");
            return;
        }

        Integer targetIndex = targetIndices.get(targetField);
        if (targetIndex == null) {
            resultArea.setText("Invalid target field");
            return;
        }

        String prediction = getPrediction(targetIndex);


        
        String predictionValue = getValueForPrediction(targetIndex, prediction);
        String displayText = String.format(
            """
            Selected Target Field: %s
            
            Current Inputs:
            ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
            Activity Sector: %s (%s)
            Required Experience: %s (%s)
            Study Level: %s (%s)
            Contract Type: %s (%s)
            Remote Work: %s (%s)
            City: %s (%s)
            
            Prediction Result:
            ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
            Predicted %s: %s
            (Key: %s)
            """,
            targetField,
            getSelectedValue(sectorCombo), activitySectorKey,
            getSelectedValue(experienceCombo), requiredExperienceKey,
            getSelectedValue(studyCombo), studyLevelKey,
            getSelectedValue(contractCombo), contractTypeKey,
            getSelectedValue(remoteCombo), remoteWorkKey,
            getSelectedValue(cityCombo), cityKey,
            targetField, predictionValue, prediction
        );
        
        resultArea.setText(displayText);
    }

    private String getValueForKey(String field, String key) {
        if (key == null) return null;
        return switch (field) {
            case "Activity Sector" -> sectorMap.get(key);
            case "Required Experience" -> experienceMap.get(key);
            case "Study Level" -> studyMap.get(key);
            case "Contract Type" -> contractMap.get(key);
            case "Remote Work" -> remoteMap.get(key);
            case "City" -> cityMap.get(key);
            default -> null;
        };
    }

    // Helper methods for UI creation remain largely the same
    private GridPane createFilterGrid() {
        GridPane filterGrid = new GridPane();
        filterGrid.setHgap(20);
        filterGrid.setVgap(20);
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col1.setPercentWidth(50);
        col2.setPercentWidth(50);
        filterGrid.getColumnConstraints().addAll(col1, col2);
        return filterGrid;
    }

    private void addToGrid(GridPane grid, String labelText, ComboBox<?> combo, int col, int row) {
        VBox container = new VBox(5);
        Label label = new Label(labelText);
        label.setStyle("-fx-font-size: 13px; -fx-text-fill: #566573;");
        container.getChildren().addAll(label, combo);
        grid.add(container, col, row);
    }

    private VBox createActionSection() {
        VBox actionSection = new VBox(15);
        actionSection.setAlignment(Pos.CENTER);
        actionSection.setPadding(new Insets(20, 0, 0, 0));

        submitButton = new Button("Apply Filters");
        submitButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; " +
                            "-fx-font-weight: bold; -fx-padding: 10 30; " +
                            "-fx-background-radius: 5; -fx-cursor: hand;");
        submitButton.setOnAction(e -> updateDisplay());

        actionSection.getChildren().add(submitButton);
        return actionSection;
    }

    private VBox createResultsSection() {
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

        resultsSection.getChildren().addAll(resultsLabel, resultArea);
        return resultsSection;
    }
}