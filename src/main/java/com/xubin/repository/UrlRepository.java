package com.xubin.repository;

import com.xubin.po.Url;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UrlRepository extends JpaRepository<Url, Long> {

  Optional<Url> findById(Long id);

  Optional<Url> findByUrl(String url);
}
