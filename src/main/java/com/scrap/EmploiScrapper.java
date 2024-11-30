package com.scrap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.jsoup.Jsoup;
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
      //TODO: check the searcher profile if it's woring or not
      job.setSearchedProfile(jobPage.select("article.page-application-content div.job-qualifications ul").text());
      job.setJobDescription(jobPage.select("article.page-application-content div.job-description ul").text());
      job.setFunction(jobPage.select(" li.withicon.suitcase span").text());
      job.setRemoteWork(null);
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
    System.out.println("currently scrapping -> "+url);
    try{
      Document doc = createJsoupConnection(url);

      if(doc != null){
        System.out.println("doc is != null");
        Elements jobElements = doc.select("div.card.card-job");

        for(Element jobElement : jobElements){
          Job job = extractJob(jobElement);
          System.out.println(job);
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



  public static List<Job> startScrapping() throws InterruptedException {
    pagesToScrape.add("https://www.emploi.ma/recherche-jobs-maroc?page=31");
    scrapJobPage(pagesToScrape.poll());

    // for(int i=1; i<32;i++){
    //   pagesToScrape.add("https://www.emploi.ma/recherche-jobs-maroc?page="+i);
    // }

    // ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    // int pageCounter = 1;


    // while(!pagesToScrape.isEmpty()){
    //     String url = pagesToScrape.poll();
    //     if(url == null) continue;
    //     System.out.println("Current page -> ");
    //     pageCounter++;
    //     executorService.submit(() -> scrapJobPage(url));
    //     TimeUnit.MILLISECONDS.sleep(2000); // Rate limiting
    // }
    // executorService.shutdown();
    // executorService.awaitTermination(1000, TimeUnit.SECONDS);
    // System.out.println("Total jobs -> "+jobs.size()+"\n Page counter: "+pageCounter);

    return jobs;


  }
}
