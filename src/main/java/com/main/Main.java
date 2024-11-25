package com.main;

import java.util.List;

import com.scrap.RekruteScrapper;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<Job> jobs = RekruteScrapper.startScrapping();
        System.out.println("Printing jobs");
        for(Job job : jobs){
            System.out.println(job.toString());
        }
    }
}