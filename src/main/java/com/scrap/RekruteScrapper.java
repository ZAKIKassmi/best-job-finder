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

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.main.Job;

public class RekruteScrapper extends Scrapper  {
    private static final String REKRUTE_DOMAIN_NAME = "https://www.rekrute.com";
    private static final int THREAD_POOL_SIZE = 4;
    private static final int MAX_PAGES = 5;
    private static final Set<String> pagesDiscovered = Collections.synchronizedSet(new HashSet<>());
    private static final Queue<String> pagesToScrape = new ConcurrentLinkedQueue<>();
    private static final List<Job> jobs = Collections.synchronizedList(new ArrayList<>());



    public static String extractRemoteWork(String fullText) {
        String key = "Télétravail :";
        int index = fullText.indexOf(key);
        return index != -1 ? fullText.substring(index + key.length()).trim() : null;
    }

    public static void setPaginationElements(Document doc) {
        Elements paginationElements = doc.select("span.jobs select option");
        for (Element page : paginationElements) {
            String pageUrl = REKRUTE_DOMAIN_NAME + page.val();
            if (pagesDiscovered.add(pageUrl)) {
                pagesToScrape.offer(pageUrl);
            }
        }
    }

    @SuppressWarnings("null")
    public static Job extractJob(Element jobElement) {
        try {
            Job job = new Job();
            job.setJobTitle(extractJobTitle(jobElement.selectFirst(".section h2 a").text(), "^(.*?)\\|"));
            job.setCity(extractJobCity(jobElement.selectFirst(".section h2 a").text(), "\\|\\s*([^,(]+)"));
            job.setActivitySector(jobElement.select(".holder ul li:first-child a").text());
            job.setFunction(jobElement.select(".holder ul li:nth-child(2) a").text());
            job.setRequiredExperience(jobElement.select(".holder ul li:nth-child(3) a").text());
            job.setStudyLevel(jobElement.select(".holder ul li:nth-child(4) a").text());
            job.setContractType(jobElement.select(".holder ul li:last-child a").text());
            job.setRemoteWork(extractRemoteWork(jobElement.select(".holder ul li:last-child").text()));
            Document jobPage = createJsoupConnection(jobElement.selectFirst(".section h2 a").absUrl("href"));
                job.setSearchedProfile(jobPage.select(".contentbloc .col-md-12.blc:nth-child(5)").text());
            return job;
        } catch (IOException e) {
            System.err.println("Error extracting job: " + e.getMessage());
            return null;
        }
    }

    public static void scrapJobPage(String url) {

        try {
            Document doc = createJsoupConnection(url);
            Elements jobsElements = doc.select("li.post-id");
            for (Element jobElement : jobsElements) {
                Job job = extractJob(jobElement);    
                jobs.add(job);
            }
            setPaginationElements(doc);
        } catch (IOException e) {
            System.out.println("Error scraping page: " + url + " - " + e.getMessage());
        }
    }

    public static List<Job> startScrapping() throws InterruptedException {
        pagesToScrape.add("https://www.rekrute.com/offres-emploi-maroc.html");
        scrapJobPage(pagesToScrape.poll());
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        int pageCounter = 1;

        while (!pagesToScrape.isEmpty() && pageCounter < MAX_PAGES) {
            String url = pagesToScrape.poll();
            if (url == null)
                continue;
            pageCounter++;
            System.out.println("Page counter is: " + pageCounter);
            executorService.submit(() -> scrapJobPage(url));
            TimeUnit.MILLISECONDS.sleep(500); // Rate limiting
        }
        executorService.shutdown();
        executorService.awaitTermination(500, TimeUnit.SECONDS);
        return jobs;
    }

}
