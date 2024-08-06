package com.expohack.slm.matching.repository;

import com.expohack.slm.matching.model.Client;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, UUID> {

  Optional<Client> findByMobilePhone(String mobilePhone);

  Optional<Client> findByEmail(String email);

  Optional<Client> findByPassportSeriesAndPassportNumber(String passportSeries, String passportNumber);

  Optional<Client> findByDriverLicenseSeriesAndDriverLicenseNumber(String driverLicenseSeries, String driverLicenseNumber);

}
