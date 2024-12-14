package com.chartpages;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public interface ChartPage {
    VBox getChartPage(Stage primaryStage, VBox mainMenu);
}
