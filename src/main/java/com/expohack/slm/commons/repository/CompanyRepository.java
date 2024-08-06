package com.expohack.slm.commons.repository;

import com.expohack.slm.commons.model.Company;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

  List<Company> findAllByOrderByName();
}
