package com.xubin.service;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class PageService {

  public Document getPageContent(String url) {
    Document document = null;
    try {
      document = Jsoup.connect(url).get();
    } catch (IllegalArgumentException | IOException e) {
      System.out.println("Failed to open " + url);
    }
    return document;
  }
}
