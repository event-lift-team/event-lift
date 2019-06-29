package pl.sda.eventlift.stakeholders.services;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.eventlift.events.model.Event;
import pl.sda.eventlift.events.pojo.EventDTO;
import pl.sda.eventlift.events.repositories.EventRepository;
import pl.sda.eventlift.events.services.EventsService;
import pl.sda.eventlift.information.dto.TransportInfoDTO;
import pl.sda.eventlift.information.model.TransportInfo;
import pl.sda.eventlift.information.repositories.TransportInfoRepository;
import pl.sda.eventlift.information.services.TransportInfoService;
import pl.sda.eventlift.stakeholders.model.Driver;
import pl.sda.eventlift.stakeholders.model.Stakeholder;
import pl.sda.eventlift.stakeholders.repositories.DriverRepository;
import pl.sda.eventlift.stakeholders.repositories.HitchhikerRepository;
import pl.sda.eventlift.stakeholders.repositories.StakeholderRepository;

import java.util.Set;

@Service
public class DriverSigningService {

    private EventRepository eventRepository;
    private EventsService eventsService;
    private DriverRepository driverRepository;
    private StakeholderRepository stakeholderRepository;
    private HitchhikerRepository hitchhikerRepository;
    private TransportInfoService transportInfoService;
    private TransportInfoRepository transportInfoRepository;

    @Autowired
    public DriverSigningService(EventRepository eventRepository,
                                EventsService eventsService,
                                DriverRepository driverRepository,
                                StakeholderRepository stakeholderRepository,
                                HitchhikerRepository hitchhikerRepository,
                                TransportInfoService transportInfoService,
                                TransportInfoRepository transportInfoRepository) {
        this.eventRepository = eventRepository;
        this.eventsService = eventsService;
        this.driverRepository = driverRepository;
        this.stakeholderRepository = stakeholderRepository;
        this.hitchhikerRepository = hitchhikerRepository;
        this.transportInfoService = transportInfoService;
        this.transportInfoRepository = transportInfoRepository;
    }

    public void updateDriverTransportInfo(Long driverId, String eventDtoId, TransportInfoDTO transportInfoDTO) {
        TransportInfo driverTransportInfo = transportInfoService.getDriverTransportInfo(eventDtoId, driverId);
        driverTransportInfo.setNumberOfSeats(transportInfoDTO.getNumberOfSeats());
        driverTransportInfo.setStartLocation(transportInfoDTO.getStartLocation());
        driverTransportInfo.setStartTime(driverTransportInfo.getStartTime());
        driverTransportInfo.setAdditionalInformation(transportInfoDTO.getAdditionalInformation());
        transportInfoRepository.save(driverTransportInfo);
    }

    public String relateDriverWithEvent(String driverEmail, String eventDtoId, TransportInfoDTO informationDto) {
        if (hitchhikerRepository.existsByEmail(driverEmail)
                && hitchhikerRepository.findByEmail(driverEmail).getEvents().contains(eventRepository.findByUuid(eventDtoId))) {
            return "signedAsHitch-hiker";
        }
        if (driverRepository.existsByEmail(driverEmail) &&
                driverRepository.findByEmail(driverEmail).getEvents().contains(eventRepository.findByUuid(eventDtoId))) {
            return "signedAsDriver";
        }
        Driver driver = createDriverAndStakeholderDbRelation(driverEmail);
        if (eventRepository.findByUuid(eventDtoId) == null) {
            EventDTO eventDtoById = eventsService.findEventDtoById(eventDtoId);
            Event event = eventsService.eventToEntity(eventDtoById);
            eventRepository.save(event);
            createDriverAndEventDbRelation(driver, event);
            transportInfoService.createInformationAndEventDbRelation(informationDto, eventDtoId, driverEmail);
            return "ok";
        } else {
            Event event = eventRepository.findByUuid(eventDtoId);
            createDriverAndEventDbRelation(driver, event);
            createDriverAndEventDbRelation(driver, event);
            transportInfoService.createInformationAndEventDbRelation(informationDto, eventDtoId, driverEmail);
            return "ok";
        }
    }

    public Set<Driver> getDriversByEventId(String id) {
        if (!eventRepository.existsByUuid(id)) {
            return null;
        }
        Event event = eventRepository.findByUuid(id);
        return event.getDrivers();
    }

    private Driver createDriverAndStakeholderDbRelation(String stakeholderDtoEmail) {
        Stakeholder stakeholder = stakeholderRepository.findByEmail(stakeholderDtoEmail);
        if (driverRepository.findByEmail(stakeholderDtoEmail) == null) {
            driverRepository.save(Driver.builder()
                    .email(stakeholderDtoEmail)
                    .firstName(stakeholder.getFirstName())
                    .lastName(stakeholder.getLastName())
                    .stakeholder(stakeholder).build());
        }
        Driver driver = driverRepository.findByEmail(stakeholderDtoEmail);
        stakeholder.setDriver(driver);
        stakeholderRepository.save(stakeholder);
        return driver;
    }

    private void createDriverAndEventDbRelation(Driver driver, Event event) {
        if (driver.getEvents() == null) {
            driver.setEvents(Sets.newHashSet(event));
            driverRepository.save(driver);
        } else {
            Set<Event> events = driver.getEvents();
            events.add(event);
            driver.setEvents(events);
            driverRepository.save(driver);
        }
    }
}
