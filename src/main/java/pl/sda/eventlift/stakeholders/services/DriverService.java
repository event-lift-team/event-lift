package pl.sda.eventlift.stakeholders.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.eventlift.events.repositories.EventRepository;
import pl.sda.eventlift.stakeholders.model.Driver;
import pl.sda.eventlift.stakeholders.model.Hitchhiker;
import pl.sda.eventlift.stakeholders.repositories.DriverRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DriverService {

    private DriverRepository driverRepository;
    private EventRepository eventRepository;

    @Autowired
    public DriverService(DriverRepository driverRepository, EventRepository eventRepository) {
        this.driverRepository = driverRepository;
        this.eventRepository = eventRepository;
    }

    public Driver getDriverById(Long driverId) {
        return driverRepository.findById(driverId).orElseThrow(() -> new RuntimeException("driver not found"));
    }

    public Set<Hitchhiker> getDriverEventHitchhikers(Long driverId, String eventDtoId) {
        return getDriverById(driverId).getHitchhikers().stream()
                .filter(hitchhiker -> hitchhiker.getEvents().contains(eventRepository.findByUuid(eventDtoId)))
                .collect(Collectors.toSet());
    }

}
