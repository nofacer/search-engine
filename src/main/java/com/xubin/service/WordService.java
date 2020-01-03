package com.xubin.service;

import com.xubin.po.Word;
import com.xubin.repository.WordRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class WordService {

  private final WordRepository wordRepository;

  public boolean ifWordExist(String word) {
    Optional<Word> fetchedWord = wordRepository.findByWord(word);
    return fetchedWord.isPresent();
  }

  public long getWordId(String word) {
    Optional<Word> fetchedWord = wordRepository.findByWord(word);
    if (fetchedWord.isPresent()) {
      return fetchedWord.get().getId();
    } else {
      Word savedWord = saveWord(word);
      return savedWord.getId();
    }
  }

  private Word saveWord(String word) {
    Word newWord = Word.builder().word(word).build();
    return wordRepository.save(newWord);
  }
}
