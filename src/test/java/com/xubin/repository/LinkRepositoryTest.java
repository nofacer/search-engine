package com.xubin.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.xubin.po.Link;
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
class LinkRepositoryTest {

  @Autowired
  private TestEntityManager testEntityManager;

  @Autowired
  private LinkRepository linkRepository;

  @BeforeEach
  public void setUp() {
    Link link = Link.builder().fromId((long) 1).toId((long) 2).build();
    testEntityManager.persist(link);
  }

  @AfterEach
  public void tearDown() {
    testEntityManager.clear();
  }

  @Test
  void shouldFindById() {
    long targetLinkId = 1;
    Optional<Link> link = linkRepository.findById(targetLinkId);
    assertTrue(link.isPresent());
  }

  @Test
  void shouldNotFindIfNotExisting() {
    long targetLinkId = 2;
    Optional<Link> link = linkRepository.findById(targetLinkId);
    assertFalse(link.isPresent());
  }

  @Test
  void shouldFindByFromId() {
    long targetFromLinkId = 1;
    Optional<Link> link = linkRepository.findByFromId(targetFromLinkId);
    assertTrue(link.isPresent());
  }

  @Test
  void shouldNotFindByFromIdIfNotExisting() {
    long targetFromLinkId = 2;
    Optional<Link> link = linkRepository.findByFromId(targetFromLinkId);
    assertFalse(link.isPresent());
  }

  @Test
  void shouldFindByToId() {
    long targetToLinkId = 2;
    Optional<Link> link = linkRepository.findByToId(targetToLinkId);
    assertTrue(link.isPresent());
  }

  @Test
  void shouldNotFindByToIdIfNotExisting() {
    long targetToLinkId = 1;
    Optional<Link> link = linkRepository.findByToId(targetToLinkId);
    assertFalse(link.isPresent());
  }

  @Test
  void shouldSaveLink() {
    Link newLink = Link.builder().fromId((long) 3).toId((long) 4).build();
    linkRepository.save(newLink);
    Optional<Link> newAddedLink = linkRepository.findByFromId((long) 3);
    assertTrue(newAddedLink.isPresent());

  }
}
