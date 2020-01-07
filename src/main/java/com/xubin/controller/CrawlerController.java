package com.xubin.controller;

import com.xubin.bo.LinkInfo;
import com.xubin.service.CrawlerService;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class CrawlerController {

  private final CrawlerService crawlerService;

  @RequestMapping(value = "/crawl", method = RequestMethod.POST)
  public String hello(@RequestHeader(value = "start_page") String startPage,
      @RequestHeader(value = "depth") int depth)
      throws MalformedURLException {
    List<LinkInfo> linkInfos = new ArrayList<>();
    linkInfos.add(LinkInfo.builder().url(startPage).build());
    crawlerService.crawl(linkInfos, depth);
    return "Finished";
  }

}
