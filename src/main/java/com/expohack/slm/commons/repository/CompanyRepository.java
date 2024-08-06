package com.expohack.slm.commons.repository;

import com.expohack.slm.commons.model.Company;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

}
