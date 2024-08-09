package com.expohack.slm.commons.configuration.springdoc;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringdocConfiguration {

  private final String apiVersion = "v1";

  @Bean
  public GroupedOpenApi authOpenApiGroup() {
    String[] paths = {"/api/v1/auth/**"};
    return GroupedOpenApi.builder().group("gr01-authorization")
        .displayName("Авторизация")
        .addOpenApiCustomizer(openApi -> openApi
            .info(new Info().title("Authorization API").version(apiVersion)))
        .pathsToMatch(paths)
        .build();
  }

  @Bean
  public GroupedOpenApi usersOpenApiGroup() {
    String[] paths = {"/api/v1/users/**"};
    return GroupedOpenApi.builder().group("gr02-users")
        .displayName("Пользователи")
        .addOpenApiCustomizer(openApi -> openApi
            .info(new Info().title("Users API")
                .version(apiVersion)))
        .pathsToMatch(paths)
        .build();
  }

  @Bean
  public GroupedOpenApi dictionariesOpenApiGroup() {
    String[] paths = {"/api/v1/dictionaries/**"};
    return GroupedOpenApi.builder().group("gr03-dictionaries")
        .displayName("Справочники")
        .addOpenApiCustomizer(openApi -> openApi
            .info(new Info().title("Dictionaries API")
                .version(apiVersion)))
        .pathsToMatch(paths)
        .build();
  }

  @Bean
  public GroupedOpenApi companiesOpenApiGroup() {
    String[] paths = {"/api/v1/companies/**"};
    return GroupedOpenApi.builder().group("gr04-companies")
        .displayName("Компании")
        .addOpenApiCustomizer(openApi -> openApi
            .info(new Info().title("Companies API")
                .version(apiVersion)))
        .pathsToMatch(paths)
        .build();
  }

  @Bean
  public GroupedOpenApi xlsxUploadOpenApiGroup() {
    String[] paths = {"/api/upload-data/**"};
    return GroupedOpenApi.builder().group("gr04-xlsx")
        .displayName("Загрузка файла")
        .addOpenApiCustomizer(openApi -> openApi
            .info(new Info().title("XlsxUpload API")
                .version(apiVersion)))
        .pathsToMatch(paths)
        .build();
  }
}
