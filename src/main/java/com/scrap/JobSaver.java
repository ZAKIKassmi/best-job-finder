package com.scrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.main.Job;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JobSaver {

    // Méthode pour sauvegarder la liste des emplois dans un fichier JSON
    public static void saveJobsToJson(List<Job> jobs, String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Format JSON lisible

        try {
            objectMapper.writeValue(new File(filePath), jobs);
            System.out.println("Les emplois ont été sauvegardés dans " + filePath);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier JSON : " + e.getMessage());
        }
    }
}
