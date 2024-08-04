package com.api.senda.webcrawler.service;

import com.api.senda.webcrawler.util.URLManager;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class JsoupWebCrawlerTest {

    @Mock
    private URLManager urlManager;

    @Mock
    private IHttpClient httpClient;

    @InjectMocks
    private JsoupIWebCrawler jsoupWebCrawler;

    @Test
    void testCrawlNewUrl() throws IOException {
        //Prepare
        String startUrl = "http://example.com";
        String newUrl = "http://example.com/page";

        Document mockDocument = mock(Document.class);
        Elements mockElements = mock(Elements.class);
        Element mockElement = mock(Element.class);

        when(urlManager.isVisited(startUrl)).thenReturn(false);
        when(urlManager.isSameDomain(startUrl)).thenReturn(true);
        when(urlManager.isVisited(newUrl)).thenReturn(false);
        when(urlManager.isSameDomain(newUrl)).thenReturn(true);

        when(httpClient.get(startUrl)).thenReturn(mockDocument);
        when(mockDocument.select("a[href]")).thenReturn(mockElements);
        when(mockElements.iterator()).thenReturn(java.util.Collections.singletonList(mockElement).iterator());
        when(mockElement.attr("abs:href")).thenReturn(newUrl);

        //Test
        jsoupWebCrawler.crawl(startUrl);
        //Evaluate
        verify(urlManager, times(1)).addVisited(startUrl);
        verify(urlManager, times(1)).addVisited(newUrl);
    }


    @Test
    void testCrawlVisitedUrl() throws IOException {
        //Prepare
        String startUrl = "http://example.com";

        when(urlManager.isVisited(startUrl)).thenReturn(true);

        //Test
        jsoupWebCrawler.crawl(startUrl);
        //Evaluate
        verify(urlManager, never()).addVisited(anyString());
    }


    @Test
    void testCrawlException() throws IOException {
        //Prepare
        String startUrl = "http://example.com";
        IHttpClient mockHttpClient = mock(IHttpClient.class);
        URLManager mockUrlManager = mock(URLManager.class);
        Document mockDocument = mock(Document.class);

        when(mockUrlManager.isVisited(startUrl)).thenReturn(false);
        when(mockUrlManager.isSameDomain(startUrl)).thenReturn(true);
        when(mockHttpClient.get(startUrl)).thenThrow(new IOException("Connection error"));

        JsoupIWebCrawler jsoupWebCrawler = new JsoupIWebCrawler(mockUrlManager, mockHttpClient);
        //Test
        jsoupWebCrawler.crawl(startUrl);
        //Evaluate
        verify(mockUrlManager).addVisited(startUrl);
        verify(mockDocument, times(0)).select(anyString());
    }
}
