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
    Optional<WordLocation> fetchedUrl = wordLocationRepository.findTopByUrlId(urlId);
    return fetchedUrl.isPresent();
  }

  public WordLocation save(long urlId, long wordId, int location) {
    WordLocation wordLocation = WordLocation.builder().urlId(urlId).wordId(wordId)
        .location(location).build();
    return wordLocationRepository.save(wordLocation);
  }
}
