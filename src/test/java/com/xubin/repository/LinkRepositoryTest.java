package com.xubin.repository;

import static org.assertj.core.api.Assertions.assertThat;

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
    System.out.println("667666666");
    Link link = Link.builder().fromId((long) 1).toId((long) 2).build();
    testEntityManager.persist(link);
  }

  @AfterEach
  public void tearDown() {
    testEntityManager.clear();
  }

  @Test
  void shouldFindById() {
    long targetLinkId =  1;
    Link link = linkRepository.getOne(targetLinkId);
    System.out.println("=================");
    System.out.println(link);
    assertThat(Optional.ofNullable(link));
  }
}
