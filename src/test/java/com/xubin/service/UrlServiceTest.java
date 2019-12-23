package com.xubin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import com.xubin.po.Url;
import com.xubin.repository.UrlRepository;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

  @Mock
  private UrlRepository urlRepository;

  @InjectMocks
  private UrlService urlService;


  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void shouldReturnTrueIfUrlExists() {
    Optional<Url> expectedUrl = Optional.ofNullable(Url.builder().url("www.url.com").build());
    given(urlRepository.findByUrl("www.url.com")).willReturn(expectedUrl);

    boolean isExist = urlService.ifUrlExist("www.url.com");

    assertTrue(isExist);
  }

  @Test
  void shouldReturnFalseIfUrlNotExists() {
    given(urlRepository.findByUrl("www.url.com")).willReturn(Optional.empty());

    boolean isExist = urlService.ifUrlExist("www.url.com");

    assertFalse(isExist);
  }

  @Test
  void shouldGetEntryIdIfExists() {
    Optional<Url> expectedUrl = Optional.ofNullable(
        Url.builder().id((long) 1).url("www.url.com").build()
    );
    given(urlRepository.findByUrl("www.url.com")).willReturn(expectedUrl);

    long entryId = urlService.getEntryId("www.url.com");

    assertEquals((long) 1, entryId);
  }

  @Test
  void shouldGetNewSavedEntryIdIfNotExists() {
    given(urlRepository.findByUrl("www.url.com")).willReturn(Optional.empty());
    Url newUrl = Url.builder().url("www.url.com").build();
    Url savedUrl = Url.builder().id((long) 2).url("www.url.com").build();
    given(urlRepository.save(newUrl)).willReturn(savedUrl);

    long entryId = urlService.getEntryId("www.url.com");

    assertEquals((long) 2, entryId);
  }
}
