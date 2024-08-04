package com.api.senda.webcrawler.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CrawlResponse {
    private Set<String> visitedUrls;

}