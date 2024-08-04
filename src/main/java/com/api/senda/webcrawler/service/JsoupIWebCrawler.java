package com.api.senda.webcrawler.service;

import com.api.senda.webcrawler.util.URLManager;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JsoupIWebCrawler implements IWebCrawler {
    private final URLManager urlManager;
    private final IHttpClient httpClient;

    public JsoupIWebCrawler(URLManager urlManager, IHttpClient httpClient) {
        this.urlManager = urlManager;
        this.httpClient = httpClient;
    }

    @Override
    public void crawl(String startUrl) throws IOException {

        if (urlManager == null || httpClient == null) {
            throw new IllegalStateException("URLManager and HttpClient must not be null");
        }
        if (!urlManager.isVisited(startUrl) && urlManager.isSameDomain(startUrl)) {
            try {
                urlManager.addVisited(startUrl);
                Document document = httpClient.get(startUrl);

                if (document == null) {
                    System.err.println("Failed to retrieve document for URL: " + startUrl);
                    return;
                }
                Elements links = document.select("a[href]");

                for (Element link : links) {
                    String url = link.attr("abs:href");
                    if (url.contains("#")) {
                        url = url.substring(0, url.indexOf("#"));
                    }
                    if (!urlManager.isVisited(url) && urlManager.isSameDomain(url)) {
                        crawl(url);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error fetching URL: " + startUrl + " - " + e.getMessage());
            }
        }
    }
}
