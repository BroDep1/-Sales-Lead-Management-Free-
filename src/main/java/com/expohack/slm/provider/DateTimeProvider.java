package com.expohack.slm.provider;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public interface DateTimeProvider {

  OffsetDateTime offsetDateTimeNowTruncatedToMillis();

  LocalDateTime localDateTimeNowTruncatedToMillis();
}
