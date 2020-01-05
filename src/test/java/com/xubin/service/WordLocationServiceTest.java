package com.xubin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import com.xubin.po.WordLocation;
import com.xubin.repository.WordLocationRepository;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WordLocationServiceTest {

  @Mock
  private WordLocationRepository wordLocationRepository;

  @InjectMocks
  private WordLocationService wordLocationService;


  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void shouldReturnTrueIfUrlExists() {
    Optional<WordLocation> expectedUrl = Optional
        .ofNullable(WordLocation.builder().urlId((long) 1).wordId((long) 2).build());
    given(wordLocationRepository.findTopByUrlId((long) 1)).willReturn(expectedUrl);

    boolean isExist = wordLocationService.ifUrlExist(1);

    assertTrue(isExist);
  }

  @Test
  void shouldReturnFalseIfUrlNotExists() {
    given(wordLocationRepository.findTopByUrlId((long) 1)).willReturn(Optional.empty());

    boolean isExist = wordLocationService.ifUrlExist(1);

    assertFalse(isExist);
  }

  @Test
  void shouldSaveNewWordLocation() {
    WordLocation newWordLocation = WordLocation.builder().wordId((long) 1).urlId((long) 1)
        .location(2).build();
    WordLocation savedWordLocation = WordLocation.builder().id((long) 1).wordId((long) 1)
        .urlId((long) 1).location(2).build();
    given(wordLocationRepository.save(newWordLocation)).willReturn(savedWordLocation);

    WordLocation actualWordLocation = wordLocationService.save(1, 1, 2);
    assertEquals(savedWordLocation, actualWordLocation);
  }
}
