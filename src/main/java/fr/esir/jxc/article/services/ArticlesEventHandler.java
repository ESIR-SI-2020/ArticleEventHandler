package fr.esir.jxc.article.services;

import org.springframework.stereotype.Service;

import fr.esir.jxc.article.events.ArticleAdded;
import fr.esir.jxc.article.events.ArticleDeleted;
import fr.esir.jxc.article.events.ArticleShared;
import fr.esir.jxc.article.events.ArticleTagsSet;

@Service
public class ArticlesEventHandler {

  public void handleArticleAddedEvent(ArticleAdded articleAdded) {
    // TODO
  }

  public void handleArticleDeletedEvent(ArticleDeleted articleDeleted) {
    // TODO
  }

  public void handleArticleSharedEvent(ArticleShared articleShared) {
    // TODO
  }

  public void handleArticleTagSetEvent(ArticleTagsSet articleTagsSet) {
    // TODO
  }

}
