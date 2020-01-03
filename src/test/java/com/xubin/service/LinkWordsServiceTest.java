package com.xubin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import com.xubin.po.LinkWords;
import com.xubin.repository.LinkWordsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LinkWordsServiceTest {

  @Mock
  private LinkWordsRepository linkWordsRepository;

  @InjectMocks
  private LinkWordsService linkWordsService;


  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void shouldSaveNewLink() {
    LinkWords newLinkWords = LinkWords.builder().linkId((long) 1).wordId((long) 2).build();
    LinkWords savedLinkWords = LinkWords.builder().id((long) 1).linkId((long) 1).wordId((long) 2)
        .build();
    given(linkWordsRepository.save(newLinkWords)).willReturn(savedLinkWords);

    LinkWords actualLink = linkWordsService.saveLinkWords(1, 2);
    assertEquals(savedLinkWords, actualLink);
  }
}
