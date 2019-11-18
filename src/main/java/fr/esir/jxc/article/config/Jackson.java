package fr.esir.jxc.article.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Jackson {

  public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
    .registerModule(new ParameterNamesModule())
    .registerModule(new Jdk8Module())
    .registerModule(new JavaTimeModule())
    .setSerializationInclusion(JsonInclude.Include.NON_NULL);

  @Bean
  public ObjectMapper objectMapper() {
    return OBJECT_MAPPER;
  }

}
