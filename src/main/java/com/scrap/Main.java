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

    public static void scrapProductPage(
        List<Product> products,
        Set<String> pagesDiscovered,
        List<String> pagesToScrape
    ){
        if(!pagesToScrape.isEmpty()){
            //The current page is about to be scrapped
            //and should no longer be part of the scrapping queue
            String url = pagesToScrape.remove(0);
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

            Elements productElements = doc.select("li.product");
            
            for(Element productElement : productElements){
                Product product = new Product();

                product.setUrl(productElement.selectFirst("a").attr("abs:href"));
                product.setName(productElement.selectFirst("h2").text());
                product.setPrice(productElement.selectFirst("span").text());
                product.setImage(productElement.selectFirst("img").attr("src")); 

                products.add(product);   
            }

            Elements paginationElements = doc.select("a.page-numbers");

            for(Element page : paginationElements){
                String pageUrl = page.attr("href");
                if (!pagesDiscovered.contains(pageUrl) && !pagesToScrape.contains(pageUrl)) { 
                    pagesToScrape.add(pageUrl);
                } 
                pagesDiscovered.add(pageUrl); 
                
            }

            System.out.println("page scraped -> "+url);


        }


    }


    public static void main(String[] args) throws InterruptedException {
        List<Product> products = Collections.synchronizedList(new ArrayList<Product>());

        Set<String> pageDiscovered = Collections.synchronizedSet(new HashSet<String>());

        List<String> pagesToScrape = Collections.synchronizedList(new ArrayList<String>());


        pagesToScrape.add("https://www.scrapingcourse.com/ecommerce/page/1/");

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        scrapProductPage(products, pageDiscovered, pagesToScrape);
       
        int i=1;
        int limit = 12;

        while(!pagesToScrape.isEmpty() && i<limit){
            //registering the web scraping task
            executorService.execute(() -> scrapProductPage(products, pageDiscovered, pagesToScrape));
            //add 200ms delay to avoid overloading the server 
            TimeUnit.MILLISECONDS.sleep(200);
            scrapProductPage(products, pageDiscovered, pagesToScrape);
            i++;
        }
        executorService.shutdown();
        executorService.awaitTermination(300, TimeUnit.SECONDS);

        System.out.println("Products size -> "+ products.size());


    }
}