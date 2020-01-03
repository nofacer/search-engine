package com.xubin.service;

import com.xubin.po.Link;
import com.xubin.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class LinkService {

  private final LinkRepository linkRepository;


  public Link saveLink(long fromId, long toId) {
    Link newLink = Link.builder().fromId(fromId).toId(toId).build();
    return linkRepository.save(newLink);
  }
}
