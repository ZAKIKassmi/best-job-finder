package com.chartpages;

import com.db.DatabaseServices;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;

public class ChartPage4 implements ChartPage {

    @Override
    public VBox getChartPage(Stage primaryStage, VBox mainMenu) {
        // Récupérer les données pour le graphique (nombre d'offres par niveau d'études)
        Map<String, Integer> data = DatabaseServices.getJobsByStudyLevel();

        // Créer un BarChart
        BarChart<String, Number> barChart = new BarChart<>(new javafx.scene.chart.CategoryAxis(), new javafx.scene.chart.NumberAxis());
        barChart.setTitle("Nombre d'offres par Niveau d'Études");

        // Créer une série de données pour le graphique
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Offres par Niveau d'Études");

        // Vérifier que les données ne sont pas nulles et les ajouter au graphique
        if (data != null && !data.isEmpty()) {
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                // Vérifier que les valeurs ne sont pas nulles
                if (entry.getKey() != null && entry.getValue() != null) {
                    series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
                } else {
                    System.out.println("Donnée invalide : " + entry);
                }
            }

            // Ajouter la série de données au BarChart
            barChart.getData().add(series);
        } else {
            // Afficher un message d'erreur ou un graphique vide si aucune donnée n'est disponible
            System.out.println("Aucune donnée disponible pour afficher le graphique.");
        }

        // Créer un layout avec le graphique
        StackPane chartLayout = new StackPane(barChart);

        // Créer un bouton "Retour" pour revenir au menu principal
        Button backButton = new Button("Retour");
        backButton.setOnAction(e -> {
            // Revenir à la scène du menu principal
            Scene menuScene = new Scene(mainMenu, 300, 250);
            primaryStage.setScene(menuScene);
            primaryStage.show();
        });

        // Créer un VBox pour contenir le graphique et le bouton retour
        VBox vbox = new VBox(10, chartLayout, backButton);
        return vbox;
    }
}
