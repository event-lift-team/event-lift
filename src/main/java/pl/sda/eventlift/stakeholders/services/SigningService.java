package pl.sda.eventlift.stakeholders.services;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.eventlift.events.model.Event;
import pl.sda.eventlift.events.pojo.EventDTO;
import pl.sda.eventlift.events.repositories.EventRepository;
import pl.sda.eventlift.events.services.EventsService;
import pl.sda.eventlift.stakeholders.model.Stakeholder;
import pl.sda.eventlift.stakeholders.repositories.StakeholderRepository;

import java.util.Set;

@Service
public class SigningService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    StakeholderRepository stakeholderRepository;

    @Autowired
    EventsService eventsService;

    public boolean relateStakeholderWithEvent(String stakeholderDtoEmail, String eventDtoId) {
        Stakeholder stakeholder = stakeholderRepository.findByEmail(stakeholderDtoEmail);
        if (eventRepository.findByUuid(eventDtoId) == null) {
            EventDTO eventDtoById = eventsService.findEventDtoById(eventDtoId);
            Event event = eventToEntity(eventDtoById);
            eventRepository.save(event);
            return createDbRelation(stakeholder, event);
        } else {
            Event event = eventRepository.findByUuid(eventDtoId);
            return createDbRelation(stakeholder, event);
        }
    }

    public Set<Stakeholder> getStakeholdersByEventId(String id){
        if(eventRepository.existsByUuid(id) == false){
            return null;
        }
        Event event = eventRepository.findByUuid(id);
        return event.getStakeholders();
    }

    private boolean createDbRelation(Stakeholder stakeholder, Event event) {
        if (stakeholder.getEvents() == null) {
            stakeholder.setEvents(Sets.newHashSet(event));
            stakeholderRepository.save(stakeholder);
            return true;
        } else {
            Set<Event> events = stakeholder.getEvents();
            events.add(event);
            stakeholder.setEvents(events);
            stakeholderRepository.save(stakeholder);
            return true;
        }
    }

    private Event eventToEntity(EventDTO eventDTO) {
        return Event.builder().name(eventDTO.getName()).uuid(eventDTO.getId()).build();
    }
}
