package com.expohack.slm.authentication.model.dto;

import java.io.Serializable;
import java.util.UUID;

public record CompanyDto(UUID id, String name, String inn, String notificationAddress) implements
    Serializable {

}
