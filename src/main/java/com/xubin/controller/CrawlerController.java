package com.xubin.controller;

import com.xubin.service.CrawlerService;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class CrawlerController {

  private final CrawlerService crawlerService;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public void hello() throws URISyntaxException {
    List<String> pages = new ArrayList<>(Arrays.asList("https://www.thoughtworks.com/"));
    crawlerService.crawl(pages, 1);
  }

}
