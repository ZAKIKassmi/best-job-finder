package com.parsers.rekrute;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.main.Job;
import com.parsers.Parser;

public class RekruteExperienceParser extends Parser {
  private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");
  public static String parseExperienceRange(String input) {
        // Extract all numbers from the input

        if(input == null){
          return null;
        }

        List<Integer> numbers = new ArrayList<>();
        Matcher matcher = NUMBER_PATTERN.matcher(input);
        
        while (matcher.find()) {
            numbers.add(Integer.valueOf(matcher.group()));
        }

        // If no numbers found, return fraichement diplômé
        if (numbers.isEmpty()) {
            return "fraichement diplômé";
        }

        // Calculate average
        double average = numbers.stream()
                              .mapToInt(Integer::intValue)
                              .average()
                              .orElse(0.0);
        
        // Round down
        int roundedAverage = (int) Math.floor(average);

        // Map to experience category
        if (roundedAverage < 1) {
            return "fraichement diplômé";
        } else if (roundedAverage <= 3) {
            return "débutant (de 1 à 3 ans)";
        } else if (roundedAverage <= 5) {
            return "junior (de 3 à 5 ans)";
        } else if (roundedAverage <= 10) {
            return "senior (de 5 à 10 ans)";
        } else {
            return "expert (10 ou plus)";
        }
    }

  public static void parseExperience(List<Job> jobs) {
    for (Job job : jobs) {
      job.setRequiredExperience(parseExperienceRange(job.getRequiredExperience()));
    }
  }
}
