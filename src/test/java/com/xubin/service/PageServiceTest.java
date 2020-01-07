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

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void shouldGetPageContent() {
    Document document = PageService.getPageContent("http://www.baidu.com");
    assertNotNull(document);
  }

  @Test
  void shouldReturnNullIfFailToGetPageContent() {
    Document document = PageService.getPageContent("$%^&");
    assertNull(document);
  }

  @Test
  void shouldSplitWords() {
    String text = "Hello&world hello everyone  ";
    List<String> words = PageService.separateWords(text);
    System.out.println(words);
    List<String> expectedWords = new ArrayList<>(
        Arrays.asList("hello", "world", "hello", "everyone"));
    assertEquals(expectedWords, words);
  }
}
