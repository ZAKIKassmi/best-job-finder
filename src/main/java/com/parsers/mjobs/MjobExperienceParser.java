package com.parsers.mjobs;

import java.util.List;
import java.util.Map;

import com.main.Job;
import com.parsers.Parser;

public class MjobExperienceParser extends Parser{
     private static final Map<String, String> EXPERIENCE_MAPPING = Map.of(
        "fraichement diplômé", "fraichement diplômé",
        "débutant (de 1 à 3 ans)", "débutant (de 1 à 3 ans)",
        "junior (de 3 à 5 ans)", "junior (de 3 à 5 ans)",
        "senior (de 5 à 7 ans)", "senior (de 5 à 10 ans)",
        "confirmé (de 7 à 10 ans)", "senior (de 5 à 10 ans)",
        "+ 10 ans", "expert (10 ou plus)"
    );

    public static String getMinimumExperience(String experienceLine) {
        if (experienceLine == null || experienceLine.trim().isEmpty()) {
            return null;
        }
        if(!experienceLine.contains("/")){
          return EXPERIENCE_MAPPING.getOrDefault(experienceLine, experienceLine);
        }

        // Split the input string by " / "

        String[] experiences = experienceLine.split(" / ");
       
        
        return EXPERIENCE_MAPPING.getOrDefault(experiences[0], experiences[0]);
  }
    
    

    public static void parseExperience(List<Job> jobs) {
    for (Job job : jobs) {
      job.setRequiredExperience(getMinimumExperience(job.getRequiredExperience().toLowerCase()));
    }
  }
}
