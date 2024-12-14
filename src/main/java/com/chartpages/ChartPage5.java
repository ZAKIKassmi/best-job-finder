package com.chartpages;
import com.db.DatabaseServices; 

import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;

public class ChartPage5 implements ChartPage {

    @Override
    public VBox getChartPage(Stage primaryStage, VBox mainMenu) {
        // Récupérer les données pour le graphique (nombre d'offres par type de contrat)
        Map<String, Integer> data = DatabaseServices.getJobsByContractType();

        // Créer un graphique circulaire (PieChart)
        PieChart pieChart = new PieChart();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            pieChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        // Créer un layout avec le graphique
        StackPane chartLayout = new StackPane(pieChart);

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
