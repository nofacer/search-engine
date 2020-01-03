package com.xubin.service;

import static org.jsoup.Jsoup.parse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PageServiceTest {

  private PageService pageService = new PageService();

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void shouldGetPageContent() {
    Document document = pageService.getPageContent("http://www.baidu.com");
    assertNotNull(document);
  }

  @Test
  void shouldReturnNullIfFailToGetPageContent() {
    Document document = pageService.getPageContent("$%^&");
    assertNull(document);
  }

  @Test
  void shouldGetPageText() {
    String html = "<html><head><title>First parse</title></head>"
        + "<body><p>Parsed HTML into a doc. </p></body></html>";
    Document doc = parse(html);
    String pageText = pageService.getPageText(doc);
    assertEquals("First parse Parsed HTML into a doc.", pageText);
  }
}
