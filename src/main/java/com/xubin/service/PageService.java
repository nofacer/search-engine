package com.xubin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class PageService {

  public static Document getPageContent(String url) {
    Document document = null;
    try {
      document = Jsoup.connect(url).get();
    } catch (IllegalArgumentException | IOException e) {
      log.error("Failed to open:" + url);
    }
    return document;
  }

  public static List<String> separateWords(String text) {
    List<String> words = new ArrayList<>();
    for (String word : text.split("\\W")) {
      if (!word.equals("")) {
        words.add(word.toLowerCase());
      }
    }
    return words;
  }
}
