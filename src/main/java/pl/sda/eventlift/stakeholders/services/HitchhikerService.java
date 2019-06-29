package pl.sda.eventlift.stakeholders.services;

import org.springframework.stereotype.Service;
import pl.sda.eventlift.events.repositories.EventRepository;
import pl.sda.eventlift.stakeholders.model.Driver;
import pl.sda.eventlift.stakeholders.model.Hitchhiker;
import pl.sda.eventlift.stakeholders.repositories.HitchhikerRepository;

@Service
public class HitchhikerService {

    private HitchhikerRepository hitchhikerRepository;
    private EventRepository eventRepository;

    public HitchhikerService(HitchhikerRepository hitchhikerRepository, EventRepository eventRepository) {
        this.hitchhikerRepository = hitchhikerRepository;
        this.eventRepository = eventRepository;
    }

    public Driver findHitchhikerDriverByEvent(String eventDtoUuid, Long hitchhikerId) {
        if (hitchhikerRepository.findById(hitchhikerId).isPresent()) {
            Hitchhiker hitchhiker = hitchhikerRepository.findById(hitchhikerId).get();
            return hitchhiker.getDrivers().stream()
                    .filter(driver -> driver.getEvents().contains(eventRepository.findByUuid(eventDtoUuid)))
                    .findFirst()
                    .orElseGet(() -> Driver.builder().build());
        }
        return null;
    }
}
