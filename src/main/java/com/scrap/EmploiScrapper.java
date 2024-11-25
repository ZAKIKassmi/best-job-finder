package com.scrap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.main.Job;

public class EmploiScrapper extends Scrapper{

  private static final int THREAD_POOL_SIZE = 4;
    private static final Queue<String> pagesToScrape = new ConcurrentLinkedQueue<>();
    private static final List<Job> jobs = Collections.synchronizedList(new ArrayList<>());


 

  public static Job extractJob(Element jobElement){

    Job job = new Job();
    @SuppressWarnings("null")
    String pageUrl = jobElement.selectFirst(".card-job-detail h3 a").absUrl("href");

    try {
      Document jobPage = createJsoupConnection(pageUrl);
      job.setActivitySector(jobPage.select(".card-block-company .field-items").text());
      job.setSearchedProfile(jobPage.select(".job-qualifications").text());
      job.setFunction(jobPage.select(" li.withicon.suitcase span").text());
      job.setRemoteWork("unknown");
      job.setJobTitle(extractJobTitle(jobElement.select("h3 a").text(), "^(.*?)\\-"));
      job.setStudyLevel(jobElement.select("ul li:first-child").text());
      job.setRequiredExperience(jobElement.select("ul li:nth-child(2)").text());
      job.setContractType(jobElement.select("ul li:nth-child(3) strong").text());
      job.setCity(jobElement.select("ul li:nth-child(4) strong").text());
      return job;
    } 
    catch (IOException e) {
      System.out.println("Oops! something went wrong! Please try again later.");
      return null;
    }



  }

  public static void scrapJobPage(String url){

    try{
      Document doc = createJsoupConnection(url);
      Elements jobElements = doc.select("div.card.card-job.featured");

      for(Element jobElement : jobElements){
        Job job = extractJob(jobElement);
        jobs.add(job);
      }

    }
    catch(IOException e){
      System.out.println("Error scrapping page: "+url+" - "+e.getMessage());
    }



  }



  public static void startScrapping() throws InterruptedException {
    pagesToScrape.add("https://www.emploi.ma/recherche-jobs-maroc");
    scrapJobPage(pagesToScrape.poll());

    for(int i=2; i<=32;i++){
      pagesToScrape.add("https://www.emploi.ma/recherche-jobs-maroc?page="+i);
    }

    ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    int pageCounter = 1;


    while(!pagesToScrape.isEmpty() && pageCounter < 32){
        String url = pagesToScrape.poll();
        if(url == null) continue;
        pageCounter++;
        System.out.println("Page counter is: "+pageCounter);
        executorService.submit(() -> scrapJobPage(url));
        TimeUnit.MILLISECONDS.sleep(1000); // Rate limiting
    }
    executorService.shutdown();
    executorService.awaitTermination(1000, TimeUnit.SECONDS);




  }
}
