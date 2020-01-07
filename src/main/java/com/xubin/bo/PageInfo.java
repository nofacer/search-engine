package com.xubin.bo;

import static com.xubin.service.PageService.getPageContent;
import static com.xubin.service.PageService.separateWords;
import static com.xubin.utils.Config.ignoreWords;

import com.xubin.po.Link;
import com.xubin.service.LinkService;
import com.xubin.service.LinkWordsService;
import com.xubin.service.PageService;
import com.xubin.service.UrlService;
import com.xubin.service.WordLocationService;
import com.xubin.service.WordService;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

@Builder
@Log4j2
public class PageInfo {

  public String url;
  public long urlId;
  public Document pageDocument;
  public List<String> pageWords;
  @Default
  public List<LinkInfo> linkInfos = new ArrayList<>();
  @Default
  public boolean toIndex = false;

  @Autowired
  private final UrlService urlService;
  @Autowired
  private final WordLocationService wordLocationService;
  @Autowired
  private final WordService wordService;
  @Autowired
  private final PageService pageService;
  @Autowired
  private final LinkService linkService;
  @Autowired
  private final LinkWordsService linkWordsService;


  public void parse() {
    if (isIndexed() || !isValidPage()) {
      log.info("Ignore this page:" + this.url);
      return;
    }
    this.toIndex = true;
    log.info("Parsing page:" + this.url);
    getPageWords();
    getLinkInfos();
    getUrlId();
    log.info("Indexing words location");
    indexWordsLocation();
    log.info("Indexing between origin page and target page");
    indexLinkRelation();
  }


  private boolean isIndexed() {
    if (urlService.ifUrlExist(this.url)) {
      long urlId = urlService.getUrlId(this.url);
      return wordLocationService.ifUrlExist(urlId);
    }
    return false;
  }

  private boolean isValidPage() {
    this.pageDocument = getPageContent(this.url);
    return this.pageDocument != null;
  }

  private void getPageWords() {
    this.pageWords = separateWords(this.pageDocument.text());
  }

  private void getLinkInfos() {
    Elements linkElements = this.pageDocument.getElementsByTag("a");
    for (Element linkElement : linkElements) {
      String linkHref = parseLinkHref(linkElement.attr("href"));
      List<String> linkWords = separateWords(linkElement.text());
      this.linkInfos.add(LinkInfo.builder().url(linkHref).linkWords(linkWords).build());
    }
  }

  private void getUrlId() {
    this.urlId = urlService.getUrlId(this.url);
  }

  private void indexWordsLocation() {
    for (int i = 0; i < this.pageWords.size(); i++) {
      String currentWord = this.pageWords.get(i);
      if (ignoreWords.contains(currentWord)) {
        continue;
      }
      long wordId = wordService.getWordId(currentWord);
      wordLocationService.save(this.urlId, wordId, i);
    }
  }

  private String parseLinkHref(String linkHref) {
    if (linkHref.equals("") || linkHref.equals("#") || linkHref.contains(":")) {
      linkHref = "/";
    }
    if (linkHref.contains("#")) {
      linkHref = linkHref.split("#")[0];
    }
    try {
      return new URL(new URL(this.url), linkHref).toString();
    } catch (MalformedURLException e) {
      return this.url;
    }
  }

  private void indexLinkRelation() {
    for (LinkInfo linkInfo : this.linkInfos) {
      long fromId = this.urlId;
      long toId = urlService.getUrlId(linkInfo.url);
      if (fromId == toId) {
        return;
      }

      Link newLink = linkService.saveLink(fromId, toId);
      long newLinkId = newLink.getId();

      for (String word : linkInfo.linkWords) {
        if (ignoreWords.contains(word)) {
          continue;
        }
        long wordId = wordService.getWordId(word);
        linkWordsService.saveLinkWords(newLinkId, wordId);
      }
    }
  }


}
