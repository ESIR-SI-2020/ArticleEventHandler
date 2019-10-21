package fr.esir.jxc.article.events;

import java.util.List;

import lombok.Value;

@Value
public class ArticleTagsSet {

  private final String email;
  private final String articleId;
  private final List<String> tags;

}
