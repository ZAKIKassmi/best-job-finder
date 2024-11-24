package com.scrap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Main {



    public static String getRemoteWork(String fullText){
        String key = "Télétravail :";
        int index = fullText.indexOf(key);
        if(index != -1){
            String remoteWorkValue = fullText.substring(index + key.length()).trim();
            return remoteWorkValue;
        }
        else{
            return "Non";
        }
    }

    public static void scrapJobPage(
        List<Job> jobs,
        Set<String> pagesDiscovered,
        List<String> pagesToScrape
    ){
        if(!pagesToScrape.isEmpty()){
            //The current page is about to be scrapped
            //and should no longer be part of the scrapping queue
            String url = pagesToScrape.remove(0);
            String domainName = "https://www.rekrute.com";
            pagesDiscovered.add(url);
            Document doc;
            //Scrap the webpage
            try {
                doc = Jsoup
                    .connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
                    .header("Accept-Language", "*")
                    .get();
            } 
            catch (IOException e) {
                throw new RuntimeException("Oops! Something went wrong while fetching data."+e);
            }

            Elements jobsElements = doc.select("li.post-id");
            
            for(Element jobElement : jobsElements){
                //Create new job for each job in the jobs page
                Job job = new Job();
                Element jobDetailsLinkElement = jobElement.selectFirst(".section h2 a");
                if(jobDetailsLinkElement == null){
                    continue;
                }
                
                job.setJobTitle(jobDetailsLinkElement.text());
                job.setActivitySector(jobElement.select(".holder ul li:first-child a").text());
                job.setFunction(jobElement.select(".holder ul li:nth-child(2) a").text());
                job.setRequiredExperience(jobElement.select(".holder ul li:nth-child(3) a").text());
                job.setStudyLevel(jobElement.select(".holder ul li:nth-child(4) a").text());
                job.setContractType(jobElement.select(".holder ul li:last-child a").text());
                job.setremoteWork(
                    getRemoteWork(jobElement.select(".holder ul li:last-child").text())
                );

                String jobPageUrl = jobDetailsLinkElement.absUrl("href");
                try {
                    //Go to each job url to get the full profile description
                    Document jobPage = Jsoup.connect(jobPageUrl)
                                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
                                .header("Accept-Language", "*")
                                .get();
                    //TODO: Clean profile description using regex and other libraries
                    job.setsearchedProfile(jobPage.select(".contentbloc .col-md-12.blc:nth-child(5)").text());

                } catch (IOException e) {
                    System.err.println("IOException Job page url: "+jobPageUrl +"\n Error: "+e);
                }


                // jobs.add(job);
                System.out.println(job.toString());
                
            }

            Elements paginationElements = doc.select("span.jobs select option");

            for(Element page : paginationElements){
                String pageUrl = domainName + page.val();

                if (!pagesDiscovered.contains(pageUrl) && !pagesToScrape.contains(pageUrl)) { 
                    pagesToScrape.add(pageUrl);
                }
                pagesDiscovered.add(pageUrl);
                
            }



        }


    }


    public static void main(String[] args) throws InterruptedException {
        List<Job> jobs = Collections.synchronizedList(new ArrayList<Job>());

        Set<String> pageDiscovered = Collections.synchronizedSet(new HashSet<String>());

        List<String> pagesToScrape = Collections.synchronizedList(new ArrayList<String>());
        



        pagesToScrape.add("https://www.rekrute.com/offres-emploi-metiers-de-l-it.html");

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        scrapJobPage(jobs, pageDiscovered, pagesToScrape);
       
        // int i=1;
        // int limit = 12;

        // while(!pagesToScrape.isEmpty() && i<limit){
        //     //registering the web scraping task
        //     executorService.execute(() -> scrapJobPage(jobs, pageDiscovered, pagesToScrape));
        //     //add 200ms delay to avoid overloading the server 
        //     TimeUnit.MILLISECONDS.sleep(300);
        //     scrapJobPage(jobs, pageDiscovered, pagesToScrape);
        //     i++;
        // }
        executorService.shutdown();
        executorService.awaitTermination(300, TimeUnit.SECONDS);

        System.out.println("jobs size -> "+ jobs.size());


    }
}