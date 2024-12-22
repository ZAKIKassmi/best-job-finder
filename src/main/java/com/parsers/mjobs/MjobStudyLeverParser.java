package com.parsers.mjobs;

import java.util.List;
import java.util.Map;

import com.main.Job;
import com.parsers.Parser;

public class MjobStudyLeverParser extends Parser{

  private static final Map<String, String> STUDY_LEVEL_MAPPING = Map.of(
        "bac", "bac",
        "niv bac et moins", "bac",
        "bac+1", "bac +1",
        "bac+2", "bac +2",
        "bac+3", "bac +3",
        "bac+4", "bac +4",
        "bac+5 et plus", "bac +5 et plus"
    );
  public static String getMinimumStudyLevel(String studyLevel) {
    if (studyLevel == null || studyLevel.trim().isEmpty()) {
      return null;
    }
    if (!studyLevel.contains("/")) {
      return STUDY_LEVEL_MAPPING.getOrDefault(studyLevel, studyLevel);
      
    }

    // Split the input string by " / "

    String[] experiences = studyLevel.split(" / ");

    return STUDY_LEVEL_MAPPING.getOrDefault(experiences[0], experiences[0]);
  }

  public static void parseStudyLevel(List<Job> jobs) {
    for (Job job : jobs) {
      job.setStudyLevel(getMinimumStudyLevel(job.getStudyLevel().toLowerCase()));
    }
  }
}
