package pl.sda.eventlift.stakeholders.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.eventlift.stakeholders.model.Driver;
import pl.sda.eventlift.stakeholders.repositories.DriverRepository;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public Driver getDriverById(Long driverId){
        return driverRepository.findById(driverId).get();
    }
}
