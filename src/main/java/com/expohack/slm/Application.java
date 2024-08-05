package com.expohack.slm;

import com.expohack.slm.configuration.SlmProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableConfigurationProperties(SlmProperties.class)
@EnableMethodSecurity
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
