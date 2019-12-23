package com.xubin.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.xubin.po.LinkWords;
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
class LinkWordRepositoryTest {

  @Autowired
  private TestEntityManager testEntityManager;

  @Autowired
  private LinkWordsRepository linkWordsRepository;

  @BeforeEach
  public void setUp() {
    LinkWords linkWords = LinkWords.builder().wordId((long) 1).linkId((long) 2).build();
    testEntityManager.persist(linkWords);
  }

  @AfterEach
  public void tearDown() {
    testEntityManager.clear();
  }

  @Test
  void shouldFindById() {
    long targetLinkWordsId = 1;
    Optional linkWords = linkWordsRepository.findById(targetLinkWordsId);
    assertTrue(linkWords.isPresent());
  }

  @Test
  void shouldNotFindIfNotExisting() {
    long targetLinkWordsId = 2;
    Optional linkWords = linkWordsRepository.findById(targetLinkWordsId);
    assertFalse(linkWords.isPresent());
  }

  @Test
  void shouldFindByFromId() {
    long targetWordId = 1;
    Optional linkWords = linkWordsRepository.findByWordId(targetWordId);
    assertTrue(linkWords.isPresent());
  }

  @Test
  void shouldNotFindByFromIdIfNotExisting() {
    long targetWordId = 2;
    Optional linkWords = linkWordsRepository.findByWordId(targetWordId);
    assertFalse(linkWords.isPresent());
  }

  @Test
  void shouldFindByToId() {
    long targetLinkId = 2;
    Optional linkWords = linkWordsRepository.findByLinkId(targetLinkId);
    assertTrue(linkWords.isPresent());
  }

  @Test
  void shouldNotFindByToIdIfNotExisting() {
    long targetLinkId = 1;
    Optional linkWords = linkWordsRepository.findByLinkId(targetLinkId);
    assertFalse(linkWords.isPresent());
  }

  @Test
  void shouldSaveLink() {
    LinkWords newLinkWords = LinkWords.builder().wordId((long) 3).linkId((long) 4).build();
    linkWordsRepository.save(newLinkWords);
    Optional newAddedLinkWords = linkWordsRepository.findById((long) 2);
    assertTrue(newAddedLinkWords.isPresent());
  }
}
