package com.xubin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import com.xubin.po.Link;
import com.xubin.repository.LinkRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LinkServiceTest {

  @Mock
  private LinkRepository linkRepository;

  @InjectMocks
  private LinkService linkService;


  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void shouldSaveNewLink() {
    Link newLink = Link.builder().fromId((long) 1).toId((long) 2).build();
    Link savedLink = Link.builder().id((long) 1).fromId((long) 1).toId((long) 2).build();
    given(linkRepository.save(newLink)).willReturn(savedLink);

    Link actualLink = linkService.saveLink(1, 2);
    assertEquals(savedLink, actualLink);
  }
}
