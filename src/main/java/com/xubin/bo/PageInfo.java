package com.xubin.bo;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Document;

@Builder
@Log4j2
public class PageInfo {

  public String url;
  public long urlId;
  public Document pageDocument;
  public List<String> pageWords;
  @Default
  public List<LinkInfo> linkInfos = new ArrayList<>();
}
