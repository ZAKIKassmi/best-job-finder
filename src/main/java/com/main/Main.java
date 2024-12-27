package com.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ui.dashboard.DashboardApp;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
    public static  List<Job> rekrute = Collections.synchronizedList(new ArrayList<>());
    public static  List<Job> emploi = Collections.synchronizedList(new ArrayList<>());
    public static  List<Job> mjobs = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void start(Stage primaryStage) {
        DashboardApp dashboard = new DashboardApp();
        dashboard.start(primaryStage);
    }
    
    public static void main(String[] args) {


        

        Application.launch(args);



    }
}
