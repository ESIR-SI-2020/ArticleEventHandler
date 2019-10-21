package fr.esir.jxc.article.models;

import java.util.List;
import java.util.UUID;

public class Articles {

  public static final String ELASTIC_TYPE = "article";

  public static String generateUniqueId() {
    return UUID.randomUUID().toString();
  }

  public static Article updateTags(Article article, List<String> newTags) {
    return new Article(
      article.getId(),
      article.getUrl(),
      article.getOwner(),
      newTags,
      article.getSuggestedTags()
    );
  }

}
