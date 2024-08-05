package com.expohack.slm.provider;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Component;

@Component
public class DateTimeProviderImpl implements DateTimeProvider {

  public OffsetDateTime offsetDateTimeNowTruncatedToMillis() {
    return OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS);
  }

  @Override
  public LocalDateTime localDateTimeNowTruncatedToMillis() {
    return LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
  }
}
