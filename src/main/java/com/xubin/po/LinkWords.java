package com.xubin.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "link_words")
public class LinkWords {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Long wordId;
  private Long linkId;
}
