package com.parsers.emploi;

import java.util.List;

import com.main.Job;
import com.parsers.Parser;

public class EmploitCityParser extends Parser {
  public static void parseCity(List<Job> jobs){
    for(Job job: jobs){
      String city = job.getCity();
      if(job.getCity() == null) continue;
      city = city.toLowerCase().trim();
      if(VALID_CITIES.contains(city)){
        job.setCity(city);
      }
      else{
        String mappedCity = mapCity(city);
        if (mappedCity != null && VALID_CITIES.contains(mappedCity)) {
          job.setCity(mappedCity);
        } else {
          System.out.println("null: "+job.getCity());
          job.setCity(null);
        }
      }
    }
  }
}
