package pl.sda.eventlift.stakeholders.services;

import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.eventlift.stakeholders.model.Driver;
import pl.sda.eventlift.stakeholders.repositories.DriverRepository;

@Service
public class DriverService {

    private DriverRepository driverRepository;

    @Autowired
    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public Driver getDriverById(Long driverId) throws ObjectNotFoundException {
        return driverRepository.findById(driverId)
                .orElseThrow(
                        () -> new ObjectNotFoundException("Driver " + driverId + " not found")
                );
    }
}
