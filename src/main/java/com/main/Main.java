package com.main;

import com.scrap.RekruteScrapper;
import com.data_processing.*;
import com.scrap.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            // Lancer le scraping
            List<Job> jobs = RekruteScrapper.startScrapping();

            // Nettoyer et formaliser les données
            List<Job> cleanedJobs = JobCleaner.cleanJobs(jobs);

            // Sauvegarder les données nettoyées dans un fichier JSON
            String filePath = "cleaned_jobs.json";
            JobSaver.saveJobsToJson(cleanedJobs, filePath);
        } catch (InterruptedException e) {
            System.err.println("Erreur lors du scraping : " + e.getMessage());
        }
    }
}
