package com.expohack.slm.commons.provider;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public interface DateTimeProvider {

  OffsetDateTime offsetDateTimeNowTruncatedToMillis();

  LocalDateTime localDateTimeNowTruncatedToMillis();
}
