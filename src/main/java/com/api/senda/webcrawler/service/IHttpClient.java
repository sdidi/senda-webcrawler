package com.api.senda.webcrawler.service;

import org.jsoup.nodes.Document;

import java.io.IOException;

public interface IHttpClient {
    Document get(String url) throws IOException;
}
