package com.scrap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Main {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36";
    private static final String REKRUTE_DOMAIN_NAME = "https://www.rekrute.com";
    private static final int THREAD_POOL_SIZE = 4;
    private static final int MAX_PAGES = 5;

    
    public static String extractRemoteWork(String fullText){
        String key = "Télétravail :";
        int index = fullText.indexOf(key);
        return index != -1 ? fullText.substring(index + key.length()).trim() : null;
    }

    public static String extractJobTitleAndCity (String input, boolean isTitle){

        String regex = isTitle ? "^(.*?)\\|" : "\\|\\s*([^,(]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return null; 

    }

    private static Job extractJob(Element jobElement) {
        try {
            Job job = new Job();
            Element jobDetailsLinkElement = jobElement.selectFirst(".section h2 a");
            if (jobDetailsLinkElement == null) return null;

            job.setJobTitle(extractJobTitleAndCity(jobDetailsLinkElement.text(), true));
            job.setCity(extractJobTitleAndCity(jobDetailsLinkElement.text(), false));
            job.setActivitySector(jobElement.select(".holder ul li:first-child a").text());
            job.setFunction(jobElement.select(".holder ul li:nth-child(2) a").text());
            job.setRequiredExperience(jobElement.select(".holder ul li:nth-child(3) a").text());
            job.setStudyLevel(jobElement.select(".holder ul li:nth-child(4) a").text());
            job.setContractType(jobElement.select(".holder ul li:last-child a").text());
            job.setRemoteWork(extractRemoteWork(jobElement.select(".holder ul li:last-child").text()));
            return job;
        } catch (Exception e) {
            System.err.println("Error extracting job: " + e.getMessage());
            return null;
        }
    }

    private static void fetchJobDetails(Job job, String jobPageUrl) {
        try {
            Document jobPage = Jsoup.connect(jobPageUrl)
                    .userAgent(USER_AGENT)
                    .header("Accept-Language", "*")
                    .get();
            job.setSearchedProfile(jobPage.select(".contentbloc .col-md-12.blc:nth-child(5)").text());
        } catch (IOException e) {
            System.err.println("Error fetching job details: " + jobPageUrl + " - " + e.getMessage());
        }
    }

    public static void scrapJobPage(String url, List<Job> jobs, Set<String> pagesDiscovered, Queue<String> pagesToScrape){

        try {
            Document doc = Jsoup
                            .connect(url)
                            .userAgent(USER_AGENT)
                            .header("Accept-language", "*")
                            .get();
            Elements jobsElements = doc.select("li.post-id");
            for(Element jobElement : jobsElements){
                Job job = extractJob(jobElement);
                if(job != null){
                    fetchJobDetails(job, jobElement.selectFirst(".section h2 a").absUrl("href"));
                    // System.out.println(job);
                    jobs.add(job);
                }
            }

            Elements paginationElements = doc.select("span.jobs select option");
            for (Element page : paginationElements) {
                String pageUrl = REKRUTE_DOMAIN_NAME + page.val();
                if (pagesDiscovered.add(pageUrl)) {
                    pagesToScrape.offer(pageUrl);
                }
            }
            // System.err.println(".element inside the function"+pagesToScrape.peek());

            
        } catch (IOException e) {
            System.out.println("Error scraping page: " + url + " - " + e.getMessage());
        }
    }

    


    public static void main(String[] args) throws InterruptedException {

        //****** uncomment the next line if you want to create your database schema, for now it only create one table to test *******/
        //DatabaseServices.createDatabaseSchema();

        List<Job> jobs = Collections.synchronizedList(new ArrayList<>());
        Set<String> pagesDiscovered = Collections.synchronizedSet(new HashSet<>());
        Queue<String> pagesToScrape = new ConcurrentLinkedQueue<>();

        pagesToScrape.add("https://www.rekrute.com/offres-emploi-maroc.html");
        scrapJobPage(pagesToScrape.poll(), jobs, pagesDiscovered, pagesToScrape);

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        int pageCounter = 1;


        while(!pagesToScrape.isEmpty() && pageCounter < MAX_PAGES){
            String url = pagesToScrape.poll();
            if(url == null) continue;
            System.out.println("Url to be is about to be scraped is: "+ url);
            pageCounter++;
            System.out.println("Page counter is: "+pageCounter);
            executorService.submit(() -> scrapJobPage(url, jobs, pagesDiscovered, pagesToScrape));
            // System.out.println(".element outside the function"+ pagesToScrape.peek());
            TimeUnit.MILLISECONDS.sleep(500); // Rate limiting
        }
        executorService.shutdown();
        executorService.awaitTermination(500, TimeUnit.SECONDS);

        System.out.println("Total number of jobs scrapped is: "+jobs.size());


    }
}