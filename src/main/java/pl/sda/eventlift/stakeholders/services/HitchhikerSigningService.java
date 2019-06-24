package pl.sda.eventlift.stakeholders.services;

import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;
import pl.sda.eventlift.events.model.Event;
import pl.sda.eventlift.events.pojo.EventDTO;
import pl.sda.eventlift.events.repositories.EventRepository;
import pl.sda.eventlift.events.services.EventsService;
import pl.sda.eventlift.stakeholders.model.Hitchhiker;
import pl.sda.eventlift.stakeholders.model.Stakeholder;
import pl.sda.eventlift.stakeholders.repositories.DriverRepository;
import pl.sda.eventlift.stakeholders.repositories.HitchhikerRepository;
import pl.sda.eventlift.stakeholders.repositories.StakeholderRepository;

import java.util.Set;

@Service
public class HitchhikerSigningService {

    private EventRepository eventRepository;
    private EventsService eventsService;
    private DriverRepository driverRepository;
    private StakeholderRepository stakeholderRepository;
    private HitchhikerRepository hitchhikerRepository;

    public HitchhikerSigningService(EventRepository eventRepository,
                                    EventsService eventsService,
                                    DriverRepository driverRepository,
                                    StakeholderRepository stakeholderRepository,
                                    HitchhikerRepository hitchhikerRepository) {
        this.eventRepository = eventRepository;
        this.eventsService = eventsService;
        this.driverRepository = driverRepository;
        this.stakeholderRepository = stakeholderRepository;
        this.hitchhikerRepository = hitchhikerRepository;
    }

    public String relateHitchhikerWithEvent(String hitchhikerEmail, String eventDtoId) {
        if (driverRepository.existsByEmail(hitchhikerEmail)
                && driverRepository.findByEmail(hitchhikerEmail).getEvents().contains(eventRepository.findByUuid(eventDtoId))) {
            return "signedAsDriver";
        }
        if (hitchhikerRepository.existsByEmail(hitchhikerEmail) &&
                hitchhikerRepository.findByEmail(hitchhikerEmail).getEvents().contains(eventRepository.findByUuid(eventDtoId))) {
            return "signedAsHitch-hiker";
        }
        Hitchhiker hitchhiker = createHitchhikerAndStakeholderDbRelation(hitchhikerEmail);
        if (eventRepository.findByUuid(eventDtoId) == null) {
            EventDTO eventDtoById = eventsService.findEventDtoById(eventDtoId);
            Event event = eventsService.eventToEntity(eventDtoById);
            eventRepository.save(event);
            createHitchhikerAndEventDbRelation(hitchhiker, event);
            return "ok";
        } else {
            Event event = eventRepository.findByUuid(eventDtoId);
            createHitchhikerAndEventDbRelation(hitchhiker, event);
            return "ok";
        }
    }

    public void relateHitchhikerWithDriver(String hitchhikerEmail, Long driverId) {
        Hitchhiker hitchhiker = hitchhikerRepository.findByEmail(hitchhikerEmail);
        if (hitchhiker.getDrivers() == null) {
            driverRepository.findById(driverId).ifPresent(driver -> hitchhiker.setDrivers(Sets.newHashSet(driver)));
        } else {
            driverRepository.findById(driverId).ifPresent(driver -> hitchhiker.getDrivers().add(driver));
        }
        hitchhikerRepository.save(hitchhiker);
    }

    public Set<Hitchhiker> getHitchhikersByEventId(String id) {
        if (!eventRepository.existsByUuid(id)) {
            return null;
        }
        Event event = eventRepository.findByUuid(id);
        return event.getHitchhikers();
    }

    private Hitchhiker createHitchhikerAndStakeholderDbRelation(String stakeholderDtoEmail) {
        Stakeholder stakeholder = stakeholderRepository.findByEmail(stakeholderDtoEmail);
        if (hitchhikerRepository.findByEmail(stakeholderDtoEmail) == null) {
            hitchhikerRepository.save(Hitchhiker.builder()
                    .email(stakeholderDtoEmail)
                    .firstName(stakeholder.getFirstName())
                    .lastName(stakeholder.getLastName())
                    .email(stakeholderDtoEmail)
                    .stakeholder(stakeholder).build());
        }
        Hitchhiker hitchhiker = hitchhikerRepository.findByEmail(stakeholderDtoEmail);
        stakeholder.setHitchhiker(hitchhiker);
        stakeholderRepository.save(stakeholder);
        return hitchhiker;
    }

    private void createHitchhikerAndEventDbRelation(Hitchhiker hitchhiker, Event event) {
        if (hitchhiker.getEvents() == null) {
            hitchhiker.setEvents(Sets.newHashSet(event));
            hitchhikerRepository.save(hitchhiker);
        } else {
            Set<Event> events = hitchhiker.getEvents();
            events.add(event);
            hitchhiker.setEvents(events);
            hitchhikerRepository.save(hitchhiker);
        }
    }
}
