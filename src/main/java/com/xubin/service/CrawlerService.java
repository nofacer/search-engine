package com.xubin.service;

import com.xubin.po.Link;
import com.xubin.repository.LinkRepository;
import com.xubin.repository.UrlRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
  private final LinkService linkService;
  private final LinkWordsService linkWordsService;

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

        Elements linksInPage = document.getElementsByTag("a");
        for (Element link : linksInPage) {
          String linkHref = link.attr("href");
          if (!linkHref.equals("")) {
            String fullUrl = page + linkHref;
            if (fullUrl.startsWith("http") && !urlService.ifUrlExist(fullUrl)) {
              newPages.add(fullUrl);
            }
            String linkText = link.text();
            indexBetweenTwoPages(page, fullUrl, linkText);
          }
        }
        this.pages = newPages;
      }
    }
  }

  private void indexBetweenTwoPages(String fromUrl, String toUrl, String linkText) {
    long fromId = urlService.getUrlId(fromUrl);
    long toId = urlService.getUrlId(toUrl);
    if (fromId == toId) {
      return;
    }

    Link newLink = linkService.saveLink(fromId, toId);
    long newLinkId = newLink.getId();

    List<String> words = pageService.separateWords(linkText);
    for (String word : words) {
      if (ignoreWords.contains(word)) {
        continue;
      }
      long wordId = wordService.getWordId(word);
      linkWordsService.saveLinkWords(newLinkId, wordId);
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

