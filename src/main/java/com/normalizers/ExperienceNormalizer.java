package com.normalizers;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.main.Job;

public class ExperienceNormalizer {

  private static final Set<String> REQUIRED_EXPERIENCE_SET= Set.of(
        "débutant",
        "junior",
        "expert",
        "senior",
        "mix"
    );

    private static final Map<String, String> normalizationMap = Map.ofEntries(
            // Experience levels
            Map.entry("débutant", "débutant"),
            Map.entry("fraichement diplômé", "débutant"),
            Map.entry("moins de 1 an", "débutant"),
            Map.entry("de 1 à 3 ans", "débutant"),
            Map.entry("débutant < 2 ans", "débutant"),
            Map.entry("débutant (de 1 à 3 ans)", "débutant"),
            Map.entry("débutant moins de 1 an", "débutant"),
            Map.entry("niveau d'expérience : débutant < 2 ans", "débutant"),
            Map.entry("niveau d'expérience : etudiant, jeune diplômé - débutant < 2 ans", "débutant"),
            Map.entry("débutant de 1 à 3 ans", "débutant"),
            Map.entry("niveau d'expérience : débutant < 2 ans et plus", "débutant"),
            Map.entry("niveau d'expérience : etudiant, jeune diplômé", "débutant"),


            // Junior and intermediate levels
            Map.entry("de 3 à 5 ans", "junior"),
            Map.entry("junior (de 3 à 5 ans)", "junior"),
            Map.entry("junior", "junior"),
            Map.entry("niveau d'expérience : expérience entre 2 ans et 5 ans", "junior"),
            Map.entry("niveau d'expérience : expérience entre 2 ans et 5 ans et plus", "junior"),
            Map.entry("de 3 à 5 ans de 3 à 5 ans", "junior"),

            // Senior levels
            Map.entry("de 5 à 10 ans", "senior"),
            Map.entry("senior", "senior"),
            Map.entry("senior (de 5 à 7 ans)", "senior"),
            Map.entry("niveau d'expérience : expérience entre 5 ans et 10 ans", "senior"),
            Map.entry("confirmé (de 7 à 10 ans)", "senior"),
            Map.entry("de 5 à 10 ans de 5 à 10 ans", "senior"),

            // Confirmed and advanced levels
            Map.entry("expérience > 10 ans", "expert"),
            Map.entry("de 10 à 20 ans", "expert"),
            Map.entry("plus de 20 ans", "expert "),
            Map.entry("+ 10 ans", "expert "),
            Map.entry("de 10 à 20 ans plus de 20 ans", "expert"),
            Map.entry("niveau d'expérience : expérience > 10 ans", "expert"),
            Map.entry("plus de 20 ans de 10 à 20 ans", "expert"),
            Map.entry("expert", "expert"),

            // Mixed levels
            Map.entry("débutant (de 1 à 3 ans) / junior (de 3 à 5 ans)", "mix"),
            Map.entry("fraichement diplômé / débutant (de 1 à 3 ans)", "mix"),
            Map.entry("fraichement diplômé / débutant (de 1 à 3 ans) / junior (de 3 à 5 ans) / senior (de 5 à 7 ans)", "mix"),
            Map.entry("fraichement diplômé / débutant (de 1 à 3 ans) / junior (de 3 à 5 ans) / senior (de 5 à 7 ans) / confirmé (de 7 à 10 ans)", "mix"),
            Map.entry("fraichement diplômé / débutant (de 1 à 3 ans) / junior (de 3 à 5 ans) / senior (de 5 à 7 ans) / confirmé (de 7 à 10 ans) / + 10 ans", "mix"),
            Map.entry("débutant (de 1 à 3 ans) / junior (de 3 à 5 ans) / senior (de 5 à 7 ans) / confirmé (de 7 à 10 ans) / + 10 ans", "mix"),
            Map.entry("niveau d'expérience : etudiant, jeune diplômé et plus", "mix"),
            Map.entry("niveau d'expérience : etudiant, jeune diplômé & débutant < 2 ans", "mix"),
            Map.entry("niveau d'expérience : débutant < 2 ans & expérience entre 2 ans et 5 ans", "mix"),
            Map.entry("niveau d'expérience : expérience entre 5 ans et 10 ans - expérience > 10 ans", "mix"),
            Map.entry("niveau d'expérience : expérience entre 2 ans et 5 ans & expérience entre 5 ans et 10 ans", "mix"),
            Map.entry("moins de 1 an de 1 à 3 ans", "mix"),
            Map.entry("de 1 à 3 ans de 5 à 10 ans", "mix"),
            Map.entry("de 3 à 5 ans de 5 à 10 ans", "mix"),
            Map.entry("de 5 à 10 ans de 10 à 20 ans", "mix"),
            Map.entry("niveau d'expérience : expérience entre 5 ans et 10 ans & expérience > 10 ans", "mix"),
            Map.entry("débutant (de 1 à 3 ans) / junior (de 3 à 5 ans) / senior (de 5 à 7 ans)", "mix"),
            Map.entry("moins de 1 an de 3 à 5 ans", "mix"),
            Map.entry("de 1 à 3 ans de 3 à 5 ans", "mix"),
            

            // TO BE REMOVED
            Map.entry("niv bac et moins", ""),
            Map.entry("bac", ""),
            Map.entry("bac+2", ""),
            Map.entry("bac+3", ""),
            Map.entry("bac+4", ""),
            Map.entry("bac+5 et plus", ""),
            Map.entry("niv bac et moins / bac / bac+1 / bac+2 / bac+3 / bac+4 / bac+5 et plus", ""),
            Map.entry("bac+3 / bac+4 / bac+5 et plus", ""),

            // Languages
            Map.entry("français", ""),
            Map.entry("anglais", ""),
            Map.entry("arabe / français / anglais", ""),
            Map.entry("français / anglais", ""),
            Map.entry("allemand", "")
            
        );


  public static void normalizeExperience(List<Job> jobs){
    Iterator<Job> jobsIterator = jobs.iterator();
        while(jobsIterator.hasNext()){
            Job job = jobsIterator.next();
            String experience = job.getRequiredExperience().toLowerCase();
            if(experience != null){
                String normalizedExperience = normalizationMap.getOrDefault(experience, experience);
                if(normalizedExperience.length() > 0 && REQUIRED_EXPERIENCE_SET.contains(normalizedExperience)){
                    job.setRequiredExperience(normalizedExperience);
                    // distinctsValues.add(normalizedExperience);
                }
                else{
                    jobsIterator.remove();
                }
            }
        }
  }
}
