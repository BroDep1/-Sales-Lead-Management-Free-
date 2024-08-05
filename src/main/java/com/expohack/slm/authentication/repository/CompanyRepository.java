package com.expohack.slm.authentication.repository;

import com.expohack.slm.authentication.model.entity.Company;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.Repository;

public interface CompanyRepository extends Repository<Company, UUID> {

  Optional<Company> findById(UUID id);

  List<Company> findAll();
}
