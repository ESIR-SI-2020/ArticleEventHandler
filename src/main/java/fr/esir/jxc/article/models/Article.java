package fr.esir.jxc.article.models;

import java.util.List;

import lombok.Value;

@Value
public class Article {

  private final String id;
  private final String url;
  private final String owner;
  private final List<String> tags;
  private final List<String> suggestedTags;

}
