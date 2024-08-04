package com.api.senda.webcrawler.service;

import java.io.IOException;

public interface IWebCrawler {
    void crawl(String startUrl) throws IOException;
}