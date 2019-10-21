package fr.esir.jxc.article.events;

import lombok.Value;

@Value
public class ArticleDeleted {

  private final String email;
  private final String articleId;

}
