package com.ui.dashboard;

import com.db.DatabaseServices;
import com.main.Main;
import com.parsers.emploi.EmploiParsers;
import com.parsers.mjobs.MjobParsers;
import com.parsers.rekrute.RekruteParsers;
import com.scrap.EmploiScrapper;
import com.scrap.MJob;
import com.scrap.RekruteScrapper;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class MainPage extends ScrollPane {
    private TextArea statusTextArea;
    
    public MainPage() {
        VBox content = new VBox();
        content.setSpacing(20);
        content.setPadding(new Insets(20));
        
        // Scraping Section
        VBox scrapingSection = createSection("Scraping Controls");
        GridPane scrapingButtons = new GridPane();
        scrapingButtons.setHgap(10);
        scrapingButtons.setVgap(10);
        
        Button rekruteScrapBtn = createActionButton("Scrape Rekrute", "#27ae60");
        Button emploiScrapBtn = createActionButton("Scrape Emploi", "#27ae60");
        Button mjobsScrapBtn = createActionButton("Scrape M-Jobs", "#27ae60");
        Button allScrapBtn = createActionButton("Scrape All", "#2980b9");
        
        scrapingButtons.add(rekruteScrapBtn, 0, 0);
        scrapingButtons.add(emploiScrapBtn, 1, 0);
        scrapingButtons.add(mjobsScrapBtn, 2, 0);
        scrapingButtons.add(allScrapBtn, 3, 0);
        
        scrapingSection.getChildren().add(scrapingButtons);
        
        // Parsing Section
        VBox parsingSection = createSection("Parsing Controls");
        GridPane parsingButtons = new GridPane();
        parsingButtons.setHgap(10);
        parsingButtons.setVgap(10);
        
        Button rekruteParseBtn = createActionButton("Parse Rekrute", "#e67e22");
        Button emploiParseBtn = createActionButton("Parse Emploi", "#e67e22");
        Button mjobsParseBtn = createActionButton("Parse M-Jobs", "#e67e22");
        Button allParseBtn = createActionButton("Parse All", "#d35400");
        
        parsingButtons.add(rekruteParseBtn, 0, 0);
        parsingButtons.add(emploiParseBtn, 1, 0);
        parsingButtons.add(mjobsParseBtn, 2, 0);
        parsingButtons.add(allParseBtn, 3, 0);
        
        parsingSection.getChildren().add(parsingButtons);
        
        // Database Section
        VBox dbSection = createSection("Database Controls");
        GridPane dbButtons = new GridPane();
        dbButtons.setHgap(10);
        dbButtons.setVgap(10);
        
        Button createSchemaBtn = createActionButton("Create Schema", "#8e44ad");
        Button insertDataBtn = createActionButton("Insert All Data", "#8e44ad");
        Button closeDbBtn = createActionButton("Close Database", "#c0392b");
        
        dbButtons.add(createSchemaBtn, 0, 0);
        dbButtons.add(insertDataBtn, 1, 0);
        dbButtons.add(closeDbBtn, 2, 0);
        
        dbSection.getChildren().add(dbButtons);
        
        // Status Section
        VBox statusSection = createSection("Status Updates");
        
        statusTextArea = new TextArea();
        statusTextArea.setEditable(false);
        statusTextArea.setPrefRowCount(10);
        statusTextArea.setStyle(
            "-fx-control-inner-background: #f8f9fa;" +
            "-fx-font-family: 'Courier New';" +
            "-fx-font-size: 14px;"
        );
        
        statusSection.getChildren().add(statusTextArea);
        
        // Add all sections to content
        content.getChildren().addAll(scrapingSection, parsingSection, dbSection, statusSection);
        
        // Button Event Handlers
        

            setupButtonHandlers(rekruteScrapBtn, emploiScrapBtn, mjobsScrapBtn, allScrapBtn,
            rekruteParseBtn, emploiParseBtn, mjobsParseBtn, allParseBtn,
            createSchemaBtn, insertDataBtn, closeDbBtn);
        
        
        setContent(content);
        setFitToWidth(true);
        setStyle("-fx-background-color: #f8f9fa;");
    }
    
    private void setupButtonHandlers(Button... buttons) {
        buttons[0].setOnAction(e -> {
            addStatus("Scraping Rekrute...");
            try {
                RekruteScrapper.startScrapping(Main.rekrute);
            } catch (InterruptedException err) {
                addStatus(err.getMessage());
            }
            addStatus("Scraping Rekrute is completed...");

    
        });
        buttons[1].setOnAction(e -> {
            addStatus("Scraping emploi...");
            try {
                EmploiScrapper.startScrapping(Main.emploi);
            } catch (InterruptedException err) {
                addStatus(err.getMessage());
            }
            addStatus("Scraping Emploi is completed...");
        });
        buttons[2].setOnAction(e -> {
            addStatus("Scraping M-Jobs...");
            try {
                MJob.startScrapping(Main.mjobs);
            } catch (InterruptedException err) {
                addStatus(err.getMessage());
            }
            addStatus("Scraping M-Jobs is completed...");
        });
        buttons[3].setOnAction(e -> {
            addStatus("Starting scraping all websites...");
            try {
                addStatus("Scraping Rekrute...");
                RekruteScrapper.startScrapping(Main.rekrute);
                addStatus("Scraping Rekrute is completed...");

                addStatus("Scraping Emploi...");
                EmploiScrapper.startScrapping(Main.emploi);
                addStatus("Scraping Emploi is completed...");
                
                addStatus("Scraping Mjobs...");
                MJob.startScrapping(Main.mjobs);
                addStatus("Scraping M-Jobs is completed...");

            } catch (InterruptedException err) {
                addStatus(err.getMessage());
            }
            addStatus("Scraping all websites is completed...");
        });
        buttons[4].setOnAction(e -> {
            addStatus("Parsing Rekrute data...");
            RekruteParsers.parseAll(Main.rekrute);
            addStatus("Parsing Rekrute data is completed...");
        });
        buttons[5].setOnAction(e -> {
            addStatus("Parsing Emploi data...");
            EmploiParsers.parseAll(Main.emploi);
            addStatus("Parsing Emploi data is completed...");
        });
        buttons[6].setOnAction(e -> {
            addStatus("Parsing M-Jobs data...");
            MjobParsers.parseAll(Main.mjobs);
            addStatus("Parsing M-Jobs data is completed...");
        });
        buttons[7].setOnAction(e -> {
            addStatus("Parsing all data...");
            addStatus("Parsing Rekrute data...");
            RekruteParsers.parseAll(Main.rekrute);
            addStatus("Parsing Rekrute data is completed...");
            addStatus("Parsing Emploi data...");
            EmploiParsers.parseAll(Main.emploi);
            addStatus("Parsing Emploi data is completed...");
            addStatus("Parsing M-Jobs data...");
            MjobParsers.parseAll(Main.mjobs);
            addStatus("Parsing M-Jobs data is completed...");
        });
        buttons[8].setOnAction(e -> {
            addStatus("Creating database schema...");
            DatabaseServices.createDatabaseSchema();
            addStatus("Creating database schema is completed...");

        });
        buttons[9].setOnAction(e -> {
            addStatus("Inerting data...");
            DatabaseServices.insertJobsList(Main.rekrute);
            DatabaseServices.insertJobsList(Main.emploi);
            DatabaseServices.insertJobsList(Main.mjobs);
            addStatus("Inerting data is completed...");
        });
        buttons[10].setOnAction(e -> addStatus("Closing database connection..."));
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
    
    public void addStatus(String message) {
        Platform.runLater(() -> {
            String timestamp = java.time.LocalTime.now().toString().substring(0, 8);
            statusTextArea.appendText("[" + timestamp + "] " + message + "\n");
            statusTextArea.setScrollTop(Double.MAX_VALUE);
        });
    }
}