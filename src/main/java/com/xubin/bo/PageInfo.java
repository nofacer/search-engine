package com.xubin.bo;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageInfo {

  public String url;
  public List<String> pageWords;
  public List<LinkInfo> linkInfos;
}
