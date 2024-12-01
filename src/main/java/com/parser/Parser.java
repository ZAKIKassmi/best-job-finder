package com.parser;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.main.Job;

public class Parser {
  private static Set<String> validContractTypes = Set.of(
    "cdi",
    "cdd",
    "intérim",
    "autre",
    "stage",
    "freelance",
    "lettre d'engagement",
    "statutaire",
    "temps partiel"
  );
  public static String extractContractType(String input, String regex){
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(input);
    if(matcher.find()){
      return matcher.group().trim();
    }
    return null;
  }
  public static void parseContractType(List<Job> jobs){
    // String extractedValue = extractContractType(input, "^\\w+");
    for(Job job: jobs){
      String contractType = job.getContractType().toLowerCase();
      if(!validContractTypes.contains(contractType)){
          if("free lance".equals(contractType)){
              job.setContractType(contractType.replace("free lance", "freelance"));
              contractType = contractType.replace("free lance", "freelance");
          }
          else{
            job.setContractType(extractContractType(contractType, "^\\w+"));
          }
          System.out.println(contractType);
      }
      else{
        job.setContractType(contractType.toLowerCase());
      }
    }

  }
}
