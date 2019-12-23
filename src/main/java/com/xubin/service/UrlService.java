package com.xubin.service;

import com.xubin.po.Url;
import com.xubin.repository.UrlRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class UrlService {

  private final UrlRepository urlRepository;

  public boolean ifUrlExist(String url) {
    Optional<Url> fetchedUrl = urlRepository.findByUrl(url);
    return fetchedUrl.isPresent();
  }

  public long getEntryId(String url) {
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
