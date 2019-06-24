package pl.sda.eventlift.api.service;

import pl.sda.eventlift.stakeholders.dto.DriverDTO;

import java.util.Set;

public interface RestService {

    Set<DriverDTO> getAllDriversWithActiveOffer();
}
