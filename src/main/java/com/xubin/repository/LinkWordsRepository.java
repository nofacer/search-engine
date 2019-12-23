package com.xubin.repository;

import com.xubin.po.LinkWords;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkWordsRepository extends JpaRepository<LinkWords, Long> {

  Optional<LinkWords> findById(Long id);

  Optional<LinkWords> findByWordId(Long id);

  Optional<LinkWords> findByLinkId(Long id);
}
