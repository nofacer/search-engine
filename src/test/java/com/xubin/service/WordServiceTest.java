package com.xubin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import com.xubin.po.Word;
import com.xubin.repository.WordRepository;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WordServiceTest {

  @Mock
  private WordRepository wordRepository;

  @InjectMocks
  private WordService wordService;


  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void shouldReturnTrueIfWordExists() {
    Optional<Word> expectedWord = Optional.ofNullable(Word.builder().word("hello").build());
    given(wordRepository.findByWord("hello")).willReturn(expectedWord);

    boolean isExist = wordService.ifWordExist("hello");

    assertTrue(isExist);
  }

  @Test
  void shouldReturnFalseIfWordNotExists() {
    given(wordRepository.findByWord("hello")).willReturn(Optional.empty());

    boolean isExist = wordService.ifWordExist("hello");

    assertFalse(isExist);
  }

  @Test
  void shouldGetEntryIdIfExists() {
    Optional<Word> expectedWord = Optional.ofNullable(Word.builder().id((long) 1).word("hello").build());
    given(wordRepository.findByWord("hello")).willReturn(expectedWord);

    long entryId = wordService.getWordId("hello");

    assertEquals(1, entryId);
  }

  @Test
  void shouldGetNewSavedEntryIdIfNotExists() {
    given(wordRepository.findByWord("hello")).willReturn(Optional.empty());
    Word newWord = Word.builder().word("hello").build();
    Word savedWord = Word.builder().id((long) 2).word("www.url.com").build();
    given(wordRepository.save(newWord)).willReturn(savedWord);

    long entryId = wordService.getWordId("hello");

    assertEquals(2, entryId);
  }
}
