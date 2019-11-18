# ArticleEventHandler

This component is part of a bigger Event-Driven architecture composed of CQRS components.

Handles events related to the articles, namely ARTICLE_ADDED, ARTICLE_DELETED, ARTICLE_SHARED, ARTICLE_TAG_SET.
These events are listened from a Kafka instance, which configuration can be found in `src/main/resources/application.yml`. 
From these events this component update the current state of the data in an elasticsearch DB which configuration can be found in the aforementioned file.

## Start

From this project root folder
1. `./scripts/start-third-parties.sh`
2. `mvn spring-boot:run`

## Test

You can test the component by pushing events to Kafka and watching the result in Elastisearch.
To do so, follow the start section and then:
1. `./scripts/kafka-producer.sh`
2. Push events from the JSON files in `src/test/resources`. Be careful, first transform these into one liners; second update the id of the article in all events except ARTICLE_ADDED.
