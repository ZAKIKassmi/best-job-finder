package com.parsers.mjobs;

import java.util.List;

import com.main.Job;
import com.parsers.Parser;

public class MjobActivitySectorParser extends Parser {
  public static void parseActivitySector(List<Job> jobs) {
    for (Job job : jobs) {
      String activitySector = job.getActivitySector().toLowerCase();
      if (VALID_ACTIVITY_SECTORS.contains(activitySector)) {
        job.setActivitySector(activitySector);
      } else {
        activitySector = activitySector.trim();
        activitySector = mapSector(activitySector);
        if (activitySector != null && VALID_ACTIVITY_SECTORS.contains(activitySector)) {
          job.setActivitySector(activitySector);
        } else {
          job.setActivitySector(null);
        }
      }
    }
  }
}
