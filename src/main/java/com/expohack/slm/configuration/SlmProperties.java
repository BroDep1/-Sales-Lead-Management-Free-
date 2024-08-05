package com.expohack.slm.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "slm")
public record SlmProperties(RememberMe rememberMe) {

  public static final String TWO_WEEKS_S = "1209600";

  public record RememberMe(String key,
                           @DefaultValue(TWO_WEEKS_S) int tokenValiditySeconds,
                           @DefaultValue("true") boolean useSecureCookie) {

  }
}
