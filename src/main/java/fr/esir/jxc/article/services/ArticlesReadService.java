package fr.esir.jxc.article.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import fr.esir.jxc.domain.models.Article;
import fr.esir.jxc.article.utils.ElasticSearchUtils;

@Service
public class ArticlesReadService {

  private final ElasticsearchOperations elasticsearchOperations;

  public ArticlesReadService(@Autowired ElasticsearchOperations elasticsearchOperations) {
    this.elasticsearchOperations = elasticsearchOperations;
  }

  public Optional<Article> getArticleById(String id) {
    return Optional.ofNullable(
      this.elasticsearchOperations.queryForObject(ElasticSearchUtils.getQuery(id), Article.class)
    );
  }


}
