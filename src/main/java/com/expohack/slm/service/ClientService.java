package com.expohack.slm.service;

import com.expohack.slm.model.Client;
import com.expohack.slm.repository.ClientRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService {

  private final ClientRepository clientRepository;

  public Optional<Client> getByMobilePhone(String mobilePhone) {
    log.info("Retrieving client with mobile phone {}", mobilePhone);
    return clientRepository.findByMobilePhone(mobilePhone);
  }

  public Optional<Client> getByEmail(String email) {
    log.info("Retrieving client with email {}", email);
    return clientRepository.findByEmail(email);
  }

  public Optional<Client> getByDriverLicenseSeriesAndDriverLicenseNumber(
      String driverLicenseSeries,
      String driverLicenseNumber
  ) {
    log.info(
        "Retrieving client with email driver license series {} and driver license number {}",
        driverLicenseSeries,
        driverLicenseNumber
    );
    return clientRepository.findByDriverLicenseSeriesAndDriverLicenseNumber(
        driverLicenseSeries,
        driverLicenseNumber
    );
  }

  public Optional<Client> getByPassportSeriesAndPassportNumber(
      String passportSeries,
      String passportNumber
  ) {
    log.info(
        "Retrieving client with email passport series {} and passport number {}",
        passportSeries,
        passportNumber
    );
    return clientRepository.findByPassportSeriesAndPassportNumber(
        passportSeries,
        passportNumber
    );
  }
}
