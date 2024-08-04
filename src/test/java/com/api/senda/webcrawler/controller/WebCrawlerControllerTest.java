package com.api.senda.webcrawler.controller;

import com.api.senda.webcrawler.dto.CrawlRequest;
import com.api.senda.webcrawler.dto.CrawlResponse;
import com.api.senda.webcrawler.service.ErrorResponse;
import com.api.senda.webcrawler.service.WebCrawlerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class WebCrawlerControllerTest {

    @Mock
    private WebCrawlerService webCrawlerService;

    @InjectMocks
    private WebCrawlerController webCrawlerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testCrawl_successfulCrawl() throws Exception {
        // Prepare
        String url = "http://example.com";
        CrawlRequest request = new CrawlRequest(url);
        CrawlResponse expectedResponse = new CrawlResponse();

        when(webCrawlerService.crawl(anyString())).thenReturn(expectedResponse);

        // Test
        ResponseEntity<?> responseEntity = webCrawlerController.crawl(request);

        // Evaluate
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    void testCrawl_invalidUrlOrErrorDuringCrawl() throws Exception {
        // Prepare
        String url = "http://invalid-url.com";
        CrawlRequest request = new CrawlRequest(url);

        when(webCrawlerService.crawl(anyString())).thenReturn(null);

        // Test
        ResponseEntity<?> responseEntity = webCrawlerController.crawl(request);

        // Evaluate
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid URL or an error occurred during crawling.",
                ((ErrorResponse) responseEntity.getBody()).getMessage());
    }

    @Test
    void testCrawl_unexpectedError() throws Exception {
        // Prepare
        String url = "http://example.com";
        CrawlRequest request = new CrawlRequest(url);

        when(webCrawlerService.crawl(anyString())).thenThrow(new RuntimeException("Unexpected error"));

        // Test
        ResponseEntity<?> responseEntity = webCrawlerController.crawl(request);

        // Evaluate
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("An unexpected error occurred.",
                ((ErrorResponse) responseEntity.getBody()).getMessage());
    }
}
