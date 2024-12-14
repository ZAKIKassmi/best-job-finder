package com.ui;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import com.chartpages.ChartPage;
import com.chartpages.ChartPage1;
import com.chartpages.ChartPage2;
import com.chartpages.ChartPage3;
import com.chartpages.ChartPage4;
import com.chartpages.ChartPage5;
import com.chartpages.ChartPage6;

public class MainInterface extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Fixer les dimensions de la fenêtre principale
        primaryStage.setWidth(600);
        primaryStage.setHeight(500);
        primaryStage.setResizable(false);

        // Créer le conteneur racine initial
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 600, 500);
        scene.setFill(Color.LIGHTSKYBLUE); // Arrière-plan bleu ciel

        // Ajouter le titre principal avec couleur noire et texte gras
        Text title = new Text("Visualisation des Données");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24)); // Police en gras
        title.setFill(Color.BLACK);  // Couleur du texte en noir
        root.setTop(title);

        // Centrer le titre
        BorderPane.setAlignment(title, Pos.CENTER);

        // Afficher le menu principal
        root.setCenter(createMainMenu(primaryStage, root, title));

        // Configurer et afficher la fenêtre
        primaryStage.setTitle("Interface de Visualisation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Crée le menu principal
    private StackPane createMainMenu(Stage primaryStage, BorderPane root, Text title) {
        Button cityGraphButton = createButton("Graphique par Ville");
        Button activityGraphButton = createButton("Graphique par Secteur d'Activité");
        Button experienceGraphButton = createButton("Graphique d'Expérience");
        Button studyGraphButton = createButton("Graphique par Niveau d'Études");
        Button contractGraphButton = createButton("Graphique par Type de Contrat");
        Button remoteGraphButton = createButton("Graphique par Télétravail");

        // Définir les actions des boutons
        cityGraphButton.setOnAction(e -> showChartPage(primaryStage, "Graphique par Ville", root, title));
        activityGraphButton.setOnAction(e -> showChartPage(primaryStage, "Graphique par Secteur d'Activité", root, title));
        experienceGraphButton.setOnAction(e -> showChartPage(primaryStage, "Graphique par Expérience Requise", root, title));
        studyGraphButton.setOnAction(e -> showChartPage(primaryStage, "Graphique par Niveau d'Études", root, title));
        contractGraphButton.setOnAction(e -> showChartPage(primaryStage, "Graphique par Type de Contrat", root, title));
        remoteGraphButton.setOnAction(e -> showChartPage(primaryStage, "Graphique par Télétravail", root, title));

        VBox menuLayout = new VBox(15, cityGraphButton, activityGraphButton, experienceGraphButton, studyGraphButton, contractGraphButton, remoteGraphButton);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setPadding(new Insets(20));

        // Centrer le menu avec un conteneur StackPane
        StackPane centeredPane = new StackPane(menuLayout);
        StackPane.setAlignment(menuLayout, Pos.CENTER);

        return centeredPane;
    }

    private void showChartPage(Stage primaryStage, String chartTitle, BorderPane root, Text title) {
        // Créer une mise en page pour la page graphique avec texte en noir et gras
        Text chartText = new Text(chartTitle);
        chartText.setFont(Font.font("Arial", FontWeight.BOLD, 18)); // Police en gras
        chartText.setFill(Color.BLACK); // Couleur du texte en noir

        // Créer le graphique en fonction du titre
        StackPane chartPage = new StackPane();
        
        // Instancier les bonnes classes de ChartPage
        ChartPage chartPageInstance = null;
        
        switch (chartTitle) {
            case "Graphique par Ville":
                chartPageInstance = new ChartPage1();  // Utiliser ChartPage1 ici
                break;
            case "Graphique par Secteur d'Activité":
                chartPageInstance = new ChartPage2();
                break;
            case "Graphique par Expérience Requise":
                chartPageInstance = new ChartPage3();
                break;
            case "Graphique par Niveau d'Études":
                chartPageInstance = new ChartPage4();
                break;
            case "Graphique par Type de Contrat":
                chartPageInstance = new ChartPage5();
                break;
            case "Graphique par Télétravail":
                chartPageInstance = new ChartPage6();
                break;
            default:
                chartPageInstance = null;
                break;
        }

        // Assurez-vous que chartPageInstance n'est pas null avant d'appeler getChartPage
        if (chartPageInstance != null) {
            // Passer StackPane au lieu de VBox
            VBox chartLayout = chartPageInstance.getChartPage(primaryStage, createMainMenu(primaryStage, root, title));
            chartPage.getChildren().add(chartLayout); // Ajouter le VBox au StackPane
        } else {
            chartPage.getChildren().add(new Text("Graphique non disponible"));
        }

        Button backButton = createButton("Retour au menu principal");
        backButton.setOnAction(e -> {
            root.setTop(title);  // Ajouter de nouveau le titre
            root.setCenter(createMainMenu(primaryStage, root, title)); // Retourner au menu principal
        });

        VBox chartLayout = new VBox(20, chartText, chartPage, backButton);
        chartLayout.setAlignment(Pos.CENTER);
        chartLayout.setPadding(new Insets(20));

        root.setTop(null); // Supprimer le titre principal avant d'afficher la nouvelle page
        root.setCenter(chartLayout); // Ajouter la nouvelle page
    }

    // Crée un bouton stylisé avec animation au survol
    private Button createButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(250);
        button.setPrefHeight(40);
        button.setStyle("-fx-background-color: #3457D5; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-family: Arial; -fx-font-weight: bold;");

        // Animation de survol
        button.setOnMouseEntered(e -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), button);
            scaleTransition.setToX(1.1);
            scaleTransition.setToY(1.1);
            scaleTransition.play();
        });

        button.setOnMouseExited(e -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), button);
            scaleTransition.setToX(1);
            scaleTransition.setToY(1);
            scaleTransition.play();
        });

        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
