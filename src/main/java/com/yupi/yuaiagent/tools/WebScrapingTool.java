package com.yupi.yuaiagent.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

/**
 * 网页抓取工具
 */
@Slf4j
@Component
public class WebScrapingTool {

    @Tool("Scrape and extract content from a web page")
    public String scrapeWebPage(@P("URL of the web page to scrape") String url) {
        log.info("[Tool] scrapeWebPage called with url: {}", url);
        try {
            Document document = Jsoup.connect(url).get();
            return document.html();
        } catch (Exception e) {
            return "Error scraping web page: " + e.getMessage();
        }
    }
}
