package com.api.senda.webcrawler.service;

import com.api.senda.webcrawler.dto.CrawlResponse;
import com.api.senda.webcrawler.util.URLManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

@Service
public class WebCrawlerService {

    private final IHttpClient httpClient;

    @Autowired
    public WebCrawlerService(IHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public CrawlResponse crawl(String startUrl) throws Exception {
        try {
            String domain = new URL(startUrl).getHost();
            URLManager urlManager = new URLManager(domain);
            JsoupIWebCrawler crawler = new JsoupIWebCrawler(urlManager, httpClient);

            crawler.crawl(startUrl);
            Set<String> visitedUrls = URLManager.getVisitedLinks();

            return new CrawlResponse(visitedUrls);
        } catch (MalformedURLException e) {
            System.err.println("Invalid URL format: " + startUrl);
            return null;
        } catch (IOException e) {
            System.err.println("IO Error: " + e.getMessage());
            return null;
        }
    }
}
