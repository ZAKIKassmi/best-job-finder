package com.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.chartpages.ChartPage1;
import com.chartpages.ChartPage2;
import com.chartpages.ChartPage3;
import com.chartpages.ChartPage4;
import com.chartpages.ChartPage5;
import com.chartpages.ChartPage6;
import com.chartpages.ChartPage;

public class MainInterface extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Créer le titre principal
        Text title = new Text("Visualisation des Données");
        title.setFont(Font.font("Arial", 24));

        // Créer les boutons avec des styles
        Button cityGraphButton = createButton("Graphique par Ville");
        Button activityGraphButton = createButton("Graphique par Secteur d'Activité");
        Button experienceGraphButton = createButton("Graphique par Expérience Requise");
        Button studyGraphButton = createButton("Graphique par Niveau d'Études");
        Button contractGraphButton = createButton("Graphique par Type de Contrat");
        Button remoteGraphButton = createButton("Graphique par Télétravail");

        // Définir les actions des boutons
        cityGraphButton.setOnAction(e -> showChartPage(primaryStage, new ChartPage1(), createMainMenu(primaryStage)));
        activityGraphButton.setOnAction(e -> showChartPage(primaryStage, new ChartPage2(), createMainMenu(primaryStage)));
        experienceGraphButton.setOnAction(e -> showChartPage(primaryStage, new ChartPage3(), createMainMenu(primaryStage)));
        studyGraphButton.setOnAction(e -> showChartPage(primaryStage, new ChartPage4(), createMainMenu(primaryStage)));
        contractGraphButton.setOnAction(e -> showChartPage(primaryStage, new ChartPage5(), createMainMenu(primaryStage)));
        remoteGraphButton.setOnAction(e -> showChartPage(primaryStage, new ChartPage6(), createMainMenu(primaryStage)));

        // Créer le menu principal
        VBox layout = new VBox(15, cityGraphButton, activityGraphButton, experienceGraphButton, studyGraphButton, contractGraphButton, remoteGraphButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        // Ajouter un style global au fond
        BorderPane root = new BorderPane();
        root.setTop(title);
        root.setCenter(layout);
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setStyle("-fx-background-color: #f0f8ff;");

        // Initialiser et afficher la scène
        Scene scene = new Scene(root, 400, 500);
        primaryStage.setTitle("Interface de Visualisation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Méthode pour créer un bouton stylisé
    private Button createButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", 16));
        button.setStyle("-fx-background-color: #4682b4; -fx-text-fill: white; -fx-border-radius: 10; -fx-background-radius: 10;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #5a9bd3; -fx-text-fill: white; -fx-border-radius: 10; -fx-background-radius: 10;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #4682b4; -fx-text-fill: white; -fx-border-radius: 10; -fx-background-radius: 10;"));
        button.setPrefWidth(300);
        return button;
    }

    // Créer un menu principal avec les boutons
    private VBox createMainMenu(Stage primaryStage) {
        Button cityGraphButton = createButton("Graphique par Ville");
        Button activityGraphButton = createButton("Graphique par Secteur d'Activité");
        Button experienceGraphButton = createButton("Graphique par Expérience Requise");
        Button studyGraphButton = createButton("Graphique par Niveau d'Études");
        Button contractGraphButton = createButton("Graphique par Type de Contrat");
        Button remoteGraphButton = createButton("Graphique par Télétravail");

        cityGraphButton.setOnAction(e -> showChartPage(primaryStage, new ChartPage1(), createMainMenu(primaryStage)));
        activityGraphButton.setOnAction(e -> showChartPage(primaryStage, new ChartPage2(), createMainMenu(primaryStage)));
        experienceGraphButton.setOnAction(e -> showChartPage(primaryStage, new ChartPage3(), createMainMenu(primaryStage)));
        studyGraphButton.setOnAction(e -> showChartPage(primaryStage, new ChartPage4(), createMainMenu(primaryStage)));
        contractGraphButton.setOnAction(e -> showChartPage(primaryStage, new ChartPage5(), createMainMenu(primaryStage)));
        remoteGraphButton.setOnAction(e -> showChartPage(primaryStage, new ChartPage6(), createMainMenu(primaryStage)));

        VBox menuLayout = new VBox(15, cityGraphButton, activityGraphButton, experienceGraphButton, studyGraphButton, contractGraphButton, remoteGraphButton);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setPadding(new Insets(20));

        return menuLayout;
    }

    // Afficher la page de graphique
    private void showChartPage(Stage primaryStage, Object page, VBox mainMenu) {
        VBox chartLayout = ((ChartPage) page).getChartPage(primaryStage, mainMenu);
        Scene scene = new Scene(chartLayout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
