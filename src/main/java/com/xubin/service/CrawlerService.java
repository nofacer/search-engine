package com.xubin.service;

import com.xubin.repository.LinkRepository;
import com.xubin.repository.UrlRepository;
import java.util.ArrayList;
import java.util.Arrays;
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
  private final WordService wordService;

  private List<String> pages;
  private int depth;

  private List<String> ignoreWords = new ArrayList<>(
      Arrays.asList("the", "of", "to", "and", "a", "in", "is", "it")
  );


  private boolean isIndexed(String url) {
    if (urlService.ifUrlExist(url)) {
      long urlId = urlService.getUrlId(url);
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
        indexSinglePage(page, document);
      }

    }
  }

  private void indexSinglePage(String url, Document document) {
    if (isIndexed(url)) {
      return;
    }
    System.out.println("Indexing" + url);
    List<String> words = getPageWords(document);
    bindWordsToUrl(url, words);
  }

  private void bindWordsToUrl(String url, List<String> words) {
    long urlId = urlService.getUrlId(url);

    for (int i = 0; i < words.size(); i++) {
      String currentWord = words.get(i);
      if (ignoreWords.contains(currentWord)) {
        continue;
      }
      long wordId = wordService.getWordId(currentWord);
      wordLocationService.save(urlId, wordId, i);
    }
  }

  private List<String> getPageWords(Document document) {
    String pageText = document.text();
    return pageService.separateWords(pageText);
  }

}

