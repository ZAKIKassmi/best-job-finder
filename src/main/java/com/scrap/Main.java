package com.scrap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        
        //The current page is about to be scrapped
        //and should no longer be part of the scrapping queue
        String url = pagesToScrape.remove(0);
        pagesDiscovered.add(url);
        Document doc;
        //Scrap the webpage
         try {
            doc = Jsoup
                .connect("https://www.scrapingcourse.com/ecommerce/")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
                .header("Accept-Language", "*")
                .get();
            Elements productElements = doc.select("li.product");
            
            for(Element productElement : productElements){
                Product product = new Product();

                product.setUrl(productElement.selectFirst("a").attr("abs:href"));
                product.setName(productElement.selectFirst("h2").text());
                product.setPrice(productElement.selectFirst("span").text());
                product.setImage(productElement.selectFirst("img").attr("src")); 

                products.add(product);   
            }

        } 
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        Elements paginationElements = doc.select("a.page-numbers");

        for(Element pageElement : paginationElements){
            String pageUrl = pageElement.attr("href");
            if (!pagesDiscovered.contains(pageUrl) && !pagesToScrape.contains(pageUrl)) { 
				pagesToScrape.add(pageUrl);
			} 
            
            pagesDiscovered.add(pageUrl); 

        }


        


    }


    public static void main(String[] args) {
        List<Product> products = new ArrayList<Product>();

        Set<String> pageDiscovered = new HashSet<String>();
        
        List<String> pagesToScrape = new ArrayList<>();
        pagesToScrape.add("https://www.scrapingcourse.com/ecommerce/page/1/"); 

        int i=0;
        int limit = 12;

        while(!pagesToScrape.isEmpty() && i<limit){
            scrapProductPage(products, pageDiscovered, pagesToScrape);
            i++;
        }


    }
}