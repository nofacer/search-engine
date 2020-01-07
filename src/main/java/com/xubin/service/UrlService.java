package com.xubin.service;

import com.xubin.po.Url;
import com.xubin.repository.UrlRepository;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class UrlService {

  private final UrlRepository urlRepository;

  public static String mergeUrl(String hostUrl, String subUrl) throws MalformedURLException {
    if (subUrl.contains(":")) {
      return hostUrl;
    }
    if (subUrl.equals("#")) {
      return hostUrl;
    }
    if (subUrl.contains("#")) {
      subUrl = subUrl.split("#")[0];
    }
    return new URL(new URL(hostUrl), subUrl).toString();
  }

  public boolean ifUrlExist(String url) {
    Optional<Url> fetchedUrl = urlRepository.findByUrl(url);
    return fetchedUrl.isPresent();
  }

  public long getUrlId(String url) {
    Optional<Url> fetchedUrl = urlRepository.findByUrl(url);
    if (fetchedUrl.isPresent()) {
      return fetchedUrl.get().getId();
    } else {
      Url savedUrl = saveUrl(url);
      return savedUrl.getId();
    }
  }

  private Url saveUrl(String url) {
    Url newUrl = Url.builder().url(url).build();
    return urlRepository.save(newUrl);
  }
}
