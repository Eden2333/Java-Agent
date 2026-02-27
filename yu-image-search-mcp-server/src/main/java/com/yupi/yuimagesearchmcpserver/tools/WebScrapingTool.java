package com.yupi.yuimagesearchmcpserver.tools;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebScrapingTool {

    public String scrapeWebPage(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            return document.html();
        } catch (Exception e) {
            return "Error scraping web page: " + e.getMessage();
        }
    }
}
