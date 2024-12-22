package com.parsers.rekrute;

import java.util.List;

import com.main.Job;
import com.parsers.Parser;

public class RekruteFunctionParser extends  Parser {

    public static void parseFunction(List<Job> jobs){
      for(Job job: jobs){
        job.setFunction(findClosestFunctionMatch(job.getFunction()));
      }
    }

}
