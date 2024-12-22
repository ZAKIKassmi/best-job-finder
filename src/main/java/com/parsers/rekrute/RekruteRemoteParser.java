package com.parsers.rekrute;

import java.util.List;

import com.main.Job;
import com.parsers.Parser;

public class RekruteRemoteParser extends Parser {
     
    public static void parseRemoteWork(List<Job> jobs){
      for(Job job : jobs){
        String remoteWork = job.getRemoteWork();
        if(remoteWork != null){
            if(remoteWork.toLowerCase().contains("oui")){
                job.setRemoteWork("oui");
            }
            else if(remoteWork.toLowerCase().contains("hybride")){
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
