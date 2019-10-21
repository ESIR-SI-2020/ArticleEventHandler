package fr.esir.jxc.article.services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.esir.jxc.article.events.ArticleAdded;
import fr.esir.jxc.article.events.ArticleDeleted;
import fr.esir.jxc.article.events.ArticleShared;
import fr.esir.jxc.article.events.ArticleTagsSet;
import fr.esir.jxc.article.models.Article;
import fr.esir.jxc.article.models.Articles;

@Service
public class ArticlesEventHandler {

  private final ArticlesReadService articlesReadService;
  private final ArticlesWriteService articlesWriteService;

  public ArticlesEventHandler(
    @Autowired ArticlesReadService articlesReadService,
    @Autowired ArticlesWriteService articlesWriteService
  ) {
    this.articlesReadService = articlesReadService;
    this.articlesWriteService = articlesWriteService;
  }

  public void handleArticleAddedEvent(ArticleAdded articleAdded) {
    final Article newArticle = new Article(
      Articles.generateUniqueId(),
      articleAdded.getArticleUrl(),
      articleAdded.getEmail(),
      Collections.emptyList(),
      Collections.emptyList()
    );

    this.articlesWriteService.save(newArticle);
  }

  public void handleArticleDeletedEvent(ArticleDeleted articleDeleted) {
    this.articlesWriteService.delete(articleDeleted.getArticleId());
  }

  public void handleArticleSharedEvent(ArticleShared articleShared) {
    this.articlesReadService.getArticleById(articleShared.getArticleId())
      .ifPresent(sourceArticle -> {
        final Article sharedVersion = new Article(
          Articles.generateUniqueId(),
          sourceArticle.getUrl(),
          articleShared.getTargetEmail(),
          sourceArticle.getTags(),
          sourceArticle.getSuggestedTags()
        );

        this.articlesWriteService.save(sharedVersion);
      });
  }

  public void handleArticleTagSetEvent(ArticleTagsSet articleTagsSet) {
    this.articlesReadService.getArticleById(articleTagsSet.getArticleId())
      .ifPresent(article ->
        this.articlesWriteService.updateTags(article, articleTagsSet.getTags())
      );
  }

}
