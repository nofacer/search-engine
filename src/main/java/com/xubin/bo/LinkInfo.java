package com.xubin.bo;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LinkInfo {

  public String url;
  public List<String> linkWords;
}
