package com.api.senda.webcrawler.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class URLManagerTest {
    private URLManager urlManager;
    private static final String DOMAIN = "example.com";

    @BeforeEach
    void setUp() {
        urlManager = new URLManager(DOMAIN);
        URLManager.getVisitedLinks().clear();
    }

    @Test
    void testIsVisited_withUnvisitedUrl_returnsFalse() {
        // Test and Evaluate
        assertFalse(urlManager.isVisited("http://example.com/page1"));
    }

    @Test
    void testIsVisited_withVisitedUrl_returnsTrue() {
        // Test
        urlManager.addVisited("http://example.com/page1");
        // Evaluate
        assertTrue(urlManager.isVisited("http://example.com/page1"));
    }

    @Test
    void testAddVisited_addsUrlToVisitedLinks() {
        // Test
        urlManager.addVisited("http://example.com/page1");
        // Evaluate
        assertTrue(URLManager.getVisitedLinks().contains("http://example.com/page1"));
    }

    @Test
    void testIsSameDomain_withSameDomain_returnsTrue() {
        // Test and Evaluate
        assertTrue(urlManager.isSameDomain("http://example.com/page1"));
    }

    @Test
    void testIsSameDomain_withDifferentDomain_returnsFalse() {
        // Test and Evaluate
        assertFalse(urlManager.isSameDomain("http://otherdomain.com/page1"));
    }

    @Test
    void testIsSameDomain_withMalformedUrl_returnsFalse() {
        // Test and Evaluate
        assertFalse(urlManager.isSameDomain("invalid-url"));
    }

    @Test
    void testGetVisitedLinks_returnsCorrectSet() {
        // Prepare
        urlManager.addVisited("http://example.com/page1");
        urlManager.addVisited("http://example.com/page2");

        // Test
        Set<String> visitedLinks = URLManager.getVisitedLinks();
        // Evaluate
        assertEquals(2, visitedLinks.size());
        assertTrue(visitedLinks.contains("http://example.com/page1"));
        assertTrue(visitedLinks.contains("http://example.com/page2"));
    }
}
