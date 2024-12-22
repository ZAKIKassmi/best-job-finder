package com.parsers.rekrute;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.main.Job;
import com.parsers.Parser;

public class RekruteStudyLevelParser extends Parser {

  public static Map<Pattern, String> STUDY_LEVEL_MAPPER = Map.ofEntries(
      Map.entry(Pattern.compile("autodidacte", Pattern.CASE_INSENSITIVE), "autodidacte"),
      Map.entry(Pattern.compile("niveau bac|qualification", Pattern.CASE_INSENSITIVE), "qualification avant bac"),
      Map.entry(Pattern.compile("doctorat|doctrat", Pattern.CASE_INSENSITIVE), "doctorat"),
      Map.entry(Pattern.compile("bac\\+1|bac +1", Pattern.CASE_INSENSITIVE), "bac +1"),
      Map.entry(Pattern.compile("bac\\+2|bac +2", Pattern.CASE_INSENSITIVE), "bac +2"),
      Map.entry(Pattern.compile("bac\\+3|bac +3", Pattern.CASE_INSENSITIVE), "bac +3"),
      Map.entry(Pattern.compile("bac\\+4|bac +4", Pattern.CASE_INSENSITIVE), "bac +4"),
      Map.entry(Pattern.compile("bac\\+5|bac +5", Pattern.CASE_INSENSITIVE), "bac +5"));

  private static String mapStudyLevel(String studyLevel) {
    if (studyLevel == null) {
      return null;
    }
    for (Map.Entry<Pattern, String> entry : STUDY_LEVEL_MAPPER.entrySet()) {
      if (entry.getKey().matcher(studyLevel).find()) {
        return entry.getValue();

      }
    }
    return studyLevel;
  }

  public static void parseStudyLevel(List<Job> jobs) {
    for (Job job : jobs) {
      String studyLevel = job.getStudyLevel().toLowerCase();

      if (VALID_STUDY_LEVELS.contains(studyLevel)) {
        job.setStudyLevel(studyLevel);
      } else {
        String parsedStudyLevel = mapStudyLevel(studyLevel);
        if (parsedStudyLevel != null && VALID_STUDY_LEVELS.contains(parsedStudyLevel)) {
          job.setStudyLevel(parsedStudyLevel);
        } else {
          System.out.println(job.getStudyLevel());
          job.setStudyLevel(null);
        }
      }

    }
  }
}
