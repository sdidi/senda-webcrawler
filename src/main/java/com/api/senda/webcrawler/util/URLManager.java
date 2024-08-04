package com.api.senda.webcrawler.util;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class URLManager {
    private static final Set<String> visitedLinks = new HashSet<>();
    private final String domain;

    public URLManager(String domain) {
        this.domain = domain;
    }

    public boolean isVisited(String url) {
        return visitedLinks.contains(url);
    }

    public void addVisited(String url) {
        visitedLinks.add(url);
    }

    public boolean isSameDomain(String url) {
        try {
            URL linkUrl = new URL(url);
            return linkUrl.getHost().equalsIgnoreCase(domain);
        } catch (Exception e) {
            return false;
        }
    }

    public static Set<String> getVisitedLinks() {
        return visitedLinks;
    }
}
