package com.api.senda.webcrawler.controller;


import com.api.senda.webcrawler.dto.CrawlRequest;
import com.api.senda.webcrawler.dto.CrawlResponse;
import com.api.senda.webcrawler.service.ErrorResponse;
import com.api.senda.webcrawler.service.WebCrawlerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WebCrawlerController {

    @Autowired
    private WebCrawlerService webCrawlerService;

    @PostMapping("/crawl")
    public ResponseEntity<?>  crawl(@RequestBody CrawlRequest request) {
        try {
            CrawlResponse response = webCrawlerService.crawl(request.getUrl());
            if (response == null) {
                ErrorResponse errorResponse = new ErrorResponse("Invalid URL or an error occurred during crawling.");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("An unexpected error occurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}