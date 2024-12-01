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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.main.Job;

public class MJob extends Scrapper {

  private static final int THREAD_POOL_SIZE = 4;
  private static final Queue<String> pagesToScrape = new ConcurrentLinkedQueue<>();
  private static final List<Job> jobs = Collections.synchronizedList(new ArrayList<>());



  public static Job extractJob(Element jobElement){

    Job job = new Job();
    @SuppressWarnings("null")
    String pageUrl = jobElement.selectFirst(".offer-heading .offer-title a").absUrl("href");
    System.out.println(pageUrl);
    try {
      Document jobPage = createJsoupConnection(pageUrl);
      job.setJobTitle(
        extractJobTitle(
          jobPage.select("body section.main-details .offer-title").text(), "(?<=-).+|^.+$")
      );
      job.setContractType(jobPage.select("body section.main-details .details-content .header-details .list-details li:nth-child(2) h3").text());
      job.setCity(jobPage.select("body section.main-details .details-content .location span").text());
      job.setActivitySector(jobPage.select("body section.main-details .details-content .the-content > div:nth-child(8)").text());
      job.setSearchedProfile(jobPage.select("body section.main-details .details-content .the-content > div:nth-child(6)").text());
      job.setJobDescription(jobPage.select("body section.main-details .details-content .the-content > div:nth-child(4)").text());
      job.setFunction(jobPage.select("body section.main-details .details-content .the-content > div:nth-child(10)").text());
      job.setRemoteWork(null);
      job.setStudyLevel(jobPage.select("body section.main-details .details-content .the-content > div:nth-child(14)").text());
      job.setRequiredExperience(jobPage.select("body section.main-details .details-content .the-content > div:nth-child(12)").text());
      return job;
    } 
    catch (IOException e) {
      System.out.println("Oops! something went wrong! Please try again later.");
      return null;
    }

   

  }

  public static Document createJsoupConnection(String url) throws IOException {
    int retries = 3; 
    while (retries > 0) {
        try {
            return Jsoup.connect(url)
                    .userAgent(Scrapper.getUserAgent())
                    .header("Accept-Language", "*")
                    .referrer("https://google.com")
                    .timeout(60000) // 60 seconds timeout
                    .get();
        } catch (IOException e) {
            retries--;
            if (retries == 0) {
                throw e;
            }
            System.out.println("Retrying connection... Attempts left: " + retries);
        }
    }
    return null; // Unreachable, but required for compilation
}


  public static void scrapJobPage(String url){
    System.out.println("currently scrapping -> "+url);
    try{
      Document doc = createJsoupConnection(url);

      if(doc != null){

        Elements jobElements = doc.select(".offers-boxes .offer-box");
        for(Element jobElement : jobElements){
          Job job = extractJob(jobElement);
          System.out.println(job.toString());
          jobs.add(job);
        }
      }
      else{
        System.out.println("Jsoup connection returned null for: "+url);
      }

    }
    catch(IOException e){
      System.out.println("Error scrapping page: "+url+" - "+e.getMessage());
    }
  }

  public static List<Job> startScrapping() throws InterruptedException{
    for(int i=1; i<=24;i++){
      pagesToScrape.add("https://www.m-job.ma/recherche?page="+i);
    }

    ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    int pageCounter = 1;


    while(!pagesToScrape.isEmpty()){
        String url = pagesToScrape.poll();
        if(url == null) continue;
        pageCounter++;
        executorService.submit(() -> scrapJobPage(url));
        TimeUnit.MILLISECONDS.sleep(1000); // Rate limiting
    }
    executorService.shutdown();
    executorService.awaitTermination(1000, TimeUnit.SECONDS);
    System.out.println("Total jobs -> "+jobs.size()+"\n Page counter: ");
    return jobs;

  } 
}
