package com.expohack.slm.repository;

import com.expohack.slm.model.Error;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorRepository extends JpaRepository<Error, UUID> {

}
