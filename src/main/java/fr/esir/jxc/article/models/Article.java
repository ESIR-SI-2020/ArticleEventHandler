package fr.esir.jxc.article.models;

import java.util.List;

import lombok.Value;
import org.springframework.data.elasticsearch.annotations.Document;

@Value
@Document(indexName = "pocket", type = "article")
public class Article {

  private final String id;
  private final String url;
  private final List<String> tags;
  private final List<String> suggestedTags;

}
