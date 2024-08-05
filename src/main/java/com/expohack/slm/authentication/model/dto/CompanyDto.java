package com.expohack.slm.authentication.model.dto;

import java.io.Serializable;
import java.util.UUID;

public record CompanyDto(UUID id, String name) implements Serializable {

}
