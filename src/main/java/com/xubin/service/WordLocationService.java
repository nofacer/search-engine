package com.xubin.service;

import com.xubin.po.WordLocation;
import com.xubin.repository.WordLocationRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class WordLocationService {

  private final WordLocationRepository wordLocationRepository;

  public boolean ifUrlExist(long urlId) {
    Optional<WordLocation> fetchedUrl = wordLocationRepository.findByUrlId(urlId);
    return fetchedUrl.isPresent();
  }
}
