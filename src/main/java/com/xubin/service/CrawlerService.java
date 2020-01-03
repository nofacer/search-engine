package com.xubin.service;

import com.xubin.repository.LinkRepository;
import com.xubin.repository.UrlRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class CrawlerService {

  private final LinkRepository linkRepository;
  private final UrlRepository urlRepository;

  private final UrlService urlService;
  private final WordLocationService wordLocationService;
  private final PageService pageService;

  private List<String> pages;
  private int depth;


  private boolean isIndexed(String url) {
    if (urlService.ifUrlExist(url)) {
      long urlId = urlService.getEntryId(url);
      return wordLocationService.ifUrlExist(urlId);
    }
    return false;
  }

  public void crawl() {
    for (int i = 0; i < this.depth; i++) {
      List<String> newPages = new ArrayList<>();
      for (String page : this.pages) {
        Document document = pageService.getPageContent(page);
        if (document == null) {
          continue;
        }
        addToIndex(page, document);
      }

    }
  }

  private void addToIndex(String url, Document document) {
    if (isIndexed(url)) {
      return;
    }

    System.out.println("Indexing" + url);
    String pageText = document.text();

  }

}

