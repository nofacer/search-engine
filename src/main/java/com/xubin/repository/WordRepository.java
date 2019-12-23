package com.xubin.repository;

import com.xubin.po.Word;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface WordRepository extends JpaRepository<Word, Long> {

  Optional<Word> findById(Long id);

  Optional<Word> findByWord(String word);
}
