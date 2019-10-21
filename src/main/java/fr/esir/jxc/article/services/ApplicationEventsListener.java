package fr.esir.jxc.article.services;

import java.io.IOException;
import java.util.function.Consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Service;

import fr.esir.jxc.article.events.ArticleAdded;
import fr.esir.jxc.article.events.ArticleDeleted;
import fr.esir.jxc.article.events.ArticleShared;
import fr.esir.jxc.article.events.ArticleTagsSet;
import fr.esir.jxc.article.events.Events;
import fr.esir.jxc.domain.events.Event;

@Service
@Log4j2
public class ApplicationEventsListener implements MessageListener<String, Event> {

  private final ObjectMapper objectMapper;
  private final ArticlesEventHandler eventsHandler;

  public ApplicationEventsListener(
    @Autowired ObjectMapper objectMapper,
    @Autowired ArticlesEventHandler eventsHandler
  ) {
    this.objectMapper = objectMapper;
    this.eventsHandler = eventsHandler;
  }

  @Override
  public void onMessage(ConsumerRecord<String, Event> data) {
    routeEvent(data.value());
  }

  private void routeEvent(Event event) {
    switch(event.getType()) {
      case Events.ARTICLE_ADDED:
        handleEvent(event, ArticleAdded.class, this.eventsHandler::handleArticleAddedEvent);
        break;
      case Events.ARTICLE_DELETED:
        handleEvent(event, ArticleDeleted.class, this.eventsHandler::handleArticleDeletedEvent);
        break;
      case Events.ARTICLE_SHARED:
        handleEvent(event, ArticleShared.class, this.eventsHandler::handleArticleSharedEvent);
        break;
      case Events.ARTICLE_TAG_SET:
        handleEvent(event, ArticleTagsSet.class, this.eventsHandler::handleArticleTagSetEvent);
        break;
      default:
        log.warn("Received an unknown event: " + event.toString());
        break;
    }
  }

  private <T> void handleEvent(Event event, Class<T> tClass, Consumer<T> handler) {
    try {
      final T parsedBody = this.objectMapper.treeToValue(event.getMetadata(), tClass);
      handler.accept(parsedBody);
    } catch (IOException e) {
      log.error("Event received with incorrect metadata. Was: " + event.toString() + ". Exception message is: " + e.getMessage(), e);
    }
  }

}
