package com.xubin.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.xubin.po.Word;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class WordRepositoryTest {

  @Autowired
  private TestEntityManager testEntityManager;

  @Autowired
  private WordRepository wordRepository;

  @BeforeEach
  void setUp() {
    Word word = Word.builder().word("apple").build();
    testEntityManager.persist(word);
  }

  @AfterEach
  void tearDown() {
    testEntityManager.clear();
  }

  @Test
  void shouldFindById() {
    long targetWordId = 1;
    Optional word = wordRepository.findById(targetWordId);
    assertTrue(word.isPresent());
  }

  @Test
  void shouldNotFindIfNotExisting() {
    long targetWordId = 2;
    Optional word = wordRepository.findById(targetWordId);
    assertFalse(word.isPresent());
  }

  @Test
  void shouldFindByUrl() {
    String targetWord = "apple";
    Optional word = wordRepository.findByWord(targetWord);
    assertTrue(word.isPresent());
  }

  @Test
  void shouldNotFindByUrlIfNotExisting() {
    String targetWord = "banana";
    Optional word = wordRepository.findByWord(targetWord);
    assertFalse(word.isPresent());
  }

  @Test
  void shouldSaveUrl() {
    Word newWord = Word.builder().word("banana").build();
    wordRepository.save(newWord);
    Optional newAddedWord = wordRepository.findByWord("banana");
    assertTrue(newAddedWord.isPresent());
  }
}
