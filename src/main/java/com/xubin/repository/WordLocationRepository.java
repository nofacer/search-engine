package com.xubin.repository;

import com.xubin.po.WordLocation;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordLocationRepository extends JpaRepository<WordLocation, Long> {

  Optional<WordLocation> findById(Long id);

  Optional<WordLocation> findTopByUrlId(Long id);

  Optional<WordLocation> findByWordId(Long id);
}
