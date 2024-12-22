package com.parsers.emploi;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.main.Job;
import com.parsers.Parser;

public class EmploiRequiredExperience extends Parser {
  private static final Map<Pattern, String> EXPERIENCE_MAPPER = Map.ofEntries(
    Map.entry(Pattern.compile("etudiant|jeune diplômé|jeune"), "fraichement diplômé"),
    Map.entry(Pattern.compile("débutant < 2 ans"),"débutant (de 1 à 3 ans)"),
    Map.entry(Pattern.compile("expérience entre 2 ans et 5 ans"),"junior (de 3 à 5 ans)"),
    Map.entry(Pattern.compile("expérience entre 5 ans et 10 ans"),"senior (de 5 à 10 ans)"),
    Map.entry(Pattern.compile("expérience > 10 ans"),"expert (10 ou plus)")
  );
  public static String getMinimumRequiredExperience(String experience) {
    if (experience == null || experience.trim().isEmpty()) {
      return null;
    }
    if (!experience.contains(" - ")) {
      return experience;
    }

    // Split the input string by " - "

    String[] experiences = experience.split(" - ");

    return experiences[0];
  }

  public static String mapExperience(String experience){
    for (Map.Entry<Pattern, String> entry : EXPERIENCE_MAPPER.entrySet()) {
      if (entry.getKey().matcher(experience).find()) {
          return entry.getValue();
      }
    }
    return experience;
  }

  public static void parseRequiredExperience(List<Job> jobs) {
    for (Job job : jobs) {
      String experience = mapExperience(getMinimumRequiredExperience(job.getRequiredExperience().toLowerCase()));

      job.setRequiredExperience(experience);
    }
  }
}
