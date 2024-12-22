package com.parsers.emploi;

import java.util.List;

import com.main.Job;

public class EmploiRemoteParser {
  public static void parseRemote(List<Job> jobs){
    for(Job job: jobs){
      job.setRemoteWork("non");
    }
  }
}
