package com.xubin.repository;

import com.xubin.po.Link;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

  Optional<Link> findById(Long id);

  Optional<Link> findByFromId(Long id);

  Optional<Link> findByToId(Long id);
}
