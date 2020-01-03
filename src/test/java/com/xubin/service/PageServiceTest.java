package com.xubin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
  void shouldSplitWords() {
    String text = "Hello&world hello everyone  ";
    List<String> words = pageService.separateWords(text);
    System.out.println(words);
    List<String> expectedWords = new ArrayList<>(
        Arrays.asList("hello", "world", "hello", "everyone"));
    assertEquals(expectedWords, words);
  }
}
