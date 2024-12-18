package com.parsers;

import java.util.List;

import com.main.Job;

public class RemoteParser extends Parser {
     
    public static void parseRemoteWork(List<Job> jobs){
      for(Job job : jobs){
        if(job.getRemoteWork() != null){
            if(job.getRemoteWork().toLowerCase().contains("oui")){
                job.setRemoteWork("oui");
            }
            else if(job.getRemoteWork().toLowerCase().contains("hybride")){
                job.setRemoteWork("hybride");
            }
            else{
                job.setRemoteWork("non");
            }
        }
        else{
            job.setRemoteWork("non");
        }
      }
    }
}
