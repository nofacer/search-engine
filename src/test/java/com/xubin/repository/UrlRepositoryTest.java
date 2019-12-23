package com.xubin.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.xubin.po.Url;
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
class UrlRepositoryTest {

  @Autowired
  private TestEntityManager testEntityManager;

  @Autowired
  private UrlRepository urlRepository;

  @BeforeEach
  void setUp() {
    Url url = Url.builder().url("www.url1.com").build();
    testEntityManager.persist(url);
  }

  @AfterEach
  void tearDown() {
    testEntityManager.clear();
  }

  @Test
  void shouldFindById() {
    long targetUrlId = 1;
    Optional url = urlRepository.findById(targetUrlId);
    assertTrue(url.isPresent());
  }

  @Test
  void shouldNotFindIfNotExisting() {
    long targetUrlId = 2;
    Optional url = urlRepository.findById(targetUrlId);
    assertFalse(url.isPresent());
  }

  @Test
  void shouldFindByUrl() {
    String targetUrl = "www.url1.com";
    Optional url = urlRepository.findByUrl(targetUrl);
    assertTrue(url.isPresent());
  }

  @Test
  void shouldNotFindByUrlIfNotExisting() {
    String targetUrl = "www.url2.com";
    Optional url = urlRepository.findByUrl(targetUrl);
    assertFalse(url.isPresent());
  }

  @Test
  void shouldSaveUrl() {
    Url newUrl =  Url.builder().url("www.url2.com").build();
    urlRepository.save(newUrl);
    Optional newAddedLink = urlRepository.findByUrl("www.url2.com");
    assertTrue(newAddedLink.isPresent());
  }
}
