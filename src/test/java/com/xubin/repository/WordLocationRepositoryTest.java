package com.xubin.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.xubin.po.WordLocation;
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
class WordLocationRepositoryTest {

  @Autowired
  private TestEntityManager testEntityManager;

  @Autowired
  private WordLocationRepository wordLocationRepository;

  @BeforeEach
  public void setUp() {
    WordLocation wordLocation = WordLocation.builder().wordId((long) 1).urlId((long) 2).build();
    testEntityManager.persist(wordLocation);
  }

  @AfterEach
  public void tearDown() {
    testEntityManager.clear();
  }

  @Test
  void shouldFindById() {
    long targetWordLocationId = 1;
    Optional wordlocation = wordLocationRepository.findById(targetWordLocationId);
    assertTrue(wordlocation.isPresent());
  }

  @Test
  void shouldNotFindIfNotExisting() {
    long targetWordLocationId = 2;
    Optional wordlocation = wordLocationRepository.findById(targetWordLocationId);
    assertFalse(wordlocation.isPresent());
  }

  @Test
  void shouldFindByFromId() {
    long targetWordId = 1;
    Optional wordlocation = wordLocationRepository.findByWordId(targetWordId);
    assertTrue(wordlocation.isPresent());
  }

  @Test
  void shouldNotFindByFromIdIfNotExisting() {
    long targetWordId = 2;
    Optional wordlocation = wordLocationRepository.findByWordId(targetWordId);
    assertFalse(wordlocation.isPresent());
  }

  @Test
  void shouldFindByToId() {
    long targetUrlId = 2;
    Optional wordlocation = wordLocationRepository.findTopByUrlId(targetUrlId);
    assertTrue(wordlocation.isPresent());
  }

  @Test
  void shouldNotFindByToIdIfNotExisting() {
    long targetUrlId = 1;
    Optional wordlocation = wordLocationRepository.findTopByUrlId(targetUrlId);
    assertFalse(wordlocation.isPresent());
  }

  @Test
  void shouldSaveLink() {
    WordLocation newWordLocation = WordLocation.builder().wordId((long) 3).urlId((long) 4).build();
    wordLocationRepository.save(newWordLocation);
    Optional newAddedWordLocation = wordLocationRepository.findById((long) 2);
    assertTrue(newAddedWordLocation.isPresent());
  }
}
