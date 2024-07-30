package com.expohack.slm.configuration;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringdocConfiguration {

  private final String apiVersion = "v1";

  @Bean
  public GroupedOpenApi dictionariesOpenApiGroup() {
    String[] paths = {"/api/v1/dictionaries/**"};
    return GroupedOpenApi.builder().group("gr01-dictionaries")
        .displayName("Справочники")
        .addOpenApiCustomizer(openApi -> openApi
            .info(new Info().title("Dictionaries API")
                .version(apiVersion)))
        .pathsToMatch(paths)
        .build();
  }
}
