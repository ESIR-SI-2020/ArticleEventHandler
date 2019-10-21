package fr.esir.jxc.article.services;

import java.util.List;

import lombok.extern.log4j.Log4j2;

import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.stereotype.Service;

import fr.esir.jxc.article.models.Article;
import fr.esir.jxc.article.models.Articles;

@Log4j2
@Service
public class ArticlesWriteService {

  private final ElasticsearchOperations elasticsearchOperations;

  @Value("${elasticsearch.indexName}") private String indexName;

  public ArticlesWriteService(@Autowired ElasticsearchOperations elasticsearchOperations) {
    this.elasticsearchOperations = elasticsearchOperations;
  }

  public void save(Article article) {
    final IndexQuery query = new IndexQueryBuilder()
      .withId(article.getId())
      .withIndexName(indexName)
      .withObject(article)
      .withType(Articles.ELASTIC_TYPE)
      .build();

    this.elasticsearchOperations.index(query);
  }

  public void delete(String articleId) {
    this.elasticsearchOperations.delete(indexName, Articles.ELASTIC_TYPE, articleId);
  }

  public void updateTags(Article article, List<String> tags) {
    final UpdateRequest updateRequest = new UpdateRequest(indexName, Articles.ELASTIC_TYPE, article.getId())
      .doc(Articles.updateTags(article, tags));

    final UpdateQuery query = new UpdateQueryBuilder()
      .withId(article.getId())
      .withIndexName(indexName)
      .withType(Articles.ELASTIC_TYPE)
      .withUpdateRequest(updateRequest)
      .withDoUpsert(false)
      .build();

      final UpdateResponse response = this.elasticsearchOperations.update(query);
      this.handleResponse(response);
  }

  private void handleResponse(UpdateResponse response) {
    if (response.status().getStatus() >= 400) {
      log.warn("Something went wrong while trying to update document of type "
        + response.getType() + " with id " + response.getId()
        + " - Original response is: " + response.toString()
      );
    }
  }

}
