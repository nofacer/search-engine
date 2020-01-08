package com.xubin.service;

import static com.xubin.service.PageService.getPageContent;
import static com.xubin.service.PageService.separateWords;
import static com.xubin.utils.Config.ignoreWords;

import com.xubin.bo.LinkInfo;
import com.xubin.bo.PageInfo;
import com.xubin.po.Link;
import com.xubin.repository.LinkRepository;
import com.xubin.repository.UrlRepository;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
@Log4j2
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

  private List<String> alreadyIndexedUrls = new ArrayList<>();

  public void crawl(List<LinkInfo> linkInfos, int depth) {
    for (int i = 0; i < depth; i++) {
      for (LinkInfo linkInfo : linkInfos) {
        PageInfo pageInfo = PageInfo.builder().url(cleanHostURL(linkInfo.url)).build();
        parse(pageInfo);
        linkInfos = pageInfo.linkInfos;
      }
    }
  }

  public void parse(PageInfo pageInfo) {
    if (alreadyIndexedUrls.contains(pageInfo.url)) {
      return;
    }
    if (isIndexed(pageInfo) || !isValidPage(pageInfo)) {
      log.info("Ignore this page:" + pageInfo.url);
      return;
    }
    log.info("Parsing page:" + pageInfo.url);
    alreadyIndexedUrls.add(pageInfo.url);
    getPageWords(pageInfo);
    getLinkInfos(pageInfo);
    getUrlId(pageInfo);
    log.info("Indexing words location");
    indexWordsLocation(pageInfo);
    log.info("Indexing between origin page and target page");
    indexLinkRelation(pageInfo);
    log.info("Indexing between link and words");
    indexWordsInLinkRelation(pageInfo);
  }


  private boolean isIndexed(PageInfo pageInfo) {
    if (urlService.ifUrlExist(pageInfo.url)) {
      long urlId = urlService.getUrlId(pageInfo.url);
      return wordLocationService.ifUrlExist(urlId);
    }
    return false;
  }

  private boolean isValidPage(PageInfo pageInfo) {
    pageInfo.pageDocument = getPageContent(pageInfo.url);
    return pageInfo.pageDocument != null;
  }

  private void getPageWords(PageInfo pageInfo) {
    pageInfo.pageWords = separateWords(pageInfo.pageDocument.text());
  }

  private void getLinkInfos(PageInfo pageInfo) {
    Elements linkElements = pageInfo.pageDocument.getElementsByTag("a");
    for (Element linkElement : linkElements) {
      String linkHref = parseLinkHref(pageInfo, linkElement.attr("href"));
      List<String> linkWords = separateWords(linkElement.text());
      pageInfo.linkInfos.add(LinkInfo.builder().url(linkHref).linkWords(linkWords).build());
    }
  }

  private void getUrlId(PageInfo pageInfo) {
    pageInfo.urlId = urlService.getUrlId(pageInfo.url);
  }

  private void indexWordsLocation(PageInfo pageInfo) {
    for (int i = 0; i < pageInfo.pageWords.size(); i++) {
      String currentWord = pageInfo.pageWords.get(i);
      if (ignoreWords.contains(currentWord)) {
        continue;
      }
      long wordId = wordService.getWordId(currentWord);
      wordLocationService.save(pageInfo.urlId, wordId, i);
    }
  }

  private String parseLinkHref(PageInfo pageInfo, String linkHref) {
    if (linkHref.equals("") || linkHref.equals("#") || linkHref.contains(":")) {
      linkHref = "/";
    }
    if (linkHref.contains("#")) {
      linkHref = linkHref.split("#")[0];
    }
    try {
      return new URL(new URL(pageInfo.url), linkHref).toString();
    } catch (MalformedURLException e) {
      return pageInfo.url;
    }
  }

  private void indexLinkRelation(PageInfo pageInfo) {
    for (LinkInfo linkInfo : pageInfo.linkInfos) {
      long fromId = pageInfo.urlId;
      long toId = urlService.getUrlId(linkInfo.url);
      if (fromId == toId) {
        linkInfo.linkId = -1;
        return;
      }

      Link newLink = linkService.saveLink(fromId, toId);
      linkInfo.linkId = newLink.getId();
    }
  }

  private void indexWordsInLinkRelation(PageInfo pageInfo) {
    for (LinkInfo linkInfo : pageInfo.linkInfos) {
      if (linkInfo.linkId == -1) {
        continue;
      }
      for (String word : linkInfo.linkWords) {
        if (ignoreWords.contains(word)) {
          continue;
        }
        long wordId = wordService.getWordId(word);
        linkWordsService.saveLinkWords(linkInfo.linkId, wordId);
      }
    }
  }

  private String cleanHostURL(String url) {
    if (url.endsWith("/")) {
      return url.substring(0, url.length() - 2);
    }
    return url;
  }

}

