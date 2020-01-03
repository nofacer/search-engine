package com.xubin.service;

import com.xubin.po.LinkWords;
import com.xubin.repository.LinkWordsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class LinkWordsService {

  private final LinkWordsRepository linkWordsRepository;


  public LinkWords saveLinkWords(long linkId, long wordId) {
    LinkWords newLinkWords = LinkWords.builder().linkId(linkId).wordId(wordId).build();
    return linkWordsRepository.save(newLinkWords);
  }
}
