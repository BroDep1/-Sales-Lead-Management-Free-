package com.expohack.slm.authentication.model.dto;

import java.util.UUID;

public record AuthenticatedUser(UUID id,
                                CompanyDto company,
                                String username,
                                String name,
                                boolean admin,
                                boolean manager,
                                boolean employee) {

}
