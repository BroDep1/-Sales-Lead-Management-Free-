package com.expohack.slm.matching.repository;

import com.expohack.slm.matching.model.Error;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorRepository extends JpaRepository<Error, UUID> {

}
