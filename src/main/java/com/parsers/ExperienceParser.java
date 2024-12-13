package com.parsers;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import com.main.Job;

public class ExperienceParser {

    private static final Set<String> REQUIRED_EXPERIENCE_SET = Set.of(
            "fraichement diplômé",
            "débutant (de 1 à 3 ans)",
            "junior (de 3 à 5 ans)",
            "senior (de 5 à 10 ans)",
            "expert (10 ou plus)"

    );

    private static final Map<Pattern, String> normalizationMap = Map.ofEntries(
            Map.entry(Pattern.compile("bac|moins de 1 an|débutant|etudiant, jeune diplômé|fraichement diplômé",
                    Pattern.CASE_INSENSITIVE), "fraichement diplômé"),
            Map.entry(
                    Pattern.compile("de 1 à 3 ans|débutant < 2 ans|débutant (de 1 à 3 ans)", Pattern.CASE_INSENSITIVE),
                    "débutant (de 1 à 3 ans)"),
            Map.entry(Pattern.compile("de 3 à 5 ans|expérience entre 2 ans et 5 ans|junior (de 3 à 5 ans)",
                    Pattern.CASE_INSENSITIVE), "junior (de 3 à 5 ans)"),
            Map.entry(Pattern.compile("senior (de 5 à 7 ans)|de 5 à 10 ans|expérience entre 5 ans et 10 ans|senior",
                    Pattern.CASE_INSENSITIVE), "senior (de 5 à 10 ans)"),
            Map.entry(
                    Pattern.compile("de 10 à 20 ans|plus de 20 ans|expérience > 10 ans|confirmé (de 7 à 10 ans)",
                            Pattern.CASE_INSENSITIVE),
                    "expert (10 ou plus)"));

    public static void parseExperience(List<Job> jobs) {
        for (Job job : jobs) {
            String experience = job.getRequiredExperience().trim();
            if(REQUIRED_EXPERIENCE_SET.contains(experience)){
                job.setRequiredExperience(experience.toLowerCase());
            }
            else{
                String parsedExperience = null;

                // Check which pattern matches the experience
                for (Map.Entry<Pattern, String> entry : normalizationMap.entrySet()) {
                    if (entry.getKey().matcher(experience).find()) {
                        parsedExperience = entry.getValue();
                        break;
                    }
                }

                
                if (parsedExperience != null && REQUIRED_EXPERIENCE_SET.contains(parsedExperience)) {
                    job.setRequiredExperience(parsedExperience);
                } else {
                    // System.out.println("Job: " + job.getRequiredExperience() + " normalized: " +
                    // parsedExperience);
                    job.setRequiredExperience(null);
                }

                
            }

        }
    }
}
