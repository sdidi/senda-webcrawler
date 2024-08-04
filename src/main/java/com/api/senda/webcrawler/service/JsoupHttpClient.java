package com.api.senda.webcrawler.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class JsoupHttpClient implements IHttpClient {
    @Override
    public Document get(String url) throws IOException {
        return Jsoup.connect(url).ignoreContentType(true).get();
    }
}
